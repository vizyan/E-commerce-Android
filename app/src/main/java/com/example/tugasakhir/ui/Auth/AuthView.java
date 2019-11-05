package com.example.tugasakhir.ui.Auth;

import com.example.tugasakhir.data.model.User.DataUser;

public interface AuthView {

    String getEmail();

    String getName();

    String getPassword();

    void successSignin(DataUser dataUser, String balance);

    void successSignUp(DataUser data);

    void failed(String message);
}
