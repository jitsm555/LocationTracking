package com.jiteshmohite.locationtracking.service;

import android.app.IntentService;
import android.content.Intent;
import android.location.Location;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;

import com.google.android.gms.location.LocationResult;
import com.jiteshmohite.locationtracking.data.LocationTrackerStore;

import static com.jiteshmohite.locationtracking.util.LogUtils.LOGD;


/**
 * LocationTrackerService service used for handling incoming intents that are generated as a result
 * of requesting location updates Created by jitesh on 7/1/16.
 */
public class LocationTrackerService extends IntentService {

    private static final String TAG = LocationTrackerService.class.getName();

    /**
     * This constructor is required, and calls the super IntentService(String) constructor with the
     * name for a worker thread.
     */
    public LocationTrackerService() {
        // Use the TAG to name the worker thread.
        super(TAG);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        LOGD(TAG, "onHandleIntent");
//        Intent localIntent = new Intent(Constants.BROADCAST_ACTION);
        LocationResult locationResult = LocationResult.extractResult(intent);
        if (locationResult != null) {
            Location location = locationResult.getLastLocation();
            if (location != null) {
                LocationTrackerStore trackerStore = new LocationTrackerStore();
                boolean result = trackerStore.saveLocation(getApplicationContext(), location);
                if (result) {
                    // Broadcast the locations
//                    localIntent.putExtra(Constants.ACTIVITY_EXTRA, location);
//                    LocalBroadcastManager.getInstance(this).sendBroadcast(localIntent);
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LOGD(TAG, "LocationTrackerService Destroyed");
    }
}