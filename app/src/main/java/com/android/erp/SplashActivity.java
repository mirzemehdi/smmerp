package com.android.erp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        TextView splash_text = findViewById(R.id.splash_text);
        Typeface avenir = Typeface.createFromAsset(getAssets(),"fonts/AvenirBlack.ttf");
        splash_text.setTypeface(avenir);

        SharedPreferences prefs = getSharedPreferences("USER", MODE_PRIVATE);

            String userId = prefs.getString("userId", "");
            boolean check = prefs.getBoolean("check",false);
            boolean isAdmin = prefs.getBoolean("isAdmin",false);

            if (check){
                if (isAdmin){
                    Intent intent = new Intent(getApplicationContext(),FirstHomeActivity.class);
                    intent.putExtra("userId",userId);
                    startActivity(intent);
                    finish();
                }else {
                    Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
                    intent.putExtra("userId",userId);
                    startActivity(intent);
                    finish();
                }
            }else {
                new Handler().postDelayed(() -> {
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    finish();
                }, 2000);
            }

    }
}
