package com.jiteshmohite.locationtracking.tasks.fusedlocation;

import android.location.Location;
import android.text.TextUtils;

import java.util.LinkedHashMap;

/**
 * LocationFilter single ton class will be used to maintain data such as locations in
 * in-memory. Created by jitesh.mohite on 13/01/16.
 */
public class LocationFilter {
    private static LocationFilter mLocationFilter;
    private static LinkedHashMap<String, Location> mLocationMap = new LinkedHashMap<String, Location>();

    /**
     * Returns single instance of LocationFilter.
     */
    public synchronized static LocationFilter getInstance() {
        if (mLocationFilter == null) {
            mLocationFilter = new LocationFilter();
        }
        return mLocationFilter;
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