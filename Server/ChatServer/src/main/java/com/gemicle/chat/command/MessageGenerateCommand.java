package com.gemicle.chat.command;

import java.io.IOException;

import org.codehaus.jackson.map.ObjectMapper;

import com.gemicle.chat.enums.MethodsType;
import com.gemicle.chat.pojo.Message;
import com.gemicle.chat.service.message.MessageServiceHibernate;

public class MessageGenerateCommand implements Command<Message>{

	private ObjectMapper mapper = new ObjectMapper();
	private MessageServiceHibernate service = new MessageServiceHibernate();
	private MethodsType method;
	private String jsonObject = "";
	
	public MessageGenerateCommand(String jsonObject, MethodsType method){
		this.jsonObject = jsonObject;
		this.method = method;
	}
	
	@Override
	public Message execute() {
		Message message = null;
		switch(method){
		case CREATE_MESSAGE:
			message = saveMessage(jsonObject);
			break;
		default:
			break;
		}
		return message;
	}
	
	private Message saveMessage(String jsonObject){
		Message message = null;
		try {
			message = mapper.readValue(jsonObject, Message.class);			
			message.setId(service.save(message));			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return message;	
	}
	
	

}
