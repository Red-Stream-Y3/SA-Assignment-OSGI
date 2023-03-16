package com.redstream.postserviceconsumer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.redstream.postmanagerservice.PostManager;
import com.redstream.usermanagerservice.IUser;

public class Consumer {
	private IUser userService;
	private PostManager postManager;
	
	public Consumer(IUser userService, PostManager postManager) {
		this.userService = userService;
		this.postManager = postManager;
	}
	
	public void startMenu() {
		
		BufferedReader reader = new BufferedReader
				(new InputStreamReader(System.in));
		String inputString = "";
		
		try {
			inputLoop: while(true) {
				displayMenu();
				
				//get user input
				inputString = reader.readLine();
				
				switch (inputString) {
					case "1":{//create new post
						//check if user is logged in
						String username = userService.getCurrentUserName();
						
						if(username == null) {
							userService.login(); //user must login first
							username = userService.getCurrentUserName();
						}
						
						//call new post method from post manager module
						postManager.newPost(username);
						break;
					}
					case "2": {//view all posts
						postManager.displayAllPosts();
						break;
					}
					case "3": {//delete a post
						//call delete post from post manager module
						postManager.deletePost();
						break;
					}
					default:{
						if(inputString.length()==0) {
							System.out.println("Cancel!");
							break inputLoop;
						} else {
							System.out.println("Unexpected value: Enter "
								+ "1/2/3 or leave empty to cancel");
							continue inputLoop;
						}
					}
				}
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	private void displayMenu() {
		System.out.println("\n~~~~~~~~~~Menu~~~~~~~~~");
		System.out.println("1: Create post");
		System.out.println("2: View my posts");
		System.out.println("3: Delete post");
		System.out.print("\nSelect option: ");
	}
}
