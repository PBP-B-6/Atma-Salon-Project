package com.example.atmasalon.unitTesting;

public interface UserView {
    String getNama();
    void showNamaError(String error);

    String getEmail();
    void showEmailError(String error);

    String getPassword1();
    void showPassword1Error(String error);

    String getPassword2();
    void showPassword2Error(String error);
    void showPasswordMatchingError(String error);

    String getPhone();
    void showPhoneError(String error);

    int getKelaminValue();
    void showKelaminError(String error);

    void startMainUser();

    void showUserError(String message);
    void showErrorResponse(String message);
}
