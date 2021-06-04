package com.example.togetherdelivery;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class TogetherOrderCompleActivity extends AppCompatActivity {

    Button backMainBtn;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_complete);

        Intent intent = getIntent();
        userId = intent.getStringExtra("id");

        backMainBtn=findViewById(R.id.backMainBtn);

        backMainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TogetherOrderCompleActivity.this, MainActivity.class);
                intent.putExtra("id",userId);
                startActivity(intent);
            }
        });
    }
}
