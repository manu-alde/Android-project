package com.manualde.app8819.ui;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.manualde.app8819.R;
import com.manualde.app8819.utils.SharedSettings;

public class AboutActivity extends AppCompatActivity {
    TextView tvVersion;
    ImageView ivLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final SharedSettings sharedSettings = new SharedSettings(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        tvVersion = findViewById(R.id.tvVersion);
        ivLogo = findViewById(R.id.ivLogo);
        try {
            tvVersion.setText(getPackageManager().getPackageInfo(getPackageName(), 0).versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        ivLogo.setClipToOutline(true);
    }
}
