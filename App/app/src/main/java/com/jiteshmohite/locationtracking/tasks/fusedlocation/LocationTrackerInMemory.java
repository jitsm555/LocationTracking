package com.jiteshmohite.locationtracking.tasks.fusedlocation;

import android.location.Location;
import android.text.TextUtils;

import java.util.LinkedHashMap;

import static com.jiteshmohite.locationtracking.util.LogUtils.makeLogTag;

/**
 * LocationTrackerInMemory single ton class will be used to maintain data such as locations in
 * in-memory. Created by jiteshmohite on 13/01/16.
 */
public class LocationTrackerInMemory {

    private static final String TAG = makeLogTag(LocationTrackerInMemory.class);
    private static LocationTrackerInMemory mLocationTrackerInMemory;
    private static LinkedHashMap<String, Location> mLocationMap = new LinkedHashMap<String, Location>();

    /**
     * Returns single instance of LocationTrackerInMemory.
     */
    public synchronized static LocationTrackerInMemory getInstance() {
        if (mLocationTrackerInMemory == null) {
            mLocationTrackerInMemory = new LocationTrackerInMemory();
        }
        return mLocationTrackerInMemory;
    }

    /**
     * contains location's in HashMap which would be accessible overall app.
     */
    public void setLocation(String time, Location location) {
        if (TextUtils.isEmpty(time)) {
            return;
        }
        if (mLocationMap.containsKey(time)) {
            mLocationMap.remove(time);
        }
        mLocationMap.put(time, location);

    }

    /**
     * Used to get locations
     */
    public LinkedHashMap<String, Location> getLocation() {
        if (mLocationMap == null) {
            return null;
        }
        return mLocationMap;
    }
}