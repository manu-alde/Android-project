package com.manualde.app8819.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.textfield.TextInputEditText;
import com.manualde.app8819.R;
import com.manualde.app8819.data.DatabaseDemo;
import com.manualde.app8819.entities.Employee;
import com.manualde.app8819.utils.DatePickerFragment;

import java.text.DateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Objects;

public class AddEmployeeActivity extends AppCompatActivity {

    Button btnConfirm;
    TextInputEditText tiName;
    TextInputEditText tiSurname;
    TextInputEditText tiPosition;
    TextInputEditText tiTasks;
    TextInputEditText tiDepartment;
    TextInputEditText tiAge;
    TextView tvSetDate;
    DialogFragment dateFragment;

    Date dateOfEntry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);
        tvSetDate = findViewById(R.id.tvDateOfEntrance);
        btnConfirm = findViewById(R.id.btnConfirm);
        tiName = findViewById(R.id.tiName);
        tiSurname = findViewById(R.id.tiSurname);
        tiPosition = findViewById(R.id.tiPosition);
        tiTasks = findViewById(R.id.tiTasks);
        tiDepartment = findViewById(R.id.tiDepartment);
        tiAge = findViewById(R.id.tiAge);

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
                if(e==null)
                    return;
                DatabaseDemo.add(e);
                setResult(RESULT_OK, getIntent());
                finish();
            }
        });
    }

    private Employee checkEverything() {
        String name = Objects.requireNonNull(tiName.getText()).toString().trim();
        String surname = Objects.requireNonNull(tiSurname.getText()).toString().trim();
        String department = Objects.requireNonNull(tiDepartment.getText()).toString().trim();
        String position = Objects.requireNonNull(tiPosition.getText()).toString().trim();
        String actualTasks = Objects.requireNonNull(tiTasks.getText()).toString().trim();
        int age;
        try {
            age = Integer.valueOf(Objects.requireNonNull(tiAge.getText()).toString().trim());
            if(age>=100){
                tiAge.setError(getString(R.string.invalid_age));
            }
        } catch (NumberFormatException e) {
            tiAge.setError(getString(R.string.invalid_age));
            return null;
        }
        if (name.equals("")) {
            tiName.setError(getString(R.string.invalid_name));
            return null;
        }
        if (surname.equals("")) {
            tiSurname.setError(getString(R.string.invalid_surname));
            return null;
        }
        if (department.equals("")) {
            tiName.setError(getString(R.string.invalid_department));
            return null;
        }
        if (position.equals("")) {
            tiName.setError(getString(R.string.invalid_position));
            return null;
        }
        if (actualTasks.equals("")) {
            tiTasks.setError(getString(R.string.invalid_task));
            return null;
        }
        return new Employee("", name, surname, age, dateOfEntry, department, position, actualTasks);
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
