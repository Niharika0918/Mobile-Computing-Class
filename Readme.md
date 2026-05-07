**Location Logger App**  
**Assignment 3 – Mobile Computing**

**Project Name:** PA3\_Mobile\_Computing

**About the Project**

Location Logger is a simple Android application developed using Java in Android Studio. The main purpose of this application is to track the user’s current location, display the latest latitude and longitude, and save the location history locally on the device. This project was developed to understand and implement important Android concepts such as Location Services, Android Services, and Local Storage.  
The application is designed with a simple and user-friendly interface so users can easily manage location tracking. It allows users to start and stop tracking whenever required and keeps a record of location updates. Each location entry is stored along with its timestamp, latitude, and longitude, helping maintain a proper history of tracked locations.  
**Main Features**

* Start location tracking  
* Stop location tracking  
* Display current latitude and longitude  
* Save location logs locally on the device  
* Store location updates with date and time  
* Reload saved location logs when the app is reopened  
* Clear saved location history  
* Handle runtime location permissions  
* Use Android Service for tracking management

**How the App Works**

When the application starts, the user can begin location tracking by clicking the **Start Tracking** button. The app first checks for location permission and requests it if needed. Once permission is granted, the app starts tracking the device’s location and displays the latest latitude and longitude on the screen.  
Each location update is automatically saved in local storage and added to the saved location log section. The **Stop Tracking** button allows the user to stop receiving location updates, while the **Clear Saved Locations** button removes all saved records. The saved location logs remain stored even after closing the application, allowing users to view them again when the app is reopened.  
**Technologies Used**

* Java  
* Android Studio  
* LocationManager  
* LocationListener  
* Android Service  
* SharedPreferences

**Project Files**

PA3\_Mobile\_Computing/  
│── Assignment-3/  
│   │── MainActivity.java  
│   │── LocationTrackingService.java  
│   │── activity\_main.xml  
│   │── AndroidManifest.xml  
│   │── README.md  
**Conclusion**

This project helped in understanding how Android location tracking works and how services and local storage can be integrated into a single application. It demonstrates a practical implementation of location-based functionality while keeping the design simple, functional, and easy to use.  
**Author**

Niharika Surya Kesana  
