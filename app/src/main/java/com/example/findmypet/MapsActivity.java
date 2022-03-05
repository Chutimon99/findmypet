package com.example.findmypet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;

import android.os.Build;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private List<MypetModel> mypetModelList = new ArrayList<>() ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Pet-Traking").child("01");

        myRef.addValueEventListener(new ValueEventListener() {

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue().toString();
                System.out.println( "Value is: " + value);
                collectMypetDataset((Map<String,Object>)dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                System.out.println("Failed to read value." + error.toException());
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void collectMypetDataset(Map<String,Object> users) {

        ArrayList<Long> phoneNumbers = new ArrayList<>();

        //iterate through each user, ignoring their UID
        for (Map.Entry<String, Object> entry : users.entrySet()){

            //Get user map
            Map singleUser = (Map) entry.getValue();
            //Get phone field and append to list
            //phoneNumbers.add((Long) singleUser.get("phone"));

            mypetModelList.add(new MypetModel(Double.parseDouble(singleUser.get("latitude").toString()),Double.parseDouble(singleUser.get("longtitude").toString()),singleUser.get("timestamp").toString()));
        }

        //mypetModelList.((element)->{
        //for (int i = 0 ; i<mypetModelList.size(); i++){
        MypetModel maxbytimestamp = mypetModelList.stream().max(Comparator.comparing(MypetModel::getTimestamp)).orElseThrow(NoSuchElementException::new);;
           if (mMap != null ){
               Long Timestamp = Long.parseLong(maxbytimestamp.getTimestamp());
               Date timeD = new Date(Timestamp * 1000);
               SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy เวลา:HH:mm");
               String Time = sdf. format(timeD);
                LatLng sydney = new LatLng(maxbytimestamp.getLatitude(), maxbytimestamp.getLongtitude());
                mMap.addMarker(new MarkerOptions().position(sydney).title(Time));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 18), 2000, null);
            }
        //}

        //});
//        mypetModelList.forEach((element)->{
//            if (mMap != null ){
//                //LatLng sydney = new LatLng(mypetModelList, mypetModelList);
//                //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//            }
//        });

        System.out.println(phoneNumbers.toString());
    }
    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(-34, 151);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.getUiSettings().setZoomControlsEnabled(true);
    }

}