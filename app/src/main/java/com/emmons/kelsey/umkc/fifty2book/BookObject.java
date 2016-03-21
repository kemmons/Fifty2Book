package com.emmons.kelsey.umkc.fifty2book;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Kelsey Emmons on 2/21/2016.
 */
public class BookObject implements Parcelable {
    private String _title;
    private String _author;
    private String _genre;
    private String _pageNo;
    private String _rating;

    //Values to save to the Parcel
    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(_author);
        out.writeString(_title);
        out.writeString(_genre);
        out.writeString(_pageNo);
        out.writeString(_rating);
    }

    private BookObject(Parcel in) {
        _author = in.readString();
        _title = in.readString();
        _genre = in.readString();
        _pageNo = in.readString();
        _rating = in.readString();
    }

    //constructor
    public BookObject(String title, String author, String genre, String pages) {
        _title = title;
        _author = author;
        _genre = genre;
        _pageNo = pages;
    }

    public String getTitle(){return _title;}
    public String getAuthor(){return _author;}
    public String getGenre(){return _genre;}
    public String getPages(){return _pageNo;}
    public String getRating(){return _rating;}

    public void setTitle(String title) {_title = title;}
    public void setAuthor(String author) {_author = author;}
    public void setGenre(String genre) {_genre = genre;}
    public void setPages(String pages) {_pageNo = pages;}
    public void setRating(String rating) {_rating = rating;}

    public String toString(){
        return "Title: " + _title + "\nAuthor: " + _author + "\nGenre: " + _genre +
                "\nPages: " + _pageNo + "\nRating: " + _rating;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<BookObject> CREATOR = new Parcelable.Creator<BookObject>(){
        @Override
        public BookObject createFromParcel(Parcel in) {
            return new BookObject(in);
        }

        @Override
        public BookObject[] newArray(int size) {
            return new BookObject[size];
        }
    };
}
