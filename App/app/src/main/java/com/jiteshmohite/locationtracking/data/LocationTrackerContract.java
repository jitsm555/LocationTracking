package com.jiteshmohite.locationtracking.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Contract class for interacting with {@link LocationTrackerProvider}. Created by jiteshmohite on
 * 09/01/16.
 */
public class LocationTrackerContract {

    public static final String CONTENT_TYPE_APP_BASE = "locationtracker";

    public static final String CONTENT_TYPE_BASE = "vnd.android.cursor.dir/vnd."
            + CONTENT_TYPE_APP_BASE;

    public static final String CONTENT_ITEM_TYPE_BASE = "vnd.android.cursor.item/vnd."
            + CONTENT_TYPE_APP_BASE;

    // LocationColumns will contains all necessary params which need to create database
    public interface LocationColumns {
        String LOCATION_LATITUDE = "location_latitude";
        String LOCATION_LONGITUDE = "location_longitude";
        String LOCATION_ACCURACY = "location_accuracy";
        String LOCATION_SPEED = "location_speed";
        String LOCATION_ALTITUDE = "location_altitude";
        String LOCATION_TIME = "location_time";
    }


    public static final String CONTENT_AUTHORITY = "com.kratinmobile.locationtracker";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    private static final String PATH_LOCATIONS = "locations";

    public static class Locations implements LocationColumns, BaseColumns {


        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_LOCATIONS).build();

        public static final String CONTENT_TYPE_ID = "location";


    }

    public static String makeContentItemType(String id) {
        if (id != null) {
            return CONTENT_ITEM_TYPE_BASE + id;
        } else {
            return null;
        }
    }

    public static String makeContentType(String id) {
        if (id != null) {
            return CONTENT_TYPE_BASE + id;
        } else {
            return null;
        }
    }
}
