package com.example.testgps.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;

import androidx.annotation.NonNull;

public final class MyBroadcastReceiver extends BroadcastReceiver {

    public static final String LOCATION_UPDATE = "location_update";
    public static final String SUCCESS_UPDATE = "success_update";
    public static final String SPEED_UPDATE = "speed_update";

    public static final String COORDINATES = "coordinates";
    public static final String SPEED = "speed";


    public interface OnLocationUpdateListener {
        void getGoogleLocationUpdate(Location location);
    }

    public interface OnSuccessUpdateListener {
        void getGoogleSuccessLocationUpdate(Location location);
    }

    public interface  OnSpeedUpdateListener {
        void getGoogleSpeedUpdate(float speed);
    }

    private OnLocationUpdateListener onLocationUpdateListener;
    private OnSuccessUpdateListener onSuccessListener;
    private OnSpeedUpdateListener onSpeedUpdateListener;

    private MyBroadcastReceiver(@NonNull Builder builder) {
        this.onLocationUpdateListener = builder.onLocationUpdateListener;
        this.onSuccessListener = builder.onSuccessListener;
        this.onSpeedUpdateListener = builder.onSpeedUpdateListener;
    }

    public static class Builder {
        private OnLocationUpdateListener onLocationUpdateListener;
        private OnSuccessUpdateListener onSuccessListener;
        private OnSpeedUpdateListener onSpeedUpdateListener;

        public Builder() {
        }

        public Builder setLocationUpdate(OnLocationUpdateListener locationUpdate) {
            this.onLocationUpdateListener = locationUpdate;

            return this;
        }

        public Builder setSuccessLocationUpdate(OnSuccessUpdateListener successLocationUpdate) {
            this.onSuccessListener = successLocationUpdate;

            return this;
        }

        public Builder setSpeedUpdate(OnSpeedUpdateListener speedUpdate) {
            this.onSpeedUpdateListener = speedUpdate;

            return this;
        }

        public MyBroadcastReceiver build() {
            return new MyBroadcastReceiver(this);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if (action != null) {
            if (action.equals(LOCATION_UPDATE)) {
                if (intent.getExtras() != null) {
                    Location location = (Location) intent.getExtras().get(COORDINATES);
                    if (onLocationUpdateListener != null && location != null) {
                        onLocationUpdateListener.getGoogleLocationUpdate(location);
                    }
                }
            }
            if (action.equals(SUCCESS_UPDATE)) {
                if (intent.getExtras() != null) {
                    Location location = (Location) intent.getExtras().get(COORDINATES);
                    if (onSuccessListener != null && location != null) {
                        onSuccessListener.getGoogleSuccessLocationUpdate(location);
                    }
                }
            }
            if (action.equals(SPEED_UPDATE)) {
                if (intent.getExtras() != null) {
                    float speed = (float) intent.getExtras().get(SPEED);
                    if (onSpeedUpdateListener != null) {
                        onSpeedUpdateListener.getGoogleSpeedUpdate(speed);
                    }
                }
            }
        }
    }
}
