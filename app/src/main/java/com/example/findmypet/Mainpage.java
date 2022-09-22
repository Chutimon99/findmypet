package com.example.findmypet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class Mainpage extends AppCompatActivity {
    private Button scan1,find,Bmypet,edit,logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpage);

        Bmypet = (Button)findViewById(R.id.mypet) ;
        Bmypet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Bmypet = new Intent(Mainpage.this,Mypet.class);
                startActivity(Bmypet);
            }
        });

        scan1 =(Button)findViewById(R.id.qr);
        scan1 .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent scan1 = new Intent(Mainpage.this,Scan.class);
                startActivity(scan1);
            }
        });
        find = (Button)findViewById(R.id.findmypet);
        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent find = new Intent(Mainpage.this,MapsActivity.class);
                startActivity(find);
            }
        });

        edit = (Button)findViewById(R.id.editprofile);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent edit = new Intent(Mainpage.this,EditProfile.class);
                startActivity(edit);
            }
        });

        logout = (Button)findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getSharedPreferences("checkbox", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("remember","false");
                editor.apply();
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                //showDialogDelete();
            }
        });
    }

    private void showDialogDelete() {
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Pet-Traking");
        AlertDialog.Builder dialog = new AlertDialog.Builder(Mainpage.this);
        dialog.setTitle("ลบ");
        dialog.setMessage("คุณแน่ใจที่จะลบหรือไม่");
        dialog.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        myRef.child("01").getRef().removeValue();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }
        });
        dialog.setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = dialog.create();
        alertDialog.show();
    }

}