package com.jiteshmohite.locationtracking.util;

import android.content.Context;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

/**
 * Created by jitesh.mohite on 06-12-2016.
 */

public class GooglePlayServiceUtil {

    /**
     * Used to check whether google play services available on device or not
     *
     * @param context
     * @return
     */
    public static boolean isGooglePlayServicesAvailable(Context context) {
        int status = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(context);
        if (ConnectionResult.SUCCESS == status) {
            return true;
        }
        return false;
    }
}
