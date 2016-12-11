package com.jiteshmohite.locationtracking.data;

import android.content.UriMatcher;
import android.net.Uri;
import android.util.SparseArray;

import static com.jiteshmohite.locationtracking.util.LogUtils.makeLogTag;

/**
 * Provides methods to match a {@link Uri} to a {@link LocationTrackerUriEnum}. <p /> All methods
 * are thread safe, except {@link #buildUriMatcher()} and {@link #buildEnumsMap()}, which is why
 * they are called only from the constructor.
 */
public class LocationTrackerUriMatcher {

    private static final String TAG = makeLogTag(LocationTrackerProvider.class);

    /**
     * All methods on a {@link UriMatcher} are thread safe, except {@code addURI}.
     */
    private UriMatcher mUriMatcher;

    private SparseArray<LocationTrackerUriEnum> mEnumsMap = new SparseArray<>();


    public LocationTrackerUriMatcher() {
        mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        buildUriMatcher();
    }

    private void buildUriMatcher() {
        final String authority = LocationTrackerContract.CONTENT_AUTHORITY;

        LocationTrackerUriEnum[] uris = LocationTrackerUriEnum.values();
        for (int i = 0; i < uris.length; i++) {
            mUriMatcher.addURI(authority, uris[i].path, uris[i].code);
        }

        buildEnumsMap();
    }

    private void buildEnumsMap() {
        LocationTrackerUriEnum[] uris = LocationTrackerUriEnum.values();
        for (int i = 0; i < uris.length; i++) {
            mEnumsMap.put(uris[i].code, uris[i]);
        }
    }

    /**
     * Matches a {@code uri} to a {@link LocationTrackerUriEnum}.
     *
     * @return the {@link LocationTrackerUriEnum}, or throws new UnsupportedOperationException if no
     * match.
     */
    public LocationTrackerUriEnum matchUri(Uri uri) {
        final int code = mUriMatcher.match(uri);
        try {
            return matchCode(code);
        } catch (UnsupportedOperationException e) {
            throw new UnsupportedOperationException("Unknown uri " + uri);
        }
    }

    /**
     * Matches a {@code code} to a {@link LocationTrackerUriEnum}.
     *
     * @return the {@link LocationTrackerUriEnum}, or throws new UnsupportedOperationException if no
     * match.
     */
    public LocationTrackerUriEnum matchCode(int code) {
        LocationTrackerUriEnum scheduleUriEnum = mEnumsMap.get(code);
        if (scheduleUriEnum != null) {
            return scheduleUriEnum;
        } else {
            throw new UnsupportedOperationException("Unknown uri with code " + code);
        }
    }
}
