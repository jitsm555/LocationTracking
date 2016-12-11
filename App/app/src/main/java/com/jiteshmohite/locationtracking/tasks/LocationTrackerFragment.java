package com.jiteshmohite.locationtracking.tasks;


import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.jiteshmohite.locationtracking.R;
import com.jiteshmohite.locationtracking.data.LocationTrackerContract;
import com.jiteshmohite.locationtracking.tasks.fusedlocation.LocationFilter;
import com.jiteshmohite.locationtracking.util.CalenderUtil;
import com.jiteshmohite.locationtracking.util.DateUtils;
import com.jiteshmohite.locationtracking.util.LocationUtil;

import java.util.List;

import static com.jiteshmohite.locationtracking.data.LocationTrackerContract.LocationColumns.LOCATION_ACCURACY;
import static com.jiteshmohite.locationtracking.data.LocationTrackerContract.LocationColumns.LOCATION_ALTITUDE;
import static com.jiteshmohite.locationtracking.data.LocationTrackerContract.LocationColumns.LOCATION_LATITUDE;
import static com.jiteshmohite.locationtracking.data.LocationTrackerContract.LocationColumns.LOCATION_LONGITUDE;
import static com.jiteshmohite.locationtracking.data.LocationTrackerContract.LocationColumns.LOCATION_SPEED;
import static com.jiteshmohite.locationtracking.data.LocationTrackerContract.LocationColumns.LOCATION_TIME;
import static com.jiteshmohite.locationtracking.util.LogUtils.LOGD;
import static com.jiteshmohite.locationtracking.util.LogUtils.LOGI;
import static com.jiteshmohite.locationtracking.util.LogUtils.makeLogTag;

/**
 * LocationTrackingFragment class responsible for updating the navigation path on google path.
 * Created by jitesh.mohite on 05-12-2016.
 */

public class LocationTrackerFragment extends Fragment implements ILocationTrackerView, View.OnClickListener,
        LoaderManager.LoaderCallbacks<Cursor> {
    private static final String TAG = makeLogTag(LocationTrackerFragment.class);

    // The loader's unique id. Loader ids are specific to the Activity or
    // Fragment in which they reside.
    private static final int LOADER_ID = 1;
    private static final float ZOOM_LEVEL = 15;
    private ILocationTrackerPresenter locationTrackerPresenter;
    private GoogleMap mGoogleMap;
    private Button mStartLocButton;
    private static String startTime;
    private static String endTime;

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
            mStartLocButton.setText(getContext().getString(R.string.stop));
        } else {
            mStartLocButton.setText(getContext().getString(R.string.start));
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
                    mStartLocButton.setText(getContext().getString(R.string.start));
                } else {
                    locationTrackerPresenter.startLocationTracking();
                    mStartLocButton.setText(getContext().getString(R.string.stop));
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
        android.support.v4.content.CursorLoader cursorLoader = new android.support.v4.content.CursorLoader(getActivity()
                .getApplicationContext(), uri, projection,
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
                LocationFilter.getInstance().setLocation(time, LocationUtil.getLocation(cursor));
            } while (cursor.moveToNext());
        }
        setDefaultTimeAndDrawRoute();
        drawMarkerAndRoute();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // This is called when the last Cursor provided to onLoadFinished()
        // above is about to be closed.  We need to make sure we are no
        // longer using it.
        LOGI(TAG, "onLoaderReset");
    }

    // used to draw route between given locations
    private void drawMarkerAndRoute() {
        LOGD(TAG, "start time :" + startTime + ", end time :" + endTime);
        if (!TextUtils.isEmpty(startTime) && !TextUtils.isEmpty(endTime)) {
            List<Location> locations = LocationUtil.getLocationBasedOnTime(DateUtils.getTimeInUTC(startTime),
                    DateUtils.getTimeInUTC(endTime));
            if (locations != null && locations.size() > 0) {
                List<LatLng> latLngList = LocationUtil.getLatLongList(locations);
                PolylineOptions options = new PolylineOptions().width(10).color(Color.BLUE);
                for (int i = 0; i < latLngList.size(); i++) {
                    LatLng point = latLngList.get(i);
                    options.add(point);
                }
                mGoogleMap.addPolyline(options);
                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(locations.get
                        (0).getLatitude(), locations.get(0).getLongitude()), ZOOM_LEVEL));
            }
        }
    }

    // used to set default time
    private void setDefaultTimeAndDrawRoute() {
        startTime = CalenderUtil.getCurrentDate() + " " + CalenderUtil.getStartTime();
        endTime = CalenderUtil.getCurrentDate() + " " + CalenderUtil.getCurrentTime();
    }


}
