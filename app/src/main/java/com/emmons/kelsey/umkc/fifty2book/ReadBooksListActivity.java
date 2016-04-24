package com.emmons.kelsey.umkc.fifty2book;

import android.app.ListActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;

public class ReadBooksListActivity extends ListActivity {

    private ArrayList<BookObject> books = new ArrayList<BookObject>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        setContentView(R.layout.activity_read_books_list);
        books = getIntent().getParcelableArrayListExtra("readBookList");
        ListView listView = getListView();

        if (books != null && listView != null) {
            listView.setAdapter(new BookAdapter(this, books));
        }

        if (books.isEmpty()) {
            Toast toast = Toast.makeText(this, R.string.empty_list_msg, Toast.LENGTH_LONG);
            toast.show();
        }
    }
}
