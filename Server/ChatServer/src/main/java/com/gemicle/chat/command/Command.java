package com.gemicle.chat.command;

public interface Command<T> {
	public T execute();
}
