package com.example.aquamanadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UpdateProfile extends AppCompatActivity {

    private EditText updateFullName,updateHomeAddress,updateMobileNo,updateEmail,fieldEmail,fieldPassword;
    private ProgressDialog loadingBar;
    //Initiation of Firebase attributes
    private FirebaseAuth mAuth;
    FirebaseDatabase rootNode;
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);     //removes title bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();    //removes action bar
        setContentView(R.layout.activity_update_profile);
        updateFullName = (EditText) findViewById(R.id.updateFullName);
        updateHomeAddress = (EditText) findViewById(R.id.updateHomeAddress);
        updateMobileNo = (EditText) findViewById(R.id.updateMobileNo);
        updateEmail = (EditText) findViewById(R.id.updateEmail);
        fieldEmail = (EditText) findViewById(R.id.fieldEmail);
        fieldPassword = (EditText) findViewById(R.id.fieldPassword);
        Button updateButton = (Button) findViewById(R.id.updateButton);
        loadingBar = new ProgressDialog(this);

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCredentials();
            }
        });
    }


    //function for checking credentials whether inserted in correct format
    public void checkCredentials(){
        String fullName = updateFullName.getText().toString();
        String homeAddress = updateHomeAddress.getText().toString();
        String mobileNo = updateMobileNo.getText().toString();

        if(fullName.isEmpty() || fullName.length()<5)
        {
            showError(updateFullName, "Your Name is not Valid.");
        }
        else if (homeAddress.isEmpty() || homeAddress.length()<5)
        {
            showError(updateHomeAddress, "Your address is not valid.");
        }
        else if (mobileNo.length() != 10)
        {
            showError(updateMobileNo, "Your mobile no. is invalid.");
        }
        else{
            updateUser();
        }
    }


    //function for registration
    public void updateUser(){
        String fullName = updateFullName.getText().toString();
        String homeAddress = updateHomeAddress.getText().toString();
        String mobileNo = updateMobileNo.getText().toString();
        String newEmail = updateEmail.getText().toString();
        String email = fieldEmail.getText().toString();
        String password = fieldPassword.getText().toString();

        if(!newEmail.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(newEmail).matches())
        {
            if(!password.isEmpty())
            {
                loadingBar.setTitle("Update Profile");
                loadingBar.setMessage("Please wait while profile is being updated.");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();
                rootNode = FirebaseDatabase.getInstance();
                mAuth= FirebaseAuth.getInstance();
                FirebaseUser mUser = mAuth.getCurrentUser();
                String userID = mAuth.getCurrentUser().getUid();
                reference = rootNode.getReference("admins");
                //Update email in Firebase Authentication
                AuthCredential credential = EmailAuthProvider.getCredential(email,password);
                assert mUser != null;
                mUser.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
                            assert mUser != null;
                            mUser.updateEmail(newEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        //get all the values
                                        Admin admin = new Admin(fullName,homeAddress,mobileNo,newEmail);
                                        reference.child(userID).setValue(admin);
                                        loadingBar.dismiss();
                                        Toast.makeText(UpdateProfile.this, "Profile Updated."+ "Your new email is "+newEmail, Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(getApplicationContext(),DashboardAdmin.class);
                                        startActivity(new Intent(UpdateProfile.this,DashboardAdmin.class));
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    loadingBar.dismiss();
                                    Toast.makeText(UpdateProfile.this, "Some error occurred, unable to update.", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        loadingBar.dismiss();
                        Toast.makeText(UpdateProfile.this, "Some error occurred, unable to update."+ "Please check your credentials.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            else
                showError(fieldPassword, "Empty fields are not allowed.");
        }
        else if(newEmail.isEmpty())
        {
            showError(updateEmail,"Empty fields are not allowed.");
        }
        else{
            showError(updateEmail,"Please Enter correct Email.");
        }
    }

    //Function to show error message when input is not in correct format
    public void showError(EditText input, String s)
    {
        input.setError(s);
        input.requestFocus();
    }
}