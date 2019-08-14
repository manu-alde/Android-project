package com.manualde.app8819.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.manualde.app8819.R;
import com.manualde.app8819.utils.SharedSettings;
import com.manualde.app8819.utils.Utilities;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

    TextInputEditText tiMail;
    TextInputEditText tiPassword;
    TextInputEditText tiName;
    TextInputEditText tiSurname;
    TextInputEditText tiRepeatPassword;
    TextView tvError;
    Button btnRegister;

    SharedSettings sharedSettings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        sharedSettings = new SharedSettings(getApplicationContext());
        tiMail = findViewById(R.id.tiMail);
        tiPassword = findViewById(R.id.tiPassword);
        tiName = findViewById(R.id.tiName);
        tiSurname = findViewById(R.id.tiSurname);
        btnRegister = findViewById(R.id.btnRegister);
        tvError = findViewById(R.id.tvError);
        tiRepeatPassword = findViewById(R.id.tiRepeatPassword);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvError.setVisibility(View.GONE);
                if (!register()) {
                    tvError.setVisibility(View.VISIBLE);
                } else {
                    sendToShared();
                    setResult(RESULT_OK, getIntent());
                    finish();
                }
            }
        });
    }

    private boolean register() {
        if (!Utilities.validMail(Objects.requireNonNull(tiMail.getText()).toString())) {
            tiMail.setError(getString(R.string.wrong_mail));
            return false;
        }
        if (Objects.requireNonNull(tiName.getText()).toString().trim().isEmpty()) {
            tiPassword.setError(getString(R.string.wrong_name));
            return false;
        }
        if (Objects.requireNonNull(tiSurname.getText()).toString().trim().isEmpty()) {
            tiSurname.setError(getString(R.string.wrong_surname));
            return false;
        }
        if (Objects.requireNonNull(tiPassword.getText()).toString().trim().isEmpty()) {
            tiPassword.setError(getString(R.string.wrong_password));
            return false;
        }
        if (!tiPassword.getText().toString().equals(Objects.requireNonNull(tiRepeatPassword.getText()).toString())) {
            tiRepeatPassword.setError(getString(R.string.passwords_do_not_match));
            return false;
        }
        return true;
    }

    private void sendToShared() {
        sharedSettings.setMail(Objects.requireNonNull(tiMail.getText()).toString());
        sharedSettings.setPassword(Objects.requireNonNull(tiPassword.getText()).toString());
        sharedSettings.setName(Objects.requireNonNull(tiName.getText()).toString());
        sharedSettings.setSurname(Objects.requireNonNull(tiSurname.getText()).toString());
    }


    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED, getIntent());
        finish();
    }
}