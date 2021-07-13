package com.example.admin.agriculturalseedsystem.backend;

public class UserHelperClass {
    String Name;
    String Email;
    String Phone;
    String Address;
    String Pass;
    String Username;

    public UserHelperClass() {

    }

    public UserHelperClass(String name, String email, String phone, String address, String pass, String username) {
        Name = name;
        Email = email;
        Phone = phone;
        Address = address;
        Pass = pass;
        Username = username;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getPass() {
        return Pass;
    }

    public void setPass(String pass) {
        Pass = pass;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Pass = username;
    }
}

