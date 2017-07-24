package com.gemicle.chat.server;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import com.gemicle.chat.preferences.Preference;

public class SocketManager {
	public static void connectToServer(String ip, int port) {
		try {
			if (Preference.socket == null) {
				Preference.socket = new Socket(ip, port);
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Socket getSocket() {
		return Preference.socket;
	}
}
