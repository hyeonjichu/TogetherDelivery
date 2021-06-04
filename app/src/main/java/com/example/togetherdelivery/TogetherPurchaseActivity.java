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

public class TogetherPurchaseActivity extends AppCompatActivity {

    String userId, storeId, myMoney, storeName, ranNum, place, peopleNum, finishTime;
    TextView togetherMainText, curMoney_t, allMoney_t, afterMoney_t;
    EditText togetherPlace, hour, min, people;
    Button togetheOrderBtn;
    private String TAG = "Hello!!!!!!!!!!!!!!!!!";
    RecyclerView shopBagListView;
    ArrayList<MenuModel> shopBagArrayList;
    MyShopMenuAdapter myShopMenuAdapter;
    int orderMoney, curPeople, price;
    double dVal = Math.random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_together_purchase);

        togetherMainText=findViewById(R.id.togetherMainText);
        curMoney_t=findViewById(R.id.curMoney_t);
        allMoney_t=findViewById(R.id.allMoney_t);
        afterMoney_t=findViewById(R.id.afterMoney_t);
        togetherPlace=findViewById(R.id.togetherPlace);
        togetheOrderBtn=findViewById(R.id.togetheOrderBtn);
        hour=findViewById(R.id.hour);
        min=findViewById(R.id.min);
        people=findViewById(R.id.people);

        Intent togetherIntent = getIntent();
        userId = togetherIntent.getStringExtra("id");
        storeId = togetherIntent.getStringExtra("storeId");
        orderMoney = togetherIntent.getIntExtra("allMoney",0);
        if(togetherIntent.getStringExtra("ranNum")!=null){
            ranNum=togetherIntent.getStringExtra("ranNum");
        }


        togetherMainText.setText(userId+"님의 장바구니 입니다.");

        shopBagArrayList = new ArrayList<MenuModel>();

        shopBagListView = findViewById(R.id.togetherOrderList);
        shopBagListView.setHasFixedSize(true);
        shopBagListView.setLayoutManager(new LinearLayoutManager(this));
        myShopMenuAdapter = new MyShopMenuAdapter(TogetherPurchaseActivity.this,shopBagArrayList);

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
                        curMoney_t.append(document.getData().get("money").toString());
                        allMoney_t.append(String.valueOf(orderMoney));
                        myMoney=String.valueOf(Integer.parseInt((String) document.getData().get("money"))-orderMoney);
                        afterMoney_t.append(String.valueOf(Integer.parseInt((String) document.getData().get("money"))-orderMoney));
                        togetherPlace.setText(document.getData().get("address").toString());
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });


        DocumentReference doc2 = db.collection("ceoInfo").document(storeId);
        doc2.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        storeName = document.getData().get("storeName").toString();
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });

        DocumentReference doc3 = db.collection("shopBag").document(ranNum);
        doc3.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        curPeople = Integer.parseInt(document.getData().get("curPeople").toString());
                        price = Integer.parseInt(document.getData().get("price").toString());
                        place=document.getData().get("place").toString();
                        peopleNum=document.getData().get("peopleNum").toString();

                        finishTime=document.getData().get("finishTime").toString();
                        System.out.println("1111111111111111");
                        System.out.println(finishTime);
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });

        if(ranNum == null){
            togetheOrderBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String ranNum = String.valueOf(dVal);
                    Map<String, Object> newOrder = new HashMap<>();
                    newOrder.put("ranNum",ranNum);
                    newOrder.put("storeId",storeId);
                    newOrder.put("storeName",storeName);
                    newOrder.put("price",String.valueOf(orderMoney));
                    newOrder.put("place",togetherPlace.getText().toString());
                    newOrder.put("orderId",userId);
                    //newOrder.put("orderTime",);
                    newOrder.put("approval","waiting");
                    newOrder.put("complete","no");
                    newOrder.put("peopleNum",people.getText().toString());
                    newOrder.put("curStatus","모집 중");
                    newOrder.put("curPeople","1");
                    newOrder.put("finishTime",hour.getText().toString()+"시"+min.getText().toString()+"분");

                    db.collection("shopBag").document(ranNum)
                            //.collection(ranNum).document(userId)
                            .set(newOrder)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "DocumentSnapshot successfully written!");
                                    Map<String, Object> newOrder2 = new HashMap<>();
                                    newOrder2.put("ranNum",ranNum);
                                    newOrder2.put("storeId",storeId);
                                    newOrder2.put("storeName",storeName);
                                    newOrder2.put("price",String.valueOf(orderMoney));
                                    newOrder2.put("orderId",userId);
                                    newOrder2.put("approval","waiting");
                                    newOrder2.put("complete","no");
                                    newOrder2.put("menu1",shopBagArrayList.get(0).menuName);
                                    for(int i=1; i<shopBagArrayList.size(); i++){
                                        newOrder2.put("menu"+(i+1),shopBagArrayList.get(i));
                                    }
                                    db.collection("shopBag").document(ranNum)
                                            .collection(ranNum).document(userId)
                                            .set(newOrder2)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Log.d(TAG, "DocumentSnapshot successfully written!");
                                                    Intent intent = new Intent(TogetherPurchaseActivity.this, TogetherListActivity.class);
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
            });
        }else{
            togetherPlace.setText(place);
            System.out.println(place);
            System.out.println("22222222222222222");
            System.out.println(finishTime);
            hour.setText(finishTime);
            //min.setText(finishTime.substring(3,5));
            people.setText(peopleNum);

            togetheOrderBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Map<String, Object> newOrder2 = new HashMap<>();
                    newOrder2.put("ranNum",ranNum);
                    newOrder2.put("storeId",storeId);
                    newOrder2.put("storeName",storeName);
                    newOrder2.put("price",String.valueOf(orderMoney));
                    newOrder2.put("orderId",userId);
                    newOrder2.put("approval","waiting");
                    newOrder2.put("complete","no");
                    newOrder2.put("menu1",shopBagArrayList.get(0));
                    for(int i=1; i<shopBagArrayList.size(); i++){
                        newOrder2.put("menu"+(i+1),shopBagArrayList.get(i));
                    }

                    db.collection("shopBag").document(ranNum)
                        .collection(ranNum).document(userId)
                        .set(newOrder2)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "DocumentSnapshot successfully written!");
                                Map<String, Object> curUpdate = new HashMap<>();
                                curUpdate.put("curPeople",String.valueOf(curPeople+1));
                                curUpdate.put("price",String.valueOf(price+orderMoney));
                                db.collection("shopBag").document(ranNum)
                                        .set(curUpdate, SetOptions.merge())
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Log.d(TAG, "DocumentSnapshot successfully written!");
                                                Intent intent = new Intent(TogetherPurchaseActivity.this, TogetherListActivity.class);
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
            });
        }

    }
}


