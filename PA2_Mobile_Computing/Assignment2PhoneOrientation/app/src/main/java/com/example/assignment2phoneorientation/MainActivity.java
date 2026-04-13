package com.example.assignment2phoneorientation;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor accelerometer, gyroscope;

    private TextView tvAccelX, tvAccelY, tvAccelZ;
    private TextView tvGyroX, tvGyroY, tvGyroZ;
    private TextView tvPitch, tvRoll, tvYaw, tvOrientationText;

    private float yawValue = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvAccelX = findViewById(R.id.tvAccelX);
        tvAccelY = findViewById(R.id.tvAccelY);
        tvAccelZ = findViewById(R.id.tvAccelZ);

        tvGyroX = findViewById(R.id.tvGyroX);
        tvGyroY = findViewById(R.id.tvGyroY);
        tvGyroZ = findViewById(R.id.tvGyroZ);

        tvPitch = findViewById(R.id.tvPitch);
        tvRoll = findViewById(R.id.tvRoll);
        tvYaw = findViewById(R.id.tvYaw);
        tvOrientationText = findViewById(R.id.tvOrientationText);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        if (sensorManager != null) {
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (sensorManager != null) {
            if (accelerometer != null) {
                sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);
            }

            if (gyroscope != null) {
                sensorManager.registerListener(this, gyroscope, SensorManager.SENSOR_DELAY_UI);
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (sensorManager != null) {
            sensorManager.unregisterListener(this);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            tvAccelX.setText("Accel X: " + x);
            tvAccelY.setText("Accel Y: " + y);
            tvAccelZ.setText("Accel Z: " + z);

            double pitch = Math.toDegrees(Math.atan2(-x, Math.sqrt(y * y + z * z)));
            double roll = Math.toDegrees(Math.atan2(y, z));

            tvPitch.setText("Pitch: " + pitch);
            tvRoll.setText("Roll: " + roll);

            if (z > 8) {
                tvOrientationText.setText("Orientation: Face Up");
            } else if (z < -8) {
                tvOrientationText.setText("Orientation: Face Down");
            } else if (roll > 30) {
                tvOrientationText.setText("Orientation: Tilted Left");
            } else if (roll < -30) {
                tvOrientationText.setText("Orientation: Tilted Right");
            } else if (pitch < -30) {
                tvOrientationText.setText("Orientation: Upright");
            } else {
                tvOrientationText.setText("Orientation: Flat");
            }
        }

        if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            tvGyroX.setText("Gyro X: " + x);
            tvGyroY.setText("Gyro Y: " + y);
            tvGyroZ.setText("Gyro Z: " + z);

            yawValue = z;
            tvYaw.setText("Yaw: " + yawValue);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Not needed for this assignment
    }
}