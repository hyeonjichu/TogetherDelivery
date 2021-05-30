package com.example.togetherdelivery;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class StoreListActivity extends AppCompatActivity {

    TextView mainText;
    String userId, delivery;
    Button storeListBtn;
    FirebaseFirestore firebaseFirestore;

    RecyclerView mStoreListView;
    ArrayList<ProductsModel> productsModelArrayList;
    MyAdapter myAdapter;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storelist);

        Intent StoreIntent = getIntent();
        userId = StoreIntent.getStringExtra("userId");
        delivery = StoreIntent.getStringExtra("delivery");
        mainText=findViewById(R.id.mainText);
        storeListBtn=findViewById(R.id.storeListBtn);

        if(delivery.equals("together")){
            mainText.setText("같이 배달");
            mainText.setTextSize(30);
            storeListBtn.setEnabled(true);
        }else if(delivery.equals("alone")){
            mainText.setText("혼자 배달");
            mainText.setTextSize(30);
            storeListBtn.setVisibility(View.GONE);
            storeListBtn.setEnabled(false);
        }

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data...");
        progressDialog.show();

        mStoreListView = findViewById(R.id.storeList);
        mStoreListView.setHasFixedSize(true);
        mStoreListView.setLayoutManager(new LinearLayoutManager(this));

        firebaseFirestore = FirebaseFirestore.getInstance();
        productsModelArrayList = new ArrayList<>();
        myAdapter = new MyAdapter(StoreListActivity.this, productsModelArrayList);
        myAdapter.setOnItemClickListener((v, position) -> {
            Intent intent = new Intent(StoreListActivity.this, MenuListActivity.class);
            intent.putExtra("storeName",productsModelArrayList.get(position).storeName);
            startActivity(intent);
        });
        mStoreListView.setAdapter(myAdapter);
        EventChangeListener();
    }
    private void EventChangeListener() {
        firebaseFirestore.collection("ceoInfo").orderBy("id", Query.Direction.ASCENDING)
            .addSnapshotListener((value, error) -> {
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
            });
    }
}
