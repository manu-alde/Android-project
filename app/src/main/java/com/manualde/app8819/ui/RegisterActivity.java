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
                String mail = Objects.requireNonNull(tiMail.getText()).toString().trim();
                String password = Objects.requireNonNull(tiPassword.getText()).toString();
                String repeatPassword = Objects.requireNonNull(tiRepeatPassword.getText()).toString();
                String name = Utilities.toNameFormat((Objects.requireNonNull(tiName.getText())).toString().trim());
                String surname = Utilities.toNameFormat(Objects.requireNonNull(tiSurname.getText()).toString().trim());

                if (!isDataOK(mail, password, repeatPassword, name, surname)) {
                    tvError.setVisibility(View.VISIBLE);
                } else {
                    sqlUserController.newUser(mail, password, name, surname);
                    getIntent().putExtra("mail", mail);
                    setResult(RESULT_OK, getIntent());
                    finish();
                }
            }
        });
    }

    private boolean isDataOK(String mail, String password, String repeatPassword, String name, String surname) {
        boolean isOK = true;
        if (!Utilities.validMail(mail)) {
            tiMail.setError(getString(R.string.wrong_mail));
            isOK = false;
        }
        if (name.isEmpty()) {
            tiPassword.setError(getString(R.string.wrong_name));
            isOK = false;
        }
        if (sqlUserController.userExists(mail)) {
            Snackbar snackbar = Snackbar
                    .make(tvError, R.string.user_exists, Snackbar.LENGTH_LONG);
            snackbar.show();
            isOK = false;
        }
        if (surname.isEmpty()) {
            tiSurname.setError(getString(R.string.wrong_surname));
            isOK = false;
        }
        if (password.trim().isEmpty()) {
            tiPassword.setError(getString(R.string.wrong_password));
            isOK = false;
        }
        if (password.compareTo(repeatPassword) != 0) {
            tiRepeatPassword.setError(getString(R.string.passwords_do_not_match));
            isOK = false;
        }
        return isOK;
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED, getIntent());
        finish();
    }
}