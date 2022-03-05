package com.example.findmypet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class Mainpage extends AppCompatActivity {
    private Button scan1,find,Bmypet;

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
    }
}