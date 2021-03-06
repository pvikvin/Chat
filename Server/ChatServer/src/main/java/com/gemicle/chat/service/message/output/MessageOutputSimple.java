package com.gemicle.chat.service.message.output;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;

import com.gemicle.chat.enums.MethodsType;
import com.gemicle.chat.pojo.Message;
import com.gemicle.chat.pojo.User;
import com.gemicle.chat.server.SocketManager;

public class MessageOutputSimple implements MessageOutput {

	private static final String OBJECT_KEY = "object";
	private static final String METHOD_KEY = "method";

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

			Map<String, String> parametrs = new HashMap<String, String>();
			parametrs.put(METHOD_KEY, MethodsType.CREATE_MESSAGE.toString()); 
			parametrs.put(OBJECT_KEY, mapper.writeValueAsString(obj));

			
			OutputStream sout = socket.getOutputStream();
			DataOutputStream out = new DataOutputStream(sout);

			String json = mapper.writeValueAsString(parametrs);

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
