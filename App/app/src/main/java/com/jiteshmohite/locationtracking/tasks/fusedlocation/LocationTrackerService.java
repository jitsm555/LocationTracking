package com.jiteshmohite.locationtracking.tasks.fusedlocation;

import android.app.IntentService;
import android.content.Intent;
import android.location.Location;
import android.os.IBinder;

import com.google.android.gms.location.LocationResult;
import com.jiteshmohite.locationtracking.data.LocationTrackerStore;

import static com.jiteshmohite.locationtracking.util.LogUtils.LOGD;


/**
 * LocationTrackerService service used for handling incoming intents that are generated as a result
 * of requesting location updates Created by jitesh on 7/1/16.
 */
public class LocationTrackerService extends IntentService {

    private static final String TAG = LocationTrackerService.class.getName();
    private LocationTrackerStore trackerStore;

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
        trackerStore = new LocationTrackerStore();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        LOGD(TAG, "onHandleIntent");
        LocationResult locationResult = LocationResult.extractResult(intent);
        if (locationResult != null) {
            Location location = locationResult.getLastLocation();
            if (location != null) {
                trackerStore.saveLocation(getApplicationContext(), location);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LOGD(TAG, "LocationTrackerService Destroyed");
        trackerStore = null;
    }
}