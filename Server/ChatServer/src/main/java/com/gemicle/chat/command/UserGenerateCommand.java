package com.gemicle.chat.command;

import java.io.IOException;
import java.net.Socket;

import org.codehaus.jackson.map.ObjectMapper;

import com.gemicle.chat.enums.MethodsType;
import com.gemicle.chat.pojo.User;
import com.gemicle.chat.server.SocketManager;
import com.gemicle.chat.service.UserService;

public class UserGenerateCommand implements Command<User> {

	private ObjectMapper mapper = new ObjectMapper();
	private UserService userService = new UserService();
	private Socket socket;
	private MethodsType method;
	private String jsonObject = "";
	
	public UserGenerateCommand(String jsonObject, MethodsType method, Socket socket){
		this.jsonObject = jsonObject;
		this.method = method;
		this.socket = socket;
	}
	
	@Override
	public User execute() {
		User user = null;
		switch(method){
		case CREATE_USER:
			user = saveUser(jsonObject);
			SocketManager.add(user, socket);
			break;
		case DELETE_USER:
			user = deleteUser(jsonObject);
			SocketManager.delete(user);
			break;
		default:
			break;
		}
		return user;
	}
	
	private User saveUser(String jsonObject){
		User user = null;
		try {
			user = mapper.readValue(jsonObject, User.class);			
			user.setId(userService.save(user));			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return user;
	}

	private User deleteUser(String jsonObject){
		User user = null;
		try {
			user = mapper.readValue(jsonObject, User.class);
			userService.delete(user);
		} catch (IOException e) {
			e.printStackTrace();
		}	
		return user;
	}
	
	
}
