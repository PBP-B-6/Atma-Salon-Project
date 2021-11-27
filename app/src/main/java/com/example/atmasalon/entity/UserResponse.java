package com.example.atmasalon.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserResponse
{
    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private User user;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
