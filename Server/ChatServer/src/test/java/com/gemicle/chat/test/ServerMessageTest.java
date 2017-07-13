package com.gemicle.chat.test;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import com.gemicle.chat.pojo.Message;
import com.gemicle.chat.pojo.User;
import com.gemicle.chat.server.Server;

public class ServerMessageTest {

	private static final int PORT = 5555;
	private static final String IP = "127.0.0.1";
	private static final String OBJECT_KEY = "object";
	private static final String METHOD_KEY = "method";

	private Server server = new Server();
	private ObjectMapper mapper = new ObjectMapper();
	private String result = "";
	private User user;
	private Socket socket;
	private ExecutorService executor = Executors.newCachedThreadPool();
	

	@Test
	public void saveMessageTest() {
		try {
			server.start();

			InetAddress ipAddress = InetAddress.getByName(IP);
			for (int i = 1; i < 3; i++) {

				user = new User("User " + i);
				socket = new Socket(ipAddress, PORT);

				Map<String, String> parametrs = new HashMap<String, String>();
				parametrs.put(METHOD_KEY, "CREATE_USER");
				parametrs.put(OBJECT_KEY, mapper.writeValueAsString(user));
				
				result = mapper.writeValueAsString(parametrs);
				executor.submit(new OutputMessageThread(socket, result));
				executor.submit(new InputMessageThread(socket));
				
			}

			Message message = new Message();
			message.setDate(new Date());
			message.setUser_id(1);
			message.setMessageText("Hello!!!");

			Map<String, String> parametrs = new HashMap<String, String>();
			parametrs.put(METHOD_KEY, "CREATE_MESSAGE");
			parametrs.put(OBJECT_KEY, mapper.writeValueAsString(message));
			result = mapper.writeValueAsString(parametrs);

			Runnable outMessage = new OutputMessageThread(socket, result);
			Thread threadOut = new Thread(outMessage);
			threadOut.start();
			threadOut.join();

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			server.closeServer();
		}

	}

}
