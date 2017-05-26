package com.apptozee.drivingschool;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        //Using Thread to show Splash Screen for a certain time.
        Thread timerThread = new Thread() {
            public void run() {
                try {
                    sleep(2000); // 2000 means 2 second.
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    Intent intent = new Intent(SplashScreen.this, LoginActivity.class);
                    startActivity(intent);
                    // As we don't want to go back to SplashScreen onBack click at LoginActivity.
                    SplashScreen.this.finish();
                }
            }
        };
        timerThread.start();
    }
}
