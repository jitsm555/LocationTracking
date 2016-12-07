package com.jiteshmohite.locationtracking.tasks.activityRecognition;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.ActivityRecognition;
import com.jiteshmohite.locationtracking.R;
import com.jiteshmohite.locationtracking.service.ActivityRecognitionService;
import com.jiteshmohite.locationtracking.util.GooglePlayServiceUtil;

import static com.jiteshmohite.locationtracking.util.LogUtils.LOGE;
import static com.jiteshmohite.locationtracking.util.LogUtils.LOGI;
import static com.jiteshmohite.locationtracking.util.LogUtils.makeLogTag;

/**
 * ActivityDetectionRequester class manages all functionality around requesting and removing activity
 * updates Created by jitesh on 7/1/16.
 */
public class ActivityDetectionRequester
        implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, ResultCallback<Status> {

    private static final String TAG = makeLogTag(ActivityDetectionRequester.class);

    /**
     * The desired time between activity detections. Larger values result in fewer activity
     * detections while improving battery life. A value of 0 results in activity detections at the
     * fastest possible rate. Getting frequent updates negatively impact battery life and a real app
     * may prefer to request less frequent updates.
     */
    private static final long DETECTION_INTERVAL_IN_MILLISECONDS = 1000/* * 5*/;

    //Provides the entry point to Google Play services.
    private GoogleApiClient mGoogleApiClient;
    private Context mContext;

    private ActivityDetectionRequester() {
        // empty constructor
    }

    public ActivityDetectionRequester(Context context) {
        mContext = context;
        buildGoogleApiClient();
    }

    /**
     * Builds a GoogleApiClient. Uses the {@code #addApi} method to request the ActivityRecognition
     * API.
     */
    private synchronized void buildGoogleApiClient() {
        if (GooglePlayServiceUtil.isGooglePlayServicesAvailable(mContext)) {
            mGoogleApiClient = new GoogleApiClient.Builder(mContext)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(ActivityRecognition.API)
                    .build();
        }
    }

    // used to get Google api client
    public GoogleApiClient getGoogleApiClient() {
        return mGoogleApiClient;
    }

    // used to connect with ActivityDetection
    public void startUserDetectionConnection() {
        mGoogleApiClient.connect();
    }

    // used to disconnect with ActivityDetection
    public void stopUserDetectionConnection() {
        mGoogleApiClient.disconnect();
    }

    /**
     * Registers for activity recognition updates using {@link com.google.android.gms.location.ActivityRecognitionApi#requestActivityUpdates}
     * which returns a {@link com.google.android.gms.common.api.PendingResult}. Since this activity
     * implements the PendingResult interface, the activity itself receives the callback, and the
     * code within {@code onResult} executes. Note: once {@code requestActivityUpdates()} completes
     * successfully, the {@code DetectedActivitiesIntentService} starts receiving callbacks when
     * activities are detected.
     */
    public void requestForActivityUpdates() {
        if (!mGoogleApiClient.isConnected()) {
            Toast.makeText(mContext, mContext.getString(R.string.not_connected), Toast.LENGTH_SHORT).show();
            return;
        }
        ActivityRecognition.ActivityRecognitionApi.requestActivityUpdates(
                mGoogleApiClient, DETECTION_INTERVAL_IN_MILLISECONDS,
                getActivityDetectionPendingIntent()
        ).setResultCallback(this);
    }

    /**
     * Removes activity recognition updates using {@link com.google.android.gms.location.ActivityRecognitionApi#removeActivityUpdates}
     * which returns a {@link com.google.android.gms.common.api.PendingResult}. Since this activity
     * implements the PendingResult interface, the activity itself receives the callback, and the
     * code within {@code onResult} executes. Note: once {@code removeActivityUpdates()} completes
     * successfully, the {@code DetectedActivitiesIntentService} stops receiving callbacks about
     * detected activities.
     */
    public void removeActivityUpdates() {
        if (!mGoogleApiClient.isConnected()) {
            Toast.makeText(mContext, mContext.getString(R.string.not_connected), Toast.LENGTH_SHORT).show();
            return;
        }

        // Remove all activity updates for the PendingIntent that was used to request activity
        // updates.
        ActivityRecognition.ActivityRecognitionApi.removeActivityUpdates(
                mGoogleApiClient,
                getActivityDetectionPendingIntent()
        ).setResultCallback(this);
    }

    /**
     * Gets a PendingIntent to be sent for each activity detection.
     */
    private PendingIntent getActivityDetectionPendingIntent() {
        Intent intent = new Intent(mContext, ActivityRecognitionService.class);

        // We use FLAG_UPDATE_CURRENT so that we get the same pending intent back when calling
        // requestActivityUpdates() and removeActivityUpdates().
        return PendingIntent.getService(mContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    @Override
    public void onConnected(Bundle bundle) {
        LOGI(TAG, "Connected to GoogleApiClient");
//        if (!SharePrefUtil.getUpdatesRequestedActivityState(mContext)) {
//            requestForActivityUpdates();
//        } else {
//            removeActivityUpdates();
//        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        // The connection to Google Play services was lost for some reason. We call connect() to
        // attempt to re-establish the connection.
        LOGI(TAG, "Connection suspended");
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        LOGI(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + result
                .getErrorCode());
    }

    @Override
    public void onResult(Status status) {
        if (status.isSuccess()) {
            // Toggle the status of activity updates requested, and save in shared preferences.
//            boolean requestingUpdates = !SharePrefUtil.getUpdatesRequestedActivityState(mContext);
//            SharePrefUtil.setUpdatesRequestedActivityState(mContext, requestingUpdates);
//             you can update ui here
//
//            Toast.makeText(mContext, mContext.getString(requestingUpdates ? R.string
//                            .activity_updates_added :
//                            R.string.activity_updates_removed),
//                    Toast.LENGTH_SHORT
//            ).show();
        } else {
            LOGE(TAG, "Error adding or removing activity detection: " + status
                    .getStatusMessage());
        }
    }
}