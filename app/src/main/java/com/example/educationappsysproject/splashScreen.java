package com.example.educationappsysproject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.educationappsysproject.Authentication.login;

public class splashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        EdgeToEdge.enable(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
            Intent intent= new Intent(splashScreen.this, login.class);
            startActivity(intent);
            finish();
            }
        },3000);
    }
}