package com.example.findmypet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    EditText musername, mpassword;
    Button Btnsign, Btnregister;

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://find-mypet-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //รักนะอ้ั้ม 

        musername = findViewById(R.id.user);
        mpassword = findViewById(R.id.pass);
        Btnsign = findViewById(R.id.signinBt);
        Btnregister = findViewById(R.id.regigterBt);

        Btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Btnregister = new Intent(MainActivity.this,Register.class);
                startActivity(Btnregister);
            }
        });

    }
}













