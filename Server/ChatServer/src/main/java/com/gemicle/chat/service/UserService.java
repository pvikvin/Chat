package com.gemicle.chat.service;

import org.hibernate.classic.Session;

import com.gemicle.chat.pojo.User;
import com.gemicle.chat.session.SessionService;

public class UserService {
	private SessionService hibernateService = new SessionService();

	public long save(User user) {
		Session session = hibernateService.openSession();
		long id = (long) session.save(user);
		hibernateService.commitAndCloseSession(session);
		return id;
	}

	public void delete(User user){
		Session session = hibernateService.openSession();
		session.delete(user);
		hibernateService.commitAndCloseSession(session);
	}
	
	public User get(long id){
		Session session = hibernateService.openSession();
		User user = (User)session.get(User.class, id);
		hibernateService.commitAndCloseSession(session);
		return user;
	}
}

