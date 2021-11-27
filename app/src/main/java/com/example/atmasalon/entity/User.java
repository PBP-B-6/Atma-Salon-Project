package com.example.atmasalon.entity;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.example.atmasalon.BR;
import com.google.gson.annotations.SerializedName;

public class User extends BaseObservable {
    //TODO: Sesuaiin sama database baru, database internal hapus2 ja
    @SerializedName("id")
    private int id;

    @SerializedName("namaUser")
    private String nama;

    @SerializedName("email")
    private String email;

    @SerializedName("password")
    private String password;

    @SerializedName("jenisKelamin")
    private boolean jenisKelamin;

    @SerializedName("noTelpUser")
    private String noTelp;

    @SerializedName("saldo")
    private float saldo = 0;

    @SerializedName("urlGambar")
    private String urlGambar = "";

    @SerializedName("status")
    private boolean status = false;

    public User(){}

    public User(int id, String nama, String email, String password, boolean kelamin, String noTelp, float saldo, String urlGambar, boolean status) {
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

    public User(String nama, String email, String password, boolean kelamin, String noTelp, float saldo, String urlGambar, boolean status) {
        this.nama = nama;
        this.email = email;
        this.password = password;
        this.jenisKelamin = kelamin;
        this.noTelp = noTelp;
        this.saldo = saldo;
        this.urlGambar = urlGambar;
        this.status = status;
    }

    @Bindable
    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
        notifyPropertyChanged(BR.nama);
    }

    @Bindable
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        notifyPropertyChanged(BR.email);
    }

    @Bindable
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        notifyPropertyChanged(BR.password);
    }

    @Bindable
    public String getNoTelp() {
        return noTelp;
    }

    public void setNoTelp(String noTelp) {
        this.noTelp = noTelp;
        notifyPropertyChanged(BR.noTelp);
    }

    @Bindable
    public float getSaldo() {
        return saldo;
    }

    public void setSaldo(float saldo) {
        this.saldo = saldo;
        notifyPropertyChanged(BR.saldo);
    }

    @Bindable
    public String getStringSaldo(){
        return String.valueOf(saldo);
    }

    public void setStringSaldo(String saldo)
    {
        if(saldo.isEmpty())
        {
            //blm tau
        }
        else
        {
            this.saldo = Float.parseFloat(saldo);
        }
        notifyPropertyChanged(BR.saldo);
    }

    @Bindable
    public String getUrlGambar() {
        return urlGambar;
    }

    public void setUrlGambar(String urlGambar) {
        this.urlGambar = urlGambar;
    }

    @Bindable
    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isJenisKelamin() {
        return jenisKelamin;
    }

    public void setJenisKelamin(boolean jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
