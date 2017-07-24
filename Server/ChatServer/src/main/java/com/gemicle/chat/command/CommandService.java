package com.gemicle.chat.command;

import java.net.Socket;

import com.gemicle.chat.enums.MethodsType;

public class CommandService {
	
	public Object executeCommand(MethodsType method, String jsonObj, Socket socket){
		switch(method){
		case CREATE_USER:
			return new UserSave(jsonObj, socket).execute();
		case CREATE_MESSAGE:
			return new MessageSimpleSave(jsonObj).execute();
		case CREATE_MESSAGE_FILE:
			return new MessageFileSave(jsonObj).execute();
		default:
			break;
		}
		return null;
	}
}
