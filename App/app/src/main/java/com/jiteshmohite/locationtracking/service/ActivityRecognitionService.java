package com.jiteshmohite.locationtracking.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;

import com.google.android.gms.location.ActivityRecognitionResult;
import com.google.android.gms.location.DetectedActivity;
import com.jiteshmohite.locationtracking.util.ActivityRecognitionUtil;

import static com.jiteshmohite.locationtracking.util.LogUtils.LOGD;
import static com.jiteshmohite.locationtracking.util.LogUtils.LOGI;

/**
 * ActivityRecognitionService service used for handling incoming intents that are generated as a
 * result of requesting activity Created by jitesh on 7/1/16.
 */
public class ActivityRecognitionService extends IntentService {

    private static final String TAG = ActivityRecognitionService.class.getName();

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

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
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
//                SharePrefUtil.setActivityConfidence(getApplicationContext(), confidence);
//                SharePrefUtil.setActivityType(getApplicationContext(), activityType);

                LOGD(TAG, ActivityRecognitionUtil.getActivityString(getApplicationContext(),
                        activityType) + " " + confidence + "%");

                if (ActivityRecognitionUtil.isMoving(activityType) && (confidence >= 60)) {
//                    if (SharePrefUtil.getRequestLocation(getApplicationContext())) {
//                        LOGD(TAG, "Location Requested");
//                        LocationTrackerApplication.getInstance().setLocationTracker(true);
//                        SharePrefUtil.setRequestLocation(getApplicationContext(), false);
                }
            } else /*if (!SharePrefUtil.getRequestLocation(getApplicationContext())) {
                        LOGD(TAG, "Location Removed");
                        LocationTrackerApplication.getInstance().setLocationTracker(false);
                        SharePrefUtil.setRequestLocation(getApplicationContext(), true);
                    }*/ {
                  
            }

        }
    }

    @Override
    public void onDestroy() {
        LOGD(TAG, "ActivityRecognitionService is destroyed");
        super.onDestroy();
    }
}