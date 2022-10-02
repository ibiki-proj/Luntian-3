package com.example.luntian.model;

public class Users {
  String Username, Email, Password,Token;

    public Users() {
    }

    public Users(String username, String email, String password,String token) {
        Username = username;
        Email = email;
        Password = password;
        Token = token;
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

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
