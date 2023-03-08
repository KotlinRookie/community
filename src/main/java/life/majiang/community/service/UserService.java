package life.majiang.community.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import life.majiang.community.mapper.UserMapper;
import life.majiang.community.model.User;

@Service
public class UserService {

	@Autowired
	private UserMapper userMapper;

	public void creareOrUpdate(User user) {
		User dbUser = userMapper.findByAccountId(user.getAccountId());
		if(dbUser == null) {
			// 查看AccountId在数据库中有没有，如果有就做插入操作
			user.setGmtCreate(System.currentTimeMillis());
			user.setGmtModified(user.getGmtCreate());
			userMapper.insert(dbUser);
		}else {
			// 如果AccountId在数据库中没有，就做更新操作
			dbUser.setGmtModified(System.currentTimeMillis());
			dbUser.setAvatarUrl(user.getAvatarUrl());
			dbUser.setName(user.getName());
			dbUser.setToken(user.getToken());
			userMapper.update(dbUser);
		}
	}	
}
