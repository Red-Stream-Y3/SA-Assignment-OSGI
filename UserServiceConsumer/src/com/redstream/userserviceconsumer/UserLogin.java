package com.redstream.userserviceconsumer;

import java.util.Scanner;

import com.redstream.usermanagerservice.IUser;

public class UserLogin {
	private IUser userService;
	
	public UserLogin(IUser userService) {
		super();
		this.userService = userService;
	}

	public void showLoginCLI() {
		
		System.out.println("===========");
        System.out.println("  Welcome  ");
        System.out.println("===========");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Exit");
		System.out.println("===========");
		System.out.println("Enter your choice: ");

        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        boolean loginStatus = false;
        switch (choice) {
	        case 1:
	            while (loginStatus == false) {
	                loginStatus = userService.login();//
	            }
	            break;
	        case 2:
	            userService.addUser();
	            while (loginStatus == false) {
	                loginStatus = userService.login();
	            }
	            break;
	        case 3:
	        	scanner.close();
	            System.exit(0);
	        default:
	            System.out.println("Invalid choice");
        }

		// show user manager CLI
		/*UserConsumer consumer = new UserConsumer(userService);
		consumer.showUserManagerCLI();*/
        
	}

}
