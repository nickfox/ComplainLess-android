package com.websmithing.complainless;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;


public class ComplainLessActivity extends ActionBarActivity {
    private static final String TAG = "ComplainLessActivity";
    private Flipmeter flipMeter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complainless);

        flipMeter = (Flipmeter) findViewById(R.id.Flipmeter);

    }

    @Override
    public void onResume() {
        Log.d(TAG, "onResume");
        super.onResume();  // Always call the superclass method first

        flipMeter.setValue(37, true);

    }

}
