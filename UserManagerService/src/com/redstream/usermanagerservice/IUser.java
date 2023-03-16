package com.redstream.usermanagerservice;

public interface IUser {
    public void addUser();
    public void deleteUser();
    public void updateUser();
    public void getUser();
    public void getAllUsers();
    public boolean login();
    public String getCurrentUserName();
}
