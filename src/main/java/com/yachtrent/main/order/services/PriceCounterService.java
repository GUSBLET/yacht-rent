package com.yachtrent.main.order.services;

import com.yachtrent.main.yacht.Yacht;
import com.yachtrent.main.yacht.YachtRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;


@Service
public class PriceCounterService {
    private final YachtRepository yachtRepository;

    public PriceCounterService(YachtRepository yachtRepository){
        this.yachtRepository = yachtRepository;
    }

    public float countFullPrice(Date startOfRent, Date finishOfRent, long yachtId){
        // Limit in time for one order.
        final int limitOfHours = 48;

        Optional<Yacht> yacht = Optional.of(yachtRepository.findById(yachtId).get());
        float pricePerHour = yacht.isPresent() ? yacht.get().getPrice_per_hour() : -1;
        float time = countDifferentInTime(startOfRent, finishOfRent);
        if(time == -1)
            return -1;

        if(time > limitOfHours)
            return -1;

        return time * pricePerHour;
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
