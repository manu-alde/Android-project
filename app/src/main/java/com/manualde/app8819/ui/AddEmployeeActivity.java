package com.manualde.app8819.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.manualde.app8819.R;
import com.manualde.app8819.data.SQLEmployeeController;
import com.manualde.app8819.entities.Employee;
import com.manualde.app8819.utils.DatePickerFragment;
import com.manualde.app8819.utils.Utilities;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Objects;

public class AddEmployeeActivity extends AppCompatActivity {

    Button btnConfirm;
    ImageView ivProfile;
    TextInputEditText tiName;
    TextInputEditText tiSurname;
    TextInputEditText tiPosition;
    TextInputEditText tiTasks;
    TextInputEditText tiDepartment;
    TextInputEditText tiAge;
    TextView tvSetDate;
    DialogFragment dateFragment;
    Date dateOfEntry;
    Button btnUpload;
    TextInputEditText tiURL;
    String actualUrl = "";
    TextView tvBack;
    SQLEmployeeController sqlEmployeeController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);
        tvSetDate = findViewById(R.id.tvDateOfEntrance);
        ivProfile = findViewById(R.id.ivProfile);
        btnConfirm = findViewById(R.id.btnConfirm);
        tiName = findViewById(R.id.tiName);
        tiSurname = findViewById(R.id.tiSurname);
        tiPosition = findViewById(R.id.tiPosition);
        tiTasks = findViewById(R.id.tiTasks);
        tiDepartment = findViewById(R.id.tiDepartment);
        tiURL = findViewById(R.id.tiUrl);
        btnUpload = findViewById(R.id.btnUpload);
        tiAge = findViewById(R.id.tiAge);
        tvBack = findViewById(R.id.tvBack);
        ivProfile.setClipToOutline(true);
        sqlEmployeeController = new SQLEmployeeController(getApplicationContext());

        tvSetDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(tvSetDate);
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Employee e = checkEverything();
                if (e == null)
                    return;
                sqlEmployeeController.insertEmployee(e);
                setResult(RESULT_OK, getIntent());
                finish();
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Glide.with(getApplicationContext())
                            .load(Objects.requireNonNull(tiURL.getText()).toString().trim())
                            .placeholder(R.drawable.ic_baseline_account_circle_24px)
                            .into(ivProfile);
                    actualUrl = tiURL.getText().toString().trim();
                } catch (Exception e) {
                    tiURL.setError(getString(R.string.format_not_supported));
                }

            }
        });

        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_CANCELED, getIntent());
                finish();
            }
        });
    }

    private Employee checkEverything() {
        String name = Objects.requireNonNull(tiName.getText()).toString().trim();
        name = Utilities.toNameFormat(name);
        String surname = Objects.requireNonNull(tiSurname.getText()).toString().trim();
        surname = Utilities.toNameFormat(surname);
        String department = Objects.requireNonNull(tiDepartment.getText()).toString().trim();
        String position = Objects.requireNonNull(tiPosition.getText()).toString().trim();
        String actualTasks = Objects.requireNonNull(tiTasks.getText()).toString().trim();
        boolean error = false;
        int age = 0;
        try {
            age = Integer.valueOf(Objects.requireNonNull(tiAge.getText()).toString().trim());
            if (age >= 100 || age <= 10) {
                tiAge.setError(getString(R.string.invalid_age));
                error = true;
            }
        } catch (NumberFormatException e) {
            tiAge.setError(getString(R.string.invalid_age));
            error = true;
        }
        if (name.equals("")) {
            tiName.setError(getString(R.string.invalid_name));
            error = true;
        }
        if (surname.equals("")) {
            tiSurname.setError(getString(R.string.invalid_surname));
            error = true;
        }
        if (department.equals("")) {
            tiDepartment.setError(getString(R.string.invalid_department));
            error = true;
        }
        if (position.equals("")) {
            tiPosition.setError(getString(R.string.invalid_position));
            error = true;
        }
        if (actualTasks.equals("")) {
            tiTasks.setError(getString(R.string.invalid_task));
            error = true;
        }
        if (actualUrl == null || actualUrl.trim().isEmpty()) {
            error = true;
            Snackbar snackbar = Snackbar
                    .make(ivProfile, R.string.please_select_a_picture, Snackbar.LENGTH_LONG);
            snackbar.show();
        }
        if (dateOfEntry == null || dateOfEntry.after(new Date(Calendar.getInstance().getTimeInMillis()))) {
            Snackbar snackbar = Snackbar
                    .make(ivProfile, R.string.invalid_date, Snackbar.LENGTH_LONG);
            snackbar.show();
            error = true;
        }
        Calendar c = Calendar.getInstance();
        c.add(Calendar.YEAR, -age);
        Date d = new Date(c.getTimeInMillis());
        if (d.after(dateOfEntry)) {
            Snackbar snackbar = Snackbar
                    .make(ivProfile, R.string.invalid_date, Snackbar.LENGTH_LONG);
            snackbar.show();
            error = true;
        }
        if (error)
            return null;
        else {
            if (sqlEmployeeController.getEmployee(name, surname) == null)
                return new Employee(actualUrl, name, surname, age, dateOfEntry, department, position, actualTasks);
            else {
                Snackbar snackbar = Snackbar
                        .make(ivProfile, R.string.employee_exists, Snackbar.LENGTH_LONG);
                snackbar.show();
                return null;
            }
        }
    }

    public void showDatePickerDialog(TextView tv) {
        dateFragment = new DatePickerFragment(new DatePickerFragment.DatePickerFragmentOnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                GregorianCalendar c = new GregorianCalendar(year, month, day);
                dateOfEntry = new Date(c.getTimeInMillis());
                tvSetDate.setText(DateFormat.getDateInstance(DateFormat.MEDIUM).format(dateOfEntry));
            }
        });
        dateFragment.show(getSupportFragmentManager(), "datePicker");
    }

}
