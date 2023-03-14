package com.redstream.followerservicemanager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;

public class FriendsManagerImpl implements FriendsManager{
	
	//Friends count
	private int friendCount = 0;
	
	//Friends List
	private ArrayList<User> friendList = new ArrayList<User>();
	
	

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
					
					System.out.println("\nYou are now friends with " + user);
					//System.out.println("Now you have " + friendCount + " friends\n");
	
				} else {
					System.out.println("Please enter a valid username\n");					
				}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	
	@Override
	public void viewAllFriends() {
		if(friendList.isEmpty()) {
			System.out.println("You don't have any friends");
		} else {
			System.out.println("\n-------Your Friend List-------\n");
			System.out.println("You have " + friendCount + " friends.");
			int i = 1;
			for(User frnd : friendList) {
					System.out.println(" " + i + "." + String.valueOf(frnd.getUserName()));
					i++;
				}
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
