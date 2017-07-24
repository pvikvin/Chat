package com.gemicle.chat.command;

import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.gemicle.chat.pojo.FileMessage;
import com.gemicle.chat.pojo.Message;
import com.gemicle.chat.service.FileMessageHibernate;
import com.gemicle.chat.service.MessageServiceHibernate;

public class MessageFileSave implements Command<FileMessage> {

	private ObjectMapper mapper = new ObjectMapper();
	private FileMessageHibernate service = new FileMessageHibernate();
	private String jsonObject;

	public MessageFileSave(String jsonObject) {
		this.jsonObject = jsonObject;
	}

	@Override
	public FileMessage execute() {
		FileMessage fileMessage = null;
		try {
			fileMessage = mapper.readValue(jsonObject, FileMessage.class);
			fileMessage.setId(service.save(fileMessage));
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return fileMessage;
	}

}
