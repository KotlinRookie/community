package life.majiang.community.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import life.majiang.community.dto.NotificaitonDTO;
import life.majiang.community.dto.PaginationDTO;
import life.majiang.community.enums.NotificationTypeEnum;
import life.majiang.community.exception.CustomizeErrorCode;
import life.majiang.community.exception.CustomizeException;
import life.majiang.community.mapper.NotificationMapper;
import life.majiang.community.mapper.UserMapper;
import life.majiang.community.model.Notification;
import life.majiang.community.model.NotificationExample;
import life.majiang.community.model.User;
import life.majiang.community.model.UserExample;


@Service
public class NotificationService {

	@Autowired
	private NotificationMapper notificationMapper;
	@Autowired
	private UserMapper userMapper;

	public PaginationDTO<NotificaitonDTO> list(Integer userId, Integer page, Integer size) {

		PaginationDTO<NotificaitonDTO> paginationDTO = new PaginationDTO<>();

		Integer totalPage;

		NotificationExample notificationExample = new NotificationExample();
		notificationExample.createCriteria().andReceiverEqualTo(userId);
		Integer totalCount = (int) notificationMapper.countByExample(notificationExample);

		if (totalCount % size == 0) {
			totalPage = totalCount / size;
		} else {
			totalPage = totalCount / size + 1;
		}

		if (page < 1) {
			page = 1;
		}
		if (page > totalPage) {
			page = totalPage;
		}

		paginationDTO.setPagination(totalCount, page, size);

		// size*(page-1)
		Integer offset = size * (page - 1);
		NotificationExample example = new NotificationExample();
		example.createCriteria().andReceiverEqualTo(userId);
		List<Notification> notifications = notificationMapper.selectByExampleWithRowbounds
				(example,new RowBounds(offset, size));
		
		if(notifications.size() == 0) {
			return paginationDTO;
		}
		
		
		List<NotificaitonDTO> notificationsDTOS = new ArrayList<>();

		for(Notification notification : notifications) {
			NotificaitonDTO notificaitonDTO = new NotificaitonDTO();
			BeanUtils.copyProperties(notification, notificaitonDTO);
			notificaitonDTO.setType(NotificationTypeEnum.nameOfType(notification.getType()));
			notificationsDTOS.add(notificaitonDTO);
		}
		
		/*
		 * Set<Integer> disUserIds = notifications.stream().map(notify ->
		 * notify.getNotifier()).collect(Collectors.toSet()); List<Integer> userIds =
		 * new ArrayList<>(); UserExample userExample = new UserExample();
		 * userExample.createCriteria().andIdIn(userIds); List<User> users =
		 * userMapper.selectByExample(userExample); Map<Integer, User> userMap =
		 * users.stream().collect(Collectors.toMap(u->u.getId(),u->u));
		 */
		
		paginationDTO.setData(notificationsDTOS);
		return paginationDTO;
	}

	public Integer unreadCount(Integer userId) {
		NotificationExample notificationExample = new NotificationExample();
		notificationExample.createCriteria().andReceiverEqualTo(userId);
		return notificationMapper.countByExample(notificationExample);
		
	}

	public NotificaitonDTO read(Integer id, User user) {
		Notification notification = notificationMapper.selectByPrimaryKey(id);
		// 如果登入用户信息不相等则抛出异常
		if(notification == null) {
			throw new CustomizeException(CustomizeErrorCode.NOTIFICATION_NOT_FOUND);
		}
		if(!Objects.equals(notification.getReceiver(), user.getId())) {
			throw new CustomizeException(CustomizeErrorCode.READ_NOTIFICATION_FAIL);
		}
		return null;
	}

}
