package com.emmons.kelsey.umkc.fifty2book;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by KE034178 on 3/4/2016.
 */
public class BookAdapter extends BaseAdapter {

    private ArrayList<BookObject> books;
    private LayoutInflater layoutInflater;

    public BookAdapter(Context context, ArrayList<BookObject> values) {

        this.books = books;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return books.size();
    }

    @Override
    public Object getItem(int position) {
        return books.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_row_layout, null);
            holder = new ViewHolder();
            holder.titleView = (TextView) convertView.findViewById(R.id.title);
            holder.authorView = (TextView) convertView.findViewById(R.id.author);
            holder.genreView = (TextView) convertView.findViewById(R.id.genre);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.titleView.setText(books.get(position).getTitle());
        holder.authorView.setText("By " + books.get(position).getAuthor());
        holder.genreView.setText(books.get(position).getGenre());
        return convertView;
    }
    static class ViewHolder {
        TextView titleView;
        TextView authorView;
        TextView genreView;
    }
}