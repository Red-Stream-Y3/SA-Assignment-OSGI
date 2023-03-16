package com.redstream.messagemanagerservice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.redstream.smdatabase.*;

public class MessageImpl implements IMessage {

	private Connection connection = null;
	private Statement statement = null;
	private IDatabase database;
	private ResultSet resultSet;

	// ArrayList to store all messages
	private ArrayList<Message> messageList = new ArrayList<Message>();
	// BufferedReader object to read input from the user
	BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

	public MessageImpl() {
		super();
		database = (IDatabase) new SocialMediaDB();
		connection = database.connection();
	}

	// Send message method
	@Override
	public void sendMessage() {

		System.out.print("Enter Receiver Name: ");
		// Create new message object and add it to the messageList
		Message newMessage = new Message();

		String receiver;

		try {
			receiver = reader.readLine();
			newMessage.setReceiver(receiver);

			if (receiver != null) {
				System.out.print("Enter Message : ");
				String message = reader.readLine();

				newMessage.setMessage(message);

				String insertMessage = "INSERT INTO messages(receiver,message) " + "VALUES('"
						+ newMessage.getReceiver() + "', '" + newMessage.getMessage() + "')";

				try {
					statement = connection.createStatement();
					statement.executeUpdate(insertMessage);
				} catch (SQLException exc) {
					System.out.println(exc.getMessage());

				}

				System.out.println("\nMessage sent successfully!\n");
			} else {
				System.out.println("\nReceiver not found!");
			}
		} catch (Exception e) {
			// Print error message if exception occurs
			System.out.println(e.getMessage());
		}

	}

	// Delete message method
	@Override
	public void deleteMessage() {
		System.out.println("\n-------Messages--------\n");

		viewAllMessages();

		if (messageList.isEmpty()) {
			System.out.println("You don't have any messages to delete\n");
		} else {
			System.out.print("\nEnter Message ID : ");

			String messageID;

			try {
				messageID = reader.readLine();

				if (messageList.isEmpty()) {
					System.out.println("You don't have any messages");
				} else {
					// Find the message with the given ID and remove it from the array list
					for (Message message : messageList) {
						if (String.valueOf(message.getMessageID()).equals(messageID)) {
							messageList.remove(message);
							System.out.println("Message deleted successfully!\n");
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
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
