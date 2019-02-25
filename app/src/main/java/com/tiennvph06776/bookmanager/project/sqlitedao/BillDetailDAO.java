package com.tiennvph06776.bookmanager.project.sqlitedao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.tiennvph06776.bookmanager.project.sqlite.Constant;
import com.tiennvph06776.bookmanager.project.model.BillDetail;
import com.tiennvph06776.bookmanager.project.sqlite.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class BillDetailDAO implements Constant {

    DatabaseHelper databaseHelper;


    public BillDetailDAO(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
    }


    public List<BillDetail> getAllBillDetailByBillID(String billID) {
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        List<BillDetail> billDetails = new ArrayList<>();

        String SELECT_ALL_BILL_DETAIL_BY_BILL_ID = "SELECT * FROM " + TABLE_BILL_DETAIL +
                " WHERE " + DETAIL_BILL_ID + " = " + "'" + billID + "'";


        Cursor cursor = sqLiteDatabase.rawQuery(SELECT_ALL_BILL_DETAIL_BY_BILL_ID, null);

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                String id = cursor.getString(cursor.getColumnIndex(DETAIL_ID));
                String book_id = cursor.getString(cursor.getColumnIndex(DETAIL_BOOK_ID));
                String bill_id = cursor.getString(cursor.getColumnIndex(DETAIL_BILL_ID));
                int quality = cursor.getInt(cursor.getColumnIndex(DETAIL_QUALITY));

                BillDetail billDetail = new BillDetail();
                billDetail.id = id;
                billDetail.billID = bill_id;
                billDetail.bookID = book_id;
                billDetail.quality = quality;
                billDetails.add(billDetail);

            } while (cursor.moveToNext());

        }
        sqLiteDatabase.close();
        return billDetails;
    }


    public long insertBillDetail(BillDetail billDetail) {
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DETAIL_ID, billDetail.id);
        contentValues.put(DETAIL_BILL_ID, billDetail.billID);
        contentValues.put(DETAIL_BOOK_ID, billDetail.bookID);
        contentValues.put(DETAIL_QUALITY, billDetail.quality);

        long result = sqLiteDatabase.insert(TABLE_BILL_DETAIL, null, contentValues);
        return result;
    }
    public long deleteBillDetail(String id) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        long result = db.delete(TABLE_BILL_DETAIL, DETAIL_ID + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
        return result;
    }
    public int totalBill(String BillId){
        /*String SELECT_TOTAL_MANNY= "select "+ "SUM("+"e."+DETAIL_QUALITY+"* "+
                "i."+BOOK_PRICE+") as TONG "+ "from "+ TABLE_BILL_DETAIL+ "as e, "+TABLE_BILL+ "as d, "+
                TABLE_BOOK+ "as i "+ "where e."+DETAIL_BILL_ID+"=d."+B_ID+ "and e."+DETAIL_BOOK_ID+"=i."+BOOK_ID;*/
        int total=0;
        String sql="select SoLuongMua*giaBia as b from BillDetail, Bill, Books where BillDetail.MaHoaDon=Bill.MaHoaDon and BillDetail.MaSach=Books.MaSach and Bill.MaHoaDon="+ "'" + BillId + "'";
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                int many = cursor.getInt(cursor.getColumnIndex("b"));
                total=total+many;

            } while (cursor.moveToNext());

        }
        return total;
    }
}
