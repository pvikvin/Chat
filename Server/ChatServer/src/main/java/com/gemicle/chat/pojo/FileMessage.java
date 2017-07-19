package com.gemicle.chat.pojo;

import java.io.File;

import lombok.Data;

@Data
public class FileMessage {
	private File file;
		
	public FileMessage(File file){
		this.file = file;
	}

}
