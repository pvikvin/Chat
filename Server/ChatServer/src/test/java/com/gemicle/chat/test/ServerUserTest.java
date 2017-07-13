package com.gemicle.chat.test;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;

import com.gemicle.chat.pojo.User;
import com.gemicle.chat.server.Server;

public class ServerUserTest {

	private static final int PORT = 5555;
	private static final String IP = "127.0.0.1";
	private static final String OBJECT_KEY = "object";
	private static final String METHOD_KEY = "method";
	
	private Server server = new Server();
	private ObjectMapper mapper = new ObjectMapper();
	private Map<String, String> parametrs = new HashMap<String, String>();
	private String result = "";
	private String resultServer = "";

	@Test
	public void registrationUserTest() {
		try {
			server.start();

			User user = new User("User test");

			InetAddress ipAddress = InetAddress.getByName(IP);
			Socket socket = new Socket(ipAddress, PORT);
			
			InputStream sin = socket.getInputStream();
			OutputStream sout = socket.getOutputStream();

			DataInputStream in = new DataInputStream(sin);
			DataOutputStream out = new DataOutputStream(sout);

			parametrs.put(METHOD_KEY, "CREATE_USER");
			parametrs.put(OBJECT_KEY, mapper.writeValueAsString(user));

			result = mapper.writeValueAsString(parametrs);

			out.writeUTF(result);
			out.flush();
			resultServer = in.readUTF();

			User userResult = mapper.readValue(resultServer, User.class);

			Assert.assertTrue(userResult.getId() > 0);

			server.closeServer();

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			server.closeServer();
		}

	}

	@Test
	public void deleteUserTest(){
		try {
			server.start();

			User user = new User("User delete");

			InetAddress ipAddress = InetAddress.getByName(IP);
			Socket socket = new Socket(ipAddress, PORT);

			InputStream sin = socket.getInputStream();
			OutputStream sout = socket.getOutputStream();

			DataInputStream in = new DataInputStream(sin);
			DataOutputStream out = new DataOutputStream(sout);

			parametrs.put(METHOD_KEY, "CREATE_USER");
			parametrs.put(OBJECT_KEY, mapper.writeValueAsString(user));

			result = mapper.writeValueAsString(parametrs);

			out.writeUTF(result);
			out.flush();
			resultServer = in.readUTF();

			User userResult = mapper.readValue(resultServer, User.class);
			
			// delete
			
			parametrs.put(METHOD_KEY, "DELETE_USER");
			parametrs.put(OBJECT_KEY, mapper.writeValueAsString(userResult));
			result = mapper.writeValueAsString(parametrs);
			
			out.writeUTF(result);
			out.flush();
			resultServer = in.readUTF();
			User userDelete = mapper.readValue(resultServer, User.class);
			
			Assert.assertTrue(userDelete.getId() > 0);

			server.closeServer();

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			server.closeServer();
		}
	}
	
	
}
