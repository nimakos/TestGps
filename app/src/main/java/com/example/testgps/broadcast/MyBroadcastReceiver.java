package com.example.testgps.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;

import androidx.annotation.NonNull;

public final class MyBroadcastReceiver extends BroadcastReceiver {

    public static final String LOCATION_UPDATE = "location_update";
    public static final String COORDINATES = "coordinates";

    public interface OnLocationUpdateListener {
        void getGoogleLocationUpdate(Location location);
    }

    private OnLocationUpdateListener onLocationUpdateListener;

    private MyBroadcastReceiver(@NonNull Builder builder) {
        this.onLocationUpdateListener = builder.onLocationUpdateListener;
    }

    public static class Builder {
        private OnLocationUpdateListener onLocationUpdateListener;

        public Builder() {
        }

        public Builder setLocationUpdate(OnLocationUpdateListener locationUpdate) {
            this.onLocationUpdateListener = locationUpdate;

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
            if (LOCATION_UPDATE.equals(action)) {
                if (intent.getExtras() != null) {
                    Location location = (Location) intent.getExtras().get(COORDINATES);
                    if (onLocationUpdateListener != null) {
                        onLocationUpdateListener.getGoogleLocationUpdate(location);
                    }
                }
            }
        }
    }
}
