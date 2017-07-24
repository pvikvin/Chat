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
import com.gemicle.chat.pojo.FileMessage;
import com.gemicle.chat.pojo.Message;
import com.gemicle.chat.pojo.User;
import com.gemicle.chat.server.SocketManager;

public class MessageOutputFile implements MessageOutput{
	
	private static final String OBJECT_KEY = "object";
	private static final String METHOD_KEY = "method";
	
	private ObjectMapper mapper = new ObjectMapper();
	private Socket socketSender;
	private FileMessage fileMessage;
	
	public MessageOutputFile(Socket socketSender, FileMessage fileMessage){
		this.socketSender = socketSender;
		this.fileMessage = fileMessage;
	}

	@Override
	public void messageBuild() {
		Message message = new Message();
		message.setDate(new Date());
		message.setMessageText(": send file " + fileMessage.getName());
		message.setUser_id(SocketManager.getUser(socketSender).getId());
		MessageOutput messageOut = new MessageOutputSimple(socketSender, message);
		messageOut.messageBuild();
		
		sendMessageAllUsers();
	}

	@Override
	public void sendMessageUser(Socket socket, Object obj) {
		
		try {
			Map<String, String> parametrs = new HashMap<String, String>();
			parametrs.put(METHOD_KEY, MethodsType.CREATE_MESSAGE_FILE.toString());
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
			if(userSockets == socketSender ){
			//	continue;
			}
			sendMessageUser(entry.getValue(), fileMessage);
		}
	}
}
