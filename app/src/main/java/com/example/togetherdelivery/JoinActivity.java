package com.example.togetherdelivery;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class JoinActivity extends AppCompatActivity {

    EditText id, pw, pwChk, name, nickname, phone, email, address;
    private String TAG = "Hello";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        id=(EditText)findViewById(R.id.id);
        pw=(EditText)findViewById(R.id.pw);
        pwChk=(EditText)findViewById(R.id.pwChk);
        name=(EditText)findViewById(R.id.name);
        nickname=(EditText)findViewById(R.id.nickname);
        phone=(EditText)findViewById(R.id.phone);
        email=(EditText)findViewById(R.id.email);
        address=(EditText)findViewById(R.id.address);
    }
    public void onClickJoinOk(View view){
        final FirebaseFirestore db = FirebaseFirestore.getInstance();

        final String j_id=id.getText().toString();
        final String j_pw=pw.getText().toString();
        final String j_name=name.getText().toString();
        final String j_nickname=nickname.getText().toString();
        final String j_phone=phone.getText().toString();
        final String j_email=email.getText().toString();
        final String j_address=address.getText().toString();

        Map<String, Object> newUser = new HashMap<>();
        newUser.put("id",j_id);
        newUser.put("pw",j_pw);
        newUser.put("name",j_name);
        newUser.put("nickname",j_nickname);
        newUser.put("phone",j_phone);
        newUser.put("email",j_email);
        newUser.put("address",j_address);

        db.collection("userInfo").document(j_id)
                .set(newUser)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                        Intent intent = new Intent(JoinActivity.this, LoginActivity.class);
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
    public void onClickBack(View view){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
