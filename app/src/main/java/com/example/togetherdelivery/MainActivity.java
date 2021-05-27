package com.example.togetherdelivery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private String TAG = "Hello";
    Button togetherBtn, aloneBtn;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        togetherBtn = (Button)findViewById(R.id.togetherBtn);
        aloneBtn = (Button)findViewById(R.id.aloneBtn);

        Intent MainIntent = getIntent();
        userId = MainIntent.getStringExtra("userId");

        final FirebaseFirestore db = FirebaseFirestore.getInstance();

        togetherBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent togetherIntent = new Intent(MainActivity.this, TogetherActivity.class);
                togetherIntent.putExtra("userId",userId);
                startActivity(togetherIntent);
            }
        });

        aloneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent aloneIntent = new Intent(MainActivity.this, AloneActivity.class);
                aloneIntent.putExtra("userId",userId);
                startActivity(aloneIntent);
            }
        });

    }
}