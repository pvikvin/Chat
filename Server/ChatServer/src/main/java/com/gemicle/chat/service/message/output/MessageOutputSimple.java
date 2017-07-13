package com.gemicle.chat.service.message.output;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;

import com.gemicle.chat.pojo.Message;
import com.gemicle.chat.pojo.User;
import com.gemicle.chat.server.SocketManager;

public class MessageOutputSimple implements MessageOutput {

	private ObjectMapper mapper = new ObjectMapper();
	private Socket socketSender;
	private Message message;

	public MessageOutputSimple(Socket socketSender, Message message) {
		this.socketSender = socketSender;
		this.message = message;
	}

	@Override
	public void messageBuild() {
		String textMessage = message.getMessageText();
		message.setMessageText(SocketManager.getUser(socketSender).getName() + ": " + textMessage);

		sendMessageAllUsers();
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

	private void sendMessageAllUsers() {
		Map<User, Socket> userSockets = SocketManager.getUserSockets();
		for (Map.Entry<User, Socket> entry : userSockets.entrySet()) {
			sendMessageUser(entry.getValue(), message);
		}
	}

}
