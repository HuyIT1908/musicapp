package com.example.musicapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicapp.Models.DanhSachPhat;
import com.example.musicapp.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterDanhSachphat extends RecyclerView.Adapter<AdapterDanhSachphat.View_holder> {
    private Context context;
    private List<DanhSachPhat> list;
    private onClickListener listenner;
    private onClickListener DeleteListenner;

    public AdapterDanhSachphat(Context context, List<DanhSachPhat> list){
        this.context = context;
        this.list = list;
    }
    public interface onClickListener{
        void onClick(int possion);
    }
    public void onClickItemListener(onClickListener listener){
        this.listenner= listener;
    }
    public void onClickDeleteListener(onClickListener listener){
        this.DeleteListenner= listener;
    }

    @NonNull
    @Override
    public View_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(context).inflate(R.layout.danhsachphat_item,null);
       return new View_holder(view, listenner, DeleteListenner);
    }

    @Override
    public void onBindViewHolder(@NonNull View_holder holder, int position) {
        holder.tenDanhSachPhat.setText(String.valueOf(list.get(position).getTendanhsachPhat()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class View_holder extends RecyclerView.ViewHolder {
        TextView tenDanhSachPhat;
        ImageView imgDelete;
        public View_holder(@NonNull View itemView, onClickListener listenner, onClickListener deleteListenner) {
            super(itemView);
            tenDanhSachPhat =(TextView) itemView.findViewById(R.id.tv_tenDanhSachPhat);
            imgDelete = (ImageView) itemView.findViewById(R.id.img_deleteDanhSachPhat);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                if (listenner!= null){
                listenner.onClick(getAdapterPosition());
                }
                }
            });

            imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                if (deleteListenner != null){
                deleteListenner.onClick(getAdapterPosition());
                }
                }
            });
        }
    }

    public void refresh(ArrayList<DanhSachPhat> arrayList){
        list.clear();
        list.addAll(arrayList);
        if (arrayList != null){
            notifyDataSetChanged();
        }
    }
}
