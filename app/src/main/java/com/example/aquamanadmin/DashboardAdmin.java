package com.example.aquamanadmin;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class DashboardAdmin extends AppCompatActivity {
    public CardView userCard,orderCard,logOutCard,profileCard,historyCard,aboutUsCard;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);     //removes tite bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();    //removes action bar
        setContentView(R.layout.activity_dashboard_admin);
        orderCard = (CardView)findViewById(R.id.orderCard);
        orderCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),OrderActivity.class);
                startActivity(intent);
            }
        });
        logOutCard = (CardView)findViewById(R.id.logOutCard);
        logOutCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(DashboardAdmin.this);
                alertDialogBuilder.setTitle("Confirm Exit..!!");
                alertDialogBuilder.setIcon(R.drawable.ic_exit);
                alertDialogBuilder.setMessage("Are you sure ?");
                alertDialogBuilder.setCancelable(false);
                alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getApplicationContext(),LogInActivity.class);
                        mAuth.signOut();
                        startActivity(intent);
                        Toast.makeText(DashboardAdmin.this,"Logged out successfully",Toast.LENGTH_LONG).show();
                        finish();
                    }
                });
                alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(DashboardAdmin.this,"Cancelled..",Toast.LENGTH_LONG).show();
                    }
                });
                alertDialogBuilder.show();
            }
        });
        profileCard = (CardView)findViewById(R.id.profileCard);
        profileCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Profile.class);
                startActivity(intent);
            }
        });
        historyCard=(CardView) findViewById(R.id.historyCard);
        historyCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),HistoryActivity.class);
                startActivity(intent);
            }
        });
        userCard=findViewById(R.id.UserCard);
        userCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getApplicationContext(),UserActivity.class);
                startActivity(intent);
            }
        });
        aboutUsCard= (CardView)findViewById(R.id.aboutUsCard);
        aboutUsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),AboutUs.class);
                startActivity(intent);
            }
        });
};
}