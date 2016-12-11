package com.jiteshmohite.locationtracking.domain.usecase;

import com.jiteshmohite.locationtracking.tasks.activityRecognition.ActivityDetectionRequester;
import com.jiteshmohite.locationtracking.tasks.fusedlocation.FusedLocationTracker;

/**
 * StopLocationTracking class responsible for stop location and activity updates.
 * Created by jitesh.mohite on 05-12-2016.
 */
public class StopLocationTracking {
    private FusedLocationTracker mFusedLocationTracker;
    private ActivityDetectionRequester mActivityDetectionRequester;

    public StopLocationTracking(FusedLocationTracker fusedLocationTracker,
                                 ActivityDetectionRequester activityDetectionRequester) {
        mFusedLocationTracker = fusedLocationTracker;
        mActivityDetectionRequester = activityDetectionRequester;
    }

    // used to stop location tracking
    public void stopTracking() {
        mFusedLocationTracker.removeLocationUpdates();
        mFusedLocationTracker.stopLocationTracker();
//      mActivityDetectionRequester.removeActivityUpdates(); // activity updates always be active
    }
}
