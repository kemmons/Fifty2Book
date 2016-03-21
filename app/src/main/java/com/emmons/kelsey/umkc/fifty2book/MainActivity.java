package com.emmons.kelsey.umkc.fifty2book;


import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "PrefsFile";
    private final String GOAL = "goal", READ = "read", REM = "to_read",
            TIME = "time", T_UNITS = "time_units";
    private int read = 0, to_read = 0, goal = 0, time = 0;
    private String time_units = "";
    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
    private ArrayAdapter<String> mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private String mActivityTitle;
    public ArrayList<BookObject> booksList = new ArrayList<>();


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
            booksList = savedInstanceState.getParcelableArrayList("bookList");
        }


        mDrawerList = (ListView) findViewById(R.id.navList);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();
        booksList.add(new BookObject("Test", "Author", "SciFi", "400"));
        booksList.add(new BookObject("The Once and Future King", "T. H. White", "Fantasy", "500"));

        addDrawerItems();
        setupDrawer();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        LoadPreferences();
        UpdateGoals();
    }

    private void addDrawerItems() {
        String[] activityArray = {"Book List", "Quick Stats", "Add Book"};
        mAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, activityArray);
        mDrawerList.setAdapter(mAdapter);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "Item Clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Navigation");
                invalidateOptionsMenu();
                }

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu();
            }
        };
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
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
            case R.id.action_list:
                intent = new Intent(this, BookListActivity.class);
                intent.putParcelableArrayListExtra("bookList", booksList);
                startActivity(intent);
                return true;
            case R.id.action_add:
                intent = new Intent(this, AddBookActivity.class);
                intent.putParcelableArrayListExtra("bookList", booksList);
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
        icicle.putParcelableArrayList("bookList", booksList);
    }

    @Override
    protected void onRestoreInstanceState(Bundle icicle) {
        super.onRestoreInstanceState(icicle);
    }
}
