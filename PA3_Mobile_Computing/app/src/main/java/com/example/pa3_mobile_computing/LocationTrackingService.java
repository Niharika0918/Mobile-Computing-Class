package com.example.pa3_mobile_computing;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class LocationTrackingService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();

        Toast.makeText(
                this,
                "Location Service Created",
                Toast.LENGTH_SHORT
        ).show();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Toast.makeText(
                this,
                "Location Tracking Started",
                Toast.LENGTH_SHORT
        ).show();

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Toast.makeText(
                this,
                "Location Tracking Stopped",
                Toast.LENGTH_SHORT
        ).show();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}