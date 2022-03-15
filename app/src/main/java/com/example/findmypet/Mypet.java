package com.example.findmypet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class Mypet extends AppCompatActivity {

    private EditText namepet, breed;
    private TextView imageUpload;
    private ImageView UploadPet;
    public Uri imageUri;
    private Button BtnUpload;
    private RadioGroup radioGroup ;
    private RadioButton radioButton,dog,cat ;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRefUser = database.getReference("Users");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypet);

        String userid = MainActivity.getGbIdUser();

        namepet = findViewById(R.id.namepet);
        breed = findViewById(R.id.breed);

        radioGroup = findViewById(R.id.radiogroup);
        dog = findViewById(R.id.dog);
        cat = findViewById(R.id.cat);

        UploadPet = findViewById(R.id.UploadPet);
        imageUpload = findViewById(R.id.imageUpload);
        BtnUpload = findViewById(R.id.BtnUpload);


    myRefUser.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            if(snapshot.hasChild(userid)){
                String type = snapshot.child(userid).child("type").getValue(String.class);
                String name = snapshot.child(userid).child("namepet").getValue(String.class);
                namepet.setText(name);

                String breedtxt = snapshot.child(userid).child("breed").getValue(String.class);
                breed.setText(breedtxt);

                int typepet = 0;
                if (type.equals("สุนัข")){
                    typepet = 1;
                    dog.setChecked(true);
                }else {
                    if (type.equals("แมว")){
                        typepet = 0;
                        cat.setChecked(true);
                    } 
                }
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    });
        BtnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int radio = radioGroup.getCheckedRadioButtonId();
                radioButton = findViewById(radio);

               String namepettxt = namepet.getText().toString();
               //String typetxt = type.getText().toString();
               String breedtxt = breed.getText().toString();
               String typetxt = radioButton.getText().toString();


                            myRefUser.child(userid).child("namepet").setValue(namepettxt);
                            //myRefUser.child(userid).child("type").setValue(typetxt);
                            myRefUser.child(userid).child("breed").setValue(breedtxt);
                            myRefUser.child(userid).child("type").setValue(typetxt);


                            Toast.makeText(getApplicationContext(),"บันทึก",Toast.LENGTH_SHORT).show();

            }
        });






        imageUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choosePicture();
            }
        });
    }


    private void choosePicture() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,@Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode ==1 && requestCode == RESULT_OK && data != null && data.getData()!= null ){
            imageUri = data.getData();
            UploadPet.setImageURI(imageUri);
            uploadPicture();
        }
    }

    private void uploadPicture() {

    }

    }