package com.example.togetherdelivery;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TogetherOrderActivity extends AppCompatActivity {

    String userId, storeId, ranNum;
    //PeopleModel menuModel,menuModel2,menuModel3,menuModel4,menuModel5,menuModel6,menuModel7,menuModel8, menuModel9,menuModel10;
    Button finalOrderBtn;
    RecyclerView MenuListView;
    ArrayList<PeopleModel> peopleModelArrayList;
    PeopleAdapter peopleAdapter;
    ProgressDialog progressDialog;
    FirebaseFirestore db;
    boolean payChk=true;
    private String TAG = "Hello!!!!!!!!!!!!!!!11";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_together_order);
        finalOrderBtn = findViewById(R.id.finalOrderBtn);

        Intent intent = getIntent();
        userId = intent.getStringExtra("id");
        storeId = intent.getStringExtra("storeId");
        ranNum = intent.getStringExtra("ranNum");

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("조금만 기다려 주세요!...");
        progressDialog.show();

        MenuListView = findViewById(R.id.peopleList);
        MenuListView.setHasFixedSize(true);
        MenuListView.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseFirestore.getInstance();
        peopleModelArrayList = new ArrayList<PeopleModel>();
        peopleAdapter = new PeopleAdapter(TogetherOrderActivity.this, peopleModelArrayList);
        MenuListView.setAdapter(peopleAdapter);

        EventChangeListener();

        finalOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> myUpdate = new HashMap<>();
                myUpdate.put("payment","yes");
                db.collection("shopBag").document(ranNum)
                        .set(myUpdate, SetOptions.merge())
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "DocumentSnapshot successfully written!");
                                Intent intent = new Intent(TogetherOrderActivity.this, TogetherOrderCompleActivity.class);
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
            }
        });

        //모두 입금 했는지 확인 => 버튼 눌리는지 안 눌리는지
        db.collection("shopBag").document(ranNum)
                .collection(ranNum)
            .get()
            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Log.d(TAG, document.getId() + " => " + document.getData());
                            if(document.getData().get("payment").equals("no")){
                                payChk=false;
                                finalOrderBtn.setEnabled(false);
                            }
                        }
                    } else {
                        Log.d(TAG, "Error getting documents: ", task.getException());
                    }
                }
            });
    }

    public void EventChangeListener() {
        db.collection("shopBag").document(ranNum).collection(ranNum)
                //.whereEqualTo("ceoId", storeId)
                .addSnapshotListener(new EventListener<QuerySnapshot>()
                {
                    @Override
                    public void onEvent(@Nullable @org.jetbrains.annotations.Nullable QuerySnapshot value, @Nullable @org.jetbrains.annotations.Nullable FirebaseFirestoreException error) {
                        if(error != null){
                            if(progressDialog.isShowing())
                                progressDialog.dismiss();
                            Log.e("Firestore error", error.getMessage());
                            return;
                        }
                        for (DocumentChange dc : value.getDocumentChanges()){
                            if(dc.getType() == DocumentChange.Type.ADDED){
                                peopleModelArrayList.add(dc.getDocument().toObject(PeopleModel.class));
                            }
                            peopleAdapter.notifyDataSetChanged();
                            if(progressDialog.isShowing())
                                progressDialog.dismiss();
                        }
                    }
                });
    }
}
