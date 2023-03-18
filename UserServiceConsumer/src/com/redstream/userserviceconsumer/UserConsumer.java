package com.redstream.userserviceconsumer;

import java.util.Scanner;

import com.redstream.usermanagerservice.IUser;

public class UserConsumer {
	private IUser userService;
	
	public UserConsumer(IUser userService) {
		super();
		this.userService = userService;
	}

	public void showUserManagerCLI() {

		int choice = 0;
		Scanner scanner = new Scanner(System.in);
		boolean loginStatus = false;
		boolean logoutStatus = false;

		inputLoop: while (true) {
			System.out.println("==============");
			System.out.println("User Dashboard");
			System.out.println("==============");
			System.out.println("1. View Account");
			System.out.println("2. Edit Account");
			System.out.println("3. Delete Account");
			System.out.println("4. Search Users");
			System.out.println("5. LogOut");
			System.out.print("Enter your choice: ");
			choice = scanner.nextInt();

			switch (choice) {
			case 1:
				userService.getUser();
				break;
			case 2:
				userService.updateUser();
				break;
			case 3:
				logoutStatus = userService.deleteUser();
				break;
			case 4:
				userService.getAllUsers();
				break;
			case 5:
				logoutStatus = userService.logout();
				scanner.close();
				System.exit(0);
			default:
				System.out.println("Invalid choice");
				break inputLoop;
			}

			if(logoutStatus == true) {
				System.exit(0);
			}
		}

		
	}

}
