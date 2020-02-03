package com.example.testgps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.IntentFilter;
import android.location.Location;
import android.os.Bundle;

import com.example.testgps.receiver.MyBroadcastReceiver;

import static com.example.testgps.receiver.MyBroadcastReceiver.LOCATION_UPDATE;
import static com.example.testgps.receiver.MyBroadcastReceiver.SPEED_UPDATE;
import static com.example.testgps.receiver.MyBroadcastReceiver.SUCCESS_UPDATE;

public class SecondActivity extends AppCompatActivity implements
        MyBroadcastReceiver.OnLocationUpdateListener,
        MyBroadcastReceiver.OnSuccessUpdateListener,
        MyBroadcastReceiver.OnSpeedUpdateListener{

    MyBroadcastReceiver myBroadcastReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (myBroadcastReceiver == null) {
            myBroadcastReceiver = new MyBroadcastReceiver
                    .Builder()
                    .setLocationUpdate(this)
                    .setSuccessLocationUpdate(this)
                    .setSpeedUpdate(this)
                    .build();
        }
        registerReceiver(myBroadcastReceiver, new IntentFilter(LOCATION_UPDATE));
        registerReceiver(myBroadcastReceiver, new IntentFilter(SUCCESS_UPDATE));
        registerReceiver(myBroadcastReceiver, new IntentFilter(SPEED_UPDATE));
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (myBroadcastReceiver != null)
            unregisterReceiver(myBroadcastReceiver);
    }

    @Override
    public void getGoogleLocationUpdate(Location location) {

    }

    @Override
    public void getGoogleSuccessLocationUpdate(Location location) {

    }

    @Override
    public void getGoogleSpeedUpdate(float speed) {

    }
}
