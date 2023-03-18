package com.redstream.followerserviceconsumer;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.redstream.followerservicemanager.FriendsManager;
import com.redstream.usermanagerservice.IUser;

//import com.redstream.usermanagerservice.IUser;

public class FollowerConsumer {
	private IUser userService;
	private FriendsManager friendManager;
	private static String username;
	
	
	public FollowerConsumer( FriendsManager friendManager, IUser userService) {
		username = null;
		this.userService = userService;
		this.friendManager = friendManager;
	}
	
	
	public void displayMenu() {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		String option = "";

		try {
			inputLoop: while (true) {
				displayMenu1();
				//FollowerConsumer.username = userService.getCurrentUserName();
				option = reader.readLine();

				switch (option) {
				case "1": {
					//isLoggged();
					friendManager.addFriend();
					break;
				}
				case "2": {
					//isLoggged();
					friendManager.removeFriend();
					break;
				}
				case "3": {
					//isLoggged();
					friendManager.viewAllFriends();
					break;
				}
				case "4": {
					//isLoggged();
					//friends.viewAllMessages();
					break;
				}
				case "5": {
					System.out.println("Terminating Program!\n");
					break inputLoop;
				}
				default: {
					System.out.println("Unexpexted Error!\n");
					continue inputLoop;
				}
				}
			}
		} catch (Exception e) {
			// Print error message if exception occurs
			System.out.println(e.getMessage());
		}
	}
	
	private void displayMenu1() {
		System.out.println("\n-======= Manage Friends ========\n");
		System.out.println("1.Add New Friend");
		System.out.println("2.Remove Friend");
		System.out.println("3.View All Friends");
		System.out.println("4.Message a Friend");
		System.out.println("5.Exit");
		System.out.print("\nSelect an Option: ");
	}
	
//	private void isLoggged() {
//		// check if user is logged in
//		try {
//			FollowerConsumer.username = userService.getCurrentUserName();
//		} catch (NullPointerException e) {
//			System.out.println("Please Log in!");
//		}
//
//		if (FollowerConsumer.username == null) {
//			userService.login(); // user must login first
//			FollowerConsumer.username = userService.getCurrentUserName();
//		}
//	}
	
}
