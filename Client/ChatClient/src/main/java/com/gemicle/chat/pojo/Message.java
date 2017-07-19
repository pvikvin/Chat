package com.gemicle.chat.pojo;

import java.util.Date;

import lombok.Data;

@Data
public class Message {
	
	private long id;
	
	private long user_id;
	
	private String messageText;
	private Date date;
	
	private byte[] file; 
}
