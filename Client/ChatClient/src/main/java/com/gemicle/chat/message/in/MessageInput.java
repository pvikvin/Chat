package com.gemicle.chat.message.in;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

public class MessageInput extends Thread {

	private InputStream sin;
	private DataInputStream in;
	private Socket socket;
	private ObjectMapper mapper = new ObjectMapper();

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

				String json = in.readUTF();
				Map<String, String> map = mapper.readValue(json, new TypeReference<Map<String, String>>() {
				});

				int m = 9;
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
