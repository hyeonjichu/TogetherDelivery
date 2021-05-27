package com.example.togetherdelivery;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class AloneActivity extends AppCompatActivity {

    Button aloneListBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alone);

        aloneListBtn = (Button)findViewById(R.id.aloneListBtn);

        aloneListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent aloneListIntent = new Intent(AloneActivity.this, AloneListActivity.class);
                startActivity(aloneListIntent);
            }
        });
    }
}
