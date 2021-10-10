package com.example.atmasalon.entity;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.atmasalon.BR;

@Entity(tableName = "user")
public class User extends BaseObservable {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "nama")
    private String nama;

    @ColumnInfo(name = "email")
    private String email;

    @ColumnInfo(name = "password")
    private String password;

    @ColumnInfo(name = "jenisKelamin")
    private String jenisKelamin;

    @ColumnInfo(name = "noTelp")
    private String noTelp;

    @ColumnInfo(name = "saldo")
    private double saldo;

    public User(){}

    public User(String nama, String email, String password, String jenisKelamin, String noTelp, double saldo) {
        this.nama = nama;
        this.email = email;
        this.password = password;
        this.jenisKelamin = jenisKelamin;
        this.noTelp = noTelp;
        this.saldo = saldo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
    public String getJenisKelamin() {
        return jenisKelamin;
    }

    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
        notifyPropertyChanged(BR.jenisKelamin);
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
    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
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
            this.saldo = Double.parseDouble(saldo);
        }
        notifyPropertyChanged(BR.saldo);
    }
}
