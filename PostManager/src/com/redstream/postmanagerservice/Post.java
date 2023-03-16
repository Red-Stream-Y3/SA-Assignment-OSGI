package com.redstream.postmanagerservice;

import java.util.ArrayList;

public class Post {
	private int postID;
	private final String username;
	private String text;
	private String photo;
	private ArrayList<String> likedUsers;
	
	public Post(int postID, String text, String photo, String username) {
		this.postID = postID;
		this.text = text;
		this.photo = photo;
		this.username = username;
		likedUsers = new ArrayList<>();
	}
	
	public Post(int postID, String text, String username) {
		this.postID = postID;
		this.text = text;
		this.photo = "********************";
		this.username = username;
		likedUsers = new ArrayList<>();
	}

	public void addLike(String username) {
		likedUsers.add(username);
	}
	
	public void removeLike(String username) {
		likedUsers.remove(username);
	}
	
	//getters and setters
	public int getPostID() {
		return postID;
	}

	public void setPostID(int postID) {
		this.postID = postID;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public void displayPost() {
		System.out.println("\n====================");
		System.out.println(username+": "+text);
		System.out.println("--------------------");
		System.out.println(photo);
		System.out.println("====================\n");
	}
	
	public void displayPost(int index) {
		System.out.println("\n====================");
		System.out.println("Post/"+index+" | user/"+username);
		System.out.println(text);
		System.out.println("--------------------");
		System.out.println(photo);
		System.out.println("====================\n");
	}
	
}
