package com.example.pa3_mobile_computing;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.text.DateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private static final int LOCATION_PERMISSION_REQUEST = 100;

    private TextView statusText;
    private TextView locationText;
    private TextView savedLocationsText;

    private Button startButton;
    private Button stopButton;
    private Button clearButton;

    private LocationManager locationManager;
    private SharedPreferences sharedPreferences;

    private LocationListener locationListener;

    private static final String PREF_NAME = "LocationData";
    private static final String KEY_SAVED_LOCATIONS = "savedLocations";
    private static final String KEY_LATEST_LOCATION = "latestLocation";
    private static final String KEY_STATUS = "status";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        statusText = findViewById(R.id.statusText);
        locationText = findViewById(R.id.locationText);
        savedLocationsText = findViewById(R.id.savedLocationsText);

        startButton = findViewById(R.id.startButton);
        stopButton = findViewById(R.id.stopButton);
        clearButton = findViewById(R.id.clearButton);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        setupLocationListener();
        loadSavedData();

        startButton.setOnClickListener(v -> startTracking());
        stopButton.setOnClickListener(v -> stopTracking());
        clearButton.setOnClickListener(v -> clearSavedLocations());
    }

    private void setupLocationListener() {
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                saveLocationToLog(location);
            }
        };
    }

    private void startTracking() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST
            );
            return;
        }

        statusText.setText("Status: Tracking Active");

        sharedPreferences.edit()
                .putString(KEY_STATUS, "Status: Tracking Active")
                .apply();

        Intent serviceIntent = new Intent(this, LocationTrackingService.class);
        startService(serviceIntent);

        boolean gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean networkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (!gpsEnabled && !networkEnabled) {
            Toast.makeText(this, "Please enable location on emulator/device", Toast.LENGTH_LONG).show();
            return;
        }

        if (gpsEnabled) {
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    3000,
                    1,
                    locationListener
            );
        }

        if (networkEnabled) {
            locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    3000,
                    1,
                    locationListener
            );
        }

        Location lastLocation = null;

        if (gpsEnabled) {
            lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }

        if (lastLocation == null && networkEnabled) {
            lastLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }

        if (lastLocation != null) {
            saveLocationToLog(lastLocation);
        } else {
            locationText.setText("Waiting for location update...");
            Toast.makeText(this, "Waiting for location update", Toast.LENGTH_SHORT).show();
        }
    }

    private void stopTracking() {
        statusText.setText("Status: Tracking Stopped");

        sharedPreferences.edit()
                .putString(KEY_STATUS, "Status: Tracking Stopped")
                .apply();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            locationManager.removeUpdates(locationListener);
        }

        Intent serviceIntent = new Intent(this, LocationTrackingService.class);
        stopService(serviceIntent);

        Toast.makeText(this, "Tracking stopped", Toast.LENGTH_SHORT).show();
    }

    private void saveLocationToLog(Location location) {
        String time = DateFormat.getDateTimeInstance().format(new Date());

        String latestLocation =
                "Latest Location:\nLatitude: " + location.getLatitude()
                        + "\nLongitude: " + location.getLongitude();

        String logEntry =
                "Time: " + time
                        + "\nLatitude: " + location.getLatitude()
                        + "\nLongitude: " + location.getLongitude()
                        + "\n-----------------------------\n";

        String oldLog = sharedPreferences.getString(KEY_SAVED_LOCATIONS, "");
        String newLog = logEntry + oldLog;

        sharedPreferences.edit()
                .putString(KEY_LATEST_LOCATION, latestLocation)
                .putString(KEY_SAVED_LOCATIONS, newLog)
                .apply();

        locationText.setText(latestLocation);
        savedLocationsText.setText(newLog);

        Toast.makeText(this, "Location saved to log", Toast.LENGTH_SHORT).show();
    }

    private void loadSavedData() {
        String savedStatus = sharedPreferences.getString(KEY_STATUS, "Status: Tracking Stopped");
        String latestLocation = sharedPreferences.getString(KEY_LATEST_LOCATION, "Latest Location: Not Available");
        String savedLocations = sharedPreferences.getString(KEY_SAVED_LOCATIONS, "");

        statusText.setText(savedStatus);
        locationText.setText(latestLocation);

        if (savedLocations.trim().isEmpty()) {
            savedLocationsText.setText("No saved locations");
        } else {
            savedLocationsText.setText(savedLocations);
        }
    }

    private void clearSavedLocations() {
        sharedPreferences.edit()
                .remove(KEY_SAVED_LOCATIONS)
                .remove(KEY_LATEST_LOCATION)
                .apply();

        locationText.setText("Latest Location: Not Available");
        savedLocationsText.setText("No saved locations");

        Toast.makeText(this, "Saved locations cleared", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            @NonNull String[] permissions,
            @NonNull int[] grantResults
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == LOCATION_PERMISSION_REQUEST) {
            if (grantResults.length > 0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startTracking();
            } else {
                statusText.setText("Status: Permission Denied");
                Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            locationManager.removeUpdates(locationListener);
        }
    }
}