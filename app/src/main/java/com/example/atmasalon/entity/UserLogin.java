package com.example.atmasalon.entity;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.example.atmasalon.BR;

public class UserLogin extends BaseObservable {
    //UserLogin merupakan Kelas yang digunakan sebagai kelas temp / penyimpanan sementara
    //  untuk data Login pengguna
    private String email;
    private String password;

    public UserLogin(){};

    public UserLogin(String email, String password) {
        this.email = email;
        this.password = password;
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
}
