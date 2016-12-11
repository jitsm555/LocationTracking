package com.jiteshmohite.locationtracking;

import android.app.Application;

import com.jiteshmohite.locationtracking.tasks.activityRecognition.ActivityDetectionRequester;
import com.jiteshmohite.locationtracking.tasks.fusedlocation.FusedLocationTracker;

/**
 * LocationTrackerApplication class will be responsible for accessing the {@link ActivityDetectionRequester} &&
 * {@link FusedLocationTracker}
 * Created by jitesh.mohite on 7/12/16.
 */
public class LocationTrackerApplication extends Application {

    private static LocationTrackerApplication mAppInstance;
    private static ActivityDetectionRequester mActivityDetectionRequester;
    private static FusedLocationTracker mFusedLocationTracker;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppInstance = this;
        mActivityDetectionRequester = new ActivityDetectionRequester(getInstance());
        mFusedLocationTracker = new FusedLocationTracker(getInstance());
    }

    public static LocationTrackerApplication getInstance() {
        return mAppInstance;
    }

    public static ActivityDetectionRequester getActivityDetectionRequester() {
        return mActivityDetectionRequester;
    }

    public static FusedLocationTracker getFusedLocationTracker() {
        return mFusedLocationTracker;
    }


}
