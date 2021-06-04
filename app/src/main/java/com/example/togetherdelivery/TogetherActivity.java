package com.example.togetherdelivery;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class TogetherActivity extends AppCompatActivity {

    FirebaseFirestore firebaseFirestore;

    String userId;
    RecyclerView mStoreListView;
    ArrayList<ProductsModel> productsModelArrayList;
    MyAdapter myAdapter;
    ProgressDialog progressDialog;
    Button store_together_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_together);

        Intent intent = getIntent();
        userId = intent.getStringExtra("id");

        getSupportActionBar().setIcon(R.drawable.delivery);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        store_together_btn = findViewById(R.id.store_together_btn);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("조금만 기다려 주세요!...");
        progressDialog.show();

        mStoreListView = findViewById(R.id.store_together_list);
        mStoreListView.setHasFixedSize(true);
        mStoreListView.setLayoutManager(new LinearLayoutManager(this));

        firebaseFirestore = FirebaseFirestore.getInstance();
        productsModelArrayList = new ArrayList<ProductsModel>();
        myAdapter = new MyAdapter(TogetherActivity.this,productsModelArrayList);

        myAdapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                Intent intent = new Intent(TogetherActivity.this, TogetherMenuListActivity.class);
                System.out.println("111111111111111111");
                System.out.println(productsModelArrayList.get(pos).storeId);
                intent.putExtra("storeId", productsModelArrayList.get(pos).storeId);
                intent.putExtra("id",userId);
                startActivity(intent);

            }
        });
        mStoreListView.setAdapter(myAdapter);
        EventChangeListener();

        store_together_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TogetherActivity.this, TogetherListActivity.class);
                intent.putExtra("id",userId);
                startActivity(intent);
            }
        });
    }

    private void EventChangeListener() {
        firebaseFirestore.collection("ceoInfo").orderBy("storeName", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
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
                                productsModelArrayList.add(dc.getDocument().toObject(ProductsModel.class));
                            }
                            myAdapter.notifyDataSetChanged();
                            if(progressDialog.isShowing())
                                progressDialog.dismiss();
                        }
                    }
                });
    }
}





