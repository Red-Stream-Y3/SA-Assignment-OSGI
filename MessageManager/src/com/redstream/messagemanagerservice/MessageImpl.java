package com.redstream.messagemanagerservice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
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

				String insertMessage = "INSERT INTO messages(receiver,message) " + "VALUES('" + newMessage.getReceiver()
						+ "', '" + newMessage.getMessage() + "')";

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

		System.out.print("\nEnter Message ID : ");

		String messageID;

		try {
			messageID = reader.readLine();

			String deleteMessage = "DELETE FROM messages WHERE messageID='" + messageID + "' ";

			try {

				statement = connection.createStatement();
				statement.executeUpdate(deleteMessage);
				System.out.println("Message deleted successfully!\n");

			} catch (SQLException exc) {
				System.out.println("Error with delete Message");
				System.out.println(exc.getMessage());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// Get all messages
	@Override
	public void viewAllMessages() {

		// Create a new ArrayList to store all messages
		ArrayList<Message> messageList = new ArrayList<Message>();

		try {
			statement = connection.createStatement();
			String SelectAll = "SELECT * FROM messages";
			resultSet = statement.executeQuery(SelectAll);

		} catch (SQLException e) {

			e.printStackTrace();
		}

		try {
			while (resultSet.next()) {

				Message newMessage = new Message();
				newMessage.setMessageID(resultSet.getInt("messageID"));
				newMessage.setSender(resultSet.getString("sender"));
				newMessage.setReceiver(resultSet.getString("receiver"));
				newMessage.setMessage(resultSet.getString("message"));
				messageList.add(newMessage);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// Print all messages
		for (Message message : messageList) {
			if (messageList.isEmpty()) {
				System.out.println("You don't have any messages");
			} else {
				message.viewMessage();
			}
		}

	}

	@Override
	public void searchMessages() {

		System.out.print("Search messages: ");
		String message;

		try {
			message = reader.readLine();

			try {
				statement = connection.createStatement();
				String sql = "SELECT * FROM messages WHERE message LIKE ?";
				PreparedStatement statement = connection.prepareStatement(sql);
				statement.setString(1, "%" + message + "%");
				resultSet = statement.executeQuery();

				while (resultSet.next()) {
					System.out.println(resultSet.getString("receiver") + ": " + resultSet.getString("message"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("User can't be found");
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

}
