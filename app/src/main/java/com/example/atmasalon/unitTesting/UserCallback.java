package com.example.atmasalon.unitTesting;

import com.example.atmasalon.entity.UserFromJson;

public interface UserCallback {
    void onSuccess(boolean value, UserFromJson user);
    void onError();
}
