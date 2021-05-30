package com.example.togetherdelivery;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MenuListActivity extends AppCompatActivity {

    Button shopBtn;
    TextView storeName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menulist);

        shopBtn = findViewById(R.id.shopBtn);
        storeName=findViewById(R.id.storeName);

        Intent MainIntent = getIntent();
        storeName.setText(MainIntent.getStringExtra("storeName"));
        storeName.setTextSize(30);
    }

}
