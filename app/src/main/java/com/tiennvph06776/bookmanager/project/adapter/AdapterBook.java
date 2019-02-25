package com.tiennvph06776.bookmanager.project.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.tiennvph06776.bookmanager.project.R;

import com.tiennvph06776.bookmanager.project.model.Book;

import java.util.List;

public class AdapterBook extends RecyclerView.Adapter<AdapterBook.ViewHolder> {
    private List<Book> bookList;


    public AdapterBook(List<Book> bookList) {
        this.bookList = bookList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_book, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Book book = bookList.get(position);

        holder.tvMa.setText(book.id);
        holder.tvName.setText(book.typeID);
        holder.tvSoluong.setText("" + book.quality);

    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgAvatar;
        public TextView tvName;
        public TextView tvSoluong, tvMa;

        public ViewHolder(View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.imgAvatar_Sach);
            tvName = itemView.findViewById(R.id.tvTenSach_Sach);
            tvSoluong = itemView.findViewById(R.id.tvSoluong_Sach);
            tvMa = itemView.findViewById(R.id.tvMaSach_Sach);

        }

    }
}
