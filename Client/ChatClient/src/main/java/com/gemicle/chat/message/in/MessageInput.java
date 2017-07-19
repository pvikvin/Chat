package com.gemicle.chat.message.in;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import com.gemicle.chat.enums.MethodsType;
import com.gemicle.chat.jframes.MainFrame;
import com.gemicle.chat.preferences.Preference;
import com.gemicle.chat.server.SocketManager;

public class MessageInput extends Thread {

	private InputStream sin;
	private DataInputStream in;
	private ObjectMapper mapper = new ObjectMapper();
	private MainFrame main;



	public MessageInput(MainFrame main) {
		this.main = main;
	}

	@Override
	public void run() {
		inputMessage();
	}

	private void inputMessage() {
		try {
			while (true) {
				sin = SocketManager.getSocket().getInputStream();
				in = new DataInputStream(sin);

				String json = in.readUTF();
				Map<String, String> map = mapper.readValue(json, new TypeReference<Map<String, String>>() {
				});

				new MessageRecipientGenerator(map, main);

				// Object obj =
				// commandService.executeCommand(MethodsType.valueOf(map.get(METHOD_KEY)),
				// map.get(OBJECT_KEY));

				int m = 9;

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
