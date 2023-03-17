package com.redstream.messagemanagerservice;

public interface IMessage {
	public void sendMessage(String username);

	public void deleteMessage(String username);
	
	public void viewAllMessages(String username);

	public void searchMessages(String username);
}
