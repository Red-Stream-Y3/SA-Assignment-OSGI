package com.redstream.mainmenu;

import java.util.Scanner;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import com.redstream.followerserviceconsumer.FollowerConsumer;
import com.redstream.messageserviceconsumer.MessageConsumer;
import com.redstream.postserviceconsumer.Activator;
import com.redstream.postserviceconsumer.PostConsumer;
import com.redstream.userserviceconsumer.UserConsumer;
import com.redstream.userserviceconsumer.UserLogin;

public class MenuNavigator {
	BundleContext context;
	

	public MenuNavigator(BundleContext context) {
		this.context = context;
	}
	
	public void startMainMenu() {
		
		Scanner scanner = new Scanner(System.in);
		String inputString = "";

		// implement login
		// find user service
		ServiceReference<?> loginReference = context.getServiceReference(UserLogin.class.getName());
		UserLogin userLogin = (UserLogin) context.getService(loginReference);
		userLogin.showLoginCLI();
		
		menuLoop: while(true) {
			printMenu();
			
			inputString = scanner.nextLine();
			
			if(inputString.length()==0) {
				
				//close scanner and end program
				scanner.close();
				System.out.println("Goodbye!!!");
				System.exit(0);
				break menuLoop;
			}
			
			switch (inputString) {
				case "1": {//login
					//find user consumer service
					ServiceReference<?> userReference = context
							.getServiceReference(UserConsumer.class.getName());
					
					UserConsumer userConsumer = (UserConsumer) context.getService(userReference);
					
					//run user consumer module
					userConsumer.showUserManagerCLI();
					break;
				}
				case "2": {//posts
					//find the post consumer service
					ServiceReference<?> postReference = context
							.getServiceReference(PostConsumer.class.getName());
					
					PostConsumer postConsumer = (PostConsumer) context.getService(postReference);
					
					//run post consumer module
					postConsumer.startMenu();
					break;
				}
				case "3": {//friends
					//find the friends consumer service
					ServiceReference<?> friendsReference = context
							.getServiceReference(FollowerConsumer.class.getName());
					
					FollowerConsumer followerConsumer = (FollowerConsumer) context.getService(friendsReference);
					
					//run friends consumer module
					followerConsumer.displayMenu();
					break;
				}
				case "4": {//messages
					//find the message consumer service
					ServiceReference<?> messageReference = context
							.getServiceReference(MessageConsumer.class.getName());
					
					MessageConsumer messageConsumer = (MessageConsumer) context.getService(messageReference);
					
					//run friends consumer module
					messageConsumer.startMenu();
					break;
				}
				default: {
					System.out.println("Unexpected value: Enter "
							+ "1/2/3/4 or leave empty to cancel");
					
					continue menuLoop;
				}
			}
		}
	}
	
	private void printMenu() {
		System.out.println("\n~~~~~~REDSTREAM~~~~~~");
		System.out.println("1. Profile");
		System.out.println("2. Posts");
		System.out.println("3. Friends");
		System.out.println("4. Messages");
		System.out.print("\nSelect option : ");
	}
}
