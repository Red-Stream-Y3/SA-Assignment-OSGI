package com.redstream.postmanagerservice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import com.redstream.cameraprovider.CameraProvider;

public class PostManagerImpl implements PostManager {
	
	private ArrayList<Post> postList;
	private ServiceReference<?> cameraServiceReference;
	CameraProvider cameraProvider;
	private BundleContext context = Activator.getContext();
	
	public PostManagerImpl() {
		this.postList = new ArrayList<>();
	}
	
	@Override
	public void newPost(String username) {
		
		BufferedReader reader = new BufferedReader
				(new InputStreamReader(System.in));
		String inputString = "";
		
		try {
			inputLoop: while (true) {
				printNewPostMenu();
				
				//get user input on choice
				inputString = reader.readLine();
				String caption = "";
				String photo = "";
				
				if (inputString.length() == 1) {
					switch (inputString) {
						case "1": {
							//get a photo from camera provider
							System.out.println("Calling Camera Provider...");
							
							//get camera provider service
							cameraServiceReference = context
									.getServiceReference(CameraProvider.class.getName());
							cameraProvider = (CameraProvider) 
									context.getService(cameraServiceReference);
							photo = cameraProvider.takePhoto();
							
							//get text input for caption
							System.out.println("Photo Taken!");
							System.out.print("\nEnter photo caption: ");
							caption = reader.readLine();
							
							//create a post object
							postList.add(new Post(
									(postList.size()+1), 
									caption, 
									photo, 
									username));
							System.out.println("New Post Saved!");
							
							break;
						}
						case "2": {
							//get input
							System.out.print("\nEnter post caption: ");
							caption = reader.readLine();
							
							//create a post object
							postList.add(new Post(
									(postList.size()+1), 
									caption, 
									username));
							System.out.println("New Post Saved!");
							
							break;
						}
						case "3": {
							displayAllPosts();
							break;
						}
						default: {
							//any other value restarts the loop
							System.out.println("Unexpected value: Enter "
									+ "1/2 or leave empty to cancel");
							continue inputLoop;
						}
					}
				} else {
					//exit / error
					if(inputString.length() == 0) {
						System.out.println("Cancel!");
						break inputLoop;
					} else {
						System.out.println("Unexpected value: Enter 1/2 or leave empty to cancel");
					}
					
				} 
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Override
	public void displayAllPosts() {
		//display all posts
		int index = 1;
		for(Post p : postList) {
			p.displayPost(index++);
		}
	}
	
	@Override
	public void deletePost() {
		BufferedReader reader = new BufferedReader
				(new InputStreamReader(System.in));
		String inputString = "";
		
		try {
			inputLoop: while(true) {
				printDeletePostMenu();
				
				//get user input
				inputString = reader.readLine();
				
				if(inputString.length() == 0) {
					System.out.println("Cancel!");
					break inputLoop;
				} else {
					int deleteIndex = Integer.parseInt(inputString);
					
					if(postList.size() < deleteIndex) {
						System.out.println("Post not found!");
						continue inputLoop;
					} else {
						postList.remove(deleteIndex-1);
						break inputLoop;
					}
				}
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	private void printNewPostMenu() {
		System.out.println("\n~~~~~~Create a Post~~~~~");
		System.out.println("1: Photo");
		System.out.println("2: Text");
		System.out.print("\nSelect option: ");
	}
	
	private void printDeletePostMenu() {
		System.out.println("\n~~~~~~~Delete Post~~~~~~");
		System.out.print("\nEnter post number : ");
	}

}
