package com.gemicle.chat.service;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import com.gemicle.chat.command.CommandService;
import com.gemicle.chat.enums.MethodsType;

public class MessageInput extends Thread {

	private InputStream sin;
	private DataInputStream in;
	private Socket socket;
	private ObjectMapper mapper = new ObjectMapper();
	private CommandService commandService = new CommandService();
	private ExecutorService executor;
	
	private static final String OBJECT_KEY = "object";
	private static final String METHOD_KEY = "method";

	public MessageInput(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		inputMessage();
	}

	private void inputMessage() {
		try {
			while (true) {
				sin = socket.getInputStream();
				in = new DataInputStream(sin);
				Map<String, String> map = new HashMap<String, String>();

				String json = in.readUTF();
				map = mapper.readValue(json, new TypeReference<Map<String, String>>() {
				});

				Object obj = commandService.executeCommand(MethodsType.valueOf(map.get(METHOD_KEY)),
						map.get(OBJECT_KEY), socket);
				
				
				Thread messageSender = new MessageOutput(obj, MethodsType.valueOf(map.get(METHOD_KEY)), socket);
				messageSender.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
