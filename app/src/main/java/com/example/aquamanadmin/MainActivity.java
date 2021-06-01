package com.example.aquamanadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static int SPLASH_SCREEN = 5000;

    //variables
    Animation topAnim, bottomAnim;
    ImageView dolphin_logo;
    TextView aquaman, tagline;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);     //removes title bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();    //removes action bar
        setContentView(R.layout.activity_main);

        //Animations
        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        //hooks
        dolphin_logo = findViewById(R.id.addUser);
        aquaman = findViewById(R.id.Aquaman);
        tagline = findViewById(R.id.tagline);

        dolphin_logo.setAnimation(topAnim);
        aquaman.setAnimation(bottomAnim);
        tagline.setAnimation(bottomAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this,LogInActivity.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_SCREEN);
    }
}