package com.vertigrated.temporal;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * This represents a calendar Date of year, month and day of month.
 * It is an immutable object. It is not intended as a replacement for java.util.Date, it is
 * intended as an opaque struct like representation of a Date.
 */
public class Date
{
    private final int year;
    private final int month;
    private final int day;
    private final String strrep;

    public Date()
    {
        this(new java.util.Date());
    }

    public Date(final java.util.Date date)
    {
        final Calendar gc = new GregorianCalendar();
        gc.setTime(DateUtil.zeroTime(date));
        gc.set(Calendar.MILLISECOND, 0);
        this.year = gc.get(Calendar.YEAR);
        this.month = gc.get(Calendar.MONTH) + 1; // for some reason months are ZERO based.
        this.day = gc.get(Calendar.DAY_OF_MONTH);
        this.strrep = String.format("%04d-%02d-%02d", this.year, this.month, this.day);
    }

    /**
     * Constructs a Date object, throws IllegalArgumentExceptions if any of the parameters are out of range.
     *
     * @param year  year
     * @param month month
     * @param day   day of month
     */
    public Date(final int year, final int month, final int day)
    {
        // validate year
        if (year >= 0 && year <= DateUtil.getYear(DateUtil.END_OF_TIME))
        {
            this.year = year;
        }
        else
        {
            throw new IllegalArgumentException("year " + year + " is out of range ( 1 - " + DateUtil.getYear(DateUtil.END_OF_TIME) + ")");
        }

        // validate month
        if (month >= 1 && month <= 12)
        {
            this.month = month;
        }
        else
        {
            throw new IllegalArgumentException("month " + month + " is out of range ( 1 - 12 )");
        }

        // validate day of month
        if (day >= 1 && day <= Month.values()[month].getDaysInMonth(this.year))
        {
            this.day = day;
        }
        else
        {
            throw new IllegalArgumentException("day " + day + " is out of range ( 1 - " + Month.values()[month].getDaysInMonth(this.year) + " )");
        }
        this.strrep = String.format("%s-%s-%s", this.year, this.month, this.day);
    }

    public int getYear()
    {
        return year;
    }

    public int getMonth()
    {
        return month;
    }

    public int getDay()
    {
        return day;
    }

    /**
     * Uses the GregorianCalendar to convert to a java.util.Date representation.
     *
     * @return java.util.Date
     */
    public java.util.Date toJavaUtilDate()
    {
        return DateUtil.zeroTime(new GregorianCalendar(this.year, this.month - 1, this.day).getTime());
    }

    public int hashCode()
    {
        return this.strrep.hashCode();
    }

    public boolean equals(final Object obj)
    {
        return obj instanceof Date && this.strrep.equals(((Date) obj).strrep);
    }

    /**
     * Returns an ISO-8601 representation of the Date in form YYYY-MM-DD
     *
     * @return string representation of the Date
     */
    public String toString()
    {
        return this.strrep;
    }
}
