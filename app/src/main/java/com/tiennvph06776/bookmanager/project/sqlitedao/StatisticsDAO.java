package com.tiennvph06776.bookmanager.project.sqlitedao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import com.tiennvph06776.bookmanager.project.sqlite.Constant;
import com.tiennvph06776.bookmanager.project.model.Bill;
import com.tiennvph06776.bookmanager.project.model.BillDetail;
import com.tiennvph06776.bookmanager.project.sqlite.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class StatisticsDAO implements Constant {

    private DatabaseHelper databaseHelper;

    public StatisticsDAO(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
    }



    public long getStatisticsByDayCach1(long day) {
        long result = -1;
        List<Bill> bills = new ArrayList<>();

        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        String SELECT_ = "SELECT * FROM " + TABLE_BILL;

        Cursor cursor = sqLiteDatabase.rawQuery(SELECT_, null);

        if (cursor != null) {
            if (cursor.getCount() > 0) {
                do {
                    String id = cursor.getString(cursor.getColumnIndex(B_ID));
                    long date = cursor.getLong(cursor.getColumnIndex(B_DATE));

                    Bill bill = new Bill(id, date);

                    bills.add(bill);

                } while (cursor.moveToNext());

            }
        }

        // loai bo cac ngay
        for (int i = 0; i < bills.size(); i++) {

            long date = bills.get(i).date;
            if (date != day) {
                bills.remove(i);
            }

        }

        List<BillDetail> x = new ArrayList<>();
        for (int i = 0; i < bills.size(); i++) {
            List<BillDetail> billDetails_ =
                    new BillDetailDAO(databaseHelper).getAllBillDetailByBillID(bills.get(i).id);

            // lay toan bo danh sach Bill Detail theo Bill ID
            x.addAll(billDetails_);

        }

        for (int i = 0; i < x.size(); i++) {

            int quality = x.get(i).quality;
            long price = new BookDAO(databaseHelper).getBookByID(x.get(i).bookID).price;

            long sum_ = quality * price;

            result = result + sum_;

        }

        return result;
    }

    // example day = 2018-10-09   YY-MM-DD

    public long getStatisticsByDayCach2(String day) {
        long result = -1;
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        String SELECT_STATISTICS_BY_DAY = "";

        return result;

    }


    // format month : %Y-%m  2018-10

    public long getStatisticsByMonth(String month) {
        long result = -1;

        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();


        String SELECT_STATISTICS = "SELECT * FROM " + TABLE_BILL + " WHERE strftime('%Y-%m', " + B_DATE + ")  = '" + month + "'";
        Cursor cursor = sqLiteDatabase.rawQuery(SELECT_STATISTICS, null);
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                Log.e("SIZE", cursor.getCount() + "");
                cursor.moveToFirst();
                do {
                    String text = cursor.getString(0);

                    Log.e("text",
                            text);

                } while (cursor.moveToNext());
            } else {
                Log.e("SIZE=0", "000");
            }
        }

        return result;
    }




    public int totalBillY(){
        /*String SELECT_TOTAL_MANNY= "select "+ "SUM("+"e."+DETAIL_QUALITY+"* "+
                "i."+BOOK_PRICE+") as TONG "+ "from "+ TABLE_BILL_DETAIL+ "as e, "+TABLE_BILL+ "as d, "+
                TABLE_BOOK+ "as i "+ "where e."+DETAIL_BILL_ID+"=d."+B_ID+ "and e."+DETAIL_BOOK_ID+"=i."+BOOK_ID;*/
        int total=0;
        String sql="select SoLuongMua*giaBia as b from BillDetail, Bill, Books where BillDetail.MaHoaDon=Bill.MaHoaDon and BillDetail.MaSach=Books.MaSach";
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
    public int totalBillM(){
        /*String SELECT_TOTAL_MANNY= "select "+ "SUM("+"e."+DETAIL_QUALITY+"* "+
                "i."+BOOK_PRICE+") as TONG "+ "from "+ TABLE_BILL_DETAIL+ "as e, "+TABLE_BILL+ "as d, "+
                TABLE_BOOK+ "as i "+ "where e."+DETAIL_BILL_ID+"=d."+B_ID+ "and e."+DETAIL_BOOK_ID+"=i."+BOOK_ID;*/
        int total=-40000;
        String sql="select SoLuongMua*giaBia as b from BillDetail, Bill, Books where BillDetail.MaHoaDon=Bill.MaHoaDon and BillDetail.MaSach=Books.MaSach";
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


    // YY-MM-DD


    public double totalBillD(String dateFormat) {

        double result = -1;

        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        String QUERY_DAY = "SELECT SUM(tongtien) FROM (" +
                "" + "SELECT SUM(Books.giaBia * BillDetail.SoluongMua) as 'tongtien'" +
                "" + " FROM " + TABLE_BILL +
                "" + " INNER JOIN " + TABLE_BOOK + " ON " + " Books.MaSach = BillDetail.MaSach " +
                "" + " INNER JOIN " + TABLE_BILL_DETAIL + " ON " + " Bill.MaHoaDon = BillDetail.MaHoaDon " +
                "" + " WHERE  strftime(" + dateFormat + ", Bill.NgayMua / 1000 , 'unixepoch') = strftime(" + dateFormat + ",'now') " +
                "" + " GROUP BY BillDetail.MaSach" +
                ")";

        Log.e("QUERY_DAY", QUERY_DAY);

        Cursor cursor = sqLiteDatabase.rawQuery(QUERY_DAY, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();

            result = cursor.getDouble(0);
        }
        return result;
    }


}
