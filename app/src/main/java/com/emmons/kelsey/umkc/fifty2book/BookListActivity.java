package com.emmons.kelsey.umkc.fifty2book;

import android.app.ListActivity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;

import java.util.ArrayList;

public class BookListActivity extends ListActivity {

    public ListView booksList;
    private static final String PREFS_NAME = "PrefsFile";
    private ArrayList<BookObject> books = new ArrayList<BookObject>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        setContentView(R.layout.activity_book_list);
        books = getIntent().getParcelableArrayListExtra("bookList");

        booksList = (ListView) findViewById(android.R.id.list);
        if (books != null && booksList != null) {
            booksList.setAdapter(new BookAdapter(this, books));
            booksList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Object o = booksList.getItemIdAtPosition(position);
                    BookObject book = (BookObject) o;
                }

            });
        }

        if(books.isEmpty()) {
            Toast toast = Toast.makeText(this, "Your book list is empty", Toast.LENGTH_LONG);
            toast.show();
        }
    }

    private ArrayList getListData() {
        ArrayList<BookObject> results = new ArrayList<BookObject>();
        BookObject book = new BookObject("The Once and Future King", "T.H. White", "Fantasy", "500");
        results.add(book);

        return results;
    }


    public void onListItemClick(AdapterView<?> parent, View view, int position, long id) {
        String book = (String) parent.getItemAtPosition(position);
        String text = "You selected " + book;
        Toast toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        toast.show();

    }
}

