package com.example.tugasakhir.ui.User;

import com.example.tugasakhir.data.model.User.DataUser;

public interface UserView {

    String getName();

    String getPassword();

    String getPhone();

    String getAddress();

    void successEdit(DataUser data);

    void successDelete();

    void successUser(DataUser dataUser);

    void failed(String s);
}
