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

public class PurchaseWaitingActivity  extends AppCompatActivity {

    Button chkBtn;
    FirebaseFirestore db;
    String userId, ranNum;
    private String TAG="ddddddddddddddddddddddddddddd";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyend);

        Intent intent = getIntent();
        userId = intent.getStringExtra("id");
        ranNum = intent.getStringExtra("ranNum");

        getSupportActionBar().setIcon(R.drawable.delivery);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        chkBtn = findViewById(R.id.chkBtn);

        db=FirebaseFirestore.getInstance();

        chkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DocumentReference doc = db.collection("shopBag").document(ranNum);
                doc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                                if(document.getData().get("approval").equals("yes")){
                                    Intent intent = new Intent(PurchaseWaitingActivity.this, TogetherOrderCompleActivity.class );
                                    intent.putExtra("id",userId);
                                    startActivity(intent);
                                }else{
                                    AlertDialog.Builder builder = new AlertDialog.Builder(PurchaseWaitingActivity.this);
                                    builder.setTitle("알림!");
                                    builder.setMessage("주문 확인중입니다. 잠시만 기다려주세요");
                                    builder.setPositiveButton("확인",null);
                                    builder.create().show();
                                }
                            } else {
                                Log.d(TAG, "No such document");
                            }
                        } else {
                            Log.d(TAG, "get failed with ", task.getException());
                        }
                    }
                });
            }
        });
    }
}
