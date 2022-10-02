package com.example.luntian.model;

public class Users {
  String Username, Email, Password,Token,Device;

    public Users() {
    }

    public Users(String username, String email, String password,String token,String device) {
        Username = username;
        Email = email;
        Password = password;
        Token = token;
        Device = device;
    }

    public String getUsername() {
        return Username;
    }
    public String getToken() {
        return Token;
    }

    public void setUsername(String username) {
        Username = username;
    }
    public void setToken(String token) {
        Token = token;
    }
    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }
    public String getDevice() {
        return Device;
    }

    public void setDevice(String device) {
        Device = device;
    }
    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
