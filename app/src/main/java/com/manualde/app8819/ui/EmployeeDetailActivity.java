package com.manualde.app8819.ui;

import android.app.Activity;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.manualde.app8819.R;
import com.manualde.app8819.entities.Employee;

public class EmployeeDetailActivity extends AppCompatActivity {

    Employee shownEmployee;

    private TextView tvName;
    private TextView tvSurname;
    private TextView tvAge;
    private TextView tvDateOfEntrance;
    private TextView tvDepartment;
    private TextView tvPosition;
    private TextView tvTasks;
    private TextView tvAgeIcon;
    private ImageView ivProfile;
    private TextView tvBack;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_details);
        shownEmployee = getIntent().getParcelableExtra("employee");
        tvName = findViewById(R.id.tvName);
        tvSurname = findViewById(R.id.tvSurname);
        tvAge = findViewById(R.id.tvAge);
        tvDateOfEntrance = findViewById(R.id.tvDateOfEntrance);
        tvDepartment = findViewById(R.id.tvDepartment);
        tvPosition = findViewById(R.id.tvPosition);
        tvTasks = findViewById(R.id.tvTasks);
        tvAgeIcon = findViewById(R.id.tvAgeIcon);
        ivProfile = findViewById(R.id.ivProfile);
        tvBack = findViewById(R.id.tvBack);

        setup();

        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void setup() {
        ivProfile.setClipToOutline(true);
        tvAgeIcon.setText(String.valueOf(shownEmployee.getAge()));
        tvName.setText(shownEmployee.getName());
        tvSurname.setText(shownEmployee.getSurname());
        Glide.with(ivProfile.getContext())
                .load(shownEmployee.getProfileImage())
                .placeholder(R.drawable.ic_baseline_account_circle_24px)
                .into(ivProfile);
        tvAge.setText(String.valueOf(shownEmployee.getAge()));
        tvDateOfEntrance.setText(shownEmployee.getDateOfEntry().toString());
        int antiquity = shownEmployee.getAntiquity();
        if(antiquity>10){
            tvDateOfEntrance.setTextColor(getApplicationContext().getColor(R.color.ten_years));
        } else if(antiquity>=5)
            tvDateOfEntrance.setTextColor(getApplicationContext().getColor(R.color.five_years));
        tvDepartment.setText(shownEmployee.getDepartment());
        tvPosition.setText(shownEmployee.getPosition());
        tvTasks.setText(shownEmployee.getActualTasks());
    }

}
