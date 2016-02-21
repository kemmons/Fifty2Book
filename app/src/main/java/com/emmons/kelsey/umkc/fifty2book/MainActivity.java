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

public class MainActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "PrefsFile";
    private final String GOAL = "goal", READ = "read", REM = "to_read";
    private int read = 0, to_read = 0, goal = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //saved states
        if (savedInstanceState != null) {
            read = savedInstanceState.getInt("read");
            to_read = savedInstanceState.getInt("to_read");
            goal = savedInstanceState.getInt("goal");
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
        if (goal > 0) {
            int progress = ((to_read / goal) * 100);
            t = (TextView) findViewById(R.id.progressPercent);
            t.setText(Integer.toString(progress) + "%");
            ProgressBar prog = (ProgressBar) findViewById(R.id.goalProgress);
            prog.setProgress(progress);
        }
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

}
