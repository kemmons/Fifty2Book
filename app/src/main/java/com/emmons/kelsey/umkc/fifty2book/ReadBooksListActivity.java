package com.emmons.kelsey.umkc.fifty2book;

import android.app.ListActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;

public class ReadBooksListActivity extends ListActivity {

    private ArrayList<BookObject> books = new ArrayList<BookObject>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        setContentView(R.layout.activity_read_books_list);
        books = getIntent().getParcelableArrayListExtra("readBookList");
        // TODO: 4/20/2016 Finish read books list 
    }
}
