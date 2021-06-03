package com.example.aquamanadmin;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class Profile extends AppCompatActivity {

    private TextView fullNameTextView,homeAddressTextView,phoneNumberTextView,eMailTextView;
    private DatabaseReference reference;
    private FirebaseUser user;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);     //removes tite bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_profile);

        reference = FirebaseDatabase.getInstance().getReference("admins");
        user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();
        fullNameTextView = (TextView) findViewById(R.id.fullName);
        homeAddressTextView = (TextView) findViewById(R.id.homeAddress);
        phoneNumberTextView = (TextView) findViewById(R.id.phoneNumber);
        eMailTextView = (TextView) findViewById(R.id.email);

        reference.child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Admin userprofile = snapshot.getValue(Admin.class);
                if(userprofile != null){
                    String fullName = userprofile.getFullName();
                    String homeAddress = userprofile.getHomeAddress();
                    String phoneNumber = userprofile.getMobileNo();
                    String eMail = userprofile.getEmail();
                    fullNameTextView.setText(fullName);
                    homeAddressTextView.setText(homeAddress);
                    phoneNumberTextView.setText(phoneNumber);
                    eMailTextView.setText(eMail);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Profile.this, "Something went wrong :(", Toast.LENGTH_SHORT).show();
            }
        });
    }
}