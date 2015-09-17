package com.example.ishan.ftc_scouter;

import android.app.Activity;

/**
*
* @author Ishan Joshi
 * <p>This is a class for splash screen start up derived from
 * @link http://www.androidhive.info/2013/07/how-to-implement-android-splash-screen-2/
 * and thanks to their guide..
 * </p>
*
* */


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreen extends Activity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(intent);


                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

}