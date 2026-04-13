# &#x20;Phone Orientation Estimator (Android)

## &#x20;Project Overview

This project is an Android application that estimates the **orientation of a mobile device in real time** using motion sensors. The app reads data from the **accelerometer** and **gyroscope**, processes it, and displays both raw values and interpreted orientation.

Developed for **Mobile Computing – Assignment 2**.

\---

## &#x20;Objectives

* Access and use motion sensors (Accelerometer \& Gyroscope)
* Interpret sensor data to determine device orientation
* Implement lifecycle-aware sensor handling
* Display real-time updates in a clean UI

\---

## &#x20;Features

* &#x20;Real-time Accelerometer data (X, Y, Z)
* &#x20;Real-time Gyroscope data (X, Y, Z)
* 📐 Orientation calculation using:

  * Pitch
  * Roll
  * Yaw
* 🧭 Text-based orientation output:

  * Upright
  * Tilted Left
  * Tilted Right
  * Face Up
  * Face Down
  * Flat
* &#x20;Continuous UI updates
* &#x20;Handles missing sensors gracefully

\---

## &#x20;How It Works

### 1\. Sensor Data Collection

* Uses `SensorManager` to access:

  * Accelerometer → detects gravity \& tilt
  * Gyroscope → detects rotation

### 2\. Orientation Calculation

* **Pitch** → rotation around X-axis
* **Roll** → rotation around Y-axis
* **Yaw** → rotation around Z-axis

### 3\. Orientation Logic (Example)

* Roll > 90 → Tilted Left
* Roll < -90 → Tilted Right
* Pitch high → Upright / Flat
* Z ≈ +9.8 → Face Up
* Z ≈ -9.8 → Face Down



## User Interface

The app includes:

* Accelerometer Data Section
* Gyroscope Data Section
* Orientation Output Section

Clean and readable layout for real-time updates.



## Lifecycle Handling

* Sensors registered in `onResume()`
* Sensors unregistered in `onPause()`

✔ Prevents battery drain  
✔ Avoids background sensor usage



## Technologies Used

* Java
* Android Studio
* Android SDK (API 34+)
* SensorManager API
* 

## Project Structure

```
PhoneOrientationApp/
│── MainActivity.java
│── activity\_main.xml
│── AndroidManifest.xml
│── res/
│   ├── layout/
│   ├── values/
```

## Assumptions

* Device has accelerometer and gyroscope
* Simple threshold-based orientation logic is used
* No advanced sensor fusion implemented



## Possible Enhancements

* Add graphical orientation indicator
* Smooth sensor values (reduce noise)
* Add calibration/reset button
* Support landscape mode
* Add animations



## Conclusion

This application demonstrates:

* Real-time sensor data handling
* Orientation estimation
* Lifecycle-aware programming
* Clean and responsive UI

It satisfies all requirements of the assignment.

## 

## Author

Niharika Kesana

