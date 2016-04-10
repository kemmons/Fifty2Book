package com.emmons.kelsey.umkc.fifty2book;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;


import java.util.ArrayList;

public class AddBookActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "PrefsFile";
    public ArrayList<BookObject> booksList = new ArrayList<>();
    private String title, author, genre, pages, rating;
    private boolean blankField = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
        booksList = getIntent().getParcelableArrayListExtra("bookList");
        setListeners();

    }

    //Throws:
    //  InvalidInputError, if the user fails to enter data for
    //  one of the required fields.
    private void setListeners() {

        //set fields
        final EditText titleText = (EditText) findViewById(R.id.bookTitleText);
        final EditText authorText = (EditText) findViewById(R.id.authorNameText);
        final EditText genreText = (EditText) findViewById(R.id.genreText);
        final EditText pagesText = (EditText) findViewById(R.id.pagesText);
        final RatingBar ratingText = (RatingBar) findViewById(R.id.ratingBar);

        //set button
        View addButton = findViewById(R.id.addButton);
        View.OnClickListener aButton = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title = titleText.getText().toString();
                author = authorText.getText().toString();
                genre = genreText.getText().toString();
                pages = pagesText.getText().toString();
                rating = String.valueOf(ratingText.getRating());
                if (!(author.equals("") && title.equals("") && genre.equals("") && pages.equals(""))) {
                    BookObject book = new BookObject(title, author, genre, pages);
                    if (!(rating.equals(""))) {
                        book.setRating(rating);
                    }
                    booksList.add(book);
                    try {
                        addNewBook();
                    } catch (Exception e) {
                        alertUser();
                    }
                } else {
                    blankField = true;
                    try {
                        addNewBook();
                    } catch (Exception e) {
                        alertUser();
                    }
                }
            }
        };
        addButton.setOnClickListener(aButton);
    }

    public void addNewBook() throws Exception {
        if (blankField) {
            Exception e = new Exception();
            throw e;
        } else {
            Intent returnIntent = new Intent();
            returnIntent.putParcelableArrayListExtra("books", booksList);
            setResult(RESULT_OK, returnIntent);
            finish();
        }
    }

    public void alertUser() {
        AlertDialog alertDialog = new AlertDialog.Builder(AddBookActivity.this).create();
        alertDialog.setTitle("Error");
        alertDialog.setMessage("One of the required fields was left blank.");
        alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        alertDialog.show();
    }
}

