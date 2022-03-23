package com.example.findmypet;

import androidx.activity.OnBackPressedDispatcherOwner;
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

    private  EditText username,password,confirmpassword,name,number;
    private  Button Bregister;

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://find-mypet-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        confirmpassword = findViewById(R.id.confirmpassword);
        name = findViewById(R.id.name);
        number = findViewById(R.id.number);
        Bregister = findViewById(R.id.register);

        Bregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String usertxt = username.getText().toString();
                String passwordtxt = password.getText().toString();
                String conpasswordtxt = confirmpassword.getText().toString();
                String nametxt = name.getText().toString();
                String numbertxt = number.getText().toString();

                if(usertxt.isEmpty() || passwordtxt.isEmpty() || conpasswordtxt.isEmpty()
                        ||  nametxt.isEmpty() || numbertxt.isEmpty()){

                   Toast.makeText(Register.this,"กรุงณาใส่ข้อมูลให้ครบทุกช่อง",Toast.LENGTH_SHORT).show();
                }
                else if (!passwordtxt.equals(conpasswordtxt)){
                    Toast.makeText(Register.this,"รหัสผ่านไม่ตรงกัน",Toast.LENGTH_SHORT).show();
                }

                else {

                    databaseReference.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            if (snapshot.hasChild(usertxt)){
                                Toast.makeText(Register.this,"ผู้ใช้นี้ได้ลงทะเบียนแล้ว",Toast.LENGTH_SHORT).show();
                            }
                            else {
                                databaseReference.child("Users").child(usertxt).child("username").setValue(usertxt);
                                databaseReference.child("Users").child(usertxt).child("password").setValue(passwordtxt);
                                databaseReference.child("Users").child(usertxt).child("name").setValue(nametxt);
                                databaseReference.child("Users").child(usertxt).child("number").setValue(numbertxt);
                                databaseReference.child("Users").child(usertxt).child("imageURL").setValue("");
                                databaseReference.child("Users").child(usertxt).child("type").setValue("แมว");
                                Toast.makeText(Register.this,"ลงทะเบียนผู้ใช้สำเร็จ",Toast.LENGTH_SHORT).show();
                                finish();

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }


            }
        });

    }

}

