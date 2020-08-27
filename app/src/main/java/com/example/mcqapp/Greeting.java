package com.example.mcqapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Greeting extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_greeting);
    }
    @Override
    public void onBackPressed() {
        finishAffinity();
        super.onBackPressed();
    }
}

