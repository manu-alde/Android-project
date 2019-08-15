package com.manualde.app8819.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "Employ";
    private static final int DATABASE_VERSION = 1;
    static String EMPLOYEES_TABLE_NAME = "employees";
    static String USERS_TABLE_NAME = "users";

    DBHelper(Context context) {
        super(context, DB_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(String.format("CREATE TABLE IF NOT EXISTS %s(mail text, password text, name text, surname text)", USERS_TABLE_NAME));
        db.execSQL(String.format("CREATE TABLE IF NOT EXISTS %s(profileImage text, name text, surname text, age int, dateOfEntry date," +
                " department text, position text, actualTasks text)", EMPLOYEES_TABLE_NAME));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
