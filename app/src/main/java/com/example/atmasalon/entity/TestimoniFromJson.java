package com.example.atmasalon.entity;

import androidx.databinding.BaseObservable;

import com.google.gson.annotations.SerializedName;

public class TestimoniFromJson extends BaseObservable {
    @SerializedName("id")
    private int id = 0;

    @SerializedName("idUser")
    private int idUser = 0;

    @SerializedName("testimoni")
    private String testi;

    public TestimoniFromJson(){}

    public TestimoniFromJson(int id, int idUser, String testi) {
        this.id = id;
        this.idUser = idUser;
        this.testi = testi;
    }

    public TestimoniFromJson(int idUser, String testi) {
        this.idUser = idUser;
        this.testi = testi;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getTesti() {
        return testi;
    }

    public void setTesti(String testi) {
        this.testi = testi;
    }
}
