package com.emmons.kelsey.umkc.fifty2book;

import android.app.ListActivity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class BookListActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);

        ArrayList<BookObject> books = new ArrayList<BookObject>();
        books.add(new BookObject("Title", "Author", "Genre", "# of Pages"));

        setListAdapter(new BookAdapter(this, books));
    }

    @Override
    protected void onListItemClick(ListView list, View view, int position, long id) {
        BookObject book = (BookObject) getListAdapter().getItem(position);

    }
}

class BookAdapter extends ArrayAdapter {
    private final Context context;
    private final ArrayList<BookObject> values;

    public BookAdapter(Context context, ArrayList<BookObject> values) {

        super(context, R.layout.activity_book_list, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.activity_book_list, parent, false);

        return rowView;
    }
}
