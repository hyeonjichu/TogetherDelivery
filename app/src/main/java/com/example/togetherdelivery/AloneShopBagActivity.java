package com.example.togetherdelivery;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AloneShopBagActivity extends AppCompatActivity {

    String userId, storeId;
    RecyclerView shopBagListView;
    ArrayList<MenuModel> shopBagArrayList;
    MyMenuAdapter myMenuAdapter;
    Button buy_btn;
    TextView orderMoney;
    int allMoney;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_alone_shopbag);
        buy_btn = (Button)findViewById(R.id.buy_btn);
        orderMoney = findViewById(R.id.orderMoney);

        Intent togetherIntent = getIntent(); // 데이터 수신
        userId = togetherIntent.getStringExtra("id");
        storeId = togetherIntent.getStringExtra("store");

        getSupportActionBar().setIcon(R.drawable.delivery);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        shopBagArrayList = new ArrayList<MenuModel>();

        shopBagListView = findViewById(R.id.shop_bag_Recycler);
        shopBagListView.setHasFixedSize(true);
        shopBagListView.setLayoutManager(new LinearLayoutManager(this));
        myMenuAdapter = new MyMenuAdapter(AloneShopBagActivity.this,shopBagArrayList);

        ArrayList<MenuModel> menuModelArrayList = (ArrayList<MenuModel>) togetherIntent.getSerializableExtra("menuModelArrayList");
        for (int i = 0; i < menuModelArrayList.size(); i++){
            if(menuModelArrayList.get(i).isSelected()){
                shopBagArrayList.add(menuModelArrayList.get(i));
                allMoney = allMoney + Integer.parseInt(menuModelArrayList.get(i).menuPrice);
            }
            myMenuAdapter.notifyDataSetChanged();
        }
        orderMoney.append(String.valueOf(allMoney));
        shopBagListView.setAdapter(myMenuAdapter);



        buy_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent togetherIntent = new Intent(AloneShopBagActivity.this, PurchaseWaitingActivity.class);
                Intent purchaseIntent = new Intent(AloneShopBagActivity.this, PurchaseActivity.class);
                purchaseIntent.putExtra("menuModelArrayList",menuModelArrayList);
                purchaseIntent.putExtra("id",userId);
                purchaseIntent.putExtra("allMoney",allMoney);
                purchaseIntent.putExtra("store",storeId);
                startActivity(purchaseIntent);
            }
        });

    }
}