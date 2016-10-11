package com.getlosthere.weatherme.helpers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by violetaria on 10/11/16.
 */

public class DateFormatHelper {

    public static String getMonthDay(long unixUTCTimestamp) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("MMMM dd");
        Calendar calendar = Calendar.getInstance();
        TimeZone tz = TimeZone.getDefault();
        calendar.setTimeInMillis(unixUTCTimestamp * 1000);
        calendar.add(Calendar.MILLISECOND, tz.getOffset(calendar.getTimeInMillis()));
        return formatter.format(calendar.getTime());
    }

    public static String getWeekday(long unixUTCTimestamp) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("EEEE");
        Calendar calendar = Calendar.getInstance();
        TimeZone tz = TimeZone.getDefault();
        calendar.setTimeInMillis(unixUTCTimestamp * 1000);
        calendar.add(Calendar.MILLISECOND, tz.getOffset(calendar.getTimeInMillis()));
        Calendar today = Calendar.getInstance();
        Calendar tomorrow = Calendar.getInstance();
        tomorrow.add(Calendar.DATE, 1);
        if (calendar.get(Calendar.YEAR) == today.get(Calendar.YEAR) && calendar.get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR)) {
            return "Today";
        } else if (calendar.get(Calendar.YEAR) == tomorrow.get(Calendar.YEAR) && calendar.get(Calendar.DAY_OF_YEAR) == tomorrow.get(Calendar.DAY_OF_YEAR)) {
            return "Tomorrow";
        } else {
            return formatter.format(calendar.getTime());
        }
    }
}
