package com.tiennvph06776.bookmanager.project.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Toast;


import com.tiennvph06776.bookmanager.project.R;
import com.tiennvph06776.bookmanager.project.adapter.AdapterStyleBook;
import com.tiennvph06776.bookmanager.project.base.RecyclerViewClickListener;
import com.tiennvph06776.bookmanager.project.base.RecyclerViewTouchListener;
import com.tiennvph06776.bookmanager.project.model.TypeBook;

import com.tiennvph06776.bookmanager.project.sqlite.DatabaseHelper;
import com.tiennvph06776.bookmanager.project.sqlitedao.TypeBookDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ActivityStyleBook extends AppCompatActivity{

    Toolbar toolbarTheLoai;
    FloatingActionButton floatingActionButton;
    RecyclerView recyclerView;
    private List<TypeBook> typeBookList;
    private AdapterStyleBook adapter;

    private DatabaseHelper databaseHelper;

    private TypeBookDAO typeBookDAO;
    public static String Hello = "Hello";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_style_book);

        databaseHelper = new DatabaseHelper(this);
        typeBookDAO = new TypeBookDAO(databaseHelper);


        toolbarTheLoai = findViewById(R.id.toolbarTheLoai);
        setSupportActionBar(toolbarTheLoai);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbarTheLoai.setTitleTextColor(Color.WHITE);
        toolbarTheLoai.setTitle("Thể Loại");
        floatingActionButton = findViewById(R.id.fbtn_TheLoai);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogAddStyleBook();
            }
        });
        recyclerView = findViewById(R.id.RecyclerView_TheLoai);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setData();
        // add sự kiện lich cho item
        recyclerView.addOnItemTouchListener(new RecyclerViewTouchListener(getApplicationContext(), recyclerView, new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {


            }

            @Override
            public void onLongClick(View view, final int position) {

                PopupMenu popup = new PopupMenu(getApplicationContext(), view);
                popup.getMenuInflater().inflate(R.menu.poupup_menu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menu_edit:
                                Edit(typeBookList.get(position));
                                setData();
                                break;
                            case R.id.menu_delete:
                                Delete(typeBookList.get(position).id);
                                break;

                        }
                        return true;
                    }
                });
                popup.show();//showing popup menu

            }
        }));

        //typeBookList = typeBookDAO.getAllTypeBooks();

    }

    private void setData() {
        typeBookList = typeBookDAO.getAllTypeBooks();
        adapter = new AdapterStyleBook(typeBookList);
        recyclerView.setAdapter(adapter);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.timkiem_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search_item:
                showDialogSearch();
                break;
        }
        return false;
    }

    public void showDialogAddStyleBook() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.dialog_add_style_book, null);
        dialog.setView(dialogView);
        final Dialog dialog1 = dialog.show();
        Button add = dialogView.findViewById(R.id.btn_style_book);
        Button cancel = dialogView.findViewById(R.id.btn_cancel);
        final EditText edStyleBookCode = (EditText) dialogView.findViewById(R.id.ed_style_book_code);
        final EditText edStyleName = (EditText) dialogView.findViewById(R.id.ed_style_name);
        final EditText edPosition = (EditText) dialogView.findViewById(R.id.ed_position);
        EditText edPainted = (EditText) dialogView.findViewById(R.id.ed_painted);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog1.dismiss();
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TypeBook typeBook=new TypeBook();
                typeBook.id=edStyleBookCode.getText().toString().trim();
                typeBook.name=edStyleName.getText().toString().trim();
                typeBook.position=edPosition.getText().toString().trim();
                typeBook.painted=edStyleBookCode.getText().toString().trim();
                if(typeBook.id.length()==0){
                    edStyleBookCode.setError("Mã thể loại không được để trống");
                    return;
                }
                if(typeBook.name.length()==0){
                    edStyleBookCode.setError("Tên thể loại không được để trống");
                    return;
                }
                if(typeBook.position.length()==0){
                    edStyleBookCode.setError("Vị thể loại không được để trống");
                    return;
                }
                if(typeBook.painted.length()==0){
                    edStyleBookCode.setError("Mô tả thể loại không được để trống");
                    return;
                }
                typeBookDAO.insertTypeBook(typeBook);
                dialog1.dismiss();
                setData();
                Toast.makeText(ActivityStyleBook.this, "Thêm Thể loại thành công", Toast.LENGTH_SHORT).show();

            }
        });

    }

    public void showDialogSearch() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.dialog_search_style_book, null);
        dialog.setView(dialogView);
        final Dialog dialog1 = dialog.show();

        Button tim = dialogView.findViewById(R.id.btnTimTheLoai);
        Button huy = dialogView.findViewById(R.id.btnHuyTimTheLoai);
        huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        });
    }


    public void Delete(final String IDtypeBook) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Bạn có muốn xóa thể loại này không?");
        builder.setNegativeButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                typeBookDAO.deleteTypeBook(IDtypeBook);
                setData();
                Toast.makeText(ActivityStyleBook.this, "Xóa thành thành công", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setPositiveButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }


    public void Edit(TypeBook typeBook) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.dialog_edit_style_book, null);
        dialog.setView(dialogView);
        final Dialog dialog1 = dialog.show();
        Button sua = dialogView.findViewById(R.id.btnSua_EditTheLoai);
        Button huy = dialogView.findViewById(R.id.btnHuy_EditTheLoai);
        huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        });
    }
}
