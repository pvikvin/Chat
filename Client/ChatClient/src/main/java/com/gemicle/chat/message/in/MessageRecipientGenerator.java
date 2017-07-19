package com.gemicle.chat.message.in;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.gemicle.chat.enums.MethodsType;
import com.gemicle.chat.jframes.MainFrame;
import com.gemicle.chat.pojo.User;

public class MessageRecipientGenerator {

	private Map<String, String> map = new HashMap<String, String>();
	private ObjectMapper mapper = new ObjectMapper();
	private MessageRecipient messageRecipient;
	private Object obj;
	private MainFrame main;
	
	private static final String OBJECT_KEY = "object";
	private static final String METHOD_KEY = "method";

	public MessageRecipientGenerator(Map<String, String> map, MainFrame main) {
		this.map = map;
		this.main = main;
		
		this.obj = map.get(OBJECT_KEY);
		
		distribution();
	}

	private void distribution() {
		
		switch (MethodsType.valueOf(map.get(METHOD_KEY))) {
		case CREATE_USER:
			User user = null;
			try {
				user = mapper.readValue(map.get(OBJECT_KEY), User.class);
			} catch (JsonParseException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			messageRecipient = new MessageInputUser(user);
			break;
		case CREATE_MESSAGE:
			
			break;
		default:
			break;
		}
		messageRecipient.processingIncomingMessages();
	}
}
