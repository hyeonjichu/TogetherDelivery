package com.example.togetherdelivery;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ChatMessageActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;                     // 리싸이클 뷰를 위한 추가
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<ChatData> chatList;
    private String nick;  // 1 : 다 ~

    private DatabaseReference myRef;;
    private EditText EditText_chat;

    private ImageButton Button_send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_main);

        getSupportActionBar().setIcon(R.drawable.delivery);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent togetherIntent = getIntent();
        String childName = togetherIntent.getStringExtra("orderId");
        String storeName = togetherIntent.getStringExtra("storeName");
        String userId = togetherIntent.getStringExtra("id");
        nick = userId;
        Button_send = findViewById(R.id.chat_btn);
        EditText_chat = findViewById(R.id.EditText_chat);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference().child(childName).child(storeName);

        Button_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message =  EditText_chat.getText().toString(); // msg
                if(message != null) {
                    ChatData chat = new ChatData();
                    chat.setNick(nick);
                    chat.setMessage(message);
                    myRef.push().setValue(chat);
                }

            }
        });


        mRecyclerView = findViewById(R.id.my_recycler_view);    // 리싸이클 최초 기본 세팅
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        chatList = new ArrayList<>();
        mAdapter = new ChatAdapter(chatList, ChatMessageActivity.this, nick);                            // 어뎁터 최초 기본 세팅
        mRecyclerView.setAdapter(mAdapter);




        // caution !!
        myRef.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {


                // Snapshot = 채팅 데이터를 가지고 있음
                // Snapshot.getValue = 채팅 데이터를 가지고 오는 변수
                //Log.d("CHATCHAT", snapshot.getValue().toString());
                ChatData chat = snapshot.getValue(ChatData.class);
                ((ChatAdapter) mAdapter).addChat(chat);
            }

            @Override
            public void onChildChanged(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull @NotNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

    }
}

