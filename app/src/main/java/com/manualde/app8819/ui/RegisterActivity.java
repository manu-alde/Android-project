package com.manualde.app8819.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.manualde.app8819.R;
import com.manualde.app8819.data.SQLUserController;
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
    SQLUserController sqlUserController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        tiMail = findViewById(R.id.tiMail);
        tiPassword = findViewById(R.id.tiPassword);
        tiName = findViewById(R.id.tiName);
        tiSurname = findViewById(R.id.tiSurname);
        btnRegister = findViewById(R.id.btnRegister);
        tvError = findViewById(R.id.tvError);
        tiRepeatPassword = findViewById(R.id.tiRepeatPassword);

        sqlUserController = new SQLUserController(getApplicationContext());

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvError.setVisibility(View.GONE);
                if (!register()) {
                    tvError.setVisibility(View.VISIBLE);
                } else {
                    sqlUserController.newUser(Objects.requireNonNull(tiMail.getText()).toString(), Objects.requireNonNull(tiPassword.getText()).toString(),
                            Objects.requireNonNull(tiName.getText()).toString().trim(), Objects.requireNonNull(tiSurname.getText()).toString().trim());
                    getIntent().putExtra("mail", Objects.requireNonNull(tiMail.getText()).toString());
                    setResult(RESULT_OK, getIntent());
                    finish();
                }
            }
        });
    }

    private boolean register() {
        boolean error = false;
        if (!Utilities.validMail(Objects.requireNonNull(tiMail.getText()).toString())) {
            tiMail.setError(getString(R.string.wrong_mail));
            error = true;
        }
        if (Objects.requireNonNull(tiName.getText()).toString().trim().isEmpty()) {
            tiPassword.setError(getString(R.string.wrong_name));
            error = true;
        }
        if(sqlUserController.userExists(tiMail.getText().toString().trim())) {
            Snackbar snackbar = Snackbar
                    .make(tvError, R.string.user_exists, Snackbar.LENGTH_LONG);
            snackbar.show();
            error = true;
        }
        if (Objects.requireNonNull(tiSurname.getText()).toString().trim().isEmpty()) {
            tiSurname.setError(getString(R.string.wrong_surname));
            error = true;
        }
        if (Objects.requireNonNull(tiPassword.getText()).toString().trim().isEmpty()) {
            tiPassword.setError(getString(R.string.wrong_password));
            error = true;
        }
        if (!tiPassword.getText().toString().equals(Objects.requireNonNull(tiRepeatPassword.getText()).toString())) {
            tiRepeatPassword.setError(getString(R.string.passwords_do_not_match));
            error = true;
        }
        return !error;
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED, getIntent());
        finish();
    }
}