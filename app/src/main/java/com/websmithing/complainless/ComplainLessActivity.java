package com.websmithing.complainless;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import java.util.Date;1

public class ComplainLessActivity extends ActionBarActivity {
    private static final String TAG = "ComplainLessActivity";
    private Flipmeter flipMeter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complainless);

        flipMeter = (Flipmeter) findViewById(R.id.Flipmeter);

        SharedPreferences sharedPreferences = this.getSharedPreferences("com.websmithing.complainless.prefs", Context.MODE_PRIVATE);

        if (sharedPreferences.getBoolean("isFirstLaunch", true)) {
            Date now = new Date();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putLong("startDate", now.getTime());
            editor.putLong("currentBestDate", now.getTime());
            editor.putBoolean("isFirstLaunch", false);
            editor.putInt("personalBest", 0);
            editor.commit();
        }
    }

    @Override
    public void onResume() {
        Log.d(TAG, "onResume");
        super.onResume();  // Always call the superclass method first

        flipMeter.setValue(37, true);

    }

}
