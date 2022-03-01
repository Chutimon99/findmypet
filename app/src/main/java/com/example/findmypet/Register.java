package com.example.findmypet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class Register extends AppCompatActivity{

    private  EditText musername,mpassword,mconfirmpassword,mnamepet,maddress,mname,mnumber;
    private  Button Bregister;

    private  FirebaseDatabase db = FirebaseDatabase.getInstance();
    private  DatabaseReference root = db.getReference().child("Users ");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        musername = findViewById(R.id.username);
        mpassword = findViewById(R.id.password);
       mconfirmpassword = findViewById(R.id.confirmpassword);
       mnamepet = findViewById(R.id.namepet);
       maddress = findViewById(R.id.address);
       mname = findViewById(R.id.name);
       mnumber = findViewById(R.id.number);
       Bregister = findViewById(R.id.register);


       Bregister.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               Intent Bregister = new Intent(Register.this,MainActivity.class);
              startActivity(Bregister);

               String username = musername.getText().toString();
               String password = mpassword.getText().toString();
               String confirmpassword = mconfirmpassword.getText().toString();
               String namepet = mnamepet.getText().toString();
               String address = maddress.getText().toString();
               String name = mname.getText().toString();
               String number = mnumber.getText().toString();

               HashMap<String , String> userMap = new HashMap<>();

              userMap.put("username",username);
              userMap.put("password",password);
              userMap.put("confirmpassword",confirmpassword);
              userMap.put("namepet",namepet);
              userMap.put("adderss",address);
              userMap.put("name",name);
              userMap.put("number",number);

              root.push().setValue(userMap);



                       }


                   });

           }

           }

