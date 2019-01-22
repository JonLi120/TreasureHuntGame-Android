package com.unikfunlearn.treasurehuntgame;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private List<String> list = new ArrayList<>();
    private int clickPos = -1;
    private String clickLab = "";

    MyAdapter(List<String> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.setItemLab(list.get(position));
        if (clickPos != -1) {
            holder.setImgVisibility(clickPos == position);
        }
        holder.itemView.setOnClickListener(view -> {
            clickPos = position;
            clickLab = holder.getItemLab().getText().toString();
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return list == null? 0 : list.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_img)
        ImageView itemImg;
        @BindView(R.id.item_lab)
        TextView itemLab;

        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        void setItemLab(String str) {
            itemLab.setText(str);
        }

        TextView getItemLab() {
            return itemLab;
        }

        void setImgVisibility(boolean b){
            if (b) {
                itemImg.setVisibility(View.VISIBLE);
            } else {
                itemImg.setVisibility(View.INVISIBLE);
            }
        }
    }

    public int getClickPos() {
        return clickPos;
    }

    public String getClickLab(){
        return clickLab;
    }
}

