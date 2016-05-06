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
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.security.Key;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SettingsActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "PrefsFile";
    private final String GOAL = "goal", READ = "read", REM = "to_read",
            TIME = "time", T_UNITS = "time_units", START_DATE = "start_date";

    private int goal = 0, read = 0, to_read = 0;
    private int time_left = 0;
    private String time_type = "none";
    private String date = "";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setListeners();
    }

    private void setListeners() {
        Spinner goalSpinner = (Spinner) findViewById(R.id.goalSpinner);

        ArrayAdapter<CharSequence> goalSpinAdapter = ArrayAdapter
                .createFromResource(this, R.array.spinnerArray,
                        android.R.layout.simple_spinner_item);

        goalSpinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        goalSpinner.setAdapter(goalSpinAdapter);

        Button setGoalButton = (Button) findViewById(R.id.setGoalButton);
        View.OnClickListener sGButton = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editBookGoal = (EditText) findViewById(R.id.booksGoal);
                EditText editTimeGoal = (EditText) findViewById(R.id.weeksGoal);
                Spinner goalSpinner = (Spinner) findViewById(R.id.goalSpinner);

                goal = Integer.parseInt(editBookGoal.getText().toString());
                time_left = Integer.parseInt(editTimeGoal.getText().toString());
                time_type = goalSpinner.getSelectedItem().toString();

                GetDate();
                SavePreferences();
                finish();
            }
        };
        setGoalButton.setOnClickListener(sGButton);
    }

    private void GetDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date now = new Date();
        date = sdf.format(now);
    }

    private void SavePreferences() {
        SharedPreferences goalPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor prefsEditor = goalPrefs.edit();
        prefsEditor.putInt(GOAL,goal);
        prefsEditor.putInt(READ,read);
        prefsEditor.putInt(REM, to_read);
        prefsEditor.putInt(TIME, time_left);
        prefsEditor.putString(T_UNITS, time_type);
        prefsEditor.putString(START_DATE, date);
        prefsEditor.apply();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}
