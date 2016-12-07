package com.jiteshmohite.locationtracking.data;

import android.content.ContentValues;
import android.content.Context;
import android.location.Location;

import com.jiteshmohite.locationtracking.util.DateUtils;

import static com.jiteshmohite.locationtracking.data.LocationTrackerContract.LocationColumns.LOCATION_ACCURACY;
import static com.jiteshmohite.locationtracking.data.LocationTrackerContract.LocationColumns.LOCATION_ALTITUDE;
import static com.jiteshmohite.locationtracking.data.LocationTrackerContract.LocationColumns.LOCATION_LATITUDE;
import static com.jiteshmohite.locationtracking.data.LocationTrackerContract.LocationColumns.LOCATION_LONGITUDE;
import static com.jiteshmohite.locationtracking.data.LocationTrackerContract.LocationColumns.LOCATION_SPEED;
import static com.jiteshmohite.locationtracking.data.LocationTrackerContract.LocationColumns.LOCATION_TIME;
import static com.jiteshmohite.locationtracking.util.LogUtils.makeLogTag;

/**
 * LocationTrackerStore class will be taking care of providing api for storage Created by
 * jiteshmohite on 11/01/16.
 */
public class LocationTrackerStore {

    private static final String TAG = makeLogTag(LocationTrackerStore.class);

    /**
     * provide an api which used to insert location in db.
     */
    public boolean saveLocation(Context context, Location location) {
        ContentValues values = new ContentValues();
        values.put(LOCATION_LATITUDE, location.getLatitude());
        values.put(LOCATION_LONGITUDE, location.getLongitude());
        values.put(LOCATION_ACCURACY, location.getAccuracy());
        values.put(LOCATION_SPEED, location.getSpeed());
        values.put(LOCATION_ALTITUDE, location.getAltitude());
        values.put(LOCATION_TIME, DateUtils.getTimeInDateFormat(location.getTime()));
        return LocationTrackerDataHelper.insertLocation(context, values);
    }

}
