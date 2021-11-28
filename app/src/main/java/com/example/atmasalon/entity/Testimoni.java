package com.example.atmasalon.entity;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.example.atmasalon.BR;

public class Testimoni extends BaseObservable {

    private int id = 0;
    private int idUser = 0;
    private String testimoni;

    public Testimoni(){}

    @Bindable
    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
        notifyPropertyChanged(BR.idUser);
    }

    @Bindable
    public String getTestimoni() {
        return testimoni;
    }

    public void setTestimoni(String testimoni) {
        this.testimoni = testimoni;
        notifyPropertyChanged(BR.testimoni);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
