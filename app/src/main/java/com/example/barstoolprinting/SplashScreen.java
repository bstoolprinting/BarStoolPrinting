package com.example.barstoolprinting;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashScreen extends AppCompatActivity {
    private static int Splash_Time_Out = 4000;
    Animation anim;
    ImageView logo;

    Handler handler = new Handler();
    Runnable myRunnable = new Runnable() {
        public void run() {
            Intent homeIntent = new Intent(SplashScreen.this, Directory.class);
            startActivity(homeIntent);
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);

        anim = AnimationUtils.loadAnimation(SplashScreen.this, R.anim.fadein );

        logo = findViewById(R.id.logo);

        handler.postDelayed( myRunnable, Splash_Time_Out);
        logo.startAnimation(anim);

        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.removeCallbacks(myRunnable);
                myRunnable.run();
            }
        });
    }
}


