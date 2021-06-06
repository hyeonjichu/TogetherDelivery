package com.example.togetherdelivery;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class AloneMenuListActivity extends AppCompatActivity {

    FirebaseFirestore firebaseFirestore;
    RecyclerView MenuListView;
    ArrayList<MenuModel> menuModelArrayList;
    MyMenuAdapter myMenuAdapter;
    ProgressDialog progressDialog;
    String storeId, userId, limitMoney;
    ImageButton menu_shopbag_btn;
    TextView limitText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        getSupportActionBar().setIcon(R.drawable.delivery);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        menu_shopbag_btn = findViewById(R.id.menu_shopbag_btn);
        limitText = findViewById(R.id.limitText);


        Intent intent = getIntent(); // 데이터 수신
        storeId = intent.getStringExtra("storeId");
        userId=intent.getStringExtra("id");
        limitMoney = intent.getStringExtra("limitMoney");

        limitText.append("\n");
        limitText.append("최소 주문금액은 "+limitMoney+"원 입니다.");

        //final CheckBox checkBox = (CheckBox)findViewById(R.id.Menu_checkBox);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("조금만 기다려 주세요!...");
        progressDialog.show();

        MenuListView = findViewById(R.id.menu_list);
        MenuListView.setHasFixedSize(true);
        MenuListView.setLayoutManager(new LinearLayoutManager(this));

        firebaseFirestore = FirebaseFirestore.getInstance();
        menuModelArrayList = new ArrayList<MenuModel>();
        myMenuAdapter = new MyMenuAdapter(AloneMenuListActivity.this,menuModelArrayList);
        MenuListView.setAdapter(myMenuAdapter);

        EventChangeListener();

        menu_shopbag_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent togetherIntent = new Intent(AloneMenuListActivity.this, AloneShopBagActivity.class);
                togetherIntent.putExtra("menuModelArrayList",menuModelArrayList);
                togetherIntent.putExtra("id",userId);
                togetherIntent.putExtra("store",storeId);
                startActivity(togetherIntent);
            }
        });

    }
    public void EventChangeListener() {
        firebaseFirestore.collection("storeMenu")
                .whereEqualTo("ceoId", storeId)
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

                                menuModelArrayList.add(dc.getDocument().toObject(MenuModel.class));
                            }
                            myMenuAdapter.notifyDataSetChanged();
                            if(progressDialog.isShowing())
                                progressDialog.dismiss();
                        }
                    }
                });
    }
}
