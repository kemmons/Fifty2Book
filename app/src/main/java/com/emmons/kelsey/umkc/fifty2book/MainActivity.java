package com.emmons.kelsey.umkc.fifty2book;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
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
import junit.framework.Assert;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "PrefsFile";
    private final String GOAL = "goal", READ = "read", REM = "to_read",
            TIME = "time", T_UNITS = "time_units", START_DATE = "start_date";
    private int read = 0, to_read = 0, goal = 0, time = 0;
    private String time_units = "", start_date = "";
    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
    private ArrayAdapter<CharSequence> mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private String mActivityTitle;
    private Resources res = getResources();
    public ArrayList<BookObject> booksList = new ArrayList<>();
    public ArrayList<BookObject> readBookList = new ArrayList<>();


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
            readBookList = savedInstanceState.getParcelableArrayList("readBookList");
        }

        mDrawerList = (ListView) findViewById(R.id.navList);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();

        addDrawerItems();
        setupDrawer();


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        LoadPreferences();
        UpdateGoals();
    }

    private void addDrawerItems() {
        mAdapter = ArrayAdapter.createFromResource(this, R.array.navArray, android.R.layout.simple_list_item_1);
        mDrawerList.setAdapter(mAdapter);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectItem(position);
            }
        });
    }

    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle(R.string.navTitle);
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

    private void selectItem(int pos) {
        Intent intent;
        switch(pos) {
            case 0:
                intent = new Intent(MainActivity.this, BookListActivity.class);
                startActivity(intent);
                break;
            case 1:
                break;
            case 2:
                intent = new Intent(MainActivity.this, AddBookActivity.class);
                startActivityForResult(intent,1);
                break;
        }
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
        start_date = goalPrefs.getString(START_DATE, "");
        UpdateGoals();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void UpdateGoals() {
        String date = getCurrentTime();
        String wks = res.getStringArray(R.array.spinnerArray)[1];
        String mnths = res.getStringArray(R.array.spinnerArray)[2];
        TextView t = (TextView) findViewById(R.id.goalValue);
        t.setText(Integer.toString(goal));
        t = (TextView) findViewById(R.id.remainValue);
        t.setText(Integer.toString(to_read));
        if  (!time_units.equals("")) {
            if (time_units.equals(wks)) {
                time = time * 7;
            } else if (time_units.equals(mnths)) {
                time = time * 30;
            }
            t = (TextView) findViewById(R.id.daysVal);

            long days_passed = getDaysDiff(date, start_date);
            if (days_passed != -1) {
                String time_left = Integer.toString((int) (time - days_passed));
                String label = res.getString(R.string.days_left, time_left);
                t.setText(label);
            }
            else {
                String time_left = Integer.toString(time);
                String label = res.getString(R.string.days_left, time_left);
                t.setText(label);
            }
        }
        if (goal > 0) {
            int progress = ((read / goal) * 100);
            t = (TextView) findViewById(R.id.progressPercent);
            String pString = Integer.toString(progress);
            String label = String.format(res.getString(R.string.percent), pString);
            t.setText(label);
            ProgressBar prog = (ProgressBar) findViewById(R.id.goalProgress);
            prog.setProgress(progress);

            ProgressBar timeBar = (ProgressBar) findViewById(R.id.progressBar);
            long days = getDaysDiff(date, start_date);
            if (days != -1) {
                int time_prog = (int) (((time - days) / time) * 100);
                timeBar.setProgress(time_prog);
            }
            else {
                timeBar.setProgress(100);
            }
        }
    }

    public static long getDaysDiff(String today, String started) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date now = sdf.parse(today);
            Date then = sdf.parse(started);
            long diff = now.getTime() - then.getTime();
            return (TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
        } catch (ParseException pe) {
            //there was an issue with the Date parsing,
            //return -1 and resort to default behavior
            //(no date calculation)
            return -1;
        }
    }

    public static String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date now = new Date();
        String strDate = sdf.format(now);
        return strDate;
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
                Intent intent = null;
                try {
                    intent = new Intent(this, SettingsActivity.class);
                    startActivity(intent);
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, R.string.settingsError, Toast.LENGTH_SHORT).show();
                }
            case R.id.action_list:
                intent = new Intent(this, BookListActivity.class);
                intent.putParcelableArrayListExtra("bookList", booksList);
                intent.putParcelableArrayListExtra("readBookList", readBookList);
                startActivityForResult(intent, 2);
                return true;
            case R.id.action_add:
                intent = new Intent(this, AddBookActivity.class);
                intent.putParcelableArrayListExtra("bookList", booksList);
                intent.putParcelableArrayListExtra("readBookList", readBookList);
                startActivityForResult(intent, 1);
                return true;
            case R.id.action_read_list:
                intent = new Intent(this, ReadBooksListActivity.class);
                intent.putParcelableArrayListExtra("readBookList", readBookList);
                startActivity(intent);
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    //life cycle methods
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                booksList = data.getParcelableArrayListExtra("books");
                Assert.assertNotNull(booksList);
            }
        }
        else if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                booksList = data.getParcelableArrayListExtra("books");
                readBookList = data.getParcelableArrayListExtra("readBookList");
            }
        }
    }
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
        icicle.putParcelableArrayList("readBookList", readBookList);
    }

    @Override
    protected void onRestoreInstanceState(Bundle icicle) {
        super.onRestoreInstanceState(icicle);
    }
}
