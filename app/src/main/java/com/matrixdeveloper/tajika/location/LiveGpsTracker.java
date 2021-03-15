package com.matrixdeveloper.tajika.location;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Looper;
import android.provider.Settings;
import android.text.TextUtils;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.SettingsClient;


public class LiveGpsTracker {
    private static final LiveGpsTracker ourInstance = new LiveGpsTracker();
    public static final int REQUEST_FINE_LOCATION = 9876;
    static Activity mActivity;
    private long UPDATE_INTERVAL = 500 * 1000;  /* 5 min */
    private long FASTEST_INTERVAL = 2000; /* 2 sec */
    private LocationRequest mLocationRequest;
    static LocationUpdate mListener;
    private AlertDialog alertDialog;
    private FusedLocationProviderClient mFusedLocationClient;
    LocationSettingsRequest.Builder locationBuilder;

    public static LiveGpsTracker getInstance(Activity activity, LocationUpdate listener) {
        mActivity = activity;
        mListener = listener;
        return ourInstance;
    }


    private LiveGpsTracker() {
    }

    // Trigger new location updates at interval
    public void initLocationUpdate() {

        int LOC_MODE = getLocationMode();
         if(LOC_MODE<=1){
            //Toast.makeText(mActivity,"Location Mode Need to change",Toast.LENGTH_LONG);
            //CatUtils.getInstance().showAlertDialogBox(mActivity,"Ensure your locaiton is on High priority mode");

            AlertDialog alertDialog = new AlertDialog.Builder(mActivity)
                    .setCancelable(false)
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            mActivity.startActivity(intent);
                        }
                    })
                    .create();
            alertDialog.setTitle("Locaiton Required");
            alertDialog.setMessage("Ensure your location setting is on 'Standard or High priority' mode");
            alertDialog.show();
            return;
        }
        if(mFusedLocationClient==null){
            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(mActivity);
        }
        // Create the location request to start receiving updates
        if(mLocationRequest==null) {
            mLocationRequest = new LocationRequest();
            mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            mLocationRequest.setInterval(UPDATE_INTERVAL);
            mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
            // Create LocationSettingsRequest object using location request
            if(locationBuilder==null) {
                locationBuilder = new LocationSettingsRequest.Builder();
                locationBuilder.addLocationRequest(mLocationRequest);
            }
            LocationSettingsRequest locationSettingsRequest = locationBuilder.build();
            // Check whether location settings are satisfied
            // https://developers.google.com/android/reference/com/google/android/gms/location/SettingsClient
            SettingsClient settingsClient = LocationServices.getSettingsClient(mActivity);
            settingsClient.checkLocationSettings(locationSettingsRequest);
        }




        // new Google API SDK v11 uses getFusedLocationProviderClient(this)
        if(!isLocationEnabled(mActivity)){
            mListener.onPopupPermission();
            getGpsAlertDialog();
        }else if (ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            if(mListener!=null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    // only for LOLLIPOP and newer versions
                    requestPermissions();
                }else {
                    getGpsAlertDialog();
                }
                mListener.onPopupPermission();
            }
            return;
        }

        mFusedLocationClient.requestLocationUpdates(mLocationRequest,mLocationCallback,
                Looper.myLooper());
    }

    LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            // do work here
            onLocationChanged(locationResult.getLastLocation());
        }

        @Override
        public void onLocationAvailability(LocationAvailability locationAvailability) {
            super.onLocationAvailability(locationAvailability);
        }
    };

    /**
     * Method is responsible to remove location update
     * This is very important because location service taking too much battery
     * **/
    private void stopLocationCallback(){
        if(mFusedLocationClient!=null) {
            mFusedLocationClient.removeLocationUpdates(mLocationCallback);
        }
    }

    public int getLocationMode()
    {
        try {
            return Settings.Secure.getInt(mActivity.getContentResolver(), Settings.Secure.LOCATION_MODE);
        }catch (Exception e){
            e.printStackTrace();
        }
        return -1;

    }

    private boolean permissionExists() {
        if (ContextCompat.checkSelfPermission(mActivity,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    private void requestPermissions() {
        if(permissionExists()) {
            ActivityCompat.requestPermissions(mActivity,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_FINE_LOCATION);
        }else{
            ActivityCompat.requestPermissions(mActivity,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_FINE_LOCATION);
            if(mListener!=null) {
                mListener.OnLocationSettingNotFound();
            }
        }
        if(alertDialog!=null) {
            alertDialog.hide();
        }
    }


    public void onLocationChanged(Location location) {
        // New location has now been determined
        String msg = "Updated Location: " +
                Double.toString(location.getLatitude()) + "," +
                Double.toString(location.getLongitude());
        if(location!=null && mListener!=null && mActivity!= null) {
            mListener.onLocationFound(location.getLatitude(), location.getLongitude());
            stopLocationCallback();
        }
       //Toast.makeText(mActivity, msg, Toast.LENGTH_SHORT).show();
    }

    AlertDialog getGpsAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder.setCancelable(false);
        builder.setTitle("Location Services Not Active");
        builder.setMessage("Please enable Location Services and GPS");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {


            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                mActivity.startActivity(intent);
            }
        });
        alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
        return alertDialog;
    }

    public static boolean isLocationEnabled(Context context) {
        int locationMode = 0;
        String locationProviders;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            try {
                locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);

            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
                return false;
            }

            return locationMode != Settings.Secure.LOCATION_MODE_OFF;

        }else{
            locationProviders = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            return !TextUtils.isEmpty(locationProviders);
        }


    }


    public interface LocationUpdate{
        void onLocationFound(double latitide, double longitude);
        void OnLocationSettingNotFound();
        void onPopupPermission();
    }
}
