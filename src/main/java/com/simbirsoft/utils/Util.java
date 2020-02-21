package com.simbirsoft.utils;

import java.util.Calendar;
import java.util.Date;

public class Util {

    public static Date getDatePlusMinutes(int minutes) {
        Calendar date = Calendar.getInstance();
        long t = date.getTimeInMillis();
        return new Date(t + (minutes * 60000));
    }

    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException | NullPointerException e) {
            return false;
        }
        return true;
    }
}
