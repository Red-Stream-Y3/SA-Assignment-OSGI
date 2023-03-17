package com.redstream.followerservicemanager;

public class Friend {
	
	private String friendName; 
	private String followerName;
	
	public String getFriendName() {
		return friendName;
	}
	public void setFriendName(String friendName) {
		this.friendName = friendName;
	}
	
	public String getFollowerName() {
		return followerName;
	}
	
	public void setFollowerName(String followerName) {
		this.followerName = followerName;
	}
	
	public String viewFriends() {
		return (friendName);
	}

}
