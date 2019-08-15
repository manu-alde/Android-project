package com.manualde.app8819.data;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.manualde.app8819.entities.Employee;

import java.util.ArrayList;
import java.util.Date;

import static com.manualde.app8819.data.DBHelper.EMPLOYEES_TABLE_NAME;
import static com.manualde.app8819.data.DBHelper.USERS_TABLE_NAME;

public class SQLEmployeeController {
    private DBHelper dbHelper;

    public SQLEmployeeController(Context context) {
        dbHelper = new DBHelper(context);
    }

    public long insertEmployee(Employee employee) {
        // writable porque vamos a insertar
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues insertValues = new ContentValues();
        insertValues.put("profileimage", employee.getProfileImage());
        insertValues.put("name", employee.getName());
        insertValues.put("surname", employee.getSurname());
        insertValues.put("age", employee.getAge());
        insertValues.put("dateofentry", employee.getDateOfEntry().getTime());
        insertValues.put("department", employee.getDepartment());
        insertValues.put("position", employee.getPosition());
        insertValues.put("actualtasks", employee.getActualTasks());
        return database.insert(EMPLOYEES_TABLE_NAME, null, insertValues);
    }

    public ArrayList<Employee> GetEmployees() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        ArrayList<Employee> employeeList = new ArrayList<>();
        String query = "SELECT * FROM employees";
        @SuppressLint("Recycle")
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            String oProfileImage = cursor.getString(0);
            String oName = cursor.getString(1);
            String oSurname = cursor.getString(2);
            int oAge = cursor.getInt(3);
            Date oDateOfEntry = new Date(cursor.getLong(4));
            String oDepartment = cursor.getString(5);
            String oPosition = cursor.getString(6);
            String oActualTasks = cursor.getString(7);
            Employee obtainedEmployee = new Employee(oProfileImage, oName, oSurname, oAge, oDateOfEntry, oDepartment, oPosition, oActualTasks);
            employeeList.add(obtainedEmployee);
        }
        return employeeList;
    }

    public Employee obtainEmployee(String name, String surname) {
        SQLiteDatabase baseDeDatos = dbHelper.getReadableDatabase();
        String[] columnasAConsultar = {"idUser", "name", "password"};
        Cursor cursor = baseDeDatos.query(
                USERS_TABLE_NAME,//from users
                columnasAConsultar,
                "name = ?, surname = ?",
                new String[]{name},
                null,
                null,
                null
        );
        if (cursor == null) {
            return null;
        }
        if (!cursor.moveToFirst()) return null;
        String oProfileImage = cursor.getString(0);
        String oName = cursor.getString(1);
        String oSurname = cursor.getString(2);
        int oAge = cursor.getInt(3);
        Date oDateOfEntry = new Date(cursor.getLong(4));
        String oDepartment = cursor.getString(5);
        String oPosition = cursor.getString(6);
        String oActualTasks = cursor.getString(7);

        Employee obtainedEmployee = new Employee(oProfileImage, oName, oSurname, oAge, oDateOfEntry, oDepartment, oPosition, oActualTasks);
        cursor.close();
        return obtainedEmployee;
    }

    public int deleteEmployee(Employee employee) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        String[] arguments = {employee.getName(), employee.getSurname()};
        return database.delete(USERS_TABLE_NAME, "name = ?, surname = ?", arguments);
    }
}
