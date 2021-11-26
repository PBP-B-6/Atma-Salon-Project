package com.example.atmasalon.entity;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.atmasalon.BR;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "user")
public class User extends BaseObservable {
    //TODO: Sesuaiin sama database baru, database internal hapus2 ja
    @PrimaryKey(autoGenerate = true)
    private int id;

    @SerializedName("namaUser")
    @ColumnInfo(name = "nama")
    private String nama;

    @SerializedName("email")
    @ColumnInfo(name = "email")
    private String email;

    @SerializedName("password")
    @ColumnInfo(name = "password")
    private String password;

    @ColumnInfo(name = "jenisKelamin")
    private String jenisKelamin="";

    @SerializedName("jenisKelamin")
    private boolean kelamin;

    @SerializedName("noTelpUser")
    @ColumnInfo(name = "noTelp")
    private String noTelp;

    @SerializedName("saldo")
    @ColumnInfo(name = "saldo")
    private double saldo=0;

    @SerializedName("urlGambar")
    private String urlGambar="";

    @SerializedName("status")
    private boolean status=false;

    public User(){}

    //TODO: kalo dah full backend bisa, ini hapus aja, trs yang baru, kalo tidak perlu, kurangi parameter
    public User(String nama, String email, String password, String jenisKelamin, String noTelp, double saldo) {
        this.nama = nama;
        this.email = email;
        this.password = password;
        this.jenisKelamin = jenisKelamin;
        this.noTelp = noTelp;
        this.saldo = saldo;
    }

    public User(String nama, String email, String password, boolean kelamin, String noTelp, double saldo, String urlGambar, boolean status) {
        this.nama = nama;
        this.email = email;
        this.password = password;
        this.kelamin = kelamin;
        this.noTelp = noTelp;
        this.saldo = saldo;
        this.urlGambar = urlGambar;
        this.status = status;
    }

    public User(String nama, String email, String password, boolean kelamin, String noTelp) {
        this.nama = nama;
        this.email = email;
        this.password = password;
        this.kelamin = kelamin;
        this.noTelp = noTelp;
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

    public boolean isKelamin() {
        return kelamin;
    }

    public void setKelamin(boolean kelamin) {
        this.kelamin = kelamin;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
