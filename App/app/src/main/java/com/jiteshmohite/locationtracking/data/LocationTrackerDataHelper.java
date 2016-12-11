package com.jiteshmohite.locationtracking.data;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;

import static com.jiteshmohite.locationtracking.data.LocationTrackerContract.Locations.CONTENT_URI;
import static com.jiteshmohite.locationtracking.util.LogUtils.LOGD;
import static com.jiteshmohite.locationtracking.util.LogUtils.makeLogTag;


/**
 * LocationTrackerDataHelper class will be taking care of all functionality of insert,delete, update
 * and retrieve. Created by jitesh.mohite on 10/12/16.
 */
public class LocationTrackerDataHelper {

    private static final String TAG = makeLogTag(LocationTrackerDataHelper.class);

    /**
     * Used to insert locations in database
     */
    public static boolean insertLocation(Context context, ContentValues values) {
        boolean result = false;
        Uri uri = context.getContentResolver().insert(CONTENT_URI, values);
        if (uri != null) {
            LOGD(TAG, "URI After insert :" + uri);
            result = true;
        }
        return result;
    }

}
