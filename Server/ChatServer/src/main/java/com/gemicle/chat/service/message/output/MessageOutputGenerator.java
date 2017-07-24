package com.gemicle.chat.service.message.output;

import java.net.Socket;

import com.gemicle.chat.enums.MethodsType;
import com.gemicle.chat.pojo.Message;
import com.gemicle.chat.pojo.User;
import com.gemicle.chat.pojo.FileMessage;

public class MessageOutputGenerator extends Thread {

	private MethodsType methodsType;
	private Object obj;
	private Socket socketSender;

	public MessageOutputGenerator(Object obj, MethodsType methodsType, Socket socketSender) {
		this.obj = obj;
		this.methodsType = methodsType;
		this.socketSender = socketSender;
	}

	@Override
	public void run() {
		generateMessage();
	}

	private void generateMessage() {
		MessageOutput messageOutput = null;
		switch (methodsType) {
		case CREATE_USER:
			messageOutput = new MessageOutputCreateUser(socketSender, (User) obj);
			break;
		case CREATE_MESSAGE:
			messageOutput = new MessageOutputSimple(socketSender, (Message) obj);
			break;
		case CREATE_MESSAGE_FILE:
			messageOutput = new MessageOutputFile(socketSender, (FileMessage) obj);
		default:
			break;
		}
		sendMessageUser(messageOutput);
	}

	private void sendMessageUser(MessageOutput messageOutput) {
		if (messageOutput == null) {
			return;
		}
		messageOutput.messageBuild();
	}

}
