package com.example.myapplication;

public class User {
    String ID, PassWord;

    public User(String ID, String passWord) {
        this.ID = ID;
        PassWord = passWord;
    }

    public User() {
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getPassWord() {
        return PassWord;
    }

    public void setPassWord(String passWord) {
        PassWord = passWord;
    }
}