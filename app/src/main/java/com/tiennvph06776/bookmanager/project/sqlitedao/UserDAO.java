package com.tiennvph06776.bookmanager.project.sqlitedao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import com.tiennvph06776.bookmanager.project.sqlite.Constant;
import com.tiennvph06776.bookmanager.project.model.User;
import com.tiennvph06776.bookmanager.project.sqlite.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class UserDAO implements Constant {

    DatabaseHelper databaseHelper;


    public UserDAO(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
    }

    public long deleteUser(String username) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        long result = db.delete(USER_TABLE, COLUMN_USERNAME + " = ?",
                new String[]{String.valueOf(username)});
        db.close();
        return result;
    }

    public long updateUser(User user) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_USERNAME, user.username);
        contentValues.put(COLUMN_PASSWORD, user.password);
        contentValues.put(COLUMN_NAME, user.name);
        contentValues.put(COLUMN_PHONE_NUMBER, user.sdt);

        // updating row
        return db.update(USER_TABLE, contentValues, COLUMN_USERNAME + " = ?",
                new String[]{String.valueOf(user.username)});
    }

    public void insertUser(User user) {

        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_USERNAME, user.username);
        contentValues.put(COLUMN_PASSWORD, user.password);
        contentValues.put(COLUMN_NAME, user.name);
        contentValues.put(COLUMN_PHONE_NUMBER, user.sdt);

        long id = db.insert(USER_TABLE, null, contentValues);

        if (Constant.isDEBUG) Log.e("insertUser", "insertUser ID : " + id);

        db.close();

    }

    public User getUser(String username) {

        User user = null;

        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        // Truyen vao Ten bang, array bao gom ten cot, ten cot khoa chinh, gia tri khoa chinh, cac tham so con lai la null

        Cursor cursor = db.query(USER_TABLE, new String[]{COLUMN_USERNAME, COLUMN_PASSWORD, COLUMN_NAME, COLUMN_PHONE_NUMBER}, COLUMN_USERNAME + "=?", new String[]{username}, null, null, null, null);

        // moveToFirst : kiem tra xem cursor co chua du lieu khong, ham nay tra ve gia tri la true or false
        if (cursor != null && cursor.moveToFirst()) {

            String user_name = cursor.getString(cursor.getColumnIndex(COLUMN_USERNAME));

            String password = cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD));

            String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));

            String phoneNumber = cursor.getString(cursor.getColumnIndex(COLUMN_PHONE_NUMBER));

            // khoi tao user voi cac gia tri lay duoc
            user = new User(user_name, password, name, phoneNumber);


        }
        cursor.close();

        return user;
    }


    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();


        // Select All Query
        String selectQuery = "SELECT  * FROM " + USER_TABLE;

        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                String user_name = cursor.getString(cursor.getColumnIndex(COLUMN_USERNAME));

                String password = cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD));

                String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));

                String phoneNumber = cursor.getString(cursor.getColumnIndex(COLUMN_PHONE_NUMBER));

                // khoi tao user voi cac gia tri lay duoc
                User user = new User(user_name, password, name, phoneNumber);
                users.add(user);

            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();


        return users;


    }


}
