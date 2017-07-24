package com.gemicle.chat.message.out;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.codehaus.jackson.map.ObjectMapper;

import com.gemicle.chat.preferences.Preference;

public abstract class MessageSender {

	private ObjectMapper mapper = new ObjectMapper();
	private OutputStream sout;
	private DataOutputStream out;
	private MessageParameter parameters;

	public MessageSender(MessageParameter parameters) {
		this.parameters = parameters;
	}

	public void send() {
		try {
			String json = mapper.writeValueAsString(parameters.generateParameters());
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
