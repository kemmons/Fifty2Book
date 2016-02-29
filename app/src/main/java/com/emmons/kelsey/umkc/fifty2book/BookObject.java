package com.emmons.kelsey.umkc.fifty2book;

/**
 * Created by Kelsey Emmons on 2/21/2016.
 */
public class BookObject {
    private String _title;
    private String _author;
    private String _genre;
    private String _pageNo;
    private String _rating;

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

}
