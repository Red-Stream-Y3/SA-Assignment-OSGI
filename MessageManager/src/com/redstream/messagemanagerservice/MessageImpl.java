package com.redstream.messagemanagerservice;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MessageImpl implements IMessage {

	// ArrayList to store all messages
	private ArrayList<Message> messageList = new ArrayList<Message>();

	// Send message method
	@Override
	public void sendMessage() {
		// BufferedReader object to read input from the user
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		String option = "";

		try {
			inputLoop: while (true) {
				// Print message menu
				printMessageMenu();

				// Get user input on choice
				option = reader.readLine();

				switch (option) {
				case "1": {
					System.out.print("Enter Receiver Name: ");
					String receiver = reader.readLine();

					if (receiver != null) {
						System.out.print("Enter Message : ");
						String message = reader.readLine();

						// Create new message object and add it to the messageList
						Message newMessage = new Message(messageList.size() + 1, receiver, message);
						messageList.add(newMessage);

						System.out.println("\nMessage sent successfully!\n");
						break;
					} else {
						System.out.println("\nReceiver not found!");
						break;
					}
				}
				case "2": {
					System.out.println("\n-------Messages--------\n");
					viewAllMessages();
					if (messageList.isEmpty()) {
						System.out.println("You don't have any messages to delete\n");
						break;
					} else {
						System.out.print("\nEnter Message ID : ");

						String messageID = reader.readLine();

						// Delete a message by getting message id
						deleteMessage(messageID);

						break;
					}
				}
				case "3": {
					if (messageList.isEmpty()) {
						System.out.println("You don't have any messages\n");
						break;
					} else {
						// View all messages
						System.out.println("\n-------Your Messages--------\n");
						System.out.println("You have " + messageList.size() + " messages");
						viewAllMessages();
						break;
					}
				}
				case "4": {
					// Exit the loop and cancel the operation
					break inputLoop;
				}
				default: {
					System.out.println("Unexpected value: Enter 1/2/3 or leave empty to cancel\n");
					continue inputLoop;
				}
				}
			}
		} catch (Exception e) {
			// Print error message if exception occurs
			System.out.println(e.getMessage());
		}
	}

	// Delete message method
	@Override
	public boolean deleteMessage(String id) {
		if (messageList.isEmpty()) {
			System.out.println("You don't have any messages");
			return false;
		} else {
			// Find the message with the given ID and remove it from the array list
			for (Message message : messageList) {
				if (String.valueOf(message.getMessageID()).equals(id)) {
					messageList.remove(message);
					System.out.println("Message deleted successfully!\n");
					return true;
				}
			}
			return false;
		}
	}

	// Get all messages
	@Override
	public void viewAllMessages() {
		for (Message message : messageList) {
			if (messageList.isEmpty()) {
				System.out.println("You don't have any messages");
			} else {
				message.viewMessage();
			}
		}
	}

	// Message menu
	private void printMessageMenu() {
		System.out.println("\n-------Manage Messages-------\n");
		System.out.println("1. Send message");
		System.out.println("2. Delete message");
		System.out.println("3. View All Messages");
		System.out.println("4. Exit");
		System.out.print("\nSelect an Option: ");
	}
}
