package com.example.togetherdelivery;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class TogetherGroupActivity extends AppCompatActivity {

    TextView text_storeName, text_curStatus, text_finishTime, text_peopleNum, text_place;


    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_together_group);

        getSupportActionBar().setIcon(R.drawable.delivery);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        text_storeName = findViewById(R.id.text_storeName);
        text_curStatus = findViewById(R.id.text_curStatus);
        text_finishTime = findViewById(R.id.text_finishTime);
        text_peopleNum = findViewById(R.id.text_peopleNum);
        text_place = findViewById(R.id.text_place);

        Intent togetherIntent = getIntent(); // 데이터 수신
        TogetherListModel togetherListModel = (TogetherListModel) togetherIntent.getSerializableExtra("togetherListModel");
        text_storeName.setText(togetherListModel.storeName);
        text_curStatus.setText(togetherListModel.curStatus);
        text_finishTime.setText(togetherListModel.finishTime);
        text_peopleNum.setText(togetherListModel.peopleNum);
        text_place.setText(togetherListModel.place);

    }
}
