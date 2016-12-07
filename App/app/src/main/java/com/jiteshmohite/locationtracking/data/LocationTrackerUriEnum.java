package com.jiteshmohite.locationtracking.data;

import static com.jiteshmohite.locationtracking.data.LocationTrackerContract.Locations.CONTENT_TYPE_ID;

/**
 * The list of {@code Uri}s recognised by the {@code ContentProvider} of the app. segment.Created by
 * jiteshmohite on 10/01/16.
 */
public enum LocationTrackerUriEnum {

    LOCATIONS(100, "locations", CONTENT_TYPE_ID, false, LocationTrackerDatabase.Tables.LOCATION);

    public int code;

    /**
     * The path to the {@link android.content.UriMatcher} will use to match. * may be used as a wild
     * card for any text, and # may be used as a wild card for numbers.
     */
    public String path;

    public String contentType;

    public String table;

    LocationTrackerUriEnum(int code, String path, String contentTypeId, boolean item, String table) {
        this.code = code;
        this.path = path;
        this.contentType = item ? LocationTrackerContract.makeContentItemType(contentTypeId)
                : LocationTrackerContract.makeContentType(contentTypeId);
        this.table = table;
    }

}
