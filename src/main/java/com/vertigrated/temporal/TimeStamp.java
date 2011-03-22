package com.vertigrated.temporal;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * An instant in time accurate to the millisecond.
 */
public class TimeStamp
{
    public static final TimeStamp NULL_TIMESTAMP;

    static
    {
        NULL_TIMESTAMP = new TimeStamp();
    }

    private final Date date;
    private final long time;
    private final int year;
    private final int month;
    private final int dayOfMonth;
    private final int hourOfDay;
    private final int minuteOfHour;
    private final int secondOfMinute;
    private final int msOfSecond;
    private final TimeZone tz;
    private final String strrep;

    private TimeStamp()
    {
        this.date = null;
        this.time = 0;
        this.year = 0;
        this.month = 0;
        this.dayOfMonth = 0;
        this.hourOfDay = 0;
        this.minuteOfHour = 0;
        this.secondOfMinute = 0;
        this.msOfSecond = 0;
        this.tz = DateUtil.UTC;
        this.strrep = "00-00-0000T00:00:00.0000+00";
    }

    public TimeStamp(final Date date)
    {
        this(date, TimeZone.getDefault());
    }

    public TimeStamp(final Date d, final TimeZone tz)
    {
        this.date = d;
        this.time = d.getTime();
        final GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(d);
        this.year = gc.get(Calendar.YEAR);
        this.month = gc.get(Calendar.MONTH);
        this.dayOfMonth = gc.get(Calendar.DAY_OF_MONTH);
        this.hourOfDay = gc.get(Calendar.HOUR_OF_DAY);
        this.minuteOfHour = gc.get(Calendar.MINUTE);
        this.secondOfMinute = gc.get(Calendar.SECOND);
        this.msOfSecond = gc.get(Calendar.MILLISECOND);
        this.tz = tz;
        this.strrep = DateUtil.formatAsISO_8601(d, tz);
    }

    public long getTime()
    {
        return time;
    }

    public int getYear()
    {
        return year;
    }

    public int getMonth()
    {
        return month;
    }

    public int getDayOfMonth()
    {
        return dayOfMonth;
    }

    public int getHourOfDay()
    {
        return hourOfDay;
    }

    public int getMinuteOfHour()
    {
        return minuteOfHour;
    }

    public int getSecondOfMinute()
    {
        return secondOfMinute;
    }

    public int getMsOfSecond()
    {
        return msOfSecond;
    }

    public TimeZone getTimeZone()
    {
        return this.tz;
    }

    public boolean equals(final Object o)
    {
        return o instanceof TimeStamp && this.equals((TimeStamp) o);
    }

    public boolean equals(final Date d)
    {
        return this.date.equals(d);
    }

    public int hashCode()
    {
        return this.date.hashCode();
    }

    public boolean equals(final TimeStamp ts)
    {
        return this.time == ts.time;
    }

    public String toString()
    {
        return this.strrep;
    }
}
