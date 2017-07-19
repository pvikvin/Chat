package com.gemicle.chat.test;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import org.junit.Test;

import com.gemicle.chat.jframes.AuthorizationFrame;
import com.gemicle.chat.preferences.Preference;

public class AuthorizationFrameTest {

	private static final int PORT = 5555;
	private static final String IP = "127.0.0.1";
	private AuthorizationFrame authorization = new AuthorizationFrame();

	@Test
	public void connectServer() {
		try {
			if (Preference.socket == null) {
				Preference.socket = new Socket(IP, PORT);
			}
			if(Preference.user == null){
				authorization.setVisible(true);
				//authorization.initialize();
			}

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
