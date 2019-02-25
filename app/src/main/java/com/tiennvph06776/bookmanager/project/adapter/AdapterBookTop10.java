package com.tiennvph06776.bookmanager.project.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.tiennvph06776.bookmanager.project.R;
import com.tiennvph06776.bookmanager.project.model.SelectTop10Book;

import java.util.List;

public class AdapterBookTop10 extends RecyclerView.Adapter<AdapterBookTop10.ViewHolder> {
    private List<SelectTop10Book> bookList;

    public AdapterBookTop10(List<SelectTop10Book> bookList) {
        this.bookList = bookList;
    }

    @NonNull
    @Override
    public AdapterBookTop10.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_top10book, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterBookTop10.ViewHolder holder, int position) {
        SelectTop10Book st = bookList.get(position);

    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgAvatar;
        public TextView tvName;
        public TextView tvSoluong;

        public ViewHolder(View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.imgAvatar_SachBanChay);
            tvName = itemView.findViewById(R.id.tv_book_id);
            tvSoluong = itemView.findViewById(R.id.tv_soluong);
        }

    }
}
