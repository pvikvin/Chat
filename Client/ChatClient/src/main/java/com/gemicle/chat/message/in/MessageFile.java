package com.gemicle.chat.message.in;

import java.io.FileOutputStream;
import java.io.IOException;

import com.gemicle.chat.jframes.MainFrame;
import com.gemicle.chat.pojo.FileMessage;
import com.gemicle.chat.service.FileService;

public class MessageFile implements MessageRecipient {

	private FileMessage fileMessage;
	private FileService fileService = new FileService();

	public MessageFile(FileMessage fileMessage, MainFrame main) {
		this.fileMessage = fileMessage;
	}

	@Override
	public void processingIncomingMessages() {

		String path = fileService.getSavePath();
		if (path.isEmpty()) {
			return;
		}

		String fileName = path + "/" + fileMessage.getName();

		try {
			
			FileOutputStream fos = new FileOutputStream(fileName);
			fos.write(fileMessage.getFiles());
			fos.close();
			
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally{
			
		}

	}

}
