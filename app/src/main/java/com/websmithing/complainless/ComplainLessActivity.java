package com.websmithing.complainless;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class ComplainLessActivity extends ActionBarActivity {
    private static final String TAG = "ComplainLessActivity";

    SharedPreferences sharedPreferences;
    private Flipmeter flipMeter = null;
    private static Button startOverButton;
    private static TextView personalBestTextView;
    private static TextView startDateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complainless);

        flipMeter = (Flipmeter) findViewById(R.id.flipmeter_view);
        personalBestTextView = (TextView)findViewById(R.id.personalBestTextView);
        startDateTextView = (TextView)findViewById(R.id.startDateTextView);
        startOverButton = (Button)findViewById(R.id.startOverButton);

        startOverButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startOver();
            }
        });

        sharedPreferences = this.getSharedPreferences("com.websmithing.complainless.prefs", Context.MODE_PRIVATE);

        if (sharedPreferences.getBoolean("isFirstLaunch", true)) {
            DateTime now = new DateTime();
            SharedPreferences.Editor editor = sharedPreferences.edit();

            //DateTime dt = new DateTime().minusDays(3);
            //editor.putLong("startDate", dt.getMillis());
            //editor.putLong("currentBestDate", dt.getMillis());

            editor.putLong("startDate", now.getMillis());
            editor.putLong("currentBestDate", now.getMillis());
            editor.putBoolean("isFirstLaunch", false);
            editor.putInt("personalBestInDays", 0);
            editor.commit();
        }

        DateTime startDate = new DateTime(sharedPreferences.getLong("startDate", 0));
        DateTimeFormatter startDateFormatter = DateTimeFormat.forPattern("MMM d, yyyy");
        startDateTextView.setText("Start Date: " + startDate.toString(startDateFormatter));
    }

    @Override
    public void onResume() {
        super.onResume();

        updateUI();
    }

    private void updateUI() {
        DateTime now = new DateTime();
        DateTime currentBestDate = new DateTime(sharedPreferences.getLong("currentBestDate", 0));
        int numberOfDaysCompleted = Math.abs(Days.daysBetween(currentBestDate, now).getDays());
        int personalBestInDays = sharedPreferences.getInt("personalBestInDays", 0);

        // DateTime startDate = new DateTime(sharedPreferences.getLong("startDate", 0));
        //Log.e(TAG, "now: " + now);
        //Log.e(TAG, "startDate: " + startDate);
        //Log.e(TAG, "currentBestDate: " + currentBestDate);
        //Log.e(TAG, "numberOfDaysCompleted: " + numberOfDaysCompleted);
        //Log.e(TAG, "personalBestInDays: " + personalBestInDays);

        if (numberOfDaysCompleted > personalBestInDays) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("personalBestInDays", numberOfDaysCompleted);
            editor.commit();

            if (numberOfDaysCompleted == 1) {
                personalBestTextView.setText("Personal Best: 1 day");
            } else {
                personalBestTextView.setText("Personal Best: " + numberOfDaysCompleted + " days");
            }

            flipMeter.setValue(numberOfDaysCompleted, false);
        } else {
            if (personalBestInDays == 1) {
                personalBestTextView.setText("Personal Best: 1 day");
            } else {
                personalBestTextView.setText("Personal Best: " + personalBestInDays + " days");
            }

            flipMeter.setValue(personalBestInDays, false);
        }
    }

    private void startOver() {
        Toast.makeText(getApplicationContext(), R.string.start_time_reset, Toast.LENGTH_LONG).show();

        DateTime now = new DateTime();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong("currentBestDate", now.getMillis());
        editor.commit();

        flipMeter.setValue(0, false);
    }
}
