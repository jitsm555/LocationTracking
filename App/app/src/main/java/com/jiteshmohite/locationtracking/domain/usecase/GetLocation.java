package com.jiteshmohite.locationtracking.domain.usecase;

import com.jiteshmohite.locationtracking.tasks.activityRecognition.ActivityDetectionRequester;
import com.jiteshmohite.locationtracking.tasks.fusedlocation.FusedLocationTracker;

/**
 * Created by jitesh.mohite on 05-12-2016.
 */

public class GetLocation {

    private FusedLocationTracker mFusedLocationTracker;
    private ActivityDetectionRequester mActivityDetectionRequester;

    public GetLocation(FusedLocationTracker fusedLocationTracker,
                       ActivityDetectionRequester activityDetectionRequester) {
        mFusedLocationTracker = fusedLocationTracker;
        mActivityDetectionRequester = activityDetectionRequester;
    }

    public boolean isLocationTrackingOn() {
        return mFusedLocationTracker.getGoogleApiClient().isConnected();
    }

}
