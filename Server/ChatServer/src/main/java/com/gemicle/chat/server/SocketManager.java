package com.gemicle.chat.server;

import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import com.gemicle.chat.pojo.User;


public class SocketManager {
	
	private static Map<User, Socket> userSockets = new HashMap<User, Socket>();
	
	public static void add(User user, Socket socket){
		userSockets.put(user, socket);
	}
	
	public static void delete(User user){
		userSockets.remove(user);
	}
	
	public static Map<User, Socket> getUserSockets(){
		return userSockets;
	}
}
