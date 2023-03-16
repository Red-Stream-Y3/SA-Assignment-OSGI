package com.redstream.userserviceconsumer;

import java.util.Scanner;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import com.redstream.usermanagerservice.IUser;

public class Activator implements BundleActivator {

	private static BundleContext context;
	private ServiceReference<?> userServiceReference;
	
	static BundleContext getContext() { // Get bundle context
		return context;
	}

	@Override
	public void start(BundleContext bundleContext) throws Exception { // Start bundle
		Activator.context = bundleContext;
		
		userServiceReference = bundleContext.getServiceReference(IUser.class.getName()); // Get service reference
		IUser userService = (IUser) bundleContext.getService(userServiceReference); // Get service object
		System.out.println("LOG - User Subscriber Started");

		showUserManagerCLI(userService);
	}

	@Override
	public void stop(BundleContext bundleContext) throws Exception { // Stop bundle
		Activator.context = null;
		bundleContext.ungetService(userServiceReference);
		System.out.println("LOG - User Subscriber Stopped");
	}

	private void showUserManagerCLI(IUser userService) {

		int choice = 0;
		Scanner scan = new Scanner(System.in);
		
		System.out.println("================");
		System.out.println("User Manager CLI");
		System.out.println("================");
		System.out.println("================");
		System.out.println("Please Login to continue");
		System.out.println("================");
		userService.login();

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

		try{
			choice = Integer.parseInt(scan.nextLine().trim());
		} catch (NumberFormatException e) { // Catch invalid input
			System.out.println("================");
			System.out.println("Invalid choice");
			System.out.println("================");
			showUserManagerCLI(userService);
		}

		switch (choice) {
		case 1:
			userService.addUser();
			break;
		case 2:
			userService.deleteUser();
			break;
		case 3:
			userService.updateUser();
			break;
		case 4:
			userService.getUser();
			break;
		case 5:
			userService.getAllUsers();
			break;
		case 6:
			scan.close();
			System.exit(0);
			break;
		default:
			System.out.println("Invalid choice");
			break;
		}

		System.out.println("================");
		showUserManagerCLI(userService); // Show CLI again
		
	}

}