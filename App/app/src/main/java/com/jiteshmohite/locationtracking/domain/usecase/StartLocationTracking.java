package com.jiteshmohite.locationtracking.domain.usecase;

import com.jiteshmohite.locationtracking.tasks.activityRecognition.ActivityDetectionRequester;
import com.jiteshmohite.locationtracking.tasks.fusedlocation.FusedLocationTracker;

/**
 * StartLocationTracking class responsible for start location and activity updates.
 * Created by jitesh.mohite on 05-12-2016.
 */
public class StartLocationTracking {

    private FusedLocationTracker mFusedLocationTracker;
    private ActivityDetectionRequester mActivityDetectionRequester;

    public StartLocationTracking(FusedLocationTracker fusedLocationTracker,
                                 ActivityDetectionRequester activityDetectionRequester) {
        mFusedLocationTracker = fusedLocationTracker;
        mActivityDetectionRequester = activityDetectionRequester;
    }

    // used to start location tracking
    public void startTracking() {
        mFusedLocationTracker.startLocationTracker();
        mActivityDetectionRequester.requestForActivityUpdates();
    }

}


