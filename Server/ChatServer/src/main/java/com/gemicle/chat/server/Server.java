package com.gemicle.chat.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.gemicle.chat.service.MessageInput;

public class Server extends Thread {

	private static final int PORT = 5555;
	private ServerSocket ss;

	@Override
	public void run() {
		startServer();
	}

	public void startServer() {
		try {
			ss = new ServerSocket(PORT);
			while (true) {
				Socket socket = ss.accept();
				Thread messageSender = new MessageInput(socket);
				messageSender.start();
			}

		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}

	public void closeServer() {
		try {
			ss.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
