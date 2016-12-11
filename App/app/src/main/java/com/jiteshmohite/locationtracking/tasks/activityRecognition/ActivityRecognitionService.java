package com.jiteshmohite.locationtracking.tasks.activityRecognition;

import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;

import com.google.android.gms.location.ActivityRecognitionResult;
import com.google.android.gms.location.DetectedActivity;
import com.jiteshmohite.locationtracking.LocationTrackerApplication;
import com.jiteshmohite.locationtracking.tasks.fusedlocation.FusedLocationTracker;
import com.jiteshmohite.locationtracking.util.ActivityRecognitionUtil;

import static com.jiteshmohite.locationtracking.util.LogUtils.LOGD;
import static com.jiteshmohite.locationtracking.util.LogUtils.LOGI;

/**
 * ActivityRecognitionService service used for handling incoming intents that are generated as a
 * result of requesting activity Created by jitesh on 7/12/16.
 */
public class ActivityRecognitionService extends IntentService {

    private static final String TAG = ActivityRecognitionService.class.getName();

    private FusedLocationTracker mFusedLocationTracker;
    private ActivityDetectionRequester mActivityDetectionRequester;
    private boolean isTrackerOn;

    /*
     * This constructor is required, and calls the super IntentService(String)
     * constructor with the name for a worker thread.
     */
    public ActivityRecognitionService() {
        // Use the TAG to name the worker thread.
        super(TAG);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mFusedLocationTracker = LocationTrackerApplication.getFusedLocationTracker();
        mActivityDetectionRequester = LocationTrackerApplication.getActivityDetectionRequester();
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        ActivityRecognitionResult result = ActivityRecognitionResult.extractResult(intent);
        if (result != null) {
            DetectedActivity mostProbableActivity = result.getMostProbableActivity();
            // Log each activity.
            if (mostProbableActivity != null) {
                LOGI(TAG, "activities detected");
                int activityType = mostProbableActivity.getType();
                int confidence = mostProbableActivity.getConfidence();
                LOGD(TAG, ActivityRecognitionUtil.getActivityString(getApplicationContext(),
                        activityType) + " " + confidence + "%");
                isTrackerOn = mFusedLocationTracker.getGoogleApiClient().isConnected();
                if (ActivityRecognitionUtil.isMoving(activityType) && (confidence >= 60) && !isTrackerOn) {
                    // start a location fetching service if it is not connected.
                    mFusedLocationTracker.startLocationTracker();
                } else {
                    // stop a location fetching service if it is connected
                    mFusedLocationTracker.removeLocationUpdates();
                    mFusedLocationTracker.stopLocationTracker();
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        LOGD(TAG, "ActivityRecognitionService is destroyed");
        super.onDestroy();
    }
}