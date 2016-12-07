package com.jiteshmohite.locationtracking.tasks;

/**
 * Created by jitesh.mohite on 05-12-2016.
 */

public interface ILocationTrackerPresenter {

    // shows whether location tracking is on or off
    // true - location tracking on.
    // false - location tracking off.
    boolean isLocationTrackingOn();

    // used to start location tracking which continuous fetch location.
    public void startLocationTracking();

    // used to stop location tracking.
    public void stopLocationTracking();
}
