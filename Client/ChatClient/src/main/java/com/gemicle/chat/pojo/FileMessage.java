package com.gemicle.chat.pojo;

import lombok.Data;

@Data
public class FileMessage {
	
	private long id;
	private byte[] files;
	private String name;
	private long user_id;
	
}
