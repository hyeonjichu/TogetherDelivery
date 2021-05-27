package com.example.togetherdelivery;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class LoginActivity extends AppCompatActivity {

    EditText login_id, login_pw;
    private String TAG = "Hello!!!!!!!!!!!!!!!!!";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login_id = (EditText)findViewById(R.id.loginId);
        login_pw = (EditText)findViewById(R.id.loginPw);
    }
    public void onClickLogin(View view){
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        Intent intent = new Intent(this, MainActivity.class);
        final String id=login_id.getText().toString();
        final String pw=login_pw.getText().toString();

        DocumentReference doc = db.collection("userInfo").document(id);
        doc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        if(id.equals(document.get("id").toString()) && pw.equals(document.get("pw").toString())){
                            intent.putExtra("userId",id);
                            startActivity(intent);
                        }else{
                            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                            builder.setTitle("경고!");
                            builder.setMessage("아이디나 비밀번호가 일치하지 않습니다.");
                            builder.setPositiveButton("다시 시도",null);
                            builder.create().show();
                        }
                    } else {
                        Log.d(TAG, "No such document");
                        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                        builder.setTitle("경고!");
                        builder.setMessage("아이디가 존재하지 않습니다.");
                        builder.setPositiveButton("다시 시도",null);
                        builder.create().show();
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });


    }
    public void onClickJoin(View view){
        Intent intent = new Intent(this, JoinActivity.class);
        startActivity(intent);
    }
}
