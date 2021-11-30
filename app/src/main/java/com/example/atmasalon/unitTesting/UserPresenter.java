package com.example.atmasalon.unitTesting;

import android.content.Context;

import com.example.atmasalon.entity.UserFromJson;

public class UserPresenter
{
    private UserView view;
    private UserService service;
    private UserCallback callback;
    private UserFromJson user;
    private Context con;

    public UserPresenter(UserView view, UserService service, Context con)
    {
        this.view = view;
        this.service = service;
        this.con = con;
    }

    public void onProfilClicked(){
        String regexPhone = "08+[0-9]{8,11}";
        String regexEmail = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if(view.getNama().isEmpty())
        {
            view.showNamaError("Nama tidak boleh kosong");
        }
        else if(view.getEmail().isEmpty())
        {
            view.showEmailError("Email tidak boleh kosong");
        }
        else if(!(view.getEmail().matches(regexEmail)))
        {
            view.showEmailError("Email harus memenuhi format");
        }
        else if(view.getPassword1().isEmpty())
        {
            view.showPassword1Error("Password tidak boleh kosong");
        }
        else if(view.getPassword2().isEmpty())
        {
            view.showPassword2Error("Password tidak boleh kosong");
        }
        else if(!view.getPassword1().equals(view.getPassword2()))
        {
            view.showPasswordMatchingError("Password yang diisikan harus sama");
        }
        else if(view.getKelaminValue() == -1)
        {
            view.showKelaminError("Jenis kelamin harus dipilih");
        }
        else if(view.getPhone().isEmpty())
        {
            view.showPhoneError("Nomor Telepon tidak boleh kosong");
        }
        else if(view.getPhone().length() < 10 || view.getPhone().length() > 13)
        {
            view.showPhoneError("Nomor Telepon minimal 10 dan maximal 13");
        }
        else if(!view.getPhone().matches(regexPhone))
        {
            view.showPhoneError("Nomor Telepon harus memenuhi format");
        }
        else
        {
            service.CreateUser(user, view, con, new UserCallback() {
                @Override
                public void onSuccess(boolean value, UserFromJson user) {
                    view.startMainUser();
                }

                @Override
                public void onError() {

                }
            });
            return;
        }

    }
}
