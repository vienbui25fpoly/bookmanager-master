package com.tiennvph06776.bookmanager.project.adapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;


import com.tiennvph06776.bookmanager.project.R;
import com.tiennvph06776.bookmanager.project.model.TypeBook;

import java.util.List;

public class AdapterTypeBookSpinner extends BaseAdapter {


    public Context context;
    public List<TypeBook> typeBooks;

    public AdapterTypeBookSpinner(Context context, List<TypeBook> typeBooks) {
        this.context = context;
        this.typeBooks = typeBooks;
    }

    @Override
    public int getCount() {
        return typeBooks.size();
    }

    @Override
    public Object getItem(int i) {
        return typeBooks.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(context).inflate(R.layout.type_book_spinner, viewGroup, false);
        TextView tvName;
        tvName = view.findViewById(R.id.tvName);
        tvName.setText(i + "|" + typeBooks.get(i).name);
        return view;
    }

}
