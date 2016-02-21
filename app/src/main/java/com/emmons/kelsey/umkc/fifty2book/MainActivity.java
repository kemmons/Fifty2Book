package com.emmons.kelsey.umkc.fifty2book;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "PrefsFile";
    private final String GOAL = "goal", READ = "read", REM = "to_read",
            TIME = "time", T_UNITS = "time_units";
    private int read = 0, to_read = 0, goal = 0, time = 0;
    private String time_units = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //saved states
        if (savedInstanceState != null) {
            read = savedInstanceState.getInt("read");
            to_read = savedInstanceState.getInt("to_read");
            goal = savedInstanceState.getInt("goal");
            time = savedInstanceState.getInt("time");
            time_units = savedInstanceState.getString("t_units");
        }
        //toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        LoadPreferences();
        UpdateGoals();
    }

    private void LoadPreferences() {
        SharedPreferences goalPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        goal = goalPrefs.getInt(GOAL,0);
        to_read = goalPrefs.getInt(GOAL, 0) - read;
        time = goalPrefs.getInt(TIME, 0);
        time_units = goalPrefs.getString(T_UNITS, "");
        UpdateGoals();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void UpdateGoals() {
        TextView t = (TextView) findViewById(R.id.goalValue);
        t.setText(Integer.toString(goal));
        t = (TextView) findViewById(R.id.remainValue);
        t.setText(Integer.toString(to_read));
        if  (!time_units.equals("")) {
            if (time_units.equals("Weeks")) {
                time = time * 7;
            }
            else if (time_units.equals("Months")) {
                time = time * 30;
            }
            t = (TextView) findViewById(R.id.daysVal);
            t.setText(Integer.toString(time) + " days left");
        }
        if (goal > 0) {
            int progress = ((read / goal) * 100);
            t = (TextView) findViewById(R.id.progressPercent);
            t.setText(Integer.toString(progress) + "%");
            ProgressBar prog = (ProgressBar) findViewById(R.id.goalProgress);
            prog.setProgress(progress);
        }
        //TODO: add calculation using calendar to update progress bar
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    //life cycle methods
    @Override
    protected void onStart() {
        super.onStart();
        LoadPreferences();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        LoadPreferences();
    }

    @Override
    protected void onResume() {
        super.onResume();
        LoadPreferences();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle icicle) {
        super.onSaveInstanceState(icicle);
        icicle.putInt("goal", goal);
        icicle.putInt("read", read);
        icicle.putInt("rem", to_read);
    }

    @Override
    protected void onRestoreInstanceState(Bundle icicle) {
        super.onRestoreInstanceState(icicle);
    }
}
