package com.emmons.kelsey.umkc.fifty2book;

import android.test.AndroidTestCase;

/**
 * Created by KE034178 on 4/22/2016.
 */
public class BookTest extends AndroidTestCase{
    public void test() throws Exception {
        BookObject book = new BookObject("title", "author", "genre", "pages");
        assertNotNull(book);


    }
}
