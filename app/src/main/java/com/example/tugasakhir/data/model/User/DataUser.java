package com.example.tugasakhir.data.model.User;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataUser {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("stellarId")
    @Expose
    private String stellarId;
    @SerializedName("secretSeed")
    @Expose
    private String secretSeed;
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("photo")
    @Expose
    private String photo;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("address")
    @Expose
    private String address;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStellarId() {
        return stellarId;
    }

    public void setStellarId(String stellarId) {
        this.stellarId = stellarId;
    }

    public String getSecretSeed() {
        return secretSeed;
    }

    public void setSecretSeed(String secretSeed) {
        this.secretSeed = secretSeed;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
