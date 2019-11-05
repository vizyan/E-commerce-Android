package com.example.tugasakhir.ui.Home;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.tugasakhir.R;

public class HomeActivity extends AppCompatActivity implements HomeView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }
}
