package com.jiteshmohite.locationtracking.util;

import android.database.Cursor;
import android.location.Location;

import static com.jiteshmohite.locationtracking.data.LocationTrackerContract.LocationColumns.LOCATION_ACCURACY;
import static com.jiteshmohite.locationtracking.data.LocationTrackerContract.LocationColumns.LOCATION_ALTITUDE;
import static com.jiteshmohite.locationtracking.data.LocationTrackerContract.LocationColumns.LOCATION_LATITUDE;
import static com.jiteshmohite.locationtracking.data.LocationTrackerContract.LocationColumns.LOCATION_LONGITUDE;
import static com.jiteshmohite.locationtracking.data.LocationTrackerContract.LocationColumns.LOCATION_SPEED;
import static com.jiteshmohite.locationtracking.data.LocationTrackerContract.LocationColumns.LOCATION_TIME;

/**
 * Created by jitesh.mohite on 05-12-2016.
 */

public class LocationUtil {

    /**
     * Used to Create Location from cursor object
     * @param cursor
     * @return
     */

    public static Location getLocation(Cursor cursor) {
        double latitude = Double.parseDouble(cursor.getString(cursor.getColumnIndex
                (LOCATION_LATITUDE)));
        double longitude = Double.parseDouble(cursor.getString(cursor.getColumnIndex
                (LOCATION_LONGITUDE)));
        float accuracy = Float.parseFloat(cursor.getString(cursor.getColumnIndex
                (LOCATION_ACCURACY)));
        float speed = Float.parseFloat(cursor.getString(cursor.getColumnIndex
                (LOCATION_SPEED)));
        double altitude = Double.parseDouble(cursor.getString(cursor.getColumnIndex
                (LOCATION_ALTITUDE)));
        long time = DateUtils.getTimeInUTC(cursor.getString(cursor.getColumnIndex
                (LOCATION_TIME)));
        Location location = new Location("fusedLocationApi");
        location.setLatitude(latitude);
        location.setLongitude(longitude);
        location.setAccuracy(accuracy);
        location.setSpeed(speed);
        location.setAltitude(altitude);
        location.setTime(time);
        return location;
    }
}
