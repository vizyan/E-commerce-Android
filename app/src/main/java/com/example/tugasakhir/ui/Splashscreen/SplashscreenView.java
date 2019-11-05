package com.example.tugasakhir.ui.Splashscreen;

import com.example.tugasakhir.data.model.User.DataUser;

public interface SplashscreenView {
    void success(DataUser data);

    void failed(String message);
}
