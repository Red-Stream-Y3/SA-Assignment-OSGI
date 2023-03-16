package com.redstream.usermanagerservice;

import java.util.ArrayList;
import java.util.Scanner;

public class UserImpl implements IUser {

    private ArrayList<User> users = new ArrayList<User>(); // User list
    Scanner scan = new Scanner(System.in); // Scanner object

    // current user
   private User currentUser;

    @Override
    public void addUser() {
        System.out.println("================");
        System.out.println("Add New User");
        System.out.println("================");
        System.out.println("Enter User Name: ");
        String name = scan.nextLine().trim();
        System.out.println("Enter User Email: ");
        String email = scan.nextLine().trim();
        System.out.println("Enter User Country: ");
        String country = scan.nextLine().trim();
        System.out.println("Enter User Age: ");
        int age = Integer.parseInt(scan.nextLine().trim());

        User user = new User(name, email, country, age);
        
        if(users.contains(user)) {
            System.out.println("ERROR - User already exists"); // Check if user already exists
        } else {
            users.add(user);
            System.out.println("User added successfully");
        }
        
    }

    @Override
    public void deleteUser() {
        System.out.println("================");
        System.out.println("Delete User");
        System.out.println("================");
        System.out.println("Enter User Name: ");
        String name = scan.nextLine().trim();

        for (User user : users) {
            if(user.getUsername().equals(name)) {
                users.remove(user);
                System.out.println("User deleted successfully"); // Check if user exists
                return;
            }
            else {
                System.out.println("ERROR - User not found");
            }
        }
        

    }

    @Override
    public void updateUser() {
        System.out.println("================");
        System.out.println("Update User");
        System.out.println("================");
        System.out.println("Enter User Name: ");
        String name = scan.nextLine().trim();

        for (User user : users) {
            if(user.getUsername().equals(name)) { // Check if user exists
                System.out.println("What do you want to update?");
                System.out.println("1. Email");
                System.out.println("2. Country");
                System.out.println("3. Age");
                System.out.println("Enter your choice: ");
                int choice = Integer.parseInt(scan.nextLine().trim());

                switch (choice) { // Update user details
                    case 1:
                        System.out.println("Enter new email: ");
                        String email = scan.nextLine().trim();
                        user.setEmail(email);
                        break;
                    case 2:
                        System.out.println("Enter new country: ");
                        String country = scan.nextLine().trim();
                        user.setCountry(country);
                        break;
                    case 3:
                        System.out.println("Enter new age: ");
                        int age = Integer.parseInt(scan.nextLine().trim());
                        user.setAge(age);
                        break;
                    default:
                        System.out.println("Invalid choice");
                        break;
                }

                System.out.println("User updated successfully");
                return;
            }
            else {
                System.out.println("ERROR - User not found");
            }
        }
        
    }

    @Override
    public void getUser() {
        System.out.println("================");
        System.out.println("Search User");
        System.out.println("================");
        System.out.println("Enter User Name: ");
        String name = scan.nextLine().trim();

        for (User user : users) { // Check if user exists
            if(user.getUsername().equals(name)) { 
                System.out.println("User found");
                System.out.println("User Name: " + user.getUsername());
                System.out.println("User Email: " + user.getEmail());
                System.out.println("User Country: " + user.getCountry());
                System.out.println("User Age: " + user.getAge());
                return;
            }
            else {
                System.out.println("ERROR - User not found");
            }
        }
        
    }

    @Override
    public void getAllUsers() {
        System.out.println("================");
        System.out.println("View All Users");
        System.out.println("================");

        if(users.isEmpty()) {
            System.out.println("ERROR - No users found"); // Check if user list is empty
            return;
        }
        else {
            for (User user : users) { // Print all users
                System.out.println("User Name: " + user.getUsername());
                System.out.println("User Email: " + user.getEmail());
                System.out.println("User Country: " + user.getCountry());
                System.out.println("User Age: " + user.getAge());
                System.out.println("================");
            }
        }
    }

    @Override
    public boolean login() {
        System.out.println("================");
        System.out.println("Login");
        System.out.println("================");
        System.out.println("Enter User Name: ");
        String username = scan.nextLine().trim();

        /*for (User user : users) { // Check if user exists
            if(user.getUsername().equals(username)) {
                currentUser = user;
                return true;
            }
        }
        return false;*/
        System.out.println("================");
		System.out.println("User : " + username);
		System.out.println("================");
		return true;
    }

    @Override
    public String getCurrentUserName() {
        return currentUser.getUsername();
    }
    
}
