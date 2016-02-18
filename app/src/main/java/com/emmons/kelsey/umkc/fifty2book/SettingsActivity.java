package com.emmons.kelsey.umkc.fifty2book;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class SettingsActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Spinner goalSpinner = (Spinner) findViewById(R.id.goalSpinner);

        ArrayAdapter<CharSequence> goalSpinAdapter = ArrayAdapter
                .createFromResource(this, R.array.spinnerArray,
                        android.R.layout.simple_spinner_item);

        goalSpinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        goalSpinner.setAdapter(goalSpinAdapter);

        Intent intent = getIntent();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }
}
