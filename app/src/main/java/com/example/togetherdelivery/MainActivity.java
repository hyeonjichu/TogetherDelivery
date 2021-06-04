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
                .whereEqualTo("payment", "no")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                if(document.getData().get("payment").equals("no")){
                                    paymentBtn.setVisibility(View.VISIBLE);
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
                                    price=document.getData().get("price").toString();
                                    storeId=document.getData().get("storeId").toString();
                                    ranNum = document.getData().get("ranNum").toString();
                                    if(document.getData().get("menu1") != null){
                                        menu1=document.getData().get("menu1").toString();
                                    }
                                    if(document.getData().get("menu2") != null){
                                        menu2=document.getData().get("menu2").toString();
                                    }
                                    if(document.getData().get("menu3") != null){
                                        menu3=document.getData().get("menu3").toString();
                                    }
                                    if(document.getData().get("menu4") != null){
                                        menu4=document.getData().get("menu4").toString();
                                    }
                                    if(document.getData().get("menu5") != null){
                                        menu5=document.getData().get("menu5").toString();
                                    }
                                    if(document.getData().get("menu6") != null){
                                        menu6=document.getData().get("menu6").toString();
                                    }
                                    if(document.getData().get("menu7") != null){
                                        menu7=document.getData().get("menu7").toString();
                                    }
                                    if(document.getData().get("menu8") != null){
                                        menu8=document.getData().get("menu8").toString();
                                    }
                                    if(document.getData().get("menu9") != null){
                                        menu9=document.getData().get("menu9").toString();
                                    }
                                    if(document.getData().get("menu10") != null){
                                        menu10=document.getData().get("menu10").toString();
                                    }
                                }
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });



    }
}