package com.simbirsoft.utils;

import java.util.Calendar;
import java.util.Date;

public class Util {

    public static Date getDatePlusMinutes(String minutes) {
        Calendar date = Calendar.getInstance();
        long t = date.getTimeInMillis();
        return new Date(t + (Integer.parseInt(minutes) * 60000));
    }
}
