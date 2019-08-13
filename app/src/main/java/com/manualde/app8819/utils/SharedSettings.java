package com.manualde.app8819.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class SharedSettings {
    private SharedPreferences sharedPreferences;

    public SharedSettings(Context context) {
        sharedPreferences = context.getSharedPreferences("settings", MODE_PRIVATE);
    }


    public String getMail() {
        return sharedPreferences.getString("mail", "");
    }

    @SuppressLint("ApplySharedPref")
    public void setMail(String mail) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("mail", mail);
        editor.commit();
    }

    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean("loggedin", false);
    }

    @SuppressLint("ApplySharedPref")
    public void setLoggedIn(boolean status) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("loggedin", status);
        editor.commit();
    }

    public String getPassword() {
        return sharedPreferences.getString("password", "");
    }

    @SuppressLint("ApplySharedPref")
    public void setPassword(String password) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("password", password);
        editor.commit();
    }

    public String getName() {
        return sharedPreferences.getString("name", "");
    }

    @SuppressLint("ApplySharedPref")
    public void setName(String name) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("name", name);
        editor.commit();
    }

    public String getSurname() {
        return sharedPreferences.getString("surname", "");
    }

    @SuppressLint("ApplySharedPref")
    public void setSurname(String surname) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("surname", surname);
        editor.commit();
    }

    @SuppressLint("ApplySharedPref")
    public void clear() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("mail", "");
        editor.putString("password", "");
        editor.putString("name", "");
        editor.putString("surname", "");
        editor.putBoolean("loggedin", false);
        editor.commit();
    }
}
