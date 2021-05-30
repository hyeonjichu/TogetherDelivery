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

    private final String TAG = "Hello";
    Button togetherBtn, aloneBtn;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        togetherBtn = findViewById(R.id.togetherBtn);
        aloneBtn = findViewById(R.id.aloneBtn);

        Intent MainIntent = getIntent();
        userId = MainIntent.getStringExtra("userId");

        final FirebaseFirestore db = FirebaseFirestore.getInstance();

        togetherBtn.setOnClickListener(v -> {
            Intent storeIntent = new Intent(MainActivity.this, StoreListActivity.class);
            storeIntent.putExtra("userId",userId);
            storeIntent.putExtra("delivery","together");
            startActivity(storeIntent);
        });

        aloneBtn.setOnClickListener(v -> {
            Intent storeIntent = new Intent(MainActivity.this, StoreListActivity.class);
            storeIntent.putExtra("userId",userId);
            storeIntent.putExtra("delivery","alone");
            startActivity(storeIntent);
        });

    }
}