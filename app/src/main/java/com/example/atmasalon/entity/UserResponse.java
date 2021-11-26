package com.example.atmasalon.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserResponse
{
    private String message;

    //TODO: Dibenerin dan disesuaiin namanya sama api
    @SerializedName("produk")
    private List<User> userList;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }
}
