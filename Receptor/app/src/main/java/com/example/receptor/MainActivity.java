package com.example.receptor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final String TAG = "DIEGO2:";
    private GoogleMap map;
    private Marker marker;
    private LatLng point = new LatLng(-18.0058389, -70.2254838);
    private String strAddress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        TextView txtName = findViewById(R.id.txt_name);
        TextView txtLastName = findViewById(R.id.txt_last_name);
        TextView txtAddress = findViewById(R.id.txt_address);
        TextView txtStudentCode = findViewById(R.id.txt_student_code);
        TextView txtPhoneNumber = findViewById(R.id.txt_phone_number);
        TextView txtLatitude = findViewById(R.id.txt_latitude);
        TextView txtLongitude = findViewById(R.id.txt_longitude);

        Intent intent = getIntent();
        if(Intent.ACTION_SEND.equals(intent.getAction()) && intent.getType() != null){

            txtName.setText(intent.getStringExtra("name"));
            txtLastName.setText(intent.getStringExtra("lastName"));
            txtStudentCode.setText(intent.getStringExtra("studentCode"));
            txtPhoneNumber.setText(intent.getStringExtra("phoneNumber"));
            txtAddress.setText(intent.getStringExtra("address"));
            txtLatitude.setText(intent.getStringExtra("latitude"));
            txtLongitude.setText(intent.getStringExtra("longitude"));

            strAddress = txtAddress.getText().toString();
            point = new LatLng(
                    Double.parseDouble(Objects.requireNonNull(intent.getStringExtra("latitude"))),
                    Double.parseDouble(Objects.requireNonNull(intent.getStringExtra("longitude")))
            );
            if(intent.getExtras().get(Intent.EXTRA_STREAM) != null){
                Uri img = intent.getParcelableExtra(Intent.EXTRA_STREAM);
                ImageView imageView =  findViewById(R.id.img_photo);
                imageView.setImageURI(img);
            }

        }

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        marker = map.addMarker(new MarkerOptions().position(point).title(strAddress));
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(point,14f));
    }
}
