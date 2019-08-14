package com.manualde.app8819.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.DatePicker;

import com.google.android.material.textfield.TextInputEditText;
import com.manualde.app8819.R;

public class DatePickerActivity extends AppCompatActivity {

    DatePicker datePicker;
    TextInputEditText tiDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_picker);
    }
}
