package com.example.testgps;

import android.Manifest;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.testgps.androidapi.MyGPSManager;
import com.example.testgps.googleapi.GoogleLocator;
import com.example.testgps.permissions.AbsRuntimePermission;

import static com.example.testgps.receiver.MyBroadcastReceiver.COORDINATES;
import static com.example.testgps.receiver.MyBroadcastReceiver.LOCATION_UPDATE;
import static com.example.testgps.receiver.MyBroadcastReceiver.SUCCESS_UPDATE;

public class MainActivity extends AbsRuntimePermission implements MyGPSManager.GPSListener, GoogleLocator.OnLocationUpdateListener, GoogleLocator.OnSuccessListener{

    private GoogleLocator googleLocator;
    private MyGPSManager myGPSManager;
    public static final int REQUEST_CODE = 10;
    Button buttonSecond;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonSecond = findViewById(R.id.button_sec);
        buttonSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent secView = new Intent(getApplicationContext(), SecondActivity.class);
                startActivity(secView);
            }
        });
    }


    @Override
    public void onPermissionsGranted(int requestCode) {
        googleLocator = new GoogleLocator
                .Builder(this)
                .setLocationListener(this)
                .setSuccessListener(this)
                .setUpdateInterval(1000)
                .setFastestInterval(1)
                .hasSingleInstance(true)
                .build();
        /*myGPSManager = new MyGPSManager
                .Builder(this, this)
                .setMinimumDistance(1)
                .setMinimumTime(1)
                .build();*/
    }



    @Override
    protected void onStart() {
        super.onStart();

        requestAppPermissions(new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_NETWORK_STATE,

        }, REQUEST_CODE);
    }


    @Override
    public void getLocationUpdate(Location location) {

    }

    @Override
    public void getSpeedUpdate(float speed) {

    }

    @Override
    public void onGpsNetworkStatusUpdate(String status) {

    }

    @Override
    public void getLocationAsynchronousUpdate(Location location) {

    }

    @Override
    public void getGoogleLocationUpdate(Location location) {
        Intent i = new Intent(LOCATION_UPDATE);
        i.putExtra(COORDINATES, location);
        sendBroadcast(i);
    }

    @Override
    public void onSuccess(Location location) {
        Intent i = new Intent(SUCCESS_UPDATE);
        i.putExtra(COORDINATES, location);
        sendBroadcast(i);
    }
}
