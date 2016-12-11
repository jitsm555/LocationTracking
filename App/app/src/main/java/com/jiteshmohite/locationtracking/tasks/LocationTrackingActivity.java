package com.jiteshmohite.locationtracking.tasks;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jiteshmohite.locationtracking.Injection;
import com.jiteshmohite.locationtracking.LocationTrackerApplication;
import com.jiteshmohite.locationtracking.R;
import com.jiteshmohite.locationtracking.tasks.activityRecognition.ActivityDetectionRequester;
import com.jiteshmohite.locationtracking.tasks.fusedlocation.FusedLocationTracker;
import com.jiteshmohite.locationtracking.util.ActivityUtils;

/**
 * LocationTrackingActivity class responsible for loading fragment which takes care of showing
 * navigation path on google map and initialize MVP pattern.
 */
public class LocationTrackingActivity extends AppCompatActivity {
    private LocationTrackerPresenter mLocationTrackerPresenter;
    private FusedLocationTracker mFusedLocationTracker;
    private ActivityDetectionRequester mActivityDetectionRequester;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loc);

        LocationTrackerFragment locationTrackerFragment = (LocationTrackerFragment) getSupportFragmentManager().
                findFragmentById(R.id.contentFrame);
        if(locationTrackerFragment == null) {
            // create a new fragment
            locationTrackerFragment = LocationTrackerFragment.getInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), locationTrackerFragment, R.id.contentFrame);
        }
        mFusedLocationTracker = LocationTrackerApplication.getFusedLocationTracker();
        mActivityDetectionRequester = LocationTrackerApplication.getActivityDetectionRequester();
        // create a location presenter
        mLocationTrackerPresenter = new LocationTrackerPresenter(locationTrackerFragment,
                Injection.getLocationTask(mFusedLocationTracker, mActivityDetectionRequester),
                Injection.getStartLocationTrackingTask(mFusedLocationTracker, mActivityDetectionRequester),
                Injection.getStopLocationTrackingTask(mFusedLocationTracker, mActivityDetectionRequester));
    }
}
