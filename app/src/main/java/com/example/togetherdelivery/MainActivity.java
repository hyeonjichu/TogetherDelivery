package com.example.togetherdelivery;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    private String TAG = "Hello";
    Button togetherBtn, aloneBtn;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setIcon(R.drawable.delivery);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        togetherBtn = (Button)findViewById(R.id.togetherBtn);
        aloneBtn = (Button)findViewById(R.id.aloneBtn);

        Intent MainIntent = getIntent();
        userId = MainIntent.getStringExtra("id");

        final FirebaseFirestore db = FirebaseFirestore.getInstance();

        togetherBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent togetherIntent = new Intent(MainActivity.this, TogetherActivity.class);
                togetherIntent.putExtra("id",userId);
                startActivity(togetherIntent);
            }
        });

        aloneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent aloneIntent = new Intent(MainActivity.this, AloneActivity.class);
                aloneIntent.putExtra("id",userId);
                startActivity(aloneIntent);
            }
        });

    }
}