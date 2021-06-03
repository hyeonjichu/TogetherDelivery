package com.example.togetherdelivery;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

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

public class TogetherListActivity extends AppCompatActivity {

    FirebaseFirestore firebaseFirestore;

    RecyclerView mTogetherListView;
    ArrayList<TogetherListModel> togetherListModelArrayList;
    TogetherListAdapter togetherListAdapter;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_together_list);

        getSupportActionBar().setIcon(R.drawable.delivery);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        double ranNum = Math.random();

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("조금만 기다려 주세요!...");
        progressDialog.show();

        mTogetherListView = findViewById(R.id.together_list_recycler);
        mTogetherListView.setHasFixedSize(true);
        mTogetherListView.setLayoutManager(new LinearLayoutManager(this));

        firebaseFirestore = FirebaseFirestore.getInstance();
        togetherListModelArrayList = new ArrayList<TogetherListModel>();
        togetherListAdapter = new TogetherListAdapter(TogetherListActivity.this, togetherListModelArrayList);

        togetherListAdapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                Intent intent = new Intent(TogetherListActivity.this, TogetherGroupActivity.class);
                intent.putExtra("togetherListModel", togetherListModelArrayList.get(pos));
                startActivity(intent);

            }
        });
        mTogetherListView.setAdapter(togetherListAdapter);
        EventChangeListener();
    }
        private void EventChangeListener() {
            firebaseFirestore.collection("shopBag")
                    .orderBy("curStatus", Query.Direction.DESCENDING)
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

                                if(dc.getType() == DocumentChange.Type.ADDED) {
                                    if (dc.getDocument().toObject(TogetherListModel.class).peopleNum != null) {
                                        togetherListModelArrayList.add(dc.getDocument().toObject(TogetherListModel.class));
                                    }
                                }
                                togetherListAdapter.notifyDataSetChanged();
                                if(progressDialog.isShowing())
                                    progressDialog.dismiss();
                            }
                        }
                    });
        }

}
