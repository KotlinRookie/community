package life.majiang.community.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import life.majiang.community.mapper.UserMapper;
import life.majiang.community.model.User;
import life.majiang.community.model.UserExample;

@Service
public class UserService {

	@Autowired
	private UserMapper userMapper;

	public void creareOrUpdate(User user) {
		UserExample userExample = new UserExample();
		userExample.createCriteria().andAccountIdEqualTo(user.getAccountId());
		List<User> users = userMapper.selectByExample(userExample);
		if (users.size() == 0) {
			// 查看AccountId在数据库中有没有，如果有就做插入操作
			user.setGmtCreate(System.currentTimeMillis());
			user.setGmtModified(user.getGmtCreate());
			userMapper.insert(user);
		} else {
			// 如果AccountId在数据库中没有，就做更新操作
			User dbUser = users.get(0);
			User updateUser = new User();
			updateUser.setGmtModified(System.currentTimeMillis());
			updateUser.setAvatarUrl(user.getAvatarUrl());
			updateUser.setName(user.getName());
			updateUser.setToken(user.getToken());
			UserExample example = new UserExample();
			example.createCriteria().andIdEqualTo(dbUser.getId());
			userMapper.updateByExampleSelective(updateUser, example);
		}
	}
}
