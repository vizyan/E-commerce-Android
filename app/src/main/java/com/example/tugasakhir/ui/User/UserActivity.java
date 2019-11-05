package com.example.tugasakhir.ui.User;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.UnicodeSetSpanner;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tugasakhir.R;
import com.example.tugasakhir.data.model.User.DataUser;
import com.example.tugasakhir.ui.Dashboard.DashboardActivity;
import com.example.tugasakhir.util.CircleTransform;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

public class UserActivity extends AppCompatActivity implements UserView {

    private UserPresenter userPresenter;
    private DataUser dataUser;
    AlertDialog alertDialog;
    AlertDialog.Builder dialogBuilder;

    Button btnDUEdit, btnDUDelete;
    EditText etDUName, etDUEmail, etDUPassword, etDUAddress, etDUPhone;
    FloatingActionButton fabUserSave;
    ImageView ivDUPhoto;
    TextView tvDUName, tvDUEmail;
    Toolbar toolbar;

    int id;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        initPresenter();
        initPreference();
        initView();
//        initActionBar();
        initDataPresenter();
        initEdit();
        initSave();
        initDelete();
    }

    private void initPresenter() {
        this.userPresenter = new UserPresenter(this);
    }

    private void initPreference(){
        SharedPreferences preferences = getSharedPreferences("LoginPreference", Context.MODE_PRIVATE);
        String userPref = preferences.getString("dataUser", "");
        password = preferences.getString("password", "");

        if (userPref.isEmpty()){
            finish();
        }
        else {
            Gson gson = new Gson();
            dataUser = gson.fromJson(userPref, DataUser.class);
            id = dataUser.getId();
        }
    }

    @SuppressLint("RestrictedApi")
    private void initView() {
        btnDUEdit = findViewById(R.id.btnDUEdit);
        btnDUDelete = findViewById(R.id.btnDUDelete);
        etDUName = findViewById(R.id.etDUName);
        etDUName.setEnabled(false);
        etDUEmail = findViewById(R.id.etDUEmail);
        etDUEmail.setEnabled(false);
        etDUPassword = findViewById(R.id.etDUPassword);
        etDUPassword.setEnabled(false);
        etDUAddress = findViewById(R.id.etDUAddress);
        etDUAddress.setEnabled(false);
        etDUPhone = findViewById(R.id.etDUPhone);
        etDUPhone.setEnabled(false);
        tvDUName = findViewById(R.id.tvDUName);
        tvDUEmail = findViewById(R.id.tvDUEmail);
        fabUserSave = findViewById(R.id.fabUserSave);
        fabUserSave.setVisibility(View.INVISIBLE);
        ivDUPhoto = findViewById(R.id.ivDUPhoto);
        toolbar = findViewById(R.id.toolbar);
    }

    private void initActionBar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Detail Akun");
    }

    private void initDataPresenter() {
        userPresenter.getUserById(id);
    }

    private void initDataView(DataUser dataUser){
        etDUName.setText(dataUser.getName());
        etDUEmail.setText(dataUser.getEmail());
        etDUPassword.setText(password);
        etDUPhone.setText(dataUser.getPhone());
        etDUAddress.setText(dataUser.getAddress());
        tvDUName.setText(dataUser.getName());
        tvDUEmail.setText(dataUser.getEmail());
        Picasso.get()
                .load(dataUser.getPhoto())
                .resize(100, 100)
                .centerCrop()
                .transform(new CircleTransform())
                .into(ivDUPhoto);
    }

    private void initEdit() {
        btnDUEdit.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View view) {
                etDUName.setEnabled(true);
                etDUPassword.setEnabled(true);
                etDUPhone.setEnabled(true);
                etDUAddress.setEnabled(true);
                fabUserSave.setVisibility(View.VISIBLE);
            }
        });
    }

    private void initSave() {
        fabUserSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userPresenter.editUser(dataUser.getId());
                showProgressDialog();
            }
        });
    }

    private void initDelete() {
        btnDUDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userPresenter.deleteUser(dataUser.getId());
            }
        });
    }

    private void savePreference(DataUser dataUser){
        Gson gson = new Gson();
        String DataUser = gson.toJson(dataUser);
        SharedPreferences preferences = getSharedPreferences("LoginPreference", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("password", password);
        editor.putString("dataUser", DataUser);
        editor.commit();
        editor.apply();
    }

    private void showProgressDialog() {
        dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View dialogView = inflater.inflate(R.layout.progress_dialog_layout, null);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setCancelable(false);
        alertDialog = dialogBuilder.create();
        alertDialog.show();
    }

    private void hideProgressDialog(){
        alertDialog.dismiss();
    }

    @Override
    public String getName() {
        return etDUName.getText().toString();
    }

    @Override
    public String getPassword() {
        password = etDUPassword.getText().toString();
        return password;
    }

    @Override
    public String getPhone() {
        return etDUPhone.getText().toString();
    }

    @Override
    public String getAddress() {
        return etDUAddress.getText().toString();
    }

    @Override
    public void successUser(DataUser dataUser) {
        initDataView(dataUser);
        this.dataUser = dataUser;
        savePreference(dataUser);
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void successEdit(DataUser data) {
        hideProgressDialog();
        Toast.makeText(this, "Akun diperbarui", Toast.LENGTH_SHORT).show();

        dataUser = data;
        savePreference(data);
        initDataView(dataUser);

        etDUName.setEnabled(false);
        etDUPassword.setEnabled(false);
        etDUPhone.setEnabled(false);
        etDUAddress.setEnabled(false);
        fabUserSave.setVisibility(View.INVISIBLE);
    }

    @Override
    public void successDelete() {
        SharedPreferences preferences = getSharedPreferences("LoginPreference", Context.MODE_PRIVATE);
        preferences.edit().clear().apply();

        Intent home = new Intent(UserActivity.this, DashboardActivity.class);
        startActivity(home);
        finishAffinity();
    }

    @Override
    public void failed(String message) {
        hideProgressDialog();
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}
