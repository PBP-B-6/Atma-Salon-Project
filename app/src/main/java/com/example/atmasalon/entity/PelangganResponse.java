package com.example.atmasalon.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PelangganResponse
{
    private String message;

    //TODO: disesuaiin juga
    @SerializedName("produk")
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
