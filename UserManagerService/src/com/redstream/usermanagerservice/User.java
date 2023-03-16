package com.redstream.usermanagerservice;

public class User {
    private String username;
	private String password;
    private String email;
    private String country;
    private int age;
    
	public User(String username,String password, String email, String country, int age) {
		super();
		this.username = username;
		this.password = password;
		this.email = email;
		this.country = country;
		this.age = age;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
}
