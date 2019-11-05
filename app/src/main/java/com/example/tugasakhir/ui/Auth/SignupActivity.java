package com.example.tugasakhir.ui.Auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tugasakhir.R;
import com.example.tugasakhir.data.model.User.DataUser;

public class SignupActivity extends AppCompatActivity implements AuthView{

    private AuthPresenter authPresenter;
    AlertDialog alertDialog;
    AlertDialog.Builder dialogBuilder;
    AnimationDrawable animationDrawable;

    Button btnSignup;
    ConstraintLayout clBgSignup;
    TextView tvDetail;
    EditText etEmail, etName, etPassword, etPass2;

    String name, email, password, rePass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();

            StrictMode.setThreadPolicy(policy);
        }

        initPresenter();
        initView();
        initAnimation();
        initSignup();
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
        btnSignup = findViewById(R.id.btnSignup);
        clBgSignup = findViewById(R.id.clBgSignup);
        etEmail = findViewById(R.id.etSuEmail);
        etPassword = findViewById(R.id.eSutPassword);
        etPass2 = findViewById(R.id.etSuPass2);
        etName = findViewById(R.id.etSuName);
        tvDetail = findViewById(R.id.tvDetail);
    }

    private void initAnimation() {
        animationDrawable = (AnimationDrawable) clBgSignup.getBackground();
        animationDrawable.setEnterFadeDuration(3000);
        animationDrawable.setExitFadeDuration(3000);
    }

    public void initSignup() {
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (initValid()!=false){
                    authPresenter.addAccXlm();
                    showProgressDialog();
                }
            }
        });
    }

    public boolean initValid() {
        initVariable();

        validName();
        validEmail();
        validPassword();
        validRePass();

        if (validName()!=false && validEmail()!=false && validPassword()!=false && validRePass()!=false){
            return true;
        } else {
            return false;
        }
    }

    public void initVariable(){
        name = etName.getText().toString();
        email = etEmail.getText().toString();
        password = etPassword.getText().toString();
        rePass = etPass2.getText().toString();
    }

    public boolean validName(){
        if(name.isEmpty()){
            etName.setHint("Isikan nama");
            etName.setBackgroundResource(R.drawable.bg_rounded_trans_red);
            return false;
        } else {
            etName.setBackgroundResource(R.drawable.bg_rounded_trans_white);
            return true;
        }
    }

    public boolean validEmail(){
        if (email.isEmpty()){
            etEmail.setHint("Isikan email");
            etEmail.setBackgroundResource(R.drawable.bg_rounded_trans_red);
            return false;
        } else {
            if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                etEmail.setBackgroundResource(R.drawable.bg_rounded_trans_white);
                return true;
            }
            etEmail.setText("");
            etEmail.setHint("Isikan email dengan benar");
            return false;
        }
    }

    public boolean validPassword(){
        if (password.isEmpty()){
            etPassword.setHint("Isikan password");
            etPassword.setBackgroundResource(R.drawable.bg_rounded_trans_red);
            return false;
        } else {
            if (password.length()<6){
                etPassword.setText("");
                etPassword.setHint("Isikan password minimal 6 karakter");
                etPassword.setBackgroundResource(R.drawable.bg_rounded_trans_red);
                return false;
            }
            etPassword.setBackgroundResource(R.drawable.bg_rounded_trans_white);
            return true;
        }
    }

    public boolean validRePass(){
        if (rePass.isEmpty()){
            etPass2.setHint("Isikan ulang password");
            etPass2.setBackgroundResource(R.drawable.bg_rounded_trans_red);
            return false;
        } else {
            if (password.matches(rePass)){
                etPass2.setBackgroundResource(R.drawable.bg_rounded_trans_white);
                return true;
            }
            etPass2.setText("");
            etPass2.setHint("Isikan ulang password dengan benar");
            return false;
        }
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

    public void onClick(View v) {
        Intent signin = new Intent(SignupActivity.this, SigninActivity.class);
        startActivity(signin);
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void successSignin(DataUser dataUser, String balance) {

    }

    @Override
    public void successSignUp(DataUser data) {
        hideProgressDialog();
        Toast.makeText(this, "Berhasil membuat akun", Toast.LENGTH_SHORT).show();
        etEmail.setText("");
        etName.setText("");
        etPassword.setText("");
        etPass2.setText("");

        etEmail.setBackgroundResource(R.drawable.bg_rounded_trans_white);
        etName.setBackgroundResource(R.drawable.bg_rounded_trans_white);
        etPass2.setBackgroundResource(R.drawable.bg_rounded_trans_white);
        etPassword.setBackgroundResource(R.drawable.bg_rounded_trans_white);
    }

    @Override
    public void failed(String message) {
        hideProgressDialog();
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
