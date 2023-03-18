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

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import com.redstream.cameraprovider.CameraProvider;
import com.redstream.smdatabase.*;

public class MessageImpl implements IMessage {

	private Connection connection = null;
	private Statement statement = null;
	private IDatabase database;
	private ResultSet resultSet;
	private ServiceReference<?> cameraServiceReference;
	private CameraProvider cameraProvider;
	private BundleContext context = Activator.getContext();

	// BufferedReader object to read input from the user
	BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

	public MessageImpl() {
		super();
		database = (IDatabase) new SocialMediaDB();
		connection = database.connection();
	}

	// Send message method
	@Override
	public void sendMessage(String username) {

		System.out.print("Enter Receiver Name: ");
		// Create new message object and add it to the messageList
		Message newMessage = new Message();

		String receiver;
		String photo = "";

		try {
			receiver = reader.readLine();
			newMessage.setReceiver(receiver);

			if (receiver != null) {
				System.out.print("Enter Message : ");
				String message = reader.readLine();

				newMessage.setMessage(message);

				while (true) {
					System.out.println("Do you need to attach a photo? (Yes/No)");
					String answer = reader.readLine();

					if (answer.equalsIgnoreCase("yes")) {
						// get a photo from camera provider
						System.out.println("\nCalling Camera Provider...");

						// get camera provider service
						cameraServiceReference = context.getServiceReference(CameraProvider.class.getName());
						cameraProvider = (CameraProvider) context.getService(cameraServiceReference);
						photo = cameraProvider.takePhoto();

						// get text input for caption
						System.out.println(photo);
						System.out.println("Photo taken!");

						// exit the loop
						break;
					} else if (answer.equalsIgnoreCase("no")) {
						// exit the loop
						break;
					} else {
						// ask the user to enter a valid input
						System.out.println("Please enter 'Yes' or 'No'.");
					}
				}
				String insertMessage = "INSERT INTO messages(sender,receiver,message, photo) " + "VALUES('" + username
						+ "', '" + newMessage.getReceiver() + "', '" + newMessage.getMessage() + "', '" + photo + "')";

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
	public void deleteMessage(String username) {
		viewAllMessages(username);

		System.out.print("\nEnter Message ID : ");

		String messageID;

		try {
			messageID = reader.readLine();

			String deleteMessage = "DELETE FROM messages WHERE sender='" + username + "' AND messageID='" + messageID
					+ "' ";

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
	public void viewAllMessages(String username) {

		// Create a new ArrayList to store all messages
		ArrayList<Message> messageList = new ArrayList<Message>();

		try {
			statement = connection.createStatement();
			String SelectAll = "SELECT * FROM messages WHERE sender='" + username + "'";
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
				newMessage.setPhoto(resultSet.getString("photo"));
				messageList.add(newMessage);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// Print all messages
		System.out.println("\n------- Your Messages --------\n");
		for (Message message : messageList) {
			if (messageList.isEmpty()) {
				System.out.println("You don't have any messages");
			} else {
				message.viewMessage();
			}
		}

	}

	@Override
	public void searchMessages(String username) {
		System.out.print("Search messages: ");
		String message;

		try {
			message = reader.readLine();

			System.out.print("Search Result: " + message);

			try {
				statement = connection.createStatement();
				String sql = "SELECT * FROM messages WHERE sender='" + username + "' AND message LIKE ?";
				PreparedStatement statement = connection.prepareStatement(sql);
				statement.setString(1, "%" + message + "%");
				resultSet = statement.executeQuery();
				
				if (!resultSet.isBeforeFirst()) {
					System.out.println("\nYou don't have any search result.");
				} else {
					while (resultSet.next()) {
						System.out.println("\n " + resultSet.getString("receiver") + ": " + resultSet.getString("message"));
					}
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
