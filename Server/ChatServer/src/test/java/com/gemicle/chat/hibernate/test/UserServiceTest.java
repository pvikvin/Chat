package com.gemicle.chat.hibernate.test;

import org.junit.Assert;
import org.junit.Test;

import com.gemicle.chat.pojo.User;
import com.gemicle.chat.service.UserService;

public class UserServiceTest {

	private UserService userServiceDao = new UserService();

	@Test
	public void saveUserTest() {
		User user = new User("User test");
		user.setId(userServiceDao.save(user));
		Assert.assertTrue(user.getId() > 0);
	}
}
