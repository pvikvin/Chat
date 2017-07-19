package com.gemicle.chat.message.out;

public class MessageOutput extends Thread{

	private MessageSender messageSender;

	public MessageOutput(MessageSender messageSender) {
		this.messageSender = messageSender;
	}

	@Override
	public void start(){
		messageSender.send();
		interrupt();
	};

}
