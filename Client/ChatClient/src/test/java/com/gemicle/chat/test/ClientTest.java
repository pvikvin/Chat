package com.gemicle.chat.test;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import org.junit.Test;

import com.gemicle.chat.jframes.AuthorizationFrame;
import com.gemicle.chat.jframes.MainFrame;
import com.gemicle.chat.message.in.MessageInput;
import com.gemicle.chat.preferences.Preference;

public class ClientTest {

	private static final int PORT = 5555;
	private static final String IP = "127.0.0.1";

	@Test
	public void connectServer() {
		try {
			if (Preference.socket == null) {
				Preference.socket = new Socket(IP, PORT);
			}
			if(Preference.user == null || Preference.user.getId() == 0){
				new AuthorizationFrame();
			}
			
//			MessageInput input = new MessageInput();
//			input.start();
			
			int p = 9;

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
