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
import android.widget.Spinner;
import android.widget.Toast;

import com.tiennvph06776.bookmanager.project.R;
import com.tiennvph06776.bookmanager.project.adapter.AdapterBook;
import com.tiennvph06776.bookmanager.project.adapter.AdapterTypeBookSpinner;
import com.tiennvph06776.bookmanager.project.base.RecyclerViewClickListener;
import com.tiennvph06776.bookmanager.project.base.RecyclerViewTouchListener;
import com.tiennvph06776.bookmanager.project.model.Book;
import com.tiennvph06776.bookmanager.project.model.TypeBook;
import com.tiennvph06776.bookmanager.project.sqlite.DatabaseHelper;
import com.tiennvph06776.bookmanager.project.sqlitedao.BookDAO;
import com.tiennvph06776.bookmanager.project.sqlitedao.TypeBookDAO;

import java.util.ArrayList;
import java.util.List;

public class ActivityBook extends AppCompatActivity{
    Toolbar toolbarBook;
    FloatingActionButton floatingActionButton;
    RecyclerView recyclerView;
    private List<Book> bookList;
    private AdapterBook adapterBook;
    private DatabaseHelper databaseHelper;
    private BookDAO bookDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        databaseHelper = new DatabaseHelper(this);
        bookDAO = new BookDAO(databaseHelper);

        toolbarBook = findViewById(R.id.toolbarSach);
        setSupportActionBar(toolbarBook);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbarBook.setTitleTextColor(Color.WHITE);
        toolbarBook.setTitle("Sách");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        floatingActionButton = findViewById(R.id.fbtn_Sach);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogThemSach();
            }
        });

        recyclerView = findViewById(R.id.RecyclerView_Sach);
        bookList = new ArrayList<>();

        bookList = bookDAO.getAllBooks();

        adapterBook = new AdapterBook(bookList);
        recyclerView.setAdapter(adapterBook);


        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        // add sự kiện lich cho item
        recyclerView.addOnItemTouchListener(new RecyclerViewTouchListener(getApplicationContext(), recyclerView, new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
               /* Toast.makeText(getApplicationContext(), userList.get(position).username+ " is clicked!", Toast.LENGTH_SHORT).show();*/

            }

            @Override
            public void onLongClick(View view, final int position) {

                PopupMenu popup = new PopupMenu(getApplicationContext(), view);
                popup.getMenuInflater().inflate(R.menu.poupup_menu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menu_edit:
                                Edit(bookList.get(position).id);
                                break;
                            case R.id.menu_delete:
                                Delete(bookList.get(position).id,position);
                                break;

                        }
                        return true;
                    }
                });
                popup.show();//showing popup menu

            }
        }));


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
                showDialogSearchSach();
                break;
        }
        return false;
    }

    public void showDialogThemSach() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.dialog_add_book, null);
        dialog.setView(dialogView);
        final Dialog dialog1 = dialog.show();
        Button add = dialogView.findViewById(R.id.btn_add);
        Button cancel = dialogView.findViewById(R.id.btn_cancel);
        final EditText edMaSachThemSach;
        EditText edTenSachThemSach;
        final Spinner spTheLoaiThemSach;
        final EditText edTacGiaThemSach;
        final EditText edNXBThemSach;
        final EditText edGiaThemSach;
        final EditText edSoluongThemSach;

        edMaSachThemSach = dialogView.findViewById(R.id.edMaSach_ThemSach);
        edTenSachThemSach = dialogView.findViewById(R.id.edTenSach_ThemSach);
        spTheLoaiThemSach = dialogView.findViewById(R.id.spTheLoai_ThemSach);
        edTacGiaThemSach = dialogView.findViewById(R.id.edTacGia_ThemSach);
        edNXBThemSach = dialogView.findViewById(R.id.edNXB_ThemSach);
        edGiaThemSach = dialogView.findViewById(R.id.edGia_ThemSach);
        edSoluongThemSach = dialogView.findViewById(R.id.edSoluong_ThemSach);


        List<TypeBook> typeBooks = new TypeBookDAO(databaseHelper).getAllTypeBooks();
        Log.e("SIZE", typeBooks.size() + "");
        spTheLoaiThemSach.setAdapter(new AdapterTypeBookSpinner(this, typeBooks));

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Book book = new Book();
                book.id = edMaSachThemSach.getText().toString().trim();
                book.author = edTacGiaThemSach.getText().toString().trim();
                book.producer = edNXBThemSach.getText().toString().trim();
                book.price = Long.parseLong(edGiaThemSach.getText().toString().trim());
                book.quality = Integer.parseInt(edSoluongThemSach.getText().toString().trim());
                book.typeID = ((TypeBook) spTheLoaiThemSach.getSelectedItem()).id;

                long result = bookDAO.insertBook(book);

                if (result > 0) {
                    bookList.add(book);
                    adapterBook.notifyDataSetChanged();
                    Toast.makeText(ActivityBook.this, "Thêm sách thành công!", Toast.LENGTH_LONG).show();
                    dialog1.dismiss();
                } else {
                    Toast.makeText(ActivityBook.this, "Thêm sách không thành công!", Toast.LENGTH_LONG).show();

                }


            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        });
    }

    public void showDialogSearchSach() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.dialog_search_book, null);
        dialog.setView(dialogView);
        final Dialog dialog1 = dialog.show();

        Button tim = dialogView.findViewById(R.id.btnTimSach);
        Button huy = dialogView.findViewById(R.id.btnHuyTimSach);
        huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        });
    }


    public void Delete(final String id, final int position) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Bạn có muốn xóa sách này không?");
        builder.setNegativeButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                bookDAO.delBook(id);
                Toast.makeText(ActivityBook.this, "Xóa sách thành công", Toast.LENGTH_LONG).show();
                bookList.remove(position);
                adapterBook.notifyDataSetChanged();

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


    public void Edit(final String id) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.dialog_edit_book, null);
        dialog.setView(dialogView);
        final Dialog dialog1 = dialog.show();
        Button edit = dialogView.findViewById(R.id.btn_edit);
        Button cancel = dialogView.findViewById(R.id.btn_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bookDAO.delBook(id);
                Toast.makeText(ActivityBook.this, "Sửa thành sách thành công", Toast.LENGTH_LONG).show();
            }
        });

    }
}
