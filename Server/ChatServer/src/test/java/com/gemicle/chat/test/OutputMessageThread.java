package com.gemicle.chat.test;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class OutputMessageThread extends Thread {

	private OutputStream sout;
	private Socket socket;
	private String result;

	public OutputMessageThread(Socket socket, String result) {
		this.socket = socket;
		this.result = result;
	}

	@Override
	public void run() {
		try {
			sout = socket.getOutputStream();
			DataOutputStream out = new DataOutputStream(sout);
			out.writeUTF(result);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
