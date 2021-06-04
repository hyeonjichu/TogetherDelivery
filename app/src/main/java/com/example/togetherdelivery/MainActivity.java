package com.example.togetherdelivery;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private String TAG = "Hello";
    Button togetherBtn, aloneBtn, paymentBtn;
    String userId, menu1, menu2, menu3, menu4, menu5, menu6, menu7, menu8, menu9, menu10, price, storeId, ranNum;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db=FirebaseFirestore.getInstance();

        getSupportActionBar().setIcon(R.drawable.delivery);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        togetherBtn = findViewById(R.id.togetherBtn);
        aloneBtn = findViewById(R.id.aloneBtn);
        paymentBtn=findViewById(R.id.paymentBtn);
        paymentBtn.setVisibility(View.GONE);

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


        db.collection("userInfo").document(userId)
                .collection(userId)
                //.whereEqualTo("payment", "no")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                if (document.getData().get("payment").equals("no")) {
                                    paymentBtn.setVisibility(View.VISIBLE);
                                    price = document.getData().get("price").toString();
                                    storeId = document.getData().get("storeId").toString();
                                    ranNum = document.getData().get("ranNum").toString();
                                    if (document.getData().get("menu1") != null) {
                                        HashMap hashMap = (HashMap) document.getData().get("menu1");
                                        menu1 = (String) hashMap.get("menuName");
                                    }
                                    if (document.getData().get("menu2") != null) {
                                        HashMap hashMap2 = (HashMap) document.getData().get("menu2");
                                        menu2 = (String) hashMap2.get("menuName");
                                    }
                                    if (document.getData().get("menu3") != null) {
                                        HashMap hashMap3 = (HashMap) document.getData().get("menu3");
                                        menu3 = (String) hashMap3.get("menuName");
                                    }
                                    if (document.getData().get("menu4") != null) {
                                        HashMap hashMap4 = (HashMap) document.getData().get("menu4");
                                        menu4 = (String) hashMap4.get("menuName");
                                        if (document.getData().get("menu5") != null) {
                                            HashMap hashMap5 = (HashMap) document.getData().get("menu5");
                                            menu5 = (String) hashMap5.get("menuName");
                                        }
                                        if (document.getData().get("menu6") != null) {
                                            HashMap hashMap6 = (HashMap) document.getData().get("menu6");
                                            menu6 = (String) hashMap6.get("menuName");
                                        }
                                        if (document.getData().get("menu7") != null) {
                                            HashMap hashMap7 = (HashMap) document.getData().get("menu7");
                                            menu7 = (String) hashMap7.get("menuName");
                                        }
                                        if (document.getData().get("menu8") != null) {
                                            HashMap hashMap8 = (HashMap) document.getData().get("menu8");
                                            menu8 = (String) hashMap8.get("menuName");
                                        }
                                        if (document.getData().get("menu9") != null) {
                                            HashMap hashMap9 = (HashMap) document.getData().get("menu9");
                                            menu9 = (String) hashMap9.get("menuName");
                                        }
                                        if (document.getData().get("menu10") != null) {
                                            HashMap hashMap10 = (HashMap) document.getData().get("menu10");
                                            menu10 = (String) hashMap10.get("menuName");
                                        }
                                    }
                                }
                            }
                        }
                    }
                });

        paymentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TogetherPurchaseOrderActivity.class);
                intent.putExtra("price",price);
                intent.putExtra("id",userId);
                intent.putExtra("storeId",storeId);
                intent.putExtra("ranNum",ranNum);
                if(menu1 != null){
                    intent.putExtra("menu1",menu1);
                }
                if(menu2 != null){
                    intent.putExtra("menu2",menu2);
                }
                if(menu3 != null){
                    intent.putExtra("menu3",menu3);
                }
                if(menu4 != null){
                    intent.putExtra("menu4",menu4);
                }
                if(menu5 != null){
                    intent.putExtra("menu5",menu5);
                }
                if(menu6 != null){
                    intent.putExtra("menu6",menu6);
                }
                if(menu7 != null){
                    intent.putExtra("menu7",menu7);
                }
                if(menu8 != null){
                    intent.putExtra("menu8",menu8);
                }
                if(menu9 != null){
                    intent.putExtra("menu9",menu9);
                }
                if(menu10 != null){
                    intent.putExtra("menu10",menu10);
                }
                startActivity(intent);
            }
        });



    }
}