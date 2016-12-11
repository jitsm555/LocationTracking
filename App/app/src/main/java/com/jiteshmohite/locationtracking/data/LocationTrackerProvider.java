package com.jiteshmohite.locationtracking.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import java.util.Arrays;

import static com.jiteshmohite.locationtracking.data.LocationTrackerDatabase.Tables.LOCATION;
import static com.jiteshmohite.locationtracking.util.LogUtils.LOGV;
import static com.jiteshmohite.locationtracking.util.LogUtils.makeLogTag;
/**
 * {@link ContentProvider} that stores {@link LocationTrackerContract} data.Data is inserted by
 * Location which got from Location fused api and queried using {@link android.app.LoaderManager}
 * pattern. Created by jitesh.mohite on 09/12/16.
 */
public class LocationTrackerProvider extends ContentProvider {
    private static final String TAG = makeLogTag(LocationTrackerProvider.class);
    private LocationTrackerDatabase mLocationTrackerDatabase;
    private LocationTrackerUriMatcher mLocationTrackerUriMatcher;

    @Override
    public boolean onCreate() {
        mLocationTrackerDatabase = new LocationTrackerDatabase(getContext());
        mLocationTrackerUriMatcher = new LocationTrackerUriMatcher();
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        SQLiteDatabase database = mLocationTrackerDatabase.getReadableDatabase();
        LocationTrackerUriEnum trackerUriEnum = mLocationTrackerUriMatcher.matchUri(uri);
        LOGV(TAG, "uri=" + uri + " code=" + trackerUriEnum.code + " proj=" +
                Arrays.toString(projection) + " selection=" + selection + " args="
                + Arrays.toString(selectionArgs) + ")");
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        String groupBy = null;
        switch (trackerUriEnum) {
            case LOCATIONS:
                qb.setTables(LOCATION);
            default:
                break;
        }
        Cursor cursor = qb.query(database, projection, selection, selectionArgs,
                groupBy, null, sortOrder, null);
        // Registering an observer in content resolver through cursor
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        LocationTrackerUriEnum locationTrackerUriEnum = mLocationTrackerUriMatcher.matchUri(uri);
        return locationTrackerUriEnum.contentType;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        LOGV(TAG, "insert(uri=" + uri + ", values=" + values.toString());
        SQLiteDatabase database = mLocationTrackerDatabase.getWritableDatabase();
        LocationTrackerUriEnum trackerUriEnum = mLocationTrackerUriMatcher.matchUri(uri);
        long rowId = -1;
        Uri newUri = null;
        switch (trackerUriEnum) {
            case LOCATIONS:
                rowId = database.insert(LOCATION, null, values);
                break;
            default:
                break;
        }
        if (rowId > 0) {
            newUri = ContentUris.withAppendedId(uri, rowId);
            // notify content resolver about changes
            getContext().getContentResolver().notifyChange(newUri, null);
        }
        return newUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        LOGV(TAG, "delete(uri=" + uri);
        if (uri == LocationTrackerContract.BASE_CONTENT_URI) {
            deleteDatabase();
            return 1;
        }
        LocationTrackerUriEnum trackerUriEnum = mLocationTrackerUriMatcher.matchUri(uri);
        SQLiteDatabase database = mLocationTrackerDatabase.getWritableDatabase();
        int recordsDeleted = 0;

        switch (trackerUriEnum) {
            case LOCATIONS:
                recordsDeleted = database.delete(LOCATION, selection,
                        selectionArgs);
            default:
                break;
        }
        // notify content resolver about changes
        getContext().getContentResolver().notifyChange(uri, null);
        return recordsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        // TODO : As we don't required to update any locations so for now update code is on hold
        return 0;
    }

    public void deleteDatabase() {
        mLocationTrackerDatabase.close();
        Context context = getContext();
        LocationTrackerDatabase.deleteDatabase(context);
        mLocationTrackerDatabase = new LocationTrackerDatabase(context);
    }
}
