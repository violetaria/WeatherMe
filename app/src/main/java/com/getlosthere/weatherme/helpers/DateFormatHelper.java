package com.getlosthere.weatherme.helpers;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by violetaria on 10/11/16.
 */

public class DateFormatHelper {

    public static String formatDate(long epochTime) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("MMMM dd");
        String formattedDate = formatter.format(epochTime);
        return formattedDate;
    }
}
