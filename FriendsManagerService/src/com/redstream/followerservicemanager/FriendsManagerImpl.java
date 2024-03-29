package com.redstream.followerservicemanager;

import java.io.BufferedReader;
import com.redstream.smdatabase.*;
import com.redstream.usermanagerservice.IUser;
import com.redstream.usermanagerservice.UserImpl;

import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
//import com.redstream.usermanagerservice.*;

public class FriendsManagerImpl implements FriendsManager{
	
	private Connection connection = null;
	private Statement statement = null;
	private IDatabase database;
	private ResultSet resultSet;

	//Friends List
	private ArrayList<String> getUserList = new ArrayList<String>();
	
//	private String followerName;
//	private IUser iUser = new UserImpl();
	
	public FriendsManagerImpl() {
		super();
		database = (IDatabase) new SocialMediaDB();
		connection = database.connection();
//		followerName = iUser.getCurrentUserName();
	}

	@Override
	public void addFriend(String follower) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String getUsersQuery = "SELECT * FROM users";
		
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(getUsersQuery);
			while(resultSet.next()) {
				String userName = resultSet.getString("username");
				getUserList.add(userName);	
			}
			
			System.out.print("Enter User Name : ");
			String user;
			
			try {
				user = reader.readLine();

				if (user != null && !user.trim().isEmpty() && user != follower) {
					
					boolean isUserAvailble = getUserList.contains(user);
					
					if(isUserAvailble) {
						String insertFriend = "INSERT INTO friendlist(followerName, friendName) " + "VALUES('"+ follower + "' , '" + user + "')";
						
						try {
							statement = connection.createStatement();
							statement.executeUpdate(insertFriend);
						} catch (SQLException exc) {
							System.out.println(exc.getMessage());
						}
						System.out.println("\nYou are now friends with " + user);
						
					} else {
						System.out.println("Cannot find a user! Check username again.");
					}
				} else {
					System.out.println("Please enter a valid username\n");					
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		} catch (SQLException e) {
			System.out.println("Database Connection Error!");
			e.printStackTrace();
		}
	}
	
	
	@Override
	public void viewAllFriends(String follower) {
		ArrayList<Friend> getFriendList = new ArrayList<Friend>();
		String getFriends = "SELECT * FROM friendlist WHERE followerName = '"+ follower + "'";
		
		System.out.println("\n-------Your Friend List-------\n");
		
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(getFriends);
			while(resultSet.next()) {
				Friend friend = new Friend();
				friend.setFriendName(resultSet.getString("friendName"));
				friend.setFollowerName(follower);
				getFriendList.add(friend);		
				}
		} catch (SQLException e) {
			System.out.println("Database Connection Error!");
			e.printStackTrace();
		}
		
		System.out.println("You have " + getFriendList.size() + " friends.");
		
		int i = 1 ;
		for(Friend frnd : getFriendList) {
			if(getFriendList.isEmpty()) {
				System.out.println("You don't have any friends! Make your 1st friend now! ");
			} else {
				System.out.println(" " + i + "." + frnd.viewFriends());
				i++;
			}
		}		
	}

	@Override
	public void removeFriend(String username) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String userN;
	
			viewAllFriends(username);
			System.out.print("\nEnter username to unfriend : ");

			try {
				userN = reader.readLine();
				String removefriend = "DELETE FROM friendlist WHERE friendName='" + userN + "' AND followerName = '"+ username + "'";
				
				try {
					statement = connection.createStatement();
					statement.executeUpdate(removefriend);
					System.out.println("You are no longer friends with " + userN + "\n");
					
				} catch (SQLException exc) {
					System.out.println("Could not find a friend with the username " + "'" + userN + "'");
					System.out.println(exc.getMessage());
				}
				    
			}
			 catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		
		
	}
	
	
}
