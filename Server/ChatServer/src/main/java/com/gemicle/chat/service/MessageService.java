package com.gemicle.chat.service;

import org.hibernate.classic.Session;

import com.gemicle.chat.pojo.Message;
import com.gemicle.chat.session.SessionService;

public class MessageService {
	private SessionService hibernateService = new SessionService();

	public long save(Message message) {
		Session session = hibernateService.openSession();
		long id = (long) session.save(message);
		hibernateService.commitAndCloseSession(session);
		return id;
	}

	public void delete(Message message){
		Session session = hibernateService.openSession();
		session.delete(message);
		hibernateService.commitAndCloseSession(session);
	}
}
