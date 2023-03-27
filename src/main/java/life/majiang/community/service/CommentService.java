package life.majiang.community.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import life.majiang.community.dto.CommentDTO;
import life.majiang.community.enums.CommentTypeEnum;
import life.majiang.community.exception.CustomizeErrorCode;
import life.majiang.community.exception.CustomizeException;
import life.majiang.community.mapper.CommentMapper;
import life.majiang.community.mapper.QuestionExtMapper;
import life.majiang.community.mapper.QuestionMapper;
import life.majiang.community.mapper.UserMapper;
import life.majiang.community.model.Comment;
import life.majiang.community.model.CommentExample;
import life.majiang.community.model.Question;
import life.majiang.community.model.User;
import life.majiang.community.model.UserExample;

@Service
public class CommentService {
	@Autowired
	private CommentMapper commentMapper;
	@Autowired
	private QuestionMapper questionMapper;
	@Autowired
	private QuestionExtMapper questionExtMapper;
	@Autowired
	private UserMapper userMapper;

	// @Transactional 事务回滚，方法体中如果有一个失败的情况下，方法体中完成的操作全部取消
	@Transactional
	public void insert(Comment comment) {
		if (comment.getParentId() == null || comment.getParentId() == 0) {
			throw new CustomizeException(CustomizeErrorCode.TARGET_NOT_FOUND);
		}

		if (comment.getType() == null || !CommentTypeEnum.isExist(comment.getType())) {
			throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
		}

		if (comment.getType() == CommentTypeEnum.COMMENT.getType()) {
			// 回复评论
			Comment dbComment = commentMapper.selectByPrimaryKey(comment.getParentId());
			if (dbComment == null) {
				throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
			}

			commentMapper.insert(comment);
		} else {
			// 回复问题
			Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
			if (question == null) {
				throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
			}
			commentMapper.insert(comment);
			question.setCommentCount(1);
			questionExtMapper.incCommentCount(question);
		}
	}

	public List<CommentDTO> listByTargetId(Integer id,CommentTypeEnum type) {
		// 根据id和问题类型来查询对应的评论列表
		CommentExample commentExample = new CommentExample();
		commentExample.createCriteria()
				.andParentIdEqualTo(id)
				.andTypeEqualTo(type.getType());
		List<Comment> comments = commentMapper.selectByExample(commentExample);
		
		// 没有查询到，返回一个空的List，表示该问题没有任何评论
		if(comments.size() == 0) {
			return new ArrayList<>();
		}
		
		// 使用lambda获取去重的评论人
		Set<Integer> commentators = comments.stream().map(comment->comment.getCommentator()).collect(Collectors.toSet());
		List<Integer> userIds = new ArrayList();
		userIds.addAll(commentators);
		
		// 获取评论人并转换为Map
		UserExample userExample = new UserExample();
		userExample.createCriteria()
					.andIdIn(userIds); 
		List<User> users = userMapper.selectByExample(userExample);
		Map<Integer, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));
		
		// 转换Comment 为CommentDTO
		List<CommentDTO> commentDTOS = comments.stream().map(comment->{
			CommentDTO commentDTO = new CommentDTO();
			BeanUtils.copyProperties(comment, commentDTO);
			commentDTO.setUser(userMap.get(comment.getCommentator()));
			return commentDTO;
		}).collect(Collectors.toList());
		
		return commentDTOS;
	}
}
