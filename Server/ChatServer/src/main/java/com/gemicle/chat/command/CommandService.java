package com.gemicle.chat.command;

import java.net.Socket;

import com.gemicle.chat.enums.MethodsType;

public class CommandService {
	
	public Object executeCommand(MethodsType method, String object, Socket socket){
		switch(method){
		case CREATE_USER:
			return new UserGenerateCommand(object, method, socket).execute();
		case DELETE_USER:
			return new UserGenerateCommand(object, method, socket).execute();
		case CREATE_MESSAGE:
			return new MessageGenerateCommand(object, method).execute();
		default:
			break;
		}
		return null;
	}
}
