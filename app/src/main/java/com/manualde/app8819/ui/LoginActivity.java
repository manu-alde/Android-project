package com.manualde.app8819.ui;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.manualde.app8819.R;
import com.manualde.app8819.data.SQLUserController;
import com.manualde.app8819.entities.User;
import com.manualde.app8819.utils.SharedSettings;
import com.manualde.app8819.utils.Utilities;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    private ViewGroup viewMain;
    private TextInputEditText tiUsername;
    private TextInputEditText tiPassword;
    private TextView tvError;
    private Button btnLogin;
    private Button btnList;
    private TextView tvRegister;
    private ViewGroup viewSeparator;
    private SharedSettings sharedSettings;
    SQLUserController sqlUserController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_login);
        sharedSettings = new SharedSettings(getApplicationContext());
        sqlUserController = new SQLUserController(getApplicationContext());

        WindowManager wm = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        Point size = new Point();
        Objects.requireNonNull(wm).getDefaultDisplay().getRealSize(size);

        final double vertical = (double) size.y / 2;

        tiUsername = findViewById(R.id.tiMail);
        tiPassword = findViewById(R.id.tiPassword);
        tvError = findViewById(R.id.tvError);
        viewSeparator = findViewById(R.id.viewSeparator);
        viewMain = findViewById(R.id.viewMain);

        btnLogin = findViewById(R.id.btnLogin);
        btnList = findViewById(R.id.btnList);
        tvRegister = findViewById(R.id.tvRegister);


        viewMain.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View view, int left, int top, int right, int bottom, int oLeft, int oTop, int oRight, int oBottom) {
                if (bottom != 0 && Math.abs(bottom - top) < vertical)
                    viewSeparator.setVisibility(View.GONE);
                else viewSeparator.setVisibility(View.VISIBLE);
            }
        });


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvError.setVisibility(View.GONE);
                String mail, pass;
                mail = Objects.requireNonNull(tiUsername.getText()).toString().trim();
                pass = Objects.requireNonNull(tiPassword.getText()).toString();
                String error;
                if (!sqlUserController.passwordOk(mail,pass)) {
                    error = getString(R.string.wrong_mail_or_password);
                    tvError.setText(error);
                    tvError.setVisibility(View.VISIBLE);
                    return;
                }
                User u = sqlUserController.getData(mail);
                sharedSettings.setMail(mail);
                sharedSettings.setPassword(pass);
                sharedSettings.setName(u.getName());
                sharedSettings.setSurname(u.getSurname());
                sharedSettings.setLoggedIn(true);
                Intent i = new Intent(LoginActivity.this, ListActivity.class);
                startActivityForResult(i, Utilities.LOGIN_CODE);
            }
        });

        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, ListActivity.class);
                startActivityForResult(i, Utilities.LOGIN_CODE);
            }
        });

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(LoginActivity.this,RegisterActivity.class),Utilities.REGISTER_CODE);
            }
        });
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED, getIntent());
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Utilities.LOGIN_CODE) {
            if (!sharedSettings.isLoggedIn()) {
                tiUsername.setText("");
                tiPassword.setText("");
                return;
            }
            else finish();
        }
        if(requestCode == Utilities.REGISTER_CODE && resultCode == RESULT_OK && data!=null){
            String mail = data.getStringExtra("mail");
            Snackbar snackbar = Snackbar
                    .make(viewMain, mail + " " + getString(R.string.registered_successfully), Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }

}
