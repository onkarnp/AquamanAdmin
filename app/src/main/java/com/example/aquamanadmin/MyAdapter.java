package com.example.aquamanadmin;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{

    Context context;
    ArrayList<Orderinfo> list;

    public MyAdapter(Context context, ArrayList<Orderinfo> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.list_item,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Orderinfo order = list.get(position);
        holder.date.setText(order.getDate());
        holder.price.setText(order.getPrice());
        holder.summary.setText(order.getSummary());
        holder.status.setText(order.getStatus());
        holder.address.setText(order.getAddress());
        holder.name.setText(order.getName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView date,price,summary,status,address,name;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            date = itemView.findViewById(R.id.date);
            price = itemView.findViewById(R.id.price);
            summary = itemView.findViewById(R.id.summary);
            status = itemView.findViewById(R.id.status);
            address = itemView.findViewById(R.id.address);
            name = itemView.findViewById(R.id.name);
        }
    }
}

