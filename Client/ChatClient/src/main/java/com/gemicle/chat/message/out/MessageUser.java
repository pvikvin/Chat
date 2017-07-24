package com.gemicle.chat.message.out;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.gemicle.chat.enums.MethodsType;
import com.gemicle.chat.preferences.Preference;

public class MessageUser extends MessageSender {

	private static ObjectMapper mapper = new ObjectMapper();
	private static final String OBJECT_KEY = "object";
	private static final String METHOD_KEY = "method";

	private static MessageParameter messageParameter = new MessageParameter(){

		@Override
		public Map<String, String> generateParameters() {
			Map<String, String> parametersMap = null;
			try {
				parametersMap = new HashMap<String, String>();
				parametersMap.put(METHOD_KEY, MethodsType.CREATE_USER.toString());
				parametersMap.put(OBJECT_KEY, mapper.writeValueAsString(Preference.user));
			} catch (JsonGenerationException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		
			return parametersMap;
		}};
	
	public MessageUser(){
		super(messageParameter);
	}

}
