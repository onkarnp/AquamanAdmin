package com.example.aquamanadmin;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setTitle("Order Details..!!");
                alertDialogBuilder.setIcon(R.drawable.ic_flag);
                alertDialogBuilder.setMessage("Change Delivery status ?");
                alertDialogBuilder.setCancelable(false);
                alertDialogBuilder.setPositiveButton("Delivered", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ///Function to change status in the database
                        setOrderStatus(holder,"Delivered");
                        holder.status.setText("Delivered");
                        Toast.makeText(context,"Status changed to delivered.",Toast.LENGTH_LONG).show();
                    }
                }).setNegativeButton("Pending", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ///Function  to change status in the database
                        setOrderStatus(holder,"Pending");
                        holder.status.setText("Pending");
                        Toast.makeText(context,"Status changed to delivered.",Toast.LENGTH_LONG).show();
                    }
                });
                alertDialogBuilder.show();
            }
        });


    }

    public void setOrderStatus(MyViewHolder holder,String s){
        String nm = holder.name.toString();
        String dt = holder.date.toString();
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("orders");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    for(DataSnapshot snap:dataSnapshot.getChildren()){
                        String n= String.valueOf(snap.child("name").getValue());
                        String d= String.valueOf(snap.child("date").getValue());
                        if(nm.equals(n) & dt.equals(d)){
                            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("orders");
                            if(s.equals("Delivered")) {
                                ref.child(d).child(snap.getKey()).child("status").setValue(s);
                            }
                            else {
                                ref.child(d).child(snap.getKey()).child("status").setValue("Pending");
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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

