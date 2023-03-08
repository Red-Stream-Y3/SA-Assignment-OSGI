package com.redstream.usermanagerservice;

import java.util.ArrayList;

public class UserImpl implements IUser {
    private ArrayList<User> users = new ArrayList<User>();

    public boolean addUser( User user ) {
        if ( users.contains( user ) ) {
            return false;
        }
        users.add( user );
        return true;
    }

    public boolean deleteUser( String username ) {
        for ( User user : users ) {
            if ( user.getUsername().equals( username ) ) {
                users.remove( user );
                return true;
            }
        }
        return false;
    }

    public boolean updateUser( String username, User user ) {
        for ( User u : users ) {
            if ( u.getUsername().equals( username ) ) {
                u.setAge( user.getAge() );
                u.setCountry( user.getCountry() );
                u.setEmail( user.getEmail() );
                return true;
            }
        }
        return false;
    }

    public User getUser( String username ) {
        for ( User user : users ) {
            if ( user.getUsername().equals( username ) ) {
                return user;
            }
        }
        return null;
    }

    public ArrayList<User> getAllUsers() {
        return users;
    }
}
