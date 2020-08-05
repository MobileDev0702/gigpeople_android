package com.gigpeople.app.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;

public class Tracker implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, ResultCallback<LocationSettingsResult>,
        LocationListener {

    public static final int REQUEST_CHECK_SETTINGS = 7531;

    private static final String TAG = "LocationTracker";
    LocationCallback locationCallback;
    private Activity activity;
    private LocationSettingsRequest.Builder builder;
    private PendingResult<LocationSettingsResult> result;
    private GoogleApiClient googleAPIClient;
    private Location location;
    private LocationRequest mLocationRequest;

    public Tracker(Activity activity, LocationCallback locationCallback) {
        this.activity = activity;
        this.locationCallback = locationCallback;
        if (googleAPIClient == null) {
            googleAPIClient = new GoogleApiClient.Builder(activity)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
            createLocationRequest();
        }
    }

    public Tracker(Context context, LocationCallback locationCallback) {
        this.locationCallback = locationCallback;
        if (googleAPIClient == null) {
            googleAPIClient = new GoogleApiClient.Builder(context)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
            createLocationRequest();
        }
    }

    public void connectClient() {
        googleAPIClient.connect();
    }

    public void disConnectClient() {
        if (googleAPIClient.isConnected()) {
            googleAPIClient.disconnect();
        }
    }

    public boolean isConnected() {
        return googleAPIClient.isConnected();
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.i(TAG, "Google API Connected");
        /*if (location == null) {
            setLocation(LocationServices.FusedLocationApi.getLastLocation(googleAPIClient));
        }*/
        builder = new LocationSettingsRequest.Builder()
                .setAlwaysShow(true)
                .addLocationRequest(mLocationRequest);
        result = LocationServices.SettingsApi.checkLocationSettings(googleAPIClient,
                builder.build());
        result.setResultCallback(this);
    }

    public void setLocationCallback(LocationCallback locationCallback) {
        this.locationCallback = locationCallback;
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "Google API Suspended");
        googleAPIClient.connect();
        Log.i(TAG, "Google API Re-Connecting");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.i(TAG, "Google API Failed: " + connectionResult.getErrorMessage());
    }

    //
    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setSmallestDisplacement(10); //in meters
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    private void startLocationUpdate() {
        // The final argument to {@code requestLocationUpdates()} is a LocationListener
        if (googleAPIClient.isConnected()) {

            if ((ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) && (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            LocationServices.FusedLocationApi.requestLocationUpdates(googleAPIClient, mLocationRequest, this);
        }

    }

    /**
     * Removes location updates from the FusedLocationApi.
     */
    public void stopLocationUpdates() {
        // The final argument to {@code requestLocationUpdates()} is a LocationListener
        try {
            LocationServices.FusedLocationApi.removeLocationUpdates(googleAPIClient, this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            setLocation(location);
            if (locationCallback != null) {
                locationCallback.onLocationReceived(location);
            }
        }
    }

    @Override
    public void onResult(LocationSettingsResult result) {
        final Status status = result.getStatus();
        final LocationSettingsStates settingsStates = result.getLocationSettingsStates();
        switch (status.getStatusCode()) {
            case LocationSettingsStatusCodes.SUCCESS:
                // All location settings are satisfied. The client can
                // initialize location requests here.
                startLocationUpdate();
                break;
            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                // Location settings are not satisfied, but this can be fixed
                // by showing the user a dialog.
                try {
                    // Show the dialog by calling startResolutionForResult(),
                    // and check the result in onActivityResult().
                    if (activity != null)
                        status.startResolutionForResult(activity, REQUEST_CHECK_SETTINGS);
                } catch (IntentSender.SendIntentException e) {
                    // Ignore the error.
                }
                break;
            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                // Location settings are not satisfied. However, we have no way
                // to fix the settings so we won't show the dialog.
                break;
        }
    }

    public interface LocationCallback {
        public void onLocationReceived(Location location);
    }

}

