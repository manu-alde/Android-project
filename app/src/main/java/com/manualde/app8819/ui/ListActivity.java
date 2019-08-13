package com.manualde.app8819.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.manualde.app8819.R;
import com.manualde.app8819.adapters.EmployeeListAdapter;
import com.manualde.app8819.entities.Employee;
import com.manualde.app8819.utils.SharedSettings;
import com.manualde.app8819.utils.Utilities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Objects;

public class ListActivity extends AppCompatActivity {
    ArrayList<Employee> employees;
    EmployeeListAdapter listAdapter;
    Toolbar tbOptions;
    RecyclerView rvList;
    SharedSettings sharedSettings;
    TextView tvName;
    TextView tvLogout;

    @SuppressLint({"WrongConstant", "RestrictedApi"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        final SharedSettings sharedSettings = new SharedSettings(getApplicationContext());
        rvList = findViewById(R.id.rvList);
        tbOptions = findViewById(R.id.tbOptions);
        tvName = findViewById(R.id.tvName);
        tvLogout = findViewById(R.id.tvLogout);

        String name = sharedSettings.getName() + " " + sharedSettings.getSurname();
        tvName.setText(name);
        setSupportActionBar(tbOptions);
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.employees);

        employees = Utilities.getEmployees();
        Collections.sort(employees, new Utilities.SortbyName());

        setRecycler();

        tbOptions.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.action_logout) {
                    sharedSettings.setLoggedIn(false);
                    finish();
                    return true;
                }
                if (item.getItemId() == R.id.action_about) {
                    Intent i = new Intent(ListActivity.this, AboutActivity.class);
                    startActivity(i);
                }
                return false;
            }
        });

        tvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedSettings.setLoggedIn(false);
                finish();
            }
        });
    }

    private void setRecycler() {
        listAdapter = new EmployeeListAdapter(employees, new EmployeeListAdapter.RecyclerViewOnItemClickListener() {
            @Override
            public void onClick(View v, int position) {
                if (v.getId() == R.id.btnDetails) {
                    Intent i = new Intent(ListActivity.this, EmployeeDetailActivity.class);
                    Employee e = listAdapter.getItem(position);
                    i.putExtra("employee", e);
                    startActivity(i);
                }
            }

            @Override
            public void onLongClick(View v, int position) {
            }
        });
        listAdapter.setUserPermitted();
        rvList.setAdapter(listAdapter);
        rvList.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_support_bar, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK, getIntent());
        finish();
    }

}
