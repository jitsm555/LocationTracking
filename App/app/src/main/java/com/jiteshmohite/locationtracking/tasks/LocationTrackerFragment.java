package com.jiteshmohite.locationtracking.tasks;


import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.jiteshmohite.locationtracking.R;
import com.jiteshmohite.locationtracking.data.LocationTrackerContract;
import com.jiteshmohite.locationtracking.tasks.fusedlocation.LocationTrackerInMemory;
import com.jiteshmohite.locationtracking.util.LocationUtil;

import static com.jiteshmohite.locationtracking.data.LocationTrackerContract.LocationColumns.LOCATION_ACCURACY;
import static com.jiteshmohite.locationtracking.data.LocationTrackerContract.LocationColumns.LOCATION_ALTITUDE;
import static com.jiteshmohite.locationtracking.data.LocationTrackerContract.LocationColumns.LOCATION_LATITUDE;
import static com.jiteshmohite.locationtracking.data.LocationTrackerContract.LocationColumns.LOCATION_LONGITUDE;
import static com.jiteshmohite.locationtracking.data.LocationTrackerContract.LocationColumns.LOCATION_SPEED;
import static com.jiteshmohite.locationtracking.data.LocationTrackerContract.LocationColumns.LOCATION_TIME;
import static com.jiteshmohite.locationtracking.util.LogUtils.LOGD;
import static com.jiteshmohite.locationtracking.util.LogUtils.LOGI;

/**
 * LocationTrackingFragment responsible for updating the navigation path on google path.
 * Created by jitesh.mohite on 05-12-2016.
 */

public class LocationTrackerFragment extends Fragment implements ILocationTrackerView, View.OnClickListener,
        LoaderManager.LoaderCallbacks<Cursor> {
    private static final String TAG = LocationTrackerFragment.class.getSimpleName();

    // The loader's unique id. Loader ids are specific to the Activity or
    // Fragment in which they reside.
    private static final int LOADER_ID = 1;

    private ILocationTrackerPresenter locationTrackerPresenter;
    private GoogleMap mGoogleMap;
    private Button mStartLocButton;

    public LocationTrackerFragment() {
        // Require empty public constructor
    }

    public static LocationTrackerFragment getInstance() {
        return new LocationTrackerFragment();
    }

    @Override
    public void setPresenter(LocationTrackerPresenter locationTrackerPresenter) {
        this.locationTrackerPresenter = locationTrackerPresenter;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_loc, container, false);
        initViews(view);
        initListener();
        return view;
    }

    private void initViews(View view) {
        mGoogleMap = ((SupportMapFragment) getChildFragmentManager().
                findFragmentById(R.id.fragment_map)).getMap();
        mStartLocButton = (Button) view.findViewById(R.id.button_start_loc);

    }

    private void initListener() {
        mStartLocButton.setOnClickListener(this);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        updateView();
    }

    private void updateView() {
        // depending on the location tracking on/off update text.
        if (locationTrackerPresenter.isLocationTrackingOn()) {
            mStartLocButton.setText("Stop");
        } else {
            mStartLocButton.setText("Start");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        getLoaderManager().initLoader(LOADER_ID, null, this);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getLoaderManager().destroyLoader(LOADER_ID);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_start_loc:
                // check which ensure whether to start location tracking or not.
                if (locationTrackerPresenter.isLocationTrackingOn()) {
                    locationTrackerPresenter.stopLocationTracking();
                } else {
                    locationTrackerPresenter.startLocationTracking();
                }
                break;
        }
    }


    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        LOGI(TAG, "onCreateLoader");
        Uri uri = LocationTrackerContract.Locations.CONTENT_URI;
        String projection[] = null;
        String selection = null;
        String selectionArgs[] = null;
        String sortOrder = BaseColumns._ID + " ASC";
        android.support.v4.content.CursorLoader cursorLoader = new android.support.v4.content.CursorLoader(getActivity().getApplicationContext(), uri, projection,
                selection, selectionArgs, sortOrder);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        LOGI(TAG, "onLoadFinished" + ", Cursor :" + cursor);
        if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) {
            do {
                LOGD(TAG, cursor.getString(cursor.getColumnIndex(BaseColumns._ID)));
                LOGD(TAG, cursor.getString(cursor.getColumnIndex(LOCATION_LATITUDE)));
                LOGD(TAG, cursor.getString(cursor.getColumnIndex(LOCATION_LONGITUDE)));
                LOGD(TAG, cursor.getString(cursor.getColumnIndex(LOCATION_ACCURACY)));
                LOGD(TAG, cursor.getString(cursor.getColumnIndex(LOCATION_SPEED)));
                LOGD(TAG, cursor.getString(cursor.getColumnIndex(LOCATION_ALTITUDE)));
                LOGD(TAG, cursor.getString(cursor.getColumnIndex(LOCATION_TIME)));
                String time = cursor.getString(cursor.getColumnIndex(LOCATION_TIME));
                LocationTrackerInMemory.getInstance().setLocation(time, LocationUtil.getLocation(cursor));
            } while (cursor.moveToNext());
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // This is called when the last Cursor provided to onLoadFinished()
        // above is about to be closed.  We need to make sure we are no
        // longer using it.
        LOGI(TAG, "onLoaderReset");
    }



}
