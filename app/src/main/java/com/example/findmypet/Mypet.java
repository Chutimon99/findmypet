package com.example.findmypet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class Mypet extends AppCompatActivity {

    private EditText namepet, breed;
    private TextView imageUpload;
    private ImageView UploadPet;
    private Button BtnUpload;
    private RadioGroup radioGroup ;
    private RadioButton radioButton,dog,cat ;

    private static final int IMAGE_REQUEST = 1;
    private Uri imUrl;
    private String imageURL;
    private String userid = MainActivity.getGbIdUser();

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRefUser = database.getReference("Users");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypet);



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

                String imageURL = snapshot.child(userid).child("imageURL").getValue(String.class);
                if (imageURL.isEmpty()){
                    int id = getResources().getIdentifier("@drawable/iconpet", "drawable", getPackageName());
                    UploadPet.setImageResource(id);
                }else {
                    //Picasso.get().load(imageURL).fit().centerCrop().into(UploadPet);
                    //Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/find-mypet.appspot.com/o/uploadsUser%2Fp11.jpg?alt=media&token=3ba58e5e-34ab-4b49-b109-f535cd12032b").fit().centerCrop().into(UploadPet);
                    Glide.with(getApplicationContext()).load("https://firebasestorage.googleapis.com/v0/b/find-mypet.appspot.com/o/uploadsUser%2Fp11.jpg?alt=media&token=3ba58e5e-34ab-4b49-b109-f535cd12032b").fitCenter().centerCrop().into(UploadPet);
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


                            Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_SHORT).show();
                            finish();

            }
        });






        imageUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openImage();
            }
        });
    }


    private void openImage(){
        Intent intent = new Intent();
        intent.setType("image/");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_REQUEST && resultCode == RESULT_OK){
            imUrl = data.getData();
            uploadImage();
        }

    }

    private String getFileExtension(Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void uploadImage(){
        /*ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("กำลังอัพโหลด");
        pd.show();*/

        if (imUrl != null){
            StorageReference fileRef = FirebaseStorage.getInstance().getReference().child("uploadsUser").child(userid+"."+getFileExtension(imUrl));

            fileRef.putFile(imUrl).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            imageURL = uri.toString();
                            //pd.dismiss();
                            Picasso.get().load(imageURL).fit().centerCrop().into(UploadPet);
                            Toast.makeText(getApplicationContext(),"อัพโหลดสำเร็จ",Toast.LENGTH_SHORT).show();
                            myRefUser.child(userid).child("imageURL").setValue(imageURL);

                        }
                    });
                }
            });
        }
    }

    }