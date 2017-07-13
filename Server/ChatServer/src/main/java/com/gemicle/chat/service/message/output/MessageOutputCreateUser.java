package com.gemicle.chat.service.message.output;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Date;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;

import com.gemicle.chat.pojo.Message;
import com.gemicle.chat.pojo.User;
import com.gemicle.chat.server.SocketManager;

public class MessageOutputCreateUser implements MessageOutput {

	private ObjectMapper mapper = new ObjectMapper();
	private Socket socketSender;
	private User user;

	public MessageOutputCreateUser(Socket socketSender, User user) {
		this.socketSender = socketSender;
		this.user = user;
	}

	@Override
	public void messageBuild() {
		sendMessageUser(socketSender, user);

		Message message = new Message();
		message.setDate(new Date());
		message.setMessageText("Connected new user: " + user.getName());
		message.setUser_id(user.getId());

		sendMessageAllUsers(message);
	}
	
	@Override
	public void sendMessageUser(Socket socket, Object obj) {
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
	
	private void sendMessageAllUsers(Message message) {
		Map<User, Socket> userSockets = SocketManager.getUserSockets();
		for (Map.Entry<User, Socket> entry : userSockets.entrySet()) {
			sendMessageUser(entry.getValue(), message);
		}
	}

}
