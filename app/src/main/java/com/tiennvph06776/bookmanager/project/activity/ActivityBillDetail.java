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
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.tiennvph06776.bookmanager.project.base.RecyclerViewClickListener;
import com.tiennvph06776.bookmanager.project.base.RecyclerViewTouchListener;
import com.tiennvph06776.bookmanager.project.model.Bill;
import com.tiennvph06776.bookmanager.project.sqlite.Constant;
import com.tiennvph06776.bookmanager.project.R;
import com.tiennvph06776.bookmanager.project.adapter.AdapterBookSpinner;
import com.tiennvph06776.bookmanager.project.adapter.AdapterBillDetail;
import com.tiennvph06776.bookmanager.project.model.BillDetail;
import com.tiennvph06776.bookmanager.project.model.Book;
import com.tiennvph06776.bookmanager.project.sqlite.DatabaseHelper;
import com.tiennvph06776.bookmanager.project.sqlitedao.BillDetailDAO;
import com.tiennvph06776.bookmanager.project.sqlitedao.BookDAO;

import java.util.ArrayList;
import java.util.List;

public class ActivityBillDetail extends AppCompatActivity implements Constant {
    Toolbar toolbarBillDetail;
    FloatingActionButton floatingActionButton;
    RecyclerView recyclerView;
    private List<BillDetail> listBilDetail;
    private AdapterBillDetail adapter;
    private BillDetailDAO billDetailDAO;
    private String billID;
    private TextView tvTotal;




    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_detail);
        databaseHelper = new DatabaseHelper(this);
        tvTotal = (TextView) findViewById(R.id.tv_total);
        billDetailDAO = new BillDetailDAO(databaseHelper);
        // nhận giá trị từ màn hình bill
        billID = getIntent().getStringExtra(B_ID);
        setMany(billID);
        toolbarBillDetail = findViewById(R.id.toolbarHoaDonChiTiet);
        setSupportActionBar(toolbarBillDetail);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbarBillDetail.setTitleTextColor(Color.WHITE);
        toolbarBillDetail.setTitle("Hóa Đơn Chi Tiết");
        /*toolbarHoaDonChiTiet.setNavigationIcon(R.drawable.undo);*/
        floatingActionButton = findViewById(R.id.fbtn_HoaDonChiTiet);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogBillDetail();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView = findViewById(R.id.recyclerView_Bill_Detail);
        listBilDetail = new ArrayList<>();

        listBilDetail = billDetailDAO.getAllBillDetailByBillID(billID);
        adapter = new AdapterBillDetail(listBilDetail);
        recyclerView.setAdapter(adapter);

        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
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
                                break;
                            case R.id.menu_delete:
                                Delete(listBilDetail.get(position).id);
                                break;

                        }
                        return true;
                    }
                });
                popup.show();//showing popup menu

            }
        }));
    }

    public void showDialogBillDetail() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.dialog_bill_detail, null);
        dialog.setView(dialogView);
        final Dialog dialog1 = dialog.show();
        final Spinner spBookID;
        final EditText edtQuality;

        spBookID = dialogView.findViewById(R.id.spBookID);
        edtQuality = dialogView.findViewById(R.id.edtQuality);
        List<Book> books = new BookDAO(databaseHelper).getAllBooks();
        spBookID.setAdapter(new AdapterBookSpinner(this, books));
        // sự kiến nút thêm hóa đơn chi tiết
        Button add = dialogView.findViewById(R.id.btnThem_HoaDonChiTiet);
        Button huy = dialogView.findViewById(R.id.btnHuy_HoaDonChiTiet);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                BillDetail billDetail = new BillDetail();
                billDetail.billID = ActivityBillDetail.this.billID;
                billDetail.bookID = ((Book) spBookID.getSelectedItem()).id;
                billDetail.quality = Integer.parseInt(edtQuality.getText().toString().trim());

                BillDetailDAO billDetailDAO = new BillDetailDAO(databaseHelper);
                long result = billDetailDAO.insertBillDetail(billDetail);

                if (result > 0) {
                    listBilDetail.add(billDetail);
                    adapter.notifyDataSetChanged();
                    setMany(ActivityBillDetail.this.billID);
                    Toast.makeText(getApplicationContext(), "Thêm chi tiết hóa đơn thành công!", Toast.LENGTH_LONG).show();
                    dialog1.dismiss();
                } else {
                    Toast.makeText(getApplicationContext(), "Thêm chi tiết hóa đơn không thành công!", Toast.LENGTH_LONG).show();
                }
            }
        });

        huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        });
    }

    public void Delete(final String id) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Bạn có muốn xóa hóa đơn này không?");
        builder.setNegativeButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                billDetailDAO.deleteBillDetail(id);
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
    public  void setMany(String BillId){
        String tong=String.valueOf(billDetailDAO.totalBill(BillId));
        tvTotal.setText(tong+" Đ");
    }
}
