package com.redstream.usermanagerservice;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import com.redstream.smdatabase.*;

public class UserImpl implements IUser {

    private Connection connection = null;
	private Statement statement = null;
	private IDatabase database;
	private ResultSet resultSet;
    private User currentUser;

    Scanner scan = new Scanner(System.in);

    public UserImpl() {
        super();
		database = (IDatabase) new SocialMediaDB();
		connection = database.connection();
    }

    @Override
    public void addUser() {
        System.out.println("========================");
        System.out.println("Register to the platform");
        System.out.println("========================");
        System.out.println("Enter User Name: ");
        String name = scan.nextLine().trim();
        System.out.println("Enter Password: ");
        String password = scan.nextLine().trim();
        System.out.println("Enter User Email: ");
        String email = scan.nextLine().trim();
        System.out.println("Enter User Country: ");
        String country = scan.nextLine().trim();
        System.out.println("Enter User Age: ");
        int age = Integer.parseInt(scan.nextLine().trim());

        try {
            statement = connection.createStatement();
            String sql = "INSERT INTO users (username, password, email, country, age) VALUES ('" + name + "', '" + password + "', '" + email + "', '" + country + "', '" + age + "')";
            statement.executeUpdate(sql);
            System.out.println("User Added Successfully");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("User can't be added");
        }
       
    }

    @Override
    public boolean deleteUser() {
        System.out.println("=================");
        System.out.println("Delete My Account");
        System.out.println("=================");
        System.out.println("Enter User Name Again: ");
        String name = scan.nextLine().trim();
        System.out.println("Enter Password Again: ");
        String password = scan.nextLine().trim();
        if (name.equals(currentUser.getUsername()) && password.equals(currentUser.getPassword())) {
            try {
                statement = connection.createStatement();
                String sql = "DELETE FROM users WHERE username = '" + name + "' AND password = '" + password + "'";
                statement.executeUpdate(sql);
                System.out.println("User Deleted Successfully");
                return logout();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("User can't be deleted");
            }
        } else {
            System.out.println("Your credentials are incorrect");
        }
		return false;
        
    }

    @Override
    public void updateUser() {
        System.out.println("=================");
        System.out.println("Update My Account");
        System.out.println("=================");
        System.out.println("Enter User Name Again: ");
        String name = scan.nextLine().trim();
        System.out.println("Enter Password Again: ");
        String password = scan.nextLine().trim();
        if (name.equals(currentUser.getUsername()) && password.equals(currentUser.getPassword())) {
            System.out.println("What do you want to update?");
            System.out.println("1. Username");
            System.out.println("2. Password");
            System.out.println("3. Email");
            System.out.println("4. Country");
            System.out.println("5. Age");
            System.out.println("6. Exit");
            System.out.println("Enter your choice: ");
            int choice = Integer.parseInt(scan.nextLine().trim());
        
            switch (choice) {
                case 1:
                    System.out.println("Enter New Username: ");
                    String newUsername = scan.nextLine().trim();
                    try {
                        statement = connection.createStatement();
                        String sql = "UPDATE users SET username = '" + newUsername + "' WHERE username = '" + name + "' AND password = '" + password + "'";
                        statement.executeUpdate(sql);
                        currentUser.setUsername(newUsername);
                        System.out.println("Username Updated Successfully");
                    } catch (SQLException e) {
                        e.printStackTrace();
                        System.out.println("Username can't be updated");
                    }
                    break;
                case 2:
                    System.out.println("Enter New Password: ");
                    String newPassword = scan.nextLine().trim();
                    try {
                        statement = connection.createStatement();
                        String sql = "UPDATE users SET password = '" + newPassword + "' WHERE username = '" + name + "' AND password = '" + password + "'";
                        statement.executeUpdate(sql);
                        currentUser.setPassword(newPassword);
                        System.out.println("Password Updated Successfully");
                    } catch (SQLException e) {
                        e.printStackTrace();
                        System.out.println("Password can't be updated");
                    }
                    break;
                case 3:
                    System.out.println("Enter New Email: ");
                    String newEmail = scan.nextLine().trim();
                    try {
                        statement = connection.createStatement();
                        String sql = "UPDATE users SET email = '" + newEmail + "' WHERE username = '" + name + "' AND password = '" + password + "'";
                        statement.executeUpdate(sql);
                        currentUser.setEmail(newEmail);
                        System.out.println("Email Updated Successfully");
                    } catch (SQLException e) {
                        e.printStackTrace();
                        System.out.println("Email can't be updated");
                    }
                    break;
                case 4:
                    System.out.println("Enter New Country: ");
                    String newCountry = scan.nextLine().trim();
                    try {
                        statement = connection.createStatement();
                        String sql = "UPDATE users SET country = '" + newCountry + "' WHERE username = '" + name + "' AND password = '" + password + "'";
                        statement.executeUpdate(sql);
                        currentUser.setCountry(newCountry);
                        System.out.println("Country Updated Successfully");
                    } catch (SQLException e) {
                        e.printStackTrace();
                        System.out.println("Country can't be updated");
                    }
                    break;
                case 5:
                    System.out.println("Enter New Age: ");
                    int newAge = Integer.parseInt(scan.nextLine().trim());
                    try {
                        statement = connection.createStatement();
                        String sql = "UPDATE users SET age = '" + newAge + "' WHERE username = '" + name + "' AND password = '" + password + "'";
                        statement.executeUpdate(sql);
                        currentUser.setAge(newAge);
                        System.out.println("Age Updated Successfully");
                    } catch (SQLException e) {
                        e.printStackTrace();
                        System.out.println("Age can't be updated");
                    }
                    break;
                case 6:
                    break;
                default:
                    System.out.println("Invalid Choice");
                    break;
            }
        } else {
            System.out.println("Your credentials are incorrect");
        }
    }

    @Override
    public void getUser() {
        System.out.println("===============");
        System.out.println("View My Account");
        System.out.println("===============");
        // SHOWS THE RESULT OF THE CURRENT USER
        System.out.println("Username: " + currentUser.getUsername());
        System.out.println("Password: *********");
        System.out.println("Email: " + currentUser.getEmail());
        System.out.println("Country: " + currentUser.getCountry());
        System.out.println("Age: " + currentUser.getAge());
    }

    @Override
    public void getAllUsers() {
        System.out.println("============");
        System.out.println("Search Users");
        System.out.println("============");
        System.out.println("Enter Username: ");
        String name = scan.nextLine().trim();
        try {
            statement = connection.createStatement();
            String sql = "SELECT * FROM users WHERE username = '" + name + "'";
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                System.out.println("Username: " + resultSet.getString("username"));
                System.out.println("Email: " + resultSet.getString("email"));
                System.out.println("Country: " + resultSet.getString("country"));
                System.out.println("Age: " + resultSet.getInt("age"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("User can't be found");
        }
        
    }

    @Override
    public boolean login() {
        System.out.println("========================");
        System.out.println("Please Login to continue");
        System.out.println("========================");
        System.out.println("Enter User Name : ");
        String name = scan.nextLine().trim();
        System.out.println("Enter Password : ");
        String password = scan.nextLine().trim();
        try {
            statement = connection.createStatement();
            String sql = "SELECT * FROM users WHERE username = '" + name + "' AND password = '" + password + "'";
            resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                currentUser = new User(resultSet.getString("username"), resultSet.getString("password"), resultSet.getString("email"), resultSet.getString("country"), resultSet.getInt("age"));
                System.out.println("=============");
                System.out.println("Login Success");
                System.out.println("=============");
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Your credentials are incorrect");
        }
        System.out.println("Your credentials are incorrect");
        return false;
    }

    @Override
    public boolean logout() {
        currentUser = null;
        System.out.println("==============");
        System.out.println("Logout Success");
        System.out.println("==============");
        return true;
    }

    @Override
    public String getCurrentUserName() {
        return currentUser.getUsername();
    }
    
}
