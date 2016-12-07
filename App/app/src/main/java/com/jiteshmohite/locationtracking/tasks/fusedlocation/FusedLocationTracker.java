package com.jiteshmohite.locationtracking.tasks.fusedlocation;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.jiteshmohite.locationtracking.R;
import com.jiteshmohite.locationtracking.service.LocationTrackerService;
import com.jiteshmohite.locationtracking.util.GooglePlayServiceUtil;

import static com.jiteshmohite.locationtracking.util.LogUtils.LOGD;
import static com.jiteshmohite.locationtracking.util.LogUtils.LOGE;
import static com.jiteshmohite.locationtracking.util.LogUtils.LOGI;
import static com.jiteshmohite.locationtracking.util.LogUtils.makeLogTag;

/**
 * FusedLocationTracker class manages all functionality around requesting and removing location updates
 * Created by jitesh on 7/1/16.
 */
public class FusedLocationTracker implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, ResultCallback<Status> {

    private static final String TAG = makeLogTag(FusedLocationTracker.class);
    private static final long INTERVAL = 1000 * 10;
    private static final long FASTEST_INTERVAL = 1000 * 5;
    private static int DISPLACEMENT = 10;


    private Context mContext;
    //Provides the entry point to Google Play services.
    private static GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;

    public FusedLocationTracker(Context context) {
        mContext = context;
        buildGoogleApiClient();
    }

    /**
     * Builds a GoogleApiClient. Uses the {@code #addApi} method to request the Fused Location
     * API.
     */
    private synchronized void buildGoogleApiClient() {
        createLocationRequest();
        if (GooglePlayServiceUtil.isGooglePlayServicesAvailable(mContext)) {
            mGoogleApiClient = new GoogleApiClient.Builder(mContext)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }

    // used to connect with Location Services
    public void startLocationTracker() {
        mGoogleApiClient.connect();
    }

    // used to disconnect with Location Services
    public void stopLocationTracker() {
        mGoogleApiClient.disconnect();
    }

    // used to get Google api client
    public GoogleApiClient getGoogleApiClient() {
        return mGoogleApiClient;
    }

    public void removeLocationUpdates() {
        if (!mGoogleApiClient.isConnected()) {
            Toast.makeText(mContext, mContext.getString(R.string.not_connected), Toast.LENGTH_SHORT).show();
            return;
        }

        LocationServices.FusedLocationApi.removeLocationUpdates(
                mGoogleApiClient,
                getLocationTrackerPendingIntent()
        ).setResultCallback(this);
    }

    public void requestLocationUpdates() {
        if (!mGoogleApiClient.isConnected()) {
            Toast.makeText(mContext, mContext.getString(R.string.not_connected), Toast.LENGTH_SHORT).show();
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest,
                getLocationTrackerPendingIntent()
        ).setResultCallback(this);

    }

    // used to get last known location which is available
    public Location getLastLocation() {
        return LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
    }


    @Override
    public void onResult(Status status) {
        if (status.isSuccess()) {
            // Toggle the status of activity updates requested, and save in shared preferences.
//            boolean requestingUpdates = !SharePrefUtil.getUpdatesRequestedLocationState(mContext);
//            SharePrefUtil.setUpdatesRequestedLocationState(mContext, requestingUpdates);
            // you can update ui here

//            Toast.makeText(mContext, mContext.getString(requestingUpdates ? R.string
//                            .loc_updates_added : R.string.loc_updates_removed),
//                    Toast.LENGTH_SHORT).show();
        } else {
            LOGE(TAG, "Error adding or removing activity detection: " + status
                    .getStatusMessage());
        }
    }

    /**
     * Gets a PendingIntent to be sent for every location .
     */
    private PendingIntent getLocationTrackerPendingIntent() {
        Intent intent = new Intent(mContext, LocationTrackerService.class);

        // We use FLAG_UPDATE_CURRENT so that we get the same pending intent back when calling
        // requestLocationUpdates() and removeLocationUpdates().
        return PendingIntent.getService(mContext, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
    }


    protected void createLocationRequest() {
        LOGD(TAG, " ***** Creating location request");
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setSmallestDisplacement(DISPLACEMENT);
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        LOGI(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + result
                .getErrorCode());

    }

    @Override
    public void onConnected(Bundle bundle) {
        LOGI(TAG, "Connected to GoogleApiClient");
//        if (!SharePrefUtil.getUpdatesRequestedLocationState(mContext)) {
//            requestLocationUpdates();
//        } else {
//            removeLocationUpdates();
//        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        // The connection to Google Play services was lost for some reason. We call connect() to
        // attempt to re-establish the connection.
        LOGI(TAG, "Connection suspended");
        mGoogleApiClient.connect();
    }


}