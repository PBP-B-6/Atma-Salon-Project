package com.example.atmasalon.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PelangganResponse
{
    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private List<Pelanggan> pelangganList;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Pelanggan> getPelangganList() {
        return pelangganList;
    }

    public void setPelangganList(List<Pelanggan> pelangganList) {
        this.pelangganList = pelangganList;
    }
}
