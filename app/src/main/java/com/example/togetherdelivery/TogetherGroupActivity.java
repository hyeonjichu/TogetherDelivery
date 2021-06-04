package com.example.togetherdelivery;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class TogetherGroupActivity extends AppCompatActivity {

    TextView text_storeName, text_curStatus, text_finishTime, text_peopleNum, text_place, text_curPeople;
    Button togetherJoinBtn, togetherOrderBtn;
    ImageButton chat_btn, trash_can_btn;
    String storeId, userId;
    FirebaseFirestore db;
    private String TAG = "Hello!!!!!!!!!!!!!!!!!";
    private DatabaseReference myRef;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_together_group);

        getSupportActionBar().setIcon(R.drawable.delivery);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        db=FirebaseFirestore.getInstance();

        text_storeName = findViewById(R.id.text_storeName);
        text_curStatus = findViewById(R.id.text_curStatus);
        text_finishTime = findViewById(R.id.text_finishTime);
        text_peopleNum = findViewById(R.id.text_peopleNum);
        text_place = findViewById(R.id.text_place);
        text_curPeople=findViewById(R.id.text_curPeople);
        togetherJoinBtn=findViewById(R.id.togetherJoinBtn);
        togetherOrderBtn=findViewById(R.id.togetherOrderBtn);
        chat_btn = findViewById(R.id.chat_btn);
        trash_can_btn = findViewById(R.id.trash_can_btn);

        Intent togetherIntent = getIntent(); // 데이터 수신
        TogetherListModel togetherListModel = (TogetherListModel) togetherIntent.getSerializableExtra("togetherListModel");
        storeId=togetherIntent.getStringExtra("storeId");
        userId = togetherIntent.getStringExtra("id");
        text_storeName.setText(togetherListModel.storeName);
        text_curStatus.setText(togetherListModel.curStatus);
        text_finishTime.setText(togetherListModel.finishTime);
        text_peopleNum.setText(togetherListModel.peopleNum);
        text_place.setText(togetherListModel.place);
        text_curPeople.setText(togetherListModel.curPeople);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference().child(togetherListModel.orderId).child(togetherListModel.storeName);

        if(togetherListModel.orderId.equals(userId)){
            togetherJoinBtn.setVisibility(View.GONE);
        }else{
            togetherOrderBtn.setVisibility(View.GONE);
        }
        if(togetherListModel.curStatus.equals("모집 완료")){
            togetherJoinBtn.setVisibility(View.GONE);
            togetherOrderBtn.setVisibility(View.GONE);
        }

        chat_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TogetherGroupActivity.this, ChatMessageActivity.class);
                intent.putExtra("orderId", togetherListModel.orderId);
                intent.putExtra("storeName", togetherListModel.storeName);
                intent.putExtra("id",userId);
                startActivity(intent);
            }
        });
        trash_can_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myRef.removeValue();
            }
        });

        //함께 주문하기 => 해당 가게로 이동
        togetherJoinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TogetherGroupActivity.this, TogetherMenuListActivity.class);
                intent.putExtra("id",userId);
                intent.putExtra("storeId",storeId);
                intent.putExtra("ranNum",togetherListModel.ranNum);
                startActivity(intent);
            }
        });

        //등록자만 보이도록 => 최종 주문
        //모집 완료로 변경 => 각자 계산
        togetherOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder msgBuilder = new AlertDialog.Builder(TogetherGroupActivity.this)
                    .setTitle("확인")
                    .setMessage("주문하시겠습니까?")
                    .setPositiveButton("네", new DialogInterface.OnClickListener() {
                        //각각 userInfo에 주문 ranNum추가해서 있으면 결제하도록 버튼 생성
                        //다 결제하면 가게에 알림전송
                        @Override public void onClick(DialogInterface dialogInterface, int i) {
                            Map<String, Object> curUpdate = new HashMap<>();
                            curUpdate.put("curStatus","모집 완료");
                            db.collection("shopBag").document(togetherListModel.ranNum)
                                .set(curUpdate, SetOptions.merge())
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                Log.d(TAG, "DocumentSnapshot successfully written!");
                                db.collection("shopBag").document(togetherListModel.ranNum)
                                    .collection(togetherListModel.ranNum)
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {
                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                    Log.d(TAG, document.getId() + " => " + document.getData());
                                                    Map<String, Object> myorder = new HashMap<>();
                                                    myorder.put("price",document.getData().get("price"));
                                                    myorder.put("storeId",document.getData().get("storeId"));
                                                    myorder.put("menu1",document.getData().get("menu1"));
                                                    myorder.put("payment","no");
                                                    myorder.put("ranNum",togetherListModel.ranNum);
                                                    for(int i=2; i<11; i++){
                                                        if(document.getData().get("menu"+i) != null){
                                                            myorder.put("menu"+i,document.getData().get("menu"+i));
                                                        }
                                                    }
                                                    db.collection("userInfo").document(document.getData().get("orderId").toString())
                                                        .collection(document.getData().get("orderId").toString()).document(togetherListModel.ranNum)
                                                        .set(myorder, SetOptions.merge())
                                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void aVoid) {
                                                                Log.d(TAG, "DocumentSnapshot successfully written!");

                                                            }
                                                        })
                                                        .addOnFailureListener(new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e) {
                                                                Log.w(TAG, "Error writing document", e);
                                                            }
                                                        });

                                                }
                                                Intent intent = new Intent(TogetherGroupActivity.this, TogetherOrderActivity.class);
                                                intent.putExtra("id",userId);
                                                intent.putExtra("storeId",storeId);
                                                intent.putExtra("ranNum",togetherListModel.ranNum);
                                                startActivity(intent);
                                            } else {
                                                Log.d(TAG, "Error getting documents: ", task.getException());
                                            }
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
                    .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                        @Override public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(TogetherGroupActivity.this, "안 함", Toast.LENGTH_SHORT).show();
                        }
                    });
                AlertDialog msgDlg = msgBuilder.create(); msgDlg.show();

            }
        });

    }
}
