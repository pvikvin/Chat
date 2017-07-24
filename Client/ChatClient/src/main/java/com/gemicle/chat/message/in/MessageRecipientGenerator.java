package com.gemicle.chat.message.in;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.gemicle.chat.enums.MethodsType;
import com.gemicle.chat.jframes.MainFrame;
import com.gemicle.chat.pojo.FileMessage;
import com.gemicle.chat.pojo.Message;
import com.gemicle.chat.pojo.User;

public class MessageRecipientGenerator {

	private Map<String, String> map = new HashMap<String, String>();
	private ObjectMapper mapper = new ObjectMapper();
	private MessageRecipient messageRecipient;

	private MainFrame main;

	private static final String OBJECT_KEY = "object";
	private static final String METHOD_KEY = "method";

	public MessageRecipientGenerator(Map<String, String> map, MainFrame main) {
		this.map = map;
		this.main = main;

		distributionMessage();
	}

	private void distributionMessage() {
		try {
			switch (MethodsType.valueOf(map.get(METHOD_KEY))) {
			case CREATE_USER:
				User user = mapper.readValue(map.get(OBJECT_KEY), User.class);
				messageRecipient = new MessageInputUser(user);
				break;
			case CREATE_MESSAGE:
				Message message = mapper.readValue(map.get(OBJECT_KEY), Message.class);
				messageRecipient = new MessageInputSimple(message, main);
				break;
			case CREATE_MESSAGE_FILE:
				FileMessage fileMessage = mapper.readValue(map.get(OBJECT_KEY), FileMessage.class);
				messageRecipient = new MessageFile(fileMessage, main);
				break;	
			default:
				break;
			}
			messageRecipient.processingIncomingMessages();

		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
