package com.redstream.followerserviceconsumer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.redstream.followerservicemanager.FriendsManager;

public class FollowerConsumer {
	private FriendsManager friends;
	
	public FollowerConsumer(FriendsManager friends) {
		this.friends = friends;
	}

	public void displayMenu(){

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		String option = "";

		try {
			inputLoop: while (true) {
				System.out.println("\n-------Manage Friends-------\n");
				System.out.println("1.Add New Friend");
				System.out.println("2.Remove Friend");
				System.out.println("3.View All Friends");
				System.out.println("4.Message a Friend");
				System.out.println("5.Exit");
				System.out.print("\nSelect an Option: ");

				option = reader.readLine();

				switch (option) {
				case "1": {
					friends.addFriend();
					break;
				}
				case "2": {
					friends.removeFriend();
					break;
				}
				case "3": {
					friends.viewAllFriends();
					break;
				}
				case "4": {
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
}
