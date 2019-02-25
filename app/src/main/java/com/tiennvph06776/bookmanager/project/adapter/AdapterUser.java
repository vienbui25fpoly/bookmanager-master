package com.tiennvph06776.bookmanager.project.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.tiennvph06776.bookmanager.project.R;
import com.tiennvph06776.bookmanager.project.model.User;

import java.util.List;

public class AdapterUser extends RecyclerView.Adapter<AdapterUser.ViewHolder> {
    private List<User> userList;


    public AdapterUser(List<User> userList) {
        this.userList = userList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_user, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final User st = userList.get(position);
        holder.tvName.setText(st.name);
        holder.tvSDT.setText(st.sdt);

    }

    @Override
    public int getItemCount() {

        if (userList == null) return 0;

        return userList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgAvatar;
        public TextView tvName;
        public TextView tvSDT;

        public ViewHolder(View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.imgAvatar_NguoiDung);
            tvSDT = itemView.findViewById(R.id.tvSDT_NguoiDung);
            tvName = itemView.findViewById(R.id.tvName_NguoiDung);
        }

    }
}
