package com.tiennvph06776.bookmanager.project.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.tiennvph06776.bookmanager.project.base.RecyclerViewClickListener;
import com.tiennvph06776.bookmanager.project.base.RecyclerViewTouchListener;

import com.tiennvph06776.bookmanager.project.sqlite.Constant;
import com.tiennvph06776.bookmanager.project.R;
import com.tiennvph06776.bookmanager.project.adapter.AdapterBill;
import com.tiennvph06776.bookmanager.project.model.Bill;
import com.tiennvph06776.bookmanager.project.sqlite.DatabaseHelper;
import com.tiennvph06776.bookmanager.project.sqlitedao.BillDAO;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class ActivityBill extends AppCompatActivity implements Constant {
    Toolbar toolbarBill;
    RecyclerView recyclerView;
    private List<Bill> billList;
    private AdapterBill adapterBill;
    FloatingActionButton btnAddBill;
    TextView tvDate;
    long datePicker = -1;

    DatabaseHelper databaseHelper;
    BillDAO billDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);

        databaseHelper = new DatabaseHelper(this);
        billDAO = new BillDAO(databaseHelper);


        toolbarBill = findViewById(R.id.toolbarHoaDon);
        setSupportActionBar(toolbarBill);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        toolbarBill.setTitleTextColor(Color.WHITE);
        toolbarBill.setTitle("Hóa Đơn");
        btnAddBill = findViewById(R.id.fbtn_HoaDon);
        btnAddBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogHoaDon();
            }
        });

       /* toolbarHoaDon.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });*/
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.lvListBills);
        billList = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            Bill bill = new Bill(i + new Random().nextInt(1000) + "", System.currentTimeMillis());
            //billDAO.insertBill(bill);
        }

        // dao nguoc vi tri

        billList = billDAO.getAllBills();

        Collections.reverse(billList);

        adapterBill = new AdapterBill(billDAO, billList);
        recyclerView.setAdapter(adapterBill);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        // add sự kiện lich cho item
        recyclerView.addOnItemTouchListener(new RecyclerViewTouchListener(getApplicationContext(), recyclerView, new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                Bill bill= new Bill();
                bill.id=billList.get(position).id;
                bill.date=billList.get(position).date;
                addBillDetail(bill);
            }

            @Override
            public void onLongClick(View view, final int position) {

                PopupMenu popup = new PopupMenu(getApplicationContext(), view);
                popup.getMenuInflater().inflate(R.menu.poupup_menu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menu_edit:
                                break;
                            case R.id.menu_delete:
                                Delete(billList.get(position).id,position);
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
                showDialogSearchHoaDon();
                break;
        }
        return false;
    }

    public void showDialogSearchHoaDon() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.dialog_search_bill, null);
        dialog.setView(dialogView);
        final Dialog dialog1 = dialog.show();
        Button tim = dialogView.findViewById(R.id.btnTimHoaDon);
        Button huy = dialogView.findViewById(R.id.btnHuyTimHoaDon);
        huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        });
    }


    public void showDialogHoaDon() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.dialog_bill, null);
        dialog.setView(dialogView);
        final Dialog dialog1 = dialog.show();
        Button them = dialogView.findViewById(R.id.btnThem_ThemHoaDon);
        final EditText edMaHoaDon_HoaDon = dialogView.findViewById(R.id.edMaHoaDon_HoaDon);


        them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String billID = edMaHoaDon_HoaDon.getText().toString().trim();
                if (billID.length() > 6) {
                    edMaHoaDon_HoaDon.setError(getString(R.string.notify_max_bill_id));
                    return;
                }
                if (datePicker < 0) return;
                Bill bill = new Bill(billID, datePicker);
                long result = billDAO.insertBill(bill);
                if (result > 0) {

                    billList.add(bill);
                    adapterBill.notifyDataSetChanged();
                    dialog1.dismiss();
                    Toast.makeText(getApplicationContext(), "Thêm hóa đơn thành công!", Toast.LENGTH_LONG).show();

                } else {

                    Toast.makeText(getApplicationContext(), "Thêm hóa đơn thất bại!", Toast.LENGTH_LONG).show();

                }


            }
        });
        Button huy = dialogView.findViewById(R.id.btnHuy_ThemHoaDon);
        Button chon = dialogView.findViewById(R.id.btnChonNgay);
        tvDate = dialogView.findViewById(R.id.tvChonNgay);
        huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        });
        chon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

    }

    public void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        // thiet lap thong tin cho date picker

        final DatePickerDialog datePicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Integer yy = year;
                Integer mm = month;
                Integer dd = dayOfMonth;


                Calendar calendar = Calendar.getInstance();

                calendar.set(year, month, dayOfMonth);

                //
                long startTime = calendar.getTimeInMillis();

                ActivityBill.this.datePicker = calendar.getTimeInMillis();

                tvDate.setText(new Date(startTime).toString());


            }
        }, year, month, day);

        datePicker.show();
    }


    public void Delete(final String id, final int position) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Bạn có muốn xóa hóa đơn này không?");
        builder.setNegativeButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                billDAO.deleteBill(id);
                billList.remove(position);
                adapterBill.notifyDataSetChanged();
                Toast.makeText(getApplicationContext(), billList.get(position).id + " is deleted!", Toast.LENGTH_SHORT).show();
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


    public void addBillDetail(Bill bill) {
        Intent intent = new Intent(this, ActivityBillDetail.class);
        intent.putExtra(B_ID, bill.id);
        startActivity(intent);

    }
}
