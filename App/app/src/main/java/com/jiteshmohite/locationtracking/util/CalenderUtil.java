package com.jiteshmohite.locationtracking.util;

import java.util.Calendar;


/**
 * Created by jitesh.mohite on 06-11-2016.
 */

public class CalenderUtil {

    /**
     * @return Current Time from Calendar in HH:mm:SS format
     */
    public static String getStartTime() {
        //Java calendar in default timezone and default locale
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return getTime(calendar);
    }
    /**
     * @return End Time from Calendar in HH:mm:SS format
     */
    public static String getCurrentTime() {
        Calendar calendar = Calendar.getInstance();
        return getTime(calendar);
    }


    /**
     * @return current date in yyyy-MM-dd format
     */
    public static String getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        return getDate(calendar);
    }
    /**
     * @return  Date from Calendar in HH:mm:SS format
     */
    public static String getTime(Calendar cal) {
        return "" + cal.get(Calendar.HOUR_OF_DAY) + ":" +
                (cal.get(Calendar.MINUTE)) + ":" + cal.get(Calendar.SECOND);
    }

    /**
     * @return  Date from Calendar in yyyy-MM-dd format adding 1 into month because Calendar
     * month starts from zero
     */
    public static String getDate(Calendar cal) {
        return "" + cal.get(Calendar.YEAR) + "-" +
                (cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.DATE);
    }

}
