package com.example.bt_firebasevideo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class UserModel implements Serializable {
    private String email;
    private String password;
    private String name;
    private String avt;

    public UserModel(){

    }

    public UserModel(String email, String password, String name, String avt) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.avt = avt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvt() {
        return avt;
    }

    public void setAvt(String avt) {
        this.avt = avt;
    }
}
