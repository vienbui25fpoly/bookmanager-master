package com.tiennvph06776.bookmanager.project.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
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
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Toast;


import com.tiennvph06776.bookmanager.project.R;
import com.tiennvph06776.bookmanager.project.adapter.AdapterUser;
import com.tiennvph06776.bookmanager.project.base.RecyclerViewClickListener;
import com.tiennvph06776.bookmanager.project.base.RecyclerViewTouchListener;
import com.tiennvph06776.bookmanager.project.model.User;
import com.tiennvph06776.bookmanager.project.sqlite.DatabaseHelper;
import com.tiennvph06776.bookmanager.project.sqlitedao.UserDAO;

import java.util.List;

public class ActivityUser extends AppCompatActivity{
    Toolbar toolbarNguoiDung;
    RecyclerView recyclerView;
    private List<User> userList;
    private AdapterUser adapter;
    private DatabaseHelper databaseHelper;
    private UserDAO userDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        databaseHelper = new DatabaseHelper(this);
        userDAO = new UserDAO(databaseHelper);

        toolbarNguoiDung = findViewById(R.id.toolbarNguoiDung);
        setSupportActionBar(toolbarNguoiDung);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbarNguoiDung.setTitleTextColor(Color.WHITE);
        toolbarNguoiDung.setTitle(getString(R.string.title_list_user_act));

      getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.RecyclerView_User);
        setData();

        // add sự kiện lich cho item
        recyclerView.addOnItemTouchListener(new RecyclerViewTouchListener(getApplicationContext(), recyclerView, new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                Toast.makeText(getApplicationContext(), userList.get(position).username+ " is clicked!", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onLongClick(View view, final int position) {

                PopupMenu popup = new PopupMenu(getApplicationContext(), view);
                popup.getMenuInflater().inflate(R.menu.poupup_menu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menu_edit:
                                OnEdit(userList.get(position));
                                Toast.makeText(getApplicationContext(), userList.get(position).username + " is edited!", Toast.LENGTH_SHORT).show();

                                break;
                            case R.id.menu_delete:

                                OnDelete(userList.get(position));
                                Toast.makeText(getApplicationContext(), userList.get(position).username + " is deleted!", Toast.LENGTH_SHORT).show();
                                break;

                        }
                        return true;
                    }
                });
                popup.show();//showing popup menu

            }
        }));
    }

    private void setData() {
        userList = userDAO.getAllUsers();
        adapter = new AdapterUser(userList);
        recyclerView.setAdapter(adapter);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add:
                showDialogAddUser();
                break;
            /*case R.id.doiMatKhau:
                showDialogDoiMatKhau();
                break;*/

        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }

    public void showDialogAddUser() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.dialog_add_user, null);
        dialog.setView(dialogView);
        final Dialog dialog1 = dialog.show();


        final EditText edUsername = (EditText) dialogView.findViewById(R.id.ed_username);
        final EditText edPassword = (EditText) dialogView.findViewById(R.id.ed_password);
        final EditText edRepassword = (EditText) dialogView.findViewById(R.id.ed_repassword);
        final EditText edPhoneNumber = (EditText) dialogView.findViewById(R.id.ed_phone_number);
        final EditText edFullname = (EditText) dialogView.findViewById(R.id.ed_fullname);
        Button btnAdd = (Button) dialogView.findViewById(R.id.btn_add);
        Button btnCancel = (Button) dialogView.findViewById(R.id.btn_cancel);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user=new User();
                user.username=edUsername.getText().toString().trim();
                if(user.username.length()==0){
                    edUsername.setError("User không được để trống");
                    return;
                }
                user.password=edPassword.getText().toString().trim();
                if(user.password.length()==0){
                    edPassword.setError("Password không được để trống");
                    return;
                }
                String repassword=edRepassword.getText().toString().trim();
                if (!user.password.equals(repassword)){
                    edRepassword.setError("Password nhập lại  khớp");
                    return;
                }
                user.sdt=edPhoneNumber.getText().toString().trim();
                user.name=edFullname.getText().toString().trim();

                userDAO.insertUser(user);
                dialog1.dismiss();
                setData();
                Toast.makeText(ActivityUser.this, "Thêm User thành công", Toast.LENGTH_SHORT).show();
            }
        });

    }

   /* public void showDialogDoiMatKhau() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.dialog_change_password, null);
        dialog.setView(dialogView);
        final Dialog dialog1 = dialog.show();
        Button doi = dialogView.findViewById(R.id.btnDoi_Doimatkhau);
        Button huy = dialogView.findViewById(R.id.btnHuy_Doimatkhau);
        huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        });
    }*/


    public void OnDelete(final User user) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Bạn có muốn xóa người dùng này không?");
        builder.setNegativeButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                userDAO.deleteUser(user.username);
                setData();

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


    public void OnEdit(final User user) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.dialog_edit_user, null);
        dialog.setView(dialogView);
        final Dialog dialog1 = dialog.show();
        Button edit = dialogView.findViewById(R.id.btn_edit);
        Button cancel = dialogView.findViewById(R.id.btn_cancel);
        final EditText edFullname = (EditText) dialogView.findViewById(R.id.ed_fullname);
        final EditText edPhoneNumber = (EditText) dialogView.findViewById(R.id.ed_phone_number);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user.sdt=edPhoneNumber.getText().toString().trim();
                user.name=edFullname.getText().toString().trim();
                userDAO.updateUser(user);
                setData();
                dialog1.dismiss();
                Toast.makeText(ActivityUser.this, "Chỉnh sửa thành công", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
