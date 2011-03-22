package com.vertigrated.temporal;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Days of the week.
 */
public enum Day
{
    UNDEFINED("Undefined"),
    SUNDAY("Sunday"),
    MONDAY("Monday"),
    TUESDAY("Tuesday"),
    WEDNESDAY("Wednesday"),
    THURSDAY("Thursday"),
    FRIDAY("Friday"),
    SATURDAY("Saturday");

    private final String name;

    private Day(String name)
    {
        this.name = name;
    }

    public static Day getByDate(Date date)
    {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(date);
        return Day.values()[gc.get(Calendar.DAY_OF_WEEK)];
    }

    public String getAbbreviation()
    {
        return this.name.substring(0, 1);
    }

    public String getName()
    {
        return this.name;
    }

    public String toString()
    {
        return this.name;
    }
}
