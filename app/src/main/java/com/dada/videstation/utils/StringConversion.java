package com.dada.videstation.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by dada on 13/11/2015.
 */
public class StringConversion
{
    public static String humanReadableByteCount(long bytes, boolean si) {
        int unit = si ? 1000 : 1024;
        if (bytes < unit) return bytes + " B";
        int exp = (int) (Math.log(bytes) / Math.log(unit));
        String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp-1) + (si ? "" : "i");
        return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
    }

    public static String timeConversion(int totalSeconds) {

        final int MINUTES_IN_AN_HOUR = 60;
        final int SECONDS_IN_A_MINUTE = 60;

        int totalMinutes = totalSeconds / SECONDS_IN_A_MINUTE;
        int minutes = totalMinutes % MINUTES_IN_AN_HOUR;
        int hours = totalMinutes / MINUTES_IN_AN_HOUR;

        return hours + "h" + minutes+"m";
    }

    public static String dateToString(Date date){
        String strDate;

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            strDate = df.format(date);

        }catch (NullPointerException e){
            e.printStackTrace();
            strDate = "0000-00-00";
        }

        return strDate;

    }

    public static Date stringToDate(String strDate){

        DateFormat date = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ssZ");

        try {
            return date.parse(strDate);
        } catch (ParseException|NullPointerException e) {
            e.printStackTrace();
        }

        return null;
    }
}
