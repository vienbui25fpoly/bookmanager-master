package com.tiennvph06776.bookmanager.project.sqlitedao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.tiennvph06776.bookmanager.project.model.SelectTop10Book;
import com.tiennvph06776.bookmanager.project.sqlite.Constant;
import com.tiennvph06776.bookmanager.project.model.Book;
import com.tiennvph06776.bookmanager.project.sqlite.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class BookDAO implements Constant {


    private DatabaseHelper databaseHelper;

    public BookDAO(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;

    }

    public long insertBook(Book book) {
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(BOOK_ID, book.id);
        contentValues.put(BOOK_TYPE_BOOK_ID, book.typeID);
        contentValues.put(BOOK_AUTHOR, book.author);
        contentValues.put(BOOK_PRICE, book.price);
        contentValues.put(BOOK_PRODUCER, book.producer);
        contentValues.put(BOOK_QUALITY, book.quality);
        long result = sqLiteDatabase.insert(TABLE_BOOK, null, contentValues);
        sqLiteDatabase.close();
        return result;
    }

    public long updateBook(Book book) {

        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(BOOK_TYPE_BOOK_ID, book.typeID);
        contentValues.put(BOOK_AUTHOR, book.author);
        contentValues.put(BOOK_PRICE, book.price);
        contentValues.put(BOOK_PRODUCER, book.producer);
        contentValues.put(BOOK_QUALITY, book.quality);

        long result = sqLiteDatabase.update(TABLE_BOOK, contentValues,
                BOOK_ID + "=?", new String[]{book.id});
        sqLiteDatabase.close();
        return result;

    }

    public long delBook(String id) {

        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();
        long result = sqLiteDatabase.delete(TABLE_BILL, BOOK_ID + "=?", new String[]{id});
        sqLiteDatabase.close();
        return result;
    }

    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();

        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_BOOK,
                null);

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {

                String id = cursor.getString(cursor.getColumnIndex(BOOK_ID));
                long price = cursor.getLong(cursor.getColumnIndex(BOOK_PRICE));
                String typeID = cursor.getString(cursor.getColumnIndex(BOOK_TYPE_BOOK_ID));
                String author = cursor.getString(cursor.getColumnIndex(BOOK_AUTHOR));
                String producer = cursor.getString(cursor.getColumnIndex(BOOK_PRODUCER));
                int quality = cursor.getInt(cursor.getColumnIndex(BOOK_QUALITY));
                Book book = new Book();

                book.id = id;
                book.price = price;
                book.typeID = typeID;
                book.author = author;
                book.producer = producer;
                book.quality = quality;
                books.add(book);

            } while (cursor.moveToNext());
        }

        return books;
    }

    public Book getBookByID(String bookId) {
        Book book = null;

        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        Cursor cursor = sqLiteDatabase.query(TABLE_BOOK, new String[]{BOOK_ID, BOOK_TYPE_BOOK_ID, BOOK_AUTHOR, BOOK_PRICE, BOOK_PRODUCER, BOOK_QUALITY},
                BOOK_ID + "=?",
                new String[]{bookId}, null, null, null);

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();

            String id = cursor.getString(cursor.getColumnIndex(BOOK_ID));
            long price = cursor.getLong(cursor.getColumnIndex(BOOK_PRICE));
            String typeID = cursor.getString(cursor.getColumnIndex(BOOK_TYPE_BOOK_ID));
            String author = cursor.getString(cursor.getColumnIndex(BOOK_AUTHOR));
            String producer = cursor.getString(cursor.getColumnIndex(BOOK_PRODUCER));
            int quality = cursor.getInt(cursor.getColumnIndex(BOOK_QUALITY));

            book = new Book();
            book.id = id;
            book.price = price;
            book.typeID = typeID;
            book.author = author;
            book.producer = producer;
            book.quality = quality;
        }

        return book;
    }
    public List<SelectTop10Book> getTop10Books() {
        List<SelectTop10Book> selectTop10Books = new ArrayList<>();

        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select Books.MaSach as e, SUM(BillDetail.SoLuongMua) as i from BillDetail, Bill, Books where BillDetail.MaHoaDon=Bill.MaHoaDon and BillDetail.MaSach=Books.MaSach group by Books.MaSach order by SUM(BillDetail.SoLuongMua) LIMIT 10",
                null);

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                String id = cursor.getString(cursor.getColumnIndex("e"));
                int amount = cursor.getInt(cursor.getColumnIndex("i"));
                SelectTop10Book book = new SelectTop10Book();

                book.setId(id);
                book.setAmount(amount);

                selectTop10Books.add(book);

            } while (cursor.moveToNext());
        }

        return selectTop10Books;
    }
}
