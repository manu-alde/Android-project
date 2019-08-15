package com.manualde.app8819.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class SQLUserController {

    private DBHelper dbHelper;
    private String USERS_TABLE_NAME = "users";

    public SQLUserController(Context context) {
        dbHelper = new DBHelper(context);
    }

    public long newUser(String name, String password) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues insertValues = new ContentValues();
        insertValues.put("name", name);
        insertValues.put("password", password);
        return database.insert(USERS_TABLE_NAME, null, insertValues);
    }

    public Boolean userExists(String name) {
        SQLiteDatabase baseDeDatos = dbHelper.getReadableDatabase();
        String[] consultCol = {"name"};
        Cursor cursor = baseDeDatos.query(
                USERS_TABLE_NAME,//from users
                consultCol,
                "name=?",
                new String[]{name},
                null,
                null,
                null
        );
        if (cursor == null || !cursor.moveToFirst()) return false;
        cursor.close();
        return true;
    }
}
