package com.gemicle.chat.message.out;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.gemicle.chat.enums.MethodsType;
import com.gemicle.chat.jframes.MainFrame;
import com.gemicle.chat.pojo.FileMessage;
import com.gemicle.chat.preferences.Buffer;
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
				parametersMap.put(METHOD_KEY, MethodsType.CREATE_MESSAGE_FILE.toString());
				parametersMap.put(OBJECT_KEY, mapper.writeValueAsString(generateFile()));
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

	private static FileMessage generateFile() {
		FileMessage fileMessage = null;
		try {
			File file = new File(Buffer.fileBuffer);
			
			fileMessage = new FileMessage();
			fileMessage.setName(file.getName());
			fileMessage.setUser_id(Preference.user.getId());
			fileMessage.setFiles(fileService.convertToByteArray(file));
			
			Buffer.fileBuffer = "";
		} catch (IOException e) {
			e.printStackTrace();
		}

		return fileMessage;
	}
}
