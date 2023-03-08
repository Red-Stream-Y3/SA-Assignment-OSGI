package com.redstream.userserviceconsumer;

import java.util.Scanner;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import com.redstream.usermanagerservice.IUser;
import com.redstream.usermanagerservice.User;

public class Activator implements BundleActivator {

	private static BundleContext context;
	private ServiceReference<?> userServiceReference;
	
	static BundleContext getContext() {
		return context;
	}

	@Override
	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
		
		
		userServiceReference = bundleContext.getServiceReference(IUser.class.getName());
		IUser userService = (IUser) context.getService(userServiceReference);

		System.out.println("Message Subscriber Started");

		showUserManagerCLI(userService);
	}

	@Override
	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
		bundleContext.ungetService(userServiceReference);
		System.out.println("Message Subscriber Stopped");
	}

	private void showUserManagerCLI(IUser userService) {

		int choice = 0;
		Scanner scan = new Scanner(System.in);

		System.out.println("================");
		System.out.println("User Manager CLI");
		System.out.println("================");
		System.out.println("1. Add User");
		System.out.println("2. Delete User");
		System.out.println("3. Update User");
		System.out.println("4. Get User");
		System.out.println("5. Get All Users");
		System.out.println("6. Exit");
		System.out.println("================");
		System.out.println("Enter your choice: ");

		choice = Integer.parseInt(scan.nextLine().trim());

		switch (choice) {
			case 1:
				System.out.println("Enter username: ");
				String username = scan.nextLine().trim();
				System.out.println("Enter email: ");
				String email = scan.nextLine().trim();
				System.out.println("Enter country: ");
				String country = scan.nextLine().trim();
				System.out.println("Enter age: ");
				int age = Integer.parseInt(scan.nextLine().trim());
				if (userService.addUser(new User(username, email, country, age))) {
					System.out.println("User added successfully");
				} else {
					System.out.println("User already exists");
				}
				break;
			case 2:
				System.out.println("Enter username: ");
				username = scan.nextLine().trim();
				if (userService.deleteUser(username)) {
					System.out.println("User deleted successfully");
				} else {
					System.out.println("User does not exist");
				}
				break;
			case 3:
				System.out.println("Enter your username: ");
				username = scan.nextLine().trim();
				System.out.println("What do you want to update?");
				System.out.println("1. Email");
				System.out.println("2. Country");
				System.out.println("3. Age");
				System.out.println("Enter your choice: ");
				int updateChoice = Integer.parseInt(scan.nextLine().trim());

				switch (updateChoice) {
					case 1:
						System.out.println("Enter new email: ");
						email = scan.nextLine().trim();
						if (userService.updateUser(username, new User(username, email, null, 0))) {
							System.out.println("User updated successfully");
						} else {
							System.out.println("User does not exist");
						}
						break;
					case 2:
						System.out.println("Enter new country: ");
						country = scan.nextLine().trim();
						if (userService.updateUser(username, new User(username, null, country, 0))) {
							System.out.println("User updated successfully");
						} else {
							System.out.println("User does not exist");
						}
						break;
					case 3:
						System.out.println("Enter new age: ");
						age = Integer.parseInt(scan.nextLine().trim());
						if (userService.updateUser(username, new User(username, null, null, age))) {
							System.out.println("User updated successfully");
						} else {
							System.out.println("User does not exist");
						}
						break;
					default:
						System.out.println("Invalid choice");
						break;
				}
				break;
			case 4:
				System.out.println("Enter username: ");
				username = scan.nextLine().trim();
				User user = userService.getUser(username);
				if (user != null) {
					System.out.println("User details:");
					System.out.println("Username: " + user.getUsername());
					System.out.println("Email: " + user.getEmail());
					System.out.println("Country: " + user.getCountry());
					System.out.println("Age: " + user.getAge());
				} else {
					System.out.println("User does not exist");
				}
				break;
			case 5:
				System.out.println("All users:");
				for (User u : userService.getAllUsers()) {
					System.out.println("Username: " + u.getUsername());
					System.out.println("Email: " + u.getEmail());
					System.out.println("Country: " + u.getCountry());
					System.out.println("Age: " + u.getAge());
					System.out.println("----------------------------");
				}
				break;
			case 6:
				System.exit(0);
				break;
			default:
				System.out.println("Invalid choice");
				showUserManagerCLI(userService);
				break;
		}
		showUserManagerCLI(userService);
	}

}
