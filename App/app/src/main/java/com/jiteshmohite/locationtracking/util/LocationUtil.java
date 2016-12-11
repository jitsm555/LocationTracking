package com.jiteshmohite.locationtracking.util;

import android.database.Cursor;
import android.location.Location;

import com.google.android.gms.maps.model.LatLng;
import com.jiteshmohite.locationtracking.tasks.fusedlocation.LocationFilter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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


    /**
     * Used to get Locations based on time
     */
    public static List<Location> getLocationBasedOnTime(long startTime, long endTime) {
        List<Location> locationList = new ArrayList<Location>();
        HashMap<String, Location> locationMap = LocationFilter.getInstance().getLocation();
        for (Map.Entry<String, Location> entry : locationMap.entrySet()) {
            if (DateUtils.getTimeInUTC(entry.getKey()) >= startTime && DateUtils.getTimeInUTC(entry.getKey())
                    <= endTime) {
                locationList.add(entry.getValue());
            }
        }
        return locationList;
    }
    // used to get LatLongList
    public static List<LatLng> getLatLongList(List<Location> locationList) {
        List<LatLng> latLngList = new ArrayList<LatLng>();
        for (Location location : locationList) {
            latLngList.add(new LatLng(location.getLatitude(), location.getLongitude()));
        }
        return latLngList;
    }
}
