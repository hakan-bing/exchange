package com.openpayd.exchange.component;

import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Component("dateValidator")
public class DateValidatorUsingDateFormat implements DateValidator {

    /* Formats a date in the date escape format yyyy-mm-dd. */
    private final String dateFormat = "yyyy-mm-dd";

    @Override
    public boolean isValid(String dateStr) {
        DateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
        simpleDateFormat.setLenient(false);
        try {
            simpleDateFormat.parse(dateStr);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }
}
