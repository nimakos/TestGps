package com.example.testgps.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;

import androidx.annotation.NonNull;

public final class MyBroadcastReceiver extends BroadcastReceiver {

    public static final String LOCATION_UPDATE = "location_update";
    public static final String SUCCESS_UPDATE = "success_update";
    public static final String COORDINATES = "coordinates";


    public interface OnLocationUpdateListener {
        void getGoogleLocationUpdate(Location location);
    }

    public interface OnSuccessUpdateListener {
        void getGoogleSuccessLocationUpdate(Location location);
    }

    private OnLocationUpdateListener onLocationUpdateListener;
    private OnSuccessUpdateListener onSuccessListener;

    private MyBroadcastReceiver(@NonNull Builder builder) {
        this.onLocationUpdateListener = builder.onLocationUpdateListener;
        this.onSuccessListener = builder.onSuccessListener;
    }

    public static class Builder {
        private OnLocationUpdateListener onLocationUpdateListener;
        private OnSuccessUpdateListener onSuccessListener;

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
                    if (onLocationUpdateListener != null) {
                        onLocationUpdateListener.getGoogleLocationUpdate(location);
                    }
                }
            }
            if (action.equals(SUCCESS_UPDATE)) {
                if (intent.getExtras() != null) {
                    Location location = (Location) intent.getExtras().get(COORDINATES);
                    if (onSuccessListener != null) {
                        onSuccessListener.getGoogleSuccessLocationUpdate(location);
                    }
                }
            }
        }
    }
}
