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
        if(intent.getStringExtra("menu2") !=null){
            menu2=intent.getStringExtra("menu2");
        }if(intent.getStringExtra("menu3") !=null){
            menu3=intent.getStringExtra("menu3");
        }if(intent.getStringExtra("menu4") !=null){
            menu4=intent.getStringExtra("menu4");
        }if(intent.getStringExtra("menu5") !=null){
            menu5=intent.getStringExtra("menu5");
        }if(intent.getStringExtra("menu6") !=null){
            menu6=intent.getStringExtra("menu6");
        }if(intent.getStringExtra("menu7") !=null){
            menu7=intent.getStringExtra("menu7");
        }if(intent.getStringExtra("menu8") !=null){
            menu8=intent.getStringExtra("menu8");
        }if(intent.getStringExtra("menu9") !=null){
            menu9=intent.getStringExtra("menu9");
        }if(intent.getStringExtra("menu10") !=null){
            menu10=intent.getStringExtra("menu10");
        }

        ranNum=intent.getStringExtra("ranNum");

        purText = findViewById(R.id.purText);
        menuText = findViewById(R.id.menuText);
        purBtn = findViewById(R.id.purBtn);

        purText.setText(userId+"님의 결제 내역입니다.");
        menuText.setText(menu1);
        menuText.append("\n");
        if(menu2 != null){
            menuText.append(menu2);
            menuText.append("\n");
        }
        if(menu3 != null){menuText.append(menu3);
            menuText.append("\n");}
        if(menu4 != null){ menuText.append(menu4);
            menuText.append("\n");}
        if(menu5 != null){menuText.append(menu5);
            menuText.append("\n");}
        if(menu6 != null){menuText.append(menu6);
            menuText.append("\n");}
        if(menu7 != null){menuText.append(menu7);
            menuText.append("\n");}
        if(menu8 != null){menuText.append(menu8);
            menuText.append("\n");}
        if(menu9 != null){menuText.append(menu9);
            menuText.append("\n");}
        if(menu10 != null){menuText.append(menu10);}


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
                                Map<String, Object> update1 = new HashMap<>();
                                update1.put("payment","yes");
                                //payment=yes로 변경하기
                                db.collection("userInfo").document(userId)
                                        .collection(userId).document(ranNum)
                                        .set(update1, SetOptions.merge())
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
