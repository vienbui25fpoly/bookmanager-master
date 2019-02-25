package com.tiennvph06776.bookmanager.project.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.tiennvph06776.bookmanager.project.R;
import com.tiennvph06776.bookmanager.project.model.TypeBook;

import java.util.List;

public class AdapterStyleBook extends RecyclerView.Adapter<AdapterStyleBook.ViewHolder> {
    private List<TypeBook> typeBookList;



    public AdapterStyleBook(List<TypeBook> typeBookList) {
        this.typeBookList = typeBookList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_style_book, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final TypeBook typeBook = typeBookList.get(position);
        holder.tvCode.setText(typeBook.id);
        holder.tvName.setText(typeBook.name);
    }

    @Override
    public int getItemCount() {
        return typeBookList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgAvatar;
        public TextView tvName;
        public TextView tvCode;

        public ViewHolder(View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.imgAvatar);
            tvCode = itemView.findViewById(R.id.tv_style_book_code);
            tvName = itemView.findViewById(R.id.tv_Name);

        }

    }
}
