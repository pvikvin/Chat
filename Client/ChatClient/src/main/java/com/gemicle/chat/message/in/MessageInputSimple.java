package com.gemicle.chat.message.in;

import com.gemicle.chat.jframes.MainFrame;
import com.gemicle.chat.pojo.Message;

public class MessageInputSimple implements MessageRecipient{

	private Message message;
	private MainFrame main;
	
	public MessageInputSimple(Message message, MainFrame main){
		this.message = message;
		this.main = main;		
	}
	
	@Override
	public void processingIncomingMessages() {
		String textAllUsers = main.getTextMessageUsers().getText();
		main.getTextMessageUsers().setText(textAllUsers + "\n"+message.getMessageText());
	}
	
}
