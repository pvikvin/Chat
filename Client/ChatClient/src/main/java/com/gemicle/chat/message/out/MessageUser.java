package com.gemicle.chat.message.out;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;

import com.gemicle.chat.enums.MethodsType;
import com.gemicle.chat.preferences.Preference;

public class MessageUser implements MessageSender {

	private ObjectMapper mapper = new ObjectMapper();
	private static final String OBJECT_KEY = "object";
	private static final String METHOD_KEY = "method";

	private OutputStream sout;
	private DataOutputStream out;

	@Override
	public void send() {
		try {
			Map<String, String> parametrs = new HashMap<String, String>();
			parametrs.put(METHOD_KEY, MethodsType.CREATE_USER.toString());
			parametrs.put(OBJECT_KEY, mapper.writeValueAsString(Preference.user));

			String json = mapper.writeValueAsString(parametrs);

			sout = Preference.socket.getOutputStream();
			out = new DataOutputStream(sout);

			out.writeUTF(json);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {

		}

	}

}
