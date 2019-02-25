package com.tiennvph06776.bookmanager.project.activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;


import com.tiennvph06776.bookmanager.project.R;
import com.tiennvph06776.bookmanager.project.adapter.AdapterBookTop10;
import com.tiennvph06776.bookmanager.project.model.Book;
import com.tiennvph06776.bookmanager.project.model.SelectTop10Book;
import com.tiennvph06776.bookmanager.project.sqlite.DatabaseHelper;
import com.tiennvph06776.bookmanager.project.sqlitedao.BillDetailDAO;
import com.tiennvph06776.bookmanager.project.sqlitedao.BookDAO;


import java.util.ArrayList;
import java.util.List;

public class ActivityBookTop10 extends AppCompatActivity {
    Toolbar toolbarSachBanChay;
    RecyclerView rvSach;
    private List<SelectTop10Book> bookList;
    private AdapterBookTop10 adapter;
    private DatabaseHelper databaseHelper;
    private BookDAO bookDAO;

    Spinner spinnerThang;
    String thang[] = new String[]{"Tháng 1","Tháng 2","Tháng 3","Tháng 4","Tháng 5","Tháng 6","Tháng 7",
            "Tháng 8","Tháng 9","Tháng 10","Tháng 11","Tháng 12"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_top10);
        databaseHelper = new DatabaseHelper(this);
        bookDAO = new BookDAO(databaseHelper);
        toolbarSachBanChay = findViewById(R.id.toolbarSachBanChay);
        setSupportActionBar(toolbarSachBanChay);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbarSachBanChay.setTitleTextColor(Color.WHITE);
        toolbarSachBanChay.setTitle("Top 10 Sách Bán Chạy");
       /* toolbarSachBanChay.setNavigationIcon(R.drawable.undo);*/
        spinnerThang = findViewById(R.id.spThang);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, thang);
        spinnerThang.setAdapter(adapter1);

        /*toolbarSachBanChay.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });*/
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        rvSach = findViewById(R.id.RecyclerView_SachBanChay);
        bookList = bookDAO.getTop10Books();
        adapter = new AdapterBookTop10(bookList);
        rvSach.setAdapter(adapter);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        rvSach.setLayoutManager(manager);
    }

}
