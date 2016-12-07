package com.jiteshmohite.locationtracking;

import com.jiteshmohite.locationtracking.domain.usecase.GetLocation;
import com.jiteshmohite.locationtracking.domain.usecase.StartLocationTracking;
import com.jiteshmohite.locationtracking.domain.usecase.StopLocationTracking;
import com.jiteshmohite.locationtracking.tasks.activityRecognition.ActivityDetectionRequester;
import com.jiteshmohite.locationtracking.tasks.fusedlocation.FusedLocationTracker;

/**
 * Created by jitesh.mohite on 05-12-2016.
 */

public class Injection {

    public static StartLocationTracking getStartLocationTrackingTask(FusedLocationTracker fusedLocationTracker,
                                                                ActivityDetectionRequester activityDetectionRequester) {
        return new StartLocationTracking(fusedLocationTracker, activityDetectionRequester);
    }

    public static StopLocationTracking getStopLocationTrackingTask(FusedLocationTracker fusedLocationTracker,
                                                               ActivityDetectionRequester activityDetectionRequester) {
        return new StopLocationTracking(fusedLocationTracker, activityDetectionRequester);
    }

    public static GetLocation getLocationTask(FusedLocationTracker fusedLocationTracker,
                                                          ActivityDetectionRequester activityDetectionRequester) {
        return new GetLocation(fusedLocationTracker, activityDetectionRequester);
    }
}
