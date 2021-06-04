package com.example.togetherdelivery;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AlonePurchaseActivity extends AppCompatActivity {

    String userId, storeId, myMoney;
    TextView purMainText, curMoney, allMoney, afterMoney;
    EditText myPlace;
    Button orderBtn;
    private String TAG = "Hello!!!!!!!!!!!!!!!!!";
    RecyclerView shopBagListView;
    ArrayList<MenuModel> shopBagArrayList;
    MyShopMenuAdapter myShopMenuAdapter;
    int orderMoney;
    double dVal = Math.random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alone_purchase);

        purMainText=findViewById(R.id.togetherMainText);
        curMoney=findViewById(R.id.curMoney_t);
        allMoney=findViewById(R.id.allMoney_t);
        afterMoney=findViewById(R.id.afterMoney_t);
        myPlace=findViewById(R.id.togetherPlace);
        orderBtn=findViewById(R.id.togetheOrderBtn);

        Intent togetherIntent = getIntent();
        userId = togetherIntent.getStringExtra("id");
        storeId = togetherIntent.getStringExtra("store");
        orderMoney = togetherIntent.getIntExtra("allMoney",0);


        purMainText.setText(userId+"님의 장바구니 입니다.");

        shopBagArrayList = new ArrayList<MenuModel>();

        shopBagListView = findViewById(R.id.togetherOrderList);
        shopBagListView.setHasFixedSize(true);
        shopBagListView.setLayoutManager(new LinearLayoutManager(this));
        myShopMenuAdapter = new MyShopMenuAdapter(AlonePurchaseActivity.this,shopBagArrayList);

        ArrayList<MenuModel> menuModelArrayList = (ArrayList<MenuModel>) togetherIntent.getSerializableExtra("menuModelArrayList");
        for (int i = 0; i < menuModelArrayList.size(); i++){
            if(menuModelArrayList.get(i).isSelected()){
                shopBagArrayList.add(menuModelArrayList.get(i));
            }
            myShopMenuAdapter.notifyDataSetChanged();
        }
        shopBagListView.setAdapter(myShopMenuAdapter);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        DocumentReference doc = db.collection("userInfo").document(userId);
        doc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        curMoney.append(document.getData().get("money").toString());
                        allMoney.append(String.valueOf(orderMoney));
                        myMoney=String.valueOf(Integer.parseInt((String) document.getData().get("money"))-orderMoney);
                        afterMoney.append(String.valueOf(Integer.parseInt((String) document.getData().get("money"))-orderMoney));
                        myPlace.setText(document.getData().get("address").toString());
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });

        orderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ranNum = String.valueOf(dVal);
                Map<String, Object> newOrder = new HashMap<>();
                newOrder.put("ranNum",ranNum);
                newOrder.put("storeId",storeId);
                newOrder.put("price",String.valueOf(orderMoney));
                newOrder.put("place",myPlace.getText().toString());
                newOrder.put("orderId",userId);
                //newOrder.put("orderTime",);
                newOrder.put("approval","waiting");
                newOrder.put("complete","no");
                /*for(int i=1; i<shopBagArrayList.size(); i++){
                    newOrder.put("menu"+(i+1),shopBagArrayList.get(i));
                }*/

                db.collection("shopBag").document(ranNum)
                        //.collection("0.045693480562439714").document(userId)
                        .set(newOrder)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "DocumentSnapshot successfully written!");
                                Map<String, Object> myOrder = new HashMap<>();
                                myOrder.put("ranNum",ranNum);
                                myOrder.put("storeId",storeId);
                                myOrder.put("price",String.valueOf(orderMoney));
                                myOrder.put("orderId",userId);
                                myOrder.put("approval","waiting");
                                myOrder.put("complete","no");
                                myOrder.put("menu1",shopBagArrayList.get(0));
                                for(int i=1; i<shopBagArrayList.size(); i++){
                                    myOrder.put("menu"+(i+1),shopBagArrayList.get(i));
                                }

                                db.collection("shopBag").document(ranNum)
                                        .collection(ranNum).document(userId)
                                        .set(myOrder)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Log.d(TAG, "DocumentSnapshot successfully written!");
                                                Map<String, Object> myMoneyUpdate = new HashMap<>();
                                                myMoneyUpdate.put("money",myMoney);

                                                db.collection("userInfo").document(userId)
                                                        .set(myMoneyUpdate, SetOptions.merge())
                                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void aVoid) {
                                                                Log.d(TAG, "DocumentSnapshot successfully written!");
                                                                Intent intent = new Intent(AlonePurchaseActivity.this, PurchaseWaitingActivity.class);
                                                                startActivity(intent);
                                                            }
                                                        })
                                                        .addOnFailureListener(new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e) {
                                                                Log.w(TAG, "Error writing document", e);
                                                            }
                                                        });

                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.w(TAG, "Error writing document", e);
                                            }
                                        });
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error writing document", e);
                            }
                        });


            }
        });
    }
}
