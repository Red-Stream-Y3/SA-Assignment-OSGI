package com.redstream.followerservicemanager;

import java.io.BufferedReader;
import databasecon.*;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;

public class FriendsManagerImpl implements FriendsManager{
	
	private Connection connection = null;
	private Statement statement = null;
	private IDatabase database;
	private ResultSet resultSet;
	
	//Friends count
	private int friendCount = 0;
	
	//Friends List
	private ArrayList<User> friendList = new ArrayList<User>();
	private ArrayList<String> getFriendList = new ArrayList<String>();
	
	public FriendsManagerImpl() {
		super();
		database = (IDatabase) new SocialMediaDB();
		connection = database.connection();
	}

	@Override
	public void addFriend() {
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("Enter User Name : ");
		String user;
		
		try {
			user = reader.readLine();

				if (user != null && !user.trim().isEmpty()) {
					User newFollower = new User(user);
					friendList.add(newFollower);
					friendCount ++ ;
					
					String insertFriend = "INSERT INTO friendlist(username) " + "VALUES('"
							+ newFollower.getUserName() + "')";
					
					try {
						statement = connection.createStatement();
						statement.executeUpdate(insertFriend);
					} catch (SQLException exc) {
						System.out.println(exc.getMessage());

					}
					
					System.out.println("\nYou are now friends with " + user);
	
				} else {
					System.out.println("Please enter a valid username\n");					
				}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	
	@Override
	public void viewAllFriends() {
		
		String getFriends = "SELECT * FROM friendlist";
			try {
				statement = connection.createStatement();
				resultSet = statement.executeQuery(getFriends);
				while(resultSet.next()) {
					String friendName = resultSet.getString("username");
					getFriendList.add(friendName);
				}
				
				if(getFriendList.isEmpty()) {
					System.out.println("You don't have any friends! Make your 1st friend now! ");
				} else {
					System.out.println("\n-------Your Friend List-------\n");
					System.out.println("You have " + getFriendList.size() + " friends.");
					
					for(int i = 0; i < getFriendList.size(); i++) {
							System.out.println(" " + (i+1) + "." + getFriendList.get(i));
						}
				}	
			} catch (SQLException e) {
				System.out.println("Error in Database Connection!");
				e.printStackTrace();
			}
	}

	@Override
	public void removeFriend() {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		
		if(friendList.isEmpty()) {
			System.out.println("You don't have any friends to unfriend");
		} else {
			viewAllFriends();
			System.out.print("\nEnter username to unfriend : ");
			String userN;
			
			try {
				userN = reader.readLine();
				Iterator<User> iterator = friendList.iterator();
				 while (iterator.hasNext()) {
				        User frnd = iterator.next();
				        if (String.valueOf(frnd.getUserName()).equals(userN)) {
				            iterator.remove();
				            friendCount--;
				            System.out.println("You are no longer friends with " + userN);
				            return;
				        }
				    }
				    System.out.println("Could not find a friend with the username " + "'" + userN + "'");
			}
			 catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		
	}
	}
	
	
}
