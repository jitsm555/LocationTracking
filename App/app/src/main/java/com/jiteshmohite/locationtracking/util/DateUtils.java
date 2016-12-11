package com.jiteshmohite.locationtracking.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by jitesh.mohite on 05-12-2016.
 */

public class DateUtils {

    /**
     * Used to convert UTC time to Standard date format
     * @param time UTC time of this fix, in milliseconds since January 1, 1970.
     * @return standard time format
     */
    public static String getTimeInDateFormat(long time) {
        Date date = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }

    /**
     * Used to convert Time to UTC time
     * @param time
     * @return
     */
    public static long getTimeInUTC(String time) {
        Date date = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            date = simpleDateFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }
}
