package com.dandekar.epaper.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.dandekar.epaper.R;
import com.dandekar.epaper.activity.ui.login.LoginActivity;
import com.dandekar.epaper.data.Constants;
import com.dandekar.epaper.util.ApplicationCache;

public class Splash extends AppCompatActivity {

    public static final int DURATION = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        // Start a background thread to read the preference store
        Thread t = new Thread() {
            @Override
            public void run() {
                readPreference();
            }
        };
        t.start();
    }

    private void readPreference() {
        final Class clazz;
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Constants.SHARED_PREF_NAME, MODE_PRIVATE);
        String cookie = pref.getString(Constants.COOKIE_KEY, "");
        //Pause for a while
        pauseApp();
        Log.d(Constants.TAG,"Cookie ->" + cookie);
        if (!cookie.isEmpty()) {
            String userName = pref.getString(Constants.USERNAME_KEY, "");
            // Set the cookie in the app cache
            ApplicationCache.cookie = cookie;
            ApplicationCache.userName = userName;
            // Start the application with main activity
            clazz = MainActivity.class;
        } else {
            // Start the application with login activity
            clazz = LoginActivity.class;
        }
        //Launch the appropriate activity
        Handler uiHandler = new Handler(Looper.getMainLooper());
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                launchActivity(clazz);
            }
        };
        uiHandler.post(runnable);
    }

    private void launchActivity(Class clazz) {
        Intent intent = new Intent(getApplicationContext(), clazz);
        startActivity(intent);
        finish();
    }

    private void pauseApp() {
        // pause a while
        try {
            Thread.sleep(DURATION);
        } catch (InterruptedException e) {
            // Ignore the exception
        }
    }
}
