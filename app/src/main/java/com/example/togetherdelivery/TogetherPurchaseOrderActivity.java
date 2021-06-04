package com.example.togetherdelivery;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class TogetherPurchaseOrderActivity extends AppCompatActivity {

    String userId, menu1, menu2, menu3, menu4, menu5, menu6, menu7, menu8, menu9, menu10, price, storeId, ranNum, curMoney;
    private SharedPreferences mPreferences;
    private String SharedPrefFile = "com.example.togetherdelivery";
    TextView purText,menuText;
    Button purBtn;
    FirebaseFirestore db;
    private String TAG = "Hello!!!!!!!!!!!!!!!!!";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_together_purchase_order);

        db=FirebaseFirestore.getInstance();

        Intent intent = getIntent();
        userId = intent.getStringExtra("id");
        price = intent.getStringExtra("price");
        storeId = intent.getStringExtra("storeId");
        menu1= intent.getStringExtra("menu1");
        menu2=intent.getStringExtra("menu2");
        ranNum=intent.getStringExtra("ranNum");

        purText = findViewById(R.id.purText);
        menuText = findViewById(R.id.menuText);
        purBtn = findViewById(R.id.purBtn);

        purText.setText(userId+"님의 결제 내역입니다.");
        menuText.setText(menu1);
        menuText.setText("\n");
        menuText.setText(menu2);

        DocumentReference doc3 = db.collection("userInfo").document(userId);
        doc3.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        curMoney = document.getData().get("money").toString();
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });

        purBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> myMoneyUpdate = new HashMap<>();
                myMoneyUpdate.put("money",String.valueOf(Integer.parseInt(curMoney)-Integer.parseInt(price)));

                //돈 계산하기 => 결제금액 빼고 업데이트
                db.collection("userInfo").document(userId)
                        .set(myMoneyUpdate, SetOptions.merge())
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "DocumentSnapshot successfully written!");
                                Map<String, Object> update = new HashMap<>();
                                update.put("payment","yes");
                                //payment=yes로 변경하기
                                db.collection("userInfo").document(userId)
                                        .collection(userId).document(ranNum)
                                        .set(update, SetOptions.merge())
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Log.d(TAG, "DocumentSnapshot successfully written!");
                                                Map<String, Object> myUpdate = new HashMap<>();
                                                myUpdate.put("payment","yes");
                                                db.collection("shopBag").document(ranNum)
                                                        .collection(ranNum).document(userId)
                                                        .set(myUpdate, SetOptions.merge())
                                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void aVoid) {
                                                                Log.d(TAG, "DocumentSnapshot successfully written!");
                                                                Intent intent = new Intent(TogetherPurchaseOrderActivity.this, MainActivity.class);
                                                                intent.putExtra("id",userId);
                                                                startActivity(intent);
                                                            }
                                                        })
                                                        .addOnFailureListener(new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e) {
                                                                Log.w(TAG, "Error writing document", e);
                                                            }
                                                        });
                                                Intent intent = new Intent(TogetherPurchaseOrderActivity.this, MainActivity.class);
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














       /* mPreferences = getSharedPreferences(SharedPrefFile, MODE_PRIVATE);
        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        preferencesEditor.putStringSet("menu0", Collections.singleton(intent.getStringExtra("menu1")));
        String menu11=mPreferences.getString("menu0","no menu");*/

    }



}
