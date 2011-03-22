package com.vertigrated.temporal;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * This is just the TIME portion if the Date class.
 * Another name for this class could have been Clock.
 */
public class Time
{
    public static Time timeFromDate(final Date date)
    {
        final GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(date);
        return new Time(gc.get(Calendar.HOUR_OF_DAY), gc.get(Calendar.MINUTE), gc.get(Calendar.SECOND), gc.get(Calendar.MILLISECOND));
    }

    private final int offset;
    private final int hourOfDay;
    private final int minute;
    private final int second;
    private final int ms;

    public Time(final int hourOfDay, final int minute, final int second)
    {
        this(hourOfDay, minute, second, 0);
    }

    public Time(final int hourOfDay, final int minute, final int second, final int ms)
    {
        this.hourOfDay = hourOfDay;
        this.minute = minute;
        this.second = second;
        this.ms = ms;
        this.offset = (int) ((hourOfDay * Interval.HOUR.getMS()) + (minute * Interval.MINUTE.getMS()) * (second * Interval.SECOND.getMS()) + ms);
        //TODO: do we store timezone information or not?
        //TODO: build the string representation right here and store it
    }

    /**
     * Get offset in ms from start of the "day"
     *
     * @return number of milliseconds from the start of the "day".
     */
    public int getOffset()
    {
        return offset;
    }

    public int getHourOfDay()
    {
        return hourOfDay;
    }

    public int getHour()
    {
        return this.isAM() ? this.hourOfDay : this.hourOfDay - 12;
    }

    public int getMinute()
    {
        return minute;
    }

    public int getSecond()
    {
        return second;
    }

    public int getMs()
    {
        return ms;
    }

    public boolean isAM()
    {
        return this.hourOfDay < 12;
    }

    public boolean isPM()
    {
        return this.hourOfDay >= 12;
    }

    public int hashCode()
    {
        return this.offset;
    }

    public boolean equals(final Object o)
    {
        return o instanceof Time && this.equals((Time) o);
    }

    public boolean equals(final Time time)
    {
        return this.offset == time.offset;
    }

    public String toString()
    {
        return this.toString("hh:mm:ss");
    }

    public String toString(final String format)
    {
        final SimpleDateFormat sdf = new SimpleDateFormat(format);
        final GregorianCalendar gc = new GregorianCalendar();
        gc.set(Calendar.HOUR_OF_DAY, this.getHourOfDay());
        gc.set(Calendar.MINUTE, this.getMinute());
        gc.set(Calendar.SECOND, this.getSecond());
        gc.set(Calendar.MILLISECOND, this.getMs());
        return sdf.format(gc.getTime());
    }

}
