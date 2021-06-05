package com.example.aquamanadmin;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
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
    private Button updateProfileButton;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);     //removes titLe bar
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
        updateProfileButton = (Button) findViewById(R.id.updateProfileButton);
        loadingBar = new ProgressDialog(this);
        loadingBar.setTitle("Admin Profile");
        loadingBar.setMessage("Fetching your profile details, "+ "Please wait for a moment...");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

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
                    loadingBar.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                loadingBar.dismiss();
                Toast.makeText(Profile.this, "Something went wrong :(", Toast.LENGTH_SHORT).show();
            }
        });

        updateProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),UpdateProfile.class);
                startActivity(intent);
            }
        });
    }
}