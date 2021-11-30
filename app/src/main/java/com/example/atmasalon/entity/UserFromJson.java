package com.example.atmasalon.entity;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.example.atmasalon.BR;
import com.google.gson.annotations.SerializedName;

public class UserFromJson extends BaseObservable {
    //TODO: Sesuaiin sama database baru, database internal hapus2 ja
    @SerializedName("id")
    private int id = 0;

    @SerializedName("namaUser")
    private String nama;

    @SerializedName("email")
    private String email;

    @SerializedName("password")
    private String password;

    @SerializedName("jenisKelamin")
    private int jenisKelamin;

    @SerializedName("noTelpUser")
    private String noTelp;

    @SerializedName("saldo")
    private float saldo = 0;

    @SerializedName("urlGambar")
    private String urlGambar = "default";

    @SerializedName("status")
    private int status;

    public UserFromJson(){}

    public UserFromJson(int id, String nama, String email, String password, int kelamin, String noTelp, float saldo, String urlGambar, int status) {
        this.id = id;
        this.nama = nama;
        this.email = email;
        this.password = password;
        this.jenisKelamin = kelamin;
        this.noTelp = noTelp;
        this.saldo = saldo;
        this.urlGambar = urlGambar;
        this.status = status;
    }

    public UserFromJson(String nama, String email, String password, int kelamin, String noTelp, float saldo, String urlGambar, int status) {
        this.nama = nama;
        this.email = email;
        this.password = password;
        this.jenisKelamin = kelamin;
        this.noTelp = noTelp;
        this.saldo = saldo;
        this.urlGambar = urlGambar;
        this.status = status;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
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

    public String getNoTelp() {
        return noTelp;
    }

    public void setNoTelp(String noTelp) {
        this.noTelp = noTelp;
    }

    public float getSaldo() {
        return saldo;
    }

    public void setSaldo(float saldo) {
        this.saldo = saldo;
    }


    public String getUrlGambar() {
        return urlGambar;
    }

    public void setUrlGambar(String urlGambar) {
        this.urlGambar = urlGambar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getJenisKelamin() {
        return jenisKelamin;
    }

    public void setJenisKelamin(int jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
