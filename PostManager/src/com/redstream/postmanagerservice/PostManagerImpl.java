package com.redstream.postmanagerservice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import com.redstream.cameraprovider.CameraProvider;

public class PostManagerImpl implements PostManager {
	
	private ArrayList<Post> postList;
	private ServiceReference<?> cameraServiceReference;
	private CameraProvider cameraProvider;
	private BundleContext context = Activator.getContext();
	private Connection conn;
	
	private String GET_POSTS = "SELECT * FROM posts";
	
	public PostManagerImpl(Connection conn) {
		this.postList = new ArrayList<>();
		this.conn = conn;
		
		try {
			Statement stmt = conn.createStatement();
			ResultSet res = stmt.executeQuery(GET_POSTS);
			
			while(res.next()) {
				Post p = new Post(
						res.getInt(1), //post ID
						res.getString(3), //text
						res.getString(4), //photo
						res.getString(2), //user name
						res.getString(5)); //likes
				postList.add(p);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
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
					Post post;
					boolean success = false;
					
					checkInput: switch (inputString) {
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
							System.out.println(photo);
							System.out.println("Photo Taken!");
							System.out.print("\nEnter photo caption: ");
							caption = reader.readLine();
							
							//create a post object
							post = new Post(
									(postList.get(postList.size()-1).getPostID() +1), 
									caption, 
									photo, 
									username);
							postList.add(post);
							
							success = true;
							break checkInput;
						}
						case "2": {
							//get input
							System.out.print("\nEnter post caption: ");
							caption = reader.readLine();
							
							//create a post object
							post = new Post(
									(postList.get(postList.size()-1).getPostID() +1), 
									caption, 
									username);
							postList.add(post);
							
							success = true;
							break checkInput;
						}
						default: {
							//any other value restarts the loop
							System.out.println("Unexpected value: Enter "
									+ "1/2 or leave empty to cancel");
							continue inputLoop;
						}
					}// end of switch
					
					if(success) {//save the post in DB
						String query = post.getSaveQuery();
						
						Statement st = conn.createStatement();
						if(st.executeUpdate(query)>0) {
							System.out.println("New Post Saved!");
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
						//delete from DB
						String query = postList.get(deleteIndex-1).getDeleteQuery();
						try {
							Statement st = conn.createStatement();
							if(st.executeUpdate(query)>0) {
								System.out.println("Post deleted!");
							}
						} catch (SQLException e) {
							e.printStackTrace();
						}
						
						
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
