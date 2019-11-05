package com.example.tugasakhir.ui.Auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tugasakhir.R;
import com.example.tugasakhir.data.model.User.DataUser;
import com.example.tugasakhir.ui.Dashboard.DashboardActivity;
import com.google.gson.Gson;

public class SigninActivity extends AppCompatActivity implements AuthView {

    private AuthPresenter authPresenter;
    AlertDialog alertDialog;
    AlertDialog.Builder dialogBuilder;
    AnimationDrawable animationDrawable;

    Button btnSignin;
    ConstraintLayout clBgSignin;
    EditText etEmail, etPassword;
    TextView tvSinup;

    String email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        initPresenter();
        initView();
        initAnimation();
        initSignin();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (animationDrawable != null && animationDrawable.isRunning()){
            animationDrawable.stop();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (animationDrawable != null && !animationDrawable.isRunning()){
            animationDrawable.start();
        }
    }

    public void initPresenter() {
        authPresenter = new AuthPresenter(this);
    }

    public void initView() {
        btnSignin = findViewById(R.id.btnSignin);
        clBgSignin = findViewById(R.id.clBgSignin);
        etEmail = findViewById(R.id.etSiEmail);
        etPassword = findViewById(R.id.etSiPassword);
        tvSinup = findViewById(R.id.tvSignUp);
    }

    private void initAnimation() {
        animationDrawable = (AnimationDrawable) clBgSignin.getBackground();
        animationDrawable.setEnterFadeDuration(3000);
        animationDrawable.setExitFadeDuration(3000);
    }

    public void initSignin() {
        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (initValid()){
                    authPresenter.login();
                    showProgressDialog();
                }
            }
        });
    }

    public boolean initValid() {
        initVariable();
        validEmail();
        validPassword();

        if (validEmail()!=false && validPassword()!=false) {
            return true;
        }
        return false;
    }

    public void initVariable() {
        email = etEmail.getText().toString();
        password = etPassword.getText().toString();
    }

    public boolean validEmail() {
        if (email.isEmpty()) {
            etEmail.setHint("Isikan email");
            etEmail.setBackgroundResource(R.drawable.bg_rounded_trans_red);
            return false;
        } else {
            etEmail.setBackgroundResource(R.drawable.bg_rounded_trans_white);
            return true;
        }
    }

    public boolean validPassword() {
        if (password.isEmpty()) {
            etPassword.setHint("Isikan password");
            etPassword.setBackgroundResource(R.drawable.bg_rounded_trans_red);
            return false;
        } else {
            etPassword.setBackgroundResource(R.drawable.bg_rounded_trans_white);
            return true;
        }
    }

    public void onClick(View v) {
        Intent signup = new Intent(SigninActivity.this, SignupActivity.class);
        startActivity(signup);
    }

    public void savePreference(DataUser dataUser, String balance){
        Gson gson = new Gson();
        String DataUser = gson.toJson(dataUser);
        SharedPreferences preferences = getSharedPreferences("LoginPreference", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("password", password);
        editor.putString("balance", balance);
        editor.putString("dataUser", DataUser);
        editor.commit();
        editor.apply();
    }

    public void showProgressDialog() {
        dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View dialogView = inflater.inflate(R.layout.progress_dialog_layout, null);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setCancelable(false);
        alertDialog = dialogBuilder.create();
        alertDialog.show();
    }

    public void hideProgressDialog(){
        alertDialog.dismiss();
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void successSignin(DataUser dataUser, String balance) {
        hideProgressDialog();
        savePreference(dataUser, balance);
        Intent home = new Intent(this, DashboardActivity.class);
        startActivity(home);
        finishAffinity();
    }

    @Override
    public void successSignUp(DataUser data) {

    }

    @Override
    public void failed(String message) {
        hideProgressDialog();
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}

