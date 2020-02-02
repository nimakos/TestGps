package com.example.testgps.service;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.example.testgps.googleapi.GoogleLocator;
import com.example.testgps.broadcast.MyBroadcastReceiver;

public class GpsService extends Service implements GoogleLocator.OnLocationUpdateListener {

    private GoogleLocator googleLocator;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        googleLocator = new GoogleLocator.Builder(this, this)
                .setUpdateInterval(1000)
                .setFastestInterval(1)
                .hasSingleInstance(true)
                .build();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        //startForeground(9, buildNotification());

        return START_STICKY;
    }

    @Override
    public void getGoogleLocationUpdate(Location location) {
        Intent i = new Intent(MyBroadcastReceiver.LOCATION_UPDATE);
        i.putExtra(MyBroadcastReceiver.COORDINATES, location);
        sendBroadcast(i);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (googleLocator != null) {
            googleLocator.destroyInstance();
            googleLocator = null;
        }
    }

    private Notification buildNotification() {
        Notification notification = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // For foreground service
            Intent notificationIntent = new Intent(this, GpsService.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

            // Creating channel for notification
            String id = GpsService.class.getSimpleName();
            String name = GpsService.class.getSimpleName();
            NotificationChannel notificationChannel = new NotificationChannel(id, name, NotificationManager.IMPORTANCE_HIGH);
            NotificationManager service = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            if (service != null) {
                service.createNotificationChannel(notificationChannel);
            }

            // Foreground notification
             notification = new Notification.Builder(this, id)
                    .setContentTitle("movie")
                    .setContentText("Show service running reason to user")
                    .setContentIntent(pendingIntent)
                    .setTicker("Ticker text")
                    .build();
        }
        return notification;
    }
}
