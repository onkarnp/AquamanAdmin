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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {


    private static final String USER ="Admin";
    private EditText inputFullName,inputHomeAddress,inputMobileNo,inputEmail,inputPassword,inputConfirmPassword;
    private Button signUpButton;
    private ProgressDialog loadingBar;
    private FirebaseAuth mAuth;
    FirebaseDatabase rootNode;
    DatabaseReference reference;


    public SignUpActivity(EditText inputFullName, EditText inputHomeAddress, EditText inputMobileNo, EditText inputEmail, EditText inputPassword, EditText inputConfirmPassword) {
        this.inputFullName = inputFullName;
        this.inputHomeAddress = inputHomeAddress;
        this.inputMobileNo = inputMobileNo;
        this.inputEmail = inputEmail;
        this.inputPassword = inputPassword;
        this.inputConfirmPassword = inputConfirmPassword;
    }
    public SignUpActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);     //removes title bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_sign_up);

        TextView btn = findViewById(R.id.logInText);
        inputFullName = findViewById(R.id.inputFullName);
        inputHomeAddress = findViewById(R.id.inputHomeAddress);
        inputMobileNo = findViewById(R.id.inputMobileNo);
        inputEmail = findViewById(R.id.inputEmail);
        inputPassword = findViewById(R.id.inputPassword);
        inputConfirmPassword = findViewById(R.id.inputConfirmPassword);
        loadingBar = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        signUpButton = findViewById(R.id.SignupButton);

        signUpButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                checkCredentials();
            }
        });


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this,LogInActivity.class));

            }
        });
    }


    //function for checking credentials whether inserted in correct format
    public void checkCredentials(){
        String fullName = inputFullName.getText().toString();
        String homeAddress = inputHomeAddress.getText().toString();
        String mobileNo = inputMobileNo.getText().toString();

        if(fullName.isEmpty() || fullName.length()<5)
        {
            showError(inputFullName, "Your Name is not Valid.");
        }
        else if (homeAddress.isEmpty() || homeAddress.length()<5)
        {
            showError(inputHomeAddress, "Your address is not valid.");
        }
        else if (mobileNo.length() != 10)
        {
            showError(inputMobileNo, "Your mobile no. is invalid.");
        }
        else{
            createUser();
        }
    }


    //function for registration
    public void createUser(){
        String fullName = inputFullName.getText().toString();
        String homeAddress = inputHomeAddress.getText().toString();
        String mobileNo = inputMobileNo.getText().toString();
        String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();
        String confirmPassword = inputConfirmPassword.getText().toString();

        if(!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            if(!password.isEmpty() && confirmPassword.equals(password)){
                loadingBar.setTitle("Create Account");
                loadingBar.setMessage("Please wait while account is being created.");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();
                mAuth.createUserWithEmailAndPassword(email,password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    rootNode = FirebaseDatabase.getInstance();
                                    String userID = mAuth.getCurrentUser().getUid();
                                    reference = rootNode.getReference("admins");
                                    //get all the values
                                    Admin admin = new Admin(fullName,homeAddress,mobileNo,email);
                                    reference.child(userID).setValue(admin);
                                    Toast.makeText(SignUpActivity.this, "Registered successfully !!", Toast.LENGTH_SHORT).show();
                                    loadingBar.dismiss();
                                    startActivity(new Intent(SignUpActivity.this, LogInActivity.class));
                                    finish();
                                }else
                                {
                                    loadingBar.dismiss();
                                    Toast.makeText(SignUpActivity.this, "Registration error :(", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
            else {
                if(password.isEmpty()) {
                    showError(inputPassword, "Empty fields are not allowed.");
                }
                else {
                    showError(inputConfirmPassword, "Password is not matching.");
                }
            }
        }
        else if(email.isEmpty())
        {
            showError(inputEmail,"Empty fields are not allowed.");
        }
        else{
            showError(inputEmail,"Please Enter correct Email.");
        }
    }

    //Function to show error message when input is not in correct format
    public void showError(EditText input, String s)
    {
        input.setError(s);
        input.requestFocus();
    }
}