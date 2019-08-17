package com.manualde.app8819.data;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.manualde.app8819.entities.User;

import static com.manualde.app8819.data.DBHelper.USERS_TABLE_NAME;

public class SQLUserController {

    private DBHelper dbHelper;

    public SQLUserController(Context context) {
        dbHelper = new DBHelper(context);
    }

    public void newUser(String mail, String password, String name, String surname) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues insertValues = new ContentValues();
        insertValues.put("mail", mail);
        insertValues.put("password", password);
        insertValues.put("name", name);
        insertValues.put("surname", surname);
        database.insert(USERS_TABLE_NAME, null, insertValues);
        getData(mail);
    }

    public Boolean userExists(String mail) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        String[] consultCol = {"name"};
        Cursor cursor = database.query(
                USERS_TABLE_NAME,//from users
                consultCol,
                "mail=?",
                new String[]{mail},
                null,
                null,
                null
        );
        if (cursor == null) {
            return false;
        }
        if (!cursor.moveToFirst()) {
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    public User getData(String mail) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        String[] consultCol = {"name", "surname"};
        Cursor cursor = database.query(
                USERS_TABLE_NAME,//from users
                consultCol,
                "mail = ?",
                new String[]{mail},
                null,
                null,
                null
        );
        if (cursor == null) {
            return null;
        }
        if (!cursor.moveToFirst()) {
            cursor.close();
            return null;
        }
        String name = cursor.getString(0);
        String surname = cursor.getString(1);
        cursor.close();
        return new User(name, surname);
    }


    public Boolean passwordOk(String mail, String password) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        String query = "SELECT * FROM users where mail= '" + mail + "' and password= '" + password + "'";
        @SuppressLint("Recycle")
        Cursor cursor = database.rawQuery(query, null);
        if (cursor == null) {
            return false;
        }
        if (!cursor.moveToFirst()) {
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }
}
