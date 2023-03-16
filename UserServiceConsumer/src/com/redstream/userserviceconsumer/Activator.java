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
		Scanner scanner = new Scanner(System.in);
		boolean loginStatus = false;
		boolean logoutStatus = false;

		while (loginStatus == false) {
			loginStatus = userService.login();
		}

		inputLoop: while (true) {
			System.out.println("==============");
			System.out.println("User Dashboard");
			System.out.println("==============");
			System.out.println("1. View Account");
			System.out.println("2. Edit Account");
			System.out.println("3. Delete Account");
			System.out.println("4. Search Users");
			System.out.println("5. Register New User");
			System.out.println("6. LogOut");
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
				userService.addUser();
				break;
			case 6:
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