package com.gemicle.chat.service.message.output;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;

import com.gemicle.chat.enums.MethodsType;
import com.gemicle.chat.pojo.Message;
import com.gemicle.chat.pojo.User;

public class MessageOutputCreateUser implements MessageOutput {

	private static final String OBJECT_KEY = "object";
	private static final String METHOD_KEY = "method";
	
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
		message.setMessageText(": сonnected new user");
		message.setUser_id(user.getId());

		new MessageOutputSimple(socketSender, message).messageBuild();
	}

	@Override
	public void sendMessageUser(Socket socket, Object obj) {
		try {
			Map<String, String> parametrs = new HashMap<String, String>();
			parametrs.put(METHOD_KEY, MethodsType.CREATE_USER.toString());
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

}
