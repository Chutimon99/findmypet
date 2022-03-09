package com.example.findmypet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Mypet extends AppCompatActivity implements  View.OnClickListener{

    private  static final int RESULT_LOAD_IMAGE = 1 ;

    private ImageView imageUpload;
    private Button BtnUpload;


    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://find-mypet-default-rtdb.firebaseio.com/");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypet);

        imageUpload = (ImageView) findViewById(R.id.UploadPet);
        BtnUpload = (Button) findViewById(R.id.BtnUpload);

        imageUpload.setOnClickListener(this);
        BtnUpload.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.BtnUpload:
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent,RESULT_LOAD_IMAGE);
                break;


        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && requestCode == RESULT_OK && data != null ){
            Uri selectedImage = data.getData();
            imageUpload.setImageURI(selectedImage);
        }
    }
}