package com.mikhail.weatherclient;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.os.Looper;

import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.SettingsClient;

import java.io.IOException;
import java.util.Locale;

import static com.google.android.gms.location.LocationServices.getFusedLocationProviderClient;


public class LocationProvider {
    public static final int PERMISSION_REQUEST_CODE = 10;
    FusedLocationProviderClient mFusedLocationClient;
    LocationCallback locationCallback;
    private static LocationProvider instance = null;
    private Activity activity;
    LocationRequest mLocationRequest;
    Float latitude;
    Float longitude;
    long UPDATE_INTERVAL = 3000;
    long FASTEST_INTERVAL = 1000;
    String cityNameByLocator;

    public static LocationProvider getLocationProvider(Activity activity) {
        instance = instance == null ? new LocationProvider(activity) : instance;
        return instance;
    }


    LocationProvider(Activity activity) {
        this.activity = activity;
        requestLocationPermissions();
    }

    public static LocationProvider getInstance() {
        return instance;
    }

    public boolean isHavePermission() {
        return ActivityCompat.checkSelfPermission(
                activity, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(
                activity, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    public void requestLocationUpdates() {
        if (!isHavePermission()) {
            return;
        }
        mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        LocationSettingsRequest locationSettingsRequest = builder.build();

        SettingsClient settingsClient = LocationServices.getSettingsClient(activity);
        settingsClient.checkLocationSettings(locationSettingsRequest);
        mFusedLocationClient = getFusedLocationProviderClient(activity);
       /* mFusedLocationClient.requestLocationUpdates(mLocationRequest, locationCallback = new LocationCallback() {
                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        if (locationResult != null) {
                            latitude = (float) locationResult.getLastLocation().getLatitude();
                            City_changerPresenter.getInstance().setLat(latitude);
                            longitude = (float) locationResult.getLastLocation().getLongitude();
                            City_changerPresenter.getInstance().setLon(longitude);
                            cityNameByLocator = getCityName(latitude, longitude);
                            if (cityNameByLocator.isEmpty()) return;
                            City_changerPresenter.getInstance().setCityName(cityNameByLocator);
                            WeatherProvider.getInstance().setFirstDownload(true);
                        }
                    }
                },
                Looper.myLooper());
    }*/

    public void requestLocationPermissions() {
        if (!ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.CALL_PHONE)) {
            ActivityCompat.requestPermissions(activity,
                    new String[]{
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION
                    }, PERMISSION_REQUEST_CODE);
        }
    }

    public void turnOffLocaltionListener() {
        mFusedLocationClient.removeLocationUpdates(locationCallback);

    }

    public String getCityName(double latitude, double longitude) {
        String city = null;
        Geocoder geocoder = new Geocoder(activity, Locale.getDefault());
        try {
            city = geocoder.getFromLocation(latitude, longitude, 1).get(0).getLocality();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return city;
    }

}
