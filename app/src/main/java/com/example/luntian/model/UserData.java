package com.example.luntian.model;

public class UserData {

    public String device;
    public String email;
    public String password;
    public String token;
    public UserData() {
        // Default constructor required for calls to DataSnapshot.getValue(Post.class)
    }
    public UserData(String device, String email, String password, String token, String username) {
        this.device = device;
        this.email = email;
        this.password = password;
        this.token = token;
        this.username = username;
    }

    public String username;
}
