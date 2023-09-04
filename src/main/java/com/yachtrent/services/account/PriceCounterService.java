package com.yachtrent.services.account;

import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Service
public class PriceCounterService {



    public float countFullPrice(Date startOfRent, Date finishOfRent){
        // Limit in time for one order.
        final int limitOfHours = 48;

        // Price pro hour in grivna.
        final float priceProHour = 3000;

        float time = countDifferentInTime(startOfRent, finishOfRent);
        if(time == -1)
            return -1;

        if(time > limitOfHours)
            return -1;

        return time * priceProHour;
    }

    public Date convertToParametersToDate(String date, String time) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return dateFormat.parse(date + " " + time);
    }

    private float countDifferentInTime(Date startOfRent, Date finishOfRent){
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(startOfRent);

        Calendar finishCalendar = Calendar.getInstance();
        finishCalendar.setTime(finishOfRent);

        if(startCalendar.getTimeInMillis() > finishCalendar.getTimeInMillis())
            return -1;

        long differenceInMillis = finishCalendar.getTimeInMillis() - startCalendar.getTimeInMillis();

        return (float) differenceInMillis / (1000 * 60 * 60);
    }
}
