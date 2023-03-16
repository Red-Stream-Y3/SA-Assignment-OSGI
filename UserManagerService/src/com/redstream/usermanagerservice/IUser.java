package com.redstream.usermanagerservice;

public interface IUser {
    public void addUser();
    public boolean deleteUser();
    public void updateUser();
    public void getUser();
    public void getAllUsers();
    public boolean login();
    public boolean logout();
    public String getCurrentUserName();
}
