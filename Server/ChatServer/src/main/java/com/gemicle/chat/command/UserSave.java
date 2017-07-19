package com.gemicle.chat.command;

import java.io.IOException;
import java.net.Socket;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.gemicle.chat.pojo.User;
import com.gemicle.chat.server.SocketManager;
import com.gemicle.chat.service.UserService;

public class UserSave implements Command<User> {

	private ObjectMapper mapper = new ObjectMapper();
	private UserService userService = new UserService();
	private Socket socket;
	private String jsonObject = "";

	public UserSave(String jsonObject, Socket socket) {
		this.jsonObject = jsonObject;
		this.socket = socket;
	}

	@Override
	public User execute() {
		User user = null;
		try {
			user = mapper.readValue(jsonObject, User.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		user.setId(userService.save(user));
		SocketManager.add(user, socket);
		return user;
	}
}
