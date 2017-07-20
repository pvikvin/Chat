package com.gemicle.chat.message.out;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.gemicle.chat.enums.MethodsType;
import com.gemicle.chat.jframes.MainFrame;
import com.gemicle.chat.pojo.Message;
import com.gemicle.chat.preferences.Preference;
import com.gemicle.chat.service.FileService;

public class MessageFile extends MessageSender {

	private static final String OBJECT_KEY = "object";
	private static final String METHOD_KEY = "method";
	private static ObjectMapper mapper = new ObjectMapper();
	private static MainFrame mainFrame;
	private static FileService fileService = new FileService();
	
	private static MessageParameter messageParameter = new MessageParameter() {

		@Override
		public Map<String, String> generateParameters() {
			Map<String, String> parametersMap = null;
			try {
				parametersMap = new HashMap<String, String>();
				parametersMap.put(METHOD_KEY, MethodsType.CREATE_MESSAGE.toString());
				parametersMap.put(OBJECT_KEY, mapper.writeValueAsString(generateMessage()));
			} catch (JsonGenerationException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			return parametersMap;
		}
	};

	public MessageFile(MainFrame mainFrame) {
		super(messageParameter);
		this.mainFrame = mainFrame;
	}

	private static Message generateMessage() {

		Message message = new Message();
		message.setUser_id(Preference.user.getId());
		message.setMessageText(mainFrame.getTextMessageUser().getText());
		message.setDate(new Date());
		//mainFrame.getLblPathFile().getText()
		try {
			File file = new File("D:/img/google.png");
			byte[] bytes = fileService.convertToByteArray(file);
			message.setFile(bytes);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return message;
	}
}
