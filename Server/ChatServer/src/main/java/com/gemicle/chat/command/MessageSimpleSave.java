package com.gemicle.chat.command;

import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.gemicle.chat.pojo.Message;

public class MessageSimpleSave implements Command<Message> {

	private ObjectMapper mapper = new ObjectMapper();
	private MessageServiceHibernate service = new MessageServiceHibernate();
	private String jsonObject;

	public MessageSimpleSave(String jsonObject) {
		this.jsonObject = jsonObject;
	}

	@Override
	public Message execute() {
		Message message = null;
		try {
			message = mapper.readValue(jsonObject, Message.class);
			message.setId(service.save(message));
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return message;
	}

}
