package com.gemicle.chat.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.channels.SocketChannel;

public class Chat {
	
	private static final String IP_SERVER = "127.0.0.1";
	private static final int PORT = 5555;
	private static final String METHOD_NUMBER = "methodNumber";
	
	
	public static void main(String[] args){
	
		try {
			InetAddress ipAddress = InetAddress.getByName(IP_SERVER);
			Socket socket = new Socket(ipAddress, PORT);
			
			InputStream sin = socket.getInputStream();
            OutputStream sout = socket.getOutputStream();
            
            DataInputStream in = new DataInputStream(sin);
            DataOutputStream out = new DataOutputStream(sout);

			
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
