package com.gemicle.chat.pojo;

import lombok.Data;

@Data
public class User {

	private long id;

	private String name;

	public User() {
	}

	public User(String name) {
		this.name = name;
	}
}
