package com.gemicle.chat.message.in;

import com.gemicle.chat.pojo.User;
import com.gemicle.chat.preferences.Preference;

public class MessageInputUser implements MessageRecipient{

	private User user;
	
	public MessageInputUser(User user){
		this.user = user;
	}
	
	@Override
	public void processingIncomingMessages() {
		Preference.user = user;
	}
	
}
