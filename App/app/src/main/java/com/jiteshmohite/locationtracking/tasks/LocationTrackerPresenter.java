package com.jiteshmohite.locationtracking.tasks;

import com.jiteshmohite.locationtracking.domain.usecase.GetLocation;
import com.jiteshmohite.locationtracking.domain.usecase.StartLocationTracking;
import com.jiteshmohite.locationtracking.domain.usecase.StopLocationTracking;

/**
 * Listens to user actions from the UI ({@link LocationTrackerPresenter}), retrieves the data and updates the
 * UI as required.
 * Created by jitesh.mohite on 05-12-2016.
 */

public class LocationTrackerPresenter implements ILocationTrackerPresenter {
    private static final String TAG = LocationTrackerPresenter.class.getSimpleName();


    private ILocationTrackerView mLocationTrackerView;
    private GetLocation mGetLocation;
    private StartLocationTracking mStartLocationTracking;
    private StopLocationTracking mStopLocationTracking;

    public LocationTrackerPresenter(LocationTrackerFragment locationTrackerView,
                                    GetLocation locationTask, StartLocationTracking startLocationTrackingTask,
                                    StopLocationTracking stopLocationTrackingTask) {
        mLocationTrackerView = locationTrackerView;
        mGetLocation = locationTask;
        mStartLocationTracking = startLocationTrackingTask;
        mStopLocationTracking = stopLocationTrackingTask;
        mLocationTrackerView.setPresenter(this);
    }


    @Override
    public boolean isLocationTrackingOn() {
        return mGetLocation.isLocationTrackingOn();
    }

    @Override
    public void startLocationTracking() {
        mStartLocationTracking.startTracking();
    }

    @Override
    public void stopLocationTracking() {
      mStopLocationTracking.stopTracking();
    }
}
