package com.redstream.followerservicemanager;

import java.io.BufferedReader;
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
		
		try {
			inputLoop: while (true) {
				printFriendsMenu();
				
				String option = reader.readLine();
				
				switch (option) {
					case "1" : {
						System.out.print("Enter User Name : ");
						String user = reader.readLine();
						
						if (user != null && !user.trim().isEmpty()) {
							User newFollower = new User(user);
							friendList.add(newFollower);
							friendCount ++ ;
							
							System.out.println("\nYou are now friends with " + user);
							//System.out.println("Now you have " + friendCount + " friends\n");
							break;
						} else {
							System.out.println("Please enter a valid username\n");
							break;
						}
					}
					case "2" : {
						if(friendList.isEmpty()) {
							System.out.println("You don't have any friends to unfriend");
							break;
						} else {
							System.out.print("Enter Username to Unfriend : ");
							String userN = reader.readLine();
							removeFriend(userN);
							break;
						}				
					}
					case "3" : {
						viewAllFriends();
						break;	
					}
					case "4" : {
						System.out.println("\nTerminating Program!\n");
						break inputLoop;
					}
					default : {
						System.out.println("Unexpexted Error!\n");
						continue inputLoop;
					}
				}	
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	
	private void printFriendsMenu() {
		System.out.println("\n-------Manage Friends-------\n");
		System.out.println("1.Add New Friend");
		System.out.println("2.Remove Friend");
		System.out.println("3.View All Friends");
		System.out.println("4.Exit");
		System.out.print("\nSelect an Option: ");
	}


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

	public void removeFriend(String userName) {
		Iterator<User> iterator = friendList.iterator();
		 while (iterator.hasNext()) {
		        User frnd = iterator.next();
		        if (String.valueOf(frnd.getUserName()).equals(userName)) {
		            iterator.remove();
		            friendCount--;
		            System.out.println("You are no longer friends with " + userName);
		            return;
		        }
		    }
		    System.out.println("Could not find a friend with the username " + "'" + userName + "'");
	}
	
	
}
