package com.gemicle.chat.test;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.concurrent.Callable;

import org.codehaus.jackson.map.ObjectMapper;

import com.gemicle.chat.pojo.Message;
import com.gemicle.chat.pojo.User;
import com.gemicle.interfaces.test.CallBack;

public class InputMessageThread extends Thread {

	private ObjectMapper mapper = new ObjectMapper();
	private Socket socket;
	private CallBack callback;
	private InputStream sin;
	private DataInputStream in;
	private long counter = 0;

	public InputMessageThread(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		try {
			sin = socket.getInputStream();
			in = new DataInputStream(sin);
			String result = "";
			while (true) {
				result = in.readUTF();
				
				if(counter == 0){
					User user = mapper.readValue(result, User.class);
				}else{
					Message message = mapper.readValue(result, Message.class);;
				}
				
				counter++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
