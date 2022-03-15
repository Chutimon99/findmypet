package com.example.findmypet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditProfile extends AppCompatActivity {

    private EditText Editusername,Editpassword,Editconfirmpassword,Editname,Editnumber;
    private Button Btnsave;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRefUser = database.getReference("Users");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        String userid = MainActivity.getGbIdUser();



        Editpassword = findViewById(R.id.Editpassword);
        Editconfirmpassword = findViewById(R.id.Editconfirmpassword);
        Editname = findViewById(R.id.Editname);
        Editnumber = findViewById(R.id.Editnumber);
        Btnsave = findViewById(R.id.save);

        Btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String inputEditpassword = Editpassword.getText().toString();
                String inputEditconpassword = Editconfirmpassword.getText().toString();
                String inputEditname = Editname.getText().toString();
                String inputEditnumber = Editnumber.getText().toString();

                myRefUser.child(userid).child("name").setValue(inputEditname);
                myRefUser.child(userid).child("number").setValue(inputEditnumber);


                    if (inputEditpassword.equals(inputEditconpassword)){
                        myRefUser.child(userid).child("password").setValue(inputEditpassword);
                    }else {
                        Toast.makeText(getApplicationContext(),"wrong password",Toast.LENGTH_SHORT).show();
                    }


            }
        });

        myRefUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        showAllData();
    }

    private void showAllData() {

    }



}