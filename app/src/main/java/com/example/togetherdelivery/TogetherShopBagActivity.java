package com.example.togetherdelivery;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TogetherShopBagActivity extends AppCompatActivity {
    RecyclerView shopBagListView;
    ArrayList<MenuModel> shopBagArrayList;
    MyMenuAdapter myMenuAdapter;
    Button buy_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_together_shopbag);

        buy_btn = (Button)findViewById(R.id.together_list_btn);

        getSupportActionBar().setIcon(R.drawable.delivery);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        shopBagArrayList = new ArrayList<MenuModel>();

        shopBagListView = findViewById(R.id.together_shop_bag_Recycler);
        shopBagListView.setHasFixedSize(true);
        shopBagListView.setLayoutManager(new LinearLayoutManager(this));
        myMenuAdapter = new MyMenuAdapter(TogetherShopBagActivity.this,shopBagArrayList);


        Intent togetherIntent = getIntent(); // 데이터 수신
        ArrayList<MenuModel> menuModelArrayList = (ArrayList<MenuModel>) togetherIntent.getSerializableExtra("menuModelArrayList");
        for (int i = 0; i < menuModelArrayList.size(); i++){
            if(menuModelArrayList.get(i).isSelected() == true){
                shopBagArrayList.add(menuModelArrayList.get(i));
            }
            myMenuAdapter.notifyDataSetChanged();
        }
        shopBagListView.setAdapter(myMenuAdapter);


        buy_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent togetherIntent = new Intent(TogetherShopBagActivity.this, TogetherListActivity.class);
                startActivity(togetherIntent);
            }
        });

    }
}
