package com.manualde.app8819.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.manualde.app8819.R;
import com.manualde.app8819.adapters.EmployeeListAdapter;
import com.manualde.app8819.entities.Employee;
import com.manualde.app8819.utils.Utilities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;

public class ListGuestActivity extends AppCompatActivity {
    ArrayList<Employee> employees;
    EmployeeListAdapter listAdapter;
    Toolbar tbOptions;

    private RecyclerView rvList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_guest);
        rvList = findViewById(R.id.rvList);
        tbOptions = findViewById(R.id.tbOptions);
        employees = Utilities.getEmployees();
        Collections.sort(employees, new Utilities.SortbyName());
        setRecycler();
        setSupportActionBar(tbOptions);
        tbOptions.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.action_logout) {
                    finish();
                    return true;
                }
                if (item.getItemId() == R.id.action_about) {
                    Intent i = new Intent(ListGuestActivity.this, AboutActivity.class);
                    startActivity(i);
                }
                return false;
            }
        });
    }

    private void setRecycler() {
        listAdapter = new EmployeeListAdapter(employees, new EmployeeListAdapter.RecyclerViewOnItemClickListener() {
            @Override
            public void onClick(View v, int position) {
            }
            @Override
            public void onLongClick(View v, int position) {
            }
        });
        rvList.setAdapter(listAdapter);
        rvList.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
    }
}
