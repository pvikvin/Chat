package com.gemicle.chat.service.message.output;

import java.net.Socket;

public interface MessageOutput {
	public void messageBuild();

	public void sendMessageUser(Socket socket, Object obj);

}
