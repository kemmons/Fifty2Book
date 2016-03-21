package com.emmons.kelsey.umkc.fifty2book;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RatingBar;

import org.json.JSONArray;

import java.util.ArrayList;

public class AddBookActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "PrefsFile";
    public ArrayList<BookObject> booksList = new ArrayList<>();
    private String title, author, genre, pages, rating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
        booksList = getIntent().getParcelableArrayListExtra("bookList");
        setListeners();
    }

    private void setListeners() {

        //set fields
        final View titleText = findViewById(R.id.bookTitleText);
        final View authorText = findViewById(R.id.authorNameText);
        final View genreText = findViewById(R.id.genreText);
        final View pagesText = findViewById(R.id.pagesText);
        final RatingBar ratingText = (RatingBar) findViewById(R.id.ratingBar);

        //set button
        View addButton = findViewById(R.id.addButton);
        View.OnClickListener aButton = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title = titleText.toString();
                author = authorText.toString();
                genre = genreText.toString();
                pages = pagesText.toString();
                rating = String.valueOf(ratingText.getRating());
                if (!(author.equals("") && title.equals("") && genre.equals("") && pages.equals(""))) {
                    BookObject book = new BookObject(author, title, genre, pages);
                    if (!(rating.equals(""))) {
                        book.setRating(rating);
                    }
                    booksList.add(book);
                    finish();
                }
            }
        };
        addButton.setOnClickListener(aButton);
    }


}
