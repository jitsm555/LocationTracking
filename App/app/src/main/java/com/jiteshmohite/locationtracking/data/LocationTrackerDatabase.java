package com.jiteshmohite.locationtracking.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import static com.jiteshmohite.locationtracking.util.LogUtils.LOGD;
import static com.jiteshmohite.locationtracking.util.LogUtils.makeLogTag;

/**
 * Helper for managing {@link SQLiteDatabase} that stores data for {@link LocationTrackerProvider}.
 * Created by jitesh.mohite on 09/12/16.`
 */
public class LocationTrackerDatabase extends SQLiteOpenHelper {
    private static final String TAG = makeLogTag(LocationTrackerDatabase.class);

    // database name
    private static final String DATABASE_NAME = "LocationTracker.db";
    // version name of database
    private static final int CUR_VERSION = 1;

    private final Context mContext;

    interface Tables {
        String LOCATION = "location";
    }

    /*
     * Instantiates an open helper for the provider's SQLite data repository
     * Do not do database creation and upgrade here.
     */
    public LocationTrackerDatabase(Context context) {
        super(context, /*"/mnt/sdcard/locationtrackerdb/" +*/ DATABASE_NAME, null, CUR_VERSION);
        mContext = context;
    }

    /*
     * Creates the data repository. This is called when the provider attempts to open the
     * repository and SQLite reports that it doesn't exist.
     */

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + Tables.LOCATION + " ("
                        + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + LocationTrackerContract.LocationColumns.LOCATION_LATITUDE + " TEXT NOT NULL,"
                        + LocationTrackerContract.LocationColumns.LOCATION_LONGITUDE + " TEXT NOT NULL,"
                        + LocationTrackerContract.LocationColumns.LOCATION_ACCURACY + " TEXT NOT NULL,"
                        + LocationTrackerContract.LocationColumns.LOCATION_SPEED + " TEXT NOT NULL,"
                        + LocationTrackerContract.LocationColumns.LOCATION_ALTITUDE + " TEXT NOT NULL,"
                        + LocationTrackerContract.LocationColumns.LOCATION_TIME + " TEXT NOT NULL);"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        LOGD(TAG, "onUpgrade() from " + oldVersion + " to " + newVersion);

    }

    public static void deleteDatabase(Context context) {
        context.deleteDatabase(DATABASE_NAME);
    }

}
