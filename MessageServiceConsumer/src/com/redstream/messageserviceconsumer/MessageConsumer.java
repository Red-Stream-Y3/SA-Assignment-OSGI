package com.redstream.messageserviceconsumer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.redstream.messagemanagerservice.IMessage;
import com.redstream.usermanagerservice.IUser;

public class MessageConsumer {
	private IUser userService;
	private IMessage message;
	private static String username;

	public MessageConsumer(IUser userService, IMessage message) {
		this.userService = userService;
		this.message = message;
		username = null;
	}

	public void startMenu() {

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String inputString = "";

		try {
			inputLoop: while (true) {
				displayMenu();

				// get user input
				inputString = reader.readLine();

				switch (inputString) {
				case "1": {
					isLoggged();
					message.sendMessage(username);
					break;
				}
				case "2": {
					isLoggged();
					message.deleteMessage(username);
					break;
				}
				case "3": {
					isLoggged();
					message.searchMessages(username);
					break;
				}
				case "4": {
					isLoggged();
					message.viewAllMessages(username);
					break;
				}
				case "5": {
//					System.out.println("Terminating Program!\n");
					break inputLoop;
				}
				default: {
					System.out.println("Unexpected value: Enter 1/2/3/4 or leave empty to cancel\n");
					continue inputLoop;
				}
				}
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	private void displayMenu() {
		System.out.println("\n===================");
		System.out.println(" Message Dashboard");
		System.out.println("===================");
		System.out.println("1. Send Message");
		System.out.println("2. Delete Message");
		System.out.println("3. Search Message");
		System.out.println("4. View All Messages");
		System.out.println("5. Exit\n");
		System.out.print("Enter your choice: ");
	}
	
	private void isLoggged() {
		// check if user is logged in
		try {
			MessageConsumer.username = userService.getCurrentUserName();
		} catch (NullPointerException e) {
			System.out.println("Not logged in!");
		}

		if (MessageConsumer.username == null) {
			userService.login(); // user must login first
			MessageConsumer.username = userService.getCurrentUserName();
		}
	}
}