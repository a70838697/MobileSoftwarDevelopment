package com.example.casper.test;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;

import com.example.casper.data.BookSaver;
import com.example.casper.data.model.Book;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BookSaverTest {
    private BookSaver bookKeeper;
    private Context context;

    @Before
    public void setUp() throws Exception {
        context = ApplicationProvider.getApplicationContext();
        bookKeeper = new BookSaver(context);
        bookKeeper.load();
    }

    @After
    public void tearDown() throws Exception {
        bookKeeper.save();
    }

    @Test
    public void getBooks() {
        Assert.assertNotNull(bookKeeper.getBooks());
        BookSaver bookSaver = new BookSaver(context);
        Assert.assertNotNull(bookKeeper.getBooks());
        Assert.assertEquals(0, bookSaver.getBooks().size());
    }

    @Test
    public void saveThenLoad() {
        BookSaver bookSaverTest = new BookSaver(context);
        Assert.assertEquals(0, bookSaverTest.getBooks().size());
        Book book = new Book("test book", 1.23, 123);
        bookSaverTest.getBooks().add(book);
        book = new Book("test book2", 1.24, 124);
        bookSaverTest.getBooks().add(book);
        bookSaverTest.save();

        BookSaver bookSaverLoader = new BookSaver(context);
        bookSaverLoader.load();
        Assert.assertEquals(bookSaverTest.getBooks().size(), bookSaverLoader.getBooks().size());
        for (int i = 0; i < bookSaverTest.getBooks().size(); i++) {
            Book bookThis = bookSaverTest.getBooks().get(i);
            Book bookThat = bookSaverLoader.getBooks().get(i);
            Assert.assertEquals(bookThat.getCoverResourceId(), bookThis.getCoverResourceId());
            Assert.assertEquals(bookThat.getTitle(), bookThis.getTitle());
            Assert.assertTrue(Math.abs(bookThat.getPrice() - bookThis.getPrice()) < 1e-4);
        }
    }

    @Test
    public void saveEmptyThenLoad() {
        BookSaver bookSaverTest = new BookSaver(context);
        Assert.assertEquals(0, bookSaverTest.getBooks().size());
        bookSaverTest.save();

        BookSaver bookSaverLoader = new BookSaver(context);
        bookSaverLoader.load();
        Assert.assertEquals(bookSaverTest.getBooks().size(), bookSaverLoader.getBooks().size());
    }
}