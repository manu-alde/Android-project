package com.manualde.app8819.ui;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.manualde.app8819.R;
import com.manualde.app8819.utils.SharedSettings;

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
        String version = "";
        try {
            version = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        tvVersion.setText(version);

        Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (sharedSettings.isLoggedIn()) {
                    Intent i = new Intent(SplashActivity.this, ListActivity.class);
                    i.putExtra("mail", sharedSettings.getMail());
                    startActivity(i);
                    finish();
                } else {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    finish();
                }
            }
        }, 2000);
    }
}
