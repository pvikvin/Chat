package com.gemicle.chat.service;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Date;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;

import com.gemicle.chat.enums.MethodsType;
import com.gemicle.chat.pojo.Message;
import com.gemicle.chat.pojo.User;
import com.gemicle.chat.server.SocketManager;

public class MessageOutput extends Thread {

	private MethodsType methodsType;
	private Message message;
	private User user;
	private Object obj;
	private UserService userService = new UserService();
	private ObjectMapper mapper = new ObjectMapper();
	private Socket socketSender;

	public MessageOutput(Object obj, MethodsType methodsType, Socket socketSender) {
		this.obj = obj;
		this.methodsType = methodsType;
		this.socketSender = socketSender;
	}

	@Override
	public void run() {
		generateMessage();
	}

	private void generateMessage(){
		switch(methodsType){
		case CREATE_USER:
			user = (User)obj;
			
			sendMessageUser(socketSender, user);
			
			message = new Message();
			message.setDate(new Date());
			message.setMessageText("Connected new user: " + user.getName());
			message.setUser_id(user.getId());
			
			break;
		case CREATE_MESSAGE:
			message = (Message)obj;
			user = userService.get(message.getUser_id());
			String textMessage = message.getMessageText();
			message.setMessageText(user.getName() + ": " + textMessage);
			break;
		default:
			break;
		}
		sendMessageAllUsers();
	}
	
	private void sendMessageAllUsers() {
		Map<User, Socket> userSockets = SocketManager.getUserSockets();
		for (Map.Entry<User, Socket> entry : userSockets.entrySet()) {
			sendMessageUser(entry.getValue(), message);
		}
	}

	private void sendMessageUser(Socket socket, Object obj) {
		try {

			OutputStream sout = socket.getOutputStream();
			DataOutputStream out = new DataOutputStream(sout);

			String json = mapper.writeValueAsString(obj);

			out.writeUTF(json);
			out.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
