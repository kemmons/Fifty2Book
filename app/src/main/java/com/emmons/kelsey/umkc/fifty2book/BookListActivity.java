package com.emmons.kelsey.umkc.fifty2book;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;

public class BookListActivity extends ListActivity {

    private static final String PREFS_NAME = "PrefsFile";
    private ArrayList<BookObject> books = new ArrayList<BookObject>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        setContentView(R.layout.activity_book_list);
        books = getIntent().getParcelableArrayListExtra("bookList");

        ListView lv = getListView();

        if (books != null && lv != null) {
            lv.setAdapter(new BookAdapter(this, books));
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String book = parent.getItemAtPosition(position).toString();
                    AlertDialog.Builder builder = new AlertDialog.Builder(BookListActivity.this);
                    builder.setTitle(R.string.alert_title);
                    builder.setMessage(book);
                    builder.setPositiveButton(R.string.cmd_mark_read, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //// TODO: 4/20/2016 Add 'mark read' action
                        }
                    });
                    builder.setNegativeButton(R.string.cmd_cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // TODO: 4/20/2016 Add 'cancel' action
                        }
                    });
                    builder.show();
                }
            });
        }

        if(books.isEmpty()) {
            Toast toast = Toast.makeText(this, R.string.empty_list_msg, Toast.LENGTH_LONG);
            toast.show();
        }
    }
}

