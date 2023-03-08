package com.redstream.usermanagerservice;

import java.util.ArrayList;

public interface IUser {
    public boolean addUser( User user );
    public boolean deleteUser( String username );
    public boolean updateUser( String username, User user );
    public User getUser( String username );
    public ArrayList<User> getAllUsers();
}
