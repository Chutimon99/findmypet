package com.example.findmypet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
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

    private static String gbUserID;
    public static String getGbIdUser() {
        return gbUserID;
    }
    public static void setGbIdUser(String gbIdUser) {
        gbUserID = gbIdUser;
    }

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://find-mypet-default-rtdb.firebaseio.com/");

    String usertxt = "" ;
    String passwordtxt = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        musername = findViewById(R.id.user);
        mpassword = findViewById(R.id.pass);
        Btnsign = findViewById(R.id.signinBt);
        Btnregister = findViewById(R.id.regigterBt);

        SharedPreferences keep = PreferenceManager.getDefaultSharedPreferences(this);
        String user = keep.getString("memuser",usertxt);
        musername.setText(user);
        String pass = keep.getString("pass",passwordtxt);
        mpassword.setText(pass);

        SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
        String checkbox = preferences.getString("remember", "");
        if (checkbox.equals("true")) {

            MainActivity.setGbIdUser(user);
            Intent intent = new Intent(getApplicationContext(), Mainpage.class);
            startActivity(intent);
            finish();
        } else if (checkbox.equals("false")) {

        }


        Btnsign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usertxt = musername.getText().toString();
                passwordtxt = mpassword.getText().toString();

                if(usertxt.isEmpty() || passwordtxt.isEmpty()){
                    Toast.makeText(MainActivity.this,"กรุณาใส่ชื่อผู้ใช้และรหัสผ่าน",Toast.LENGTH_SHORT).show();
                }
                else {

                    databaseReference.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            if(snapshot.hasChild(usertxt)){

                                String getpassword = snapshot.child(usertxt).child("password").getValue(String.class);
                                if(getpassword.equals(passwordtxt)){
                                    Toast.makeText(MainActivity.this,"Successfully",Toast.LENGTH_SHORT).show();

                                    MainActivity.setGbIdUser(usertxt);

                                    SharedPreferences.Editor editorkp = keep.edit();
                                    editorkp.putString("memuser", usertxt);
                                    editorkp.putString("pass", passwordtxt);
                                    editorkp.apply();

                                    SharedPreferences.Editor editor = preferences.edit();
                                    editor.putString("remember","true");
                                    editor.apply();

                                    Intent intent = new Intent(MainActivity.this,Mainpage.class);
                                    startActivity(intent);
                                    finish();
                                }
                                else {
                                    Toast.makeText(MainActivity.this,"รหัสผ่านไม่ถูกต้อง",Toast.LENGTH_SHORT).show();

                                }
                            }
                            else {
                                Toast.makeText(MainActivity.this,"รหัสผ่านไม่ถูกต้อง" , Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            }
        });

        Btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Btnregister = new Intent(MainActivity.this,Register.class);
                startActivity(Btnregister);
            }

        });

    }
}













