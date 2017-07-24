package com.gemicle.chat.service;

import org.hibernate.classic.Session;

import com.gemicle.chat.pojo.FileMessage;
import com.gemicle.chat.session.SessionService;

public class FileMessageHibernate {
	private SessionService hibernateService = new SessionService();

	public long save(FileMessage fileMessage) {
		Session session = hibernateService.openSession();
		long id = (long) session.save(fileMessage);
		hibernateService.commitAndCloseSession(session);
		return id;
	}

	public void delete(FileMessage fileMessage){
		Session session = hibernateService.openSession();
		session.delete(fileMessage);
		hibernateService.commitAndCloseSession(session);
	}
}
