package com.example.aquamanadmin;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {
    private EditText name;
    RecyclerView recyclerView;
    MyAdapter myAdapter;
    ArrayList<Orderinfo> list;
    String key;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);     //removes title bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();                    //hides action bar
        setContentView(R.layout.activity_history);
        name=findViewById(R.id.name);
        Button order=findViewById(R.id.historyButton);
        recyclerView=findViewById(R.id.historyList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list= new ArrayList<>();
        myAdapter = new MyAdapter(this,list);
        recyclerView.setAdapter(myAdapter);


        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Name=name.getText().toString();
                DatabaseReference ref= FirebaseDatabase.getInstance().getReference("users");
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                        for(DataSnapshot snapshot:datasnapshot.getChildren())
                        {
                           if(String.valueOf(snapshot.child("fullName").getValue()).equals(Name))
                           {
                               key=snapshot.getKey();
                               break;
                           }
                        }
                        myAdapter.notifyDataSetChanged();

                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                DatabaseReference reference= FirebaseDatabase.getInstance().getReference("orders");
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                        list.clear();
                        for(DataSnapshot snapshot:datasnapshot.getChildren())
                        {
                            for(DataSnapshot snap:snapshot.getChildren()) {
                                String n=snap.getKey();
                                if(n.equals(key))
                                {
                                    Orderinfo info=snap.getValue(Orderinfo.class);
                                    String s = "Name:" + info.getName() + "\nDate:" + info.getDate() + "\n" + "Price:" + info.getPrice() + "\n" + "Status:" + info.getStatus() + "\n" + "Summary:" + info.getSummary() + "\nAddress:" + info.getAddress();
                                    list.add(info);

                                }
                            }

                        }
                        myAdapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });


    }
}
