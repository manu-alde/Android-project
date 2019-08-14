package com.manualde.app8819.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.manualde.app8819.R;
import com.manualde.app8819.data.DatabaseDemo;
import com.manualde.app8819.utils.SharedSettings;
import com.manualde.app8819.utils.Utilities;

public class SplashActivity extends AppCompatActivity {
    private ImageView ivPicture;
    private TextView tvVersion;
    private SharedSettings sharedSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);

        sharedSettings = new SharedSettings(getApplicationContext());
        ivPicture = findViewById(R.id.ivCreator);
        tvVersion = findViewById(R.id.tvVersion);
        ivPicture.setClipToOutline(true);
        DatabaseDemo.firstSet();
        String version = "";
        try {
            version = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        tvVersion.setText(version);

        checkPermissions();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Utilities.DIRECT_CODE)
            if (!sharedSettings.isLoggedIn()) {
                startActivityForResult(new Intent(SplashActivity.this, LoginActivity.class), Utilities.SPLASH_CODE);
                return;
            }
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case Utilities.EXTERNAL_STORAGE_REQUEST_CODE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Handler h = new Handler();
                    h.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (sharedSettings.isLoggedIn()) {
                                Intent i = new Intent(SplashActivity.this, ListActivity.class);
                                i.putExtra("mail", sharedSettings.getMail());
                                startActivityForResult(i, Utilities.DIRECT_CODE);
                            } else {
                                startActivityForResult(new Intent(SplashActivity.this, LoginActivity.class), Utilities.SPLASH_CODE);
                            }
                        }
                    }, 2000);
                } else {
                    finish();
                }
            }
        }
    }

    void checkPermissions() {
        if (ContextCompat.checkSelfPermission(SplashActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(SplashActivity.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA},
                    Utilities.EXTERNAL_STORAGE_REQUEST_CODE);
        } else {
            Handler h = new Handler();
            h.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (sharedSettings.isLoggedIn()) {
                        Intent i = new Intent(SplashActivity.this, ListActivity.class);
                        i.putExtra("mail", sharedSettings.getMail());
                        startActivityForResult(i, Utilities.DIRECT_CODE);
                    } else {
                        startActivityForResult(new Intent(SplashActivity.this, LoginActivity.class), Utilities.SPLASH_CODE);
                    }
                }
            }, 2000);
        }
    }
}
