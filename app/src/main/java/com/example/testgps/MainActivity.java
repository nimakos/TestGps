package com.example.testgps;

import android.Manifest;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.example.testgps.androidapi.MyGPSManager;
import com.example.testgps.googleapi.GoogleLocator;
import com.example.testgps.permissions.AbsRuntimePermission;

public class MainActivity extends AbsRuntimePermission implements MyGPSManager.GPSListener, GoogleLocator.OnLocationUpdateListener{

    private GoogleLocator googleLocator;
    private MyGPSManager myGPSManager;
    public static final int REQUEST_CODE = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public void onPermissionsGranted(int requestCode) {
        googleLocator = new GoogleLocator
                .Builder(this, this)
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
                Manifest.permission.FOREGROUND_SERVICE,
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

    }
}
