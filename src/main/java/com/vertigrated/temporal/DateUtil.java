package com.vertigrated.temporal;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

/**
 * Utility functions for dealing with temporal data ( Dates and Times )
 */
public class DateUtil
{
    public static final TimeZone UTC;

    /**
     * 0001.01.01 12:00:00 AM +0000
     */
    public static final Date BEGINNING_OF_TIME;

    /**
     * new Date(Long.MAX_VALUE) in UTC time zone
     */
    public static final Date END_OF_TIME;

    static
    {
        UTC = TimeZone.getTimeZone("UTC");
        final Calendar c = new GregorianCalendar(UTC);
        c.set(1, 0, 1, 0, 0, 0);
        c.set(Calendar.MILLISECOND, 0);
        BEGINNING_OF_TIME = c.getTime();
        c.setTime(new Date(Long.MAX_VALUE));
        END_OF_TIME = c.getTime();
    }

    /**
     * @param d date
     * @return iso 8601 verbose formatted date using TimeZone.getDefault() time zone.
     */
    public static String formatAsISO_8601(final Date d)
    {
        return formatAsISO_8601(d, TimeZone.getDefault());
    }

    /**
     * Format a Date object as the official ISO 8601 date time format.
     * http://www.w3.org/TR/NOTE-datetime
     * This uses the most verbose format string possible.
     *
     * @param d  date
     * @param tz TimeZone to use to format the date with
     * @return properly formated date and time as per ISO 8601 specification
     */
    public static String formatAsISO_8601(final Date d, final TimeZone tz)
    {
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        sdf.setTimeZone(tz);
        final String s = sdf.format(d);
        return s.substring(0, 26) + ":" + s.substring(26);
    }

    public static String dateToString(final Date d, final String format)
    {
        return dateToString(d, format, TimeZone.getDefault());
    }

    public static String dateToString(final Date d, final String format, final TimeZone tz)
    {
        final SimpleDateFormat sdf = new SimpleDateFormat(format);
        sdf.setTimeZone(tz);
        return sdf.format(d);
    }

    public static Date dateFromString(final String s, final String format)
    {
        final SimpleDateFormat sdf = new SimpleDateFormat(format);
        final Date d;
        try
        {
            d = sdf.parse(s);
        }
        catch (ParseException pe)
        {
            throw new RuntimeException(pe);
        }
        return d;
    }

    /**
     * Given a Date construct a List<Date> that includes all
     * the days before and after that given date that makes up a
     * logical week. If the date is a Wednesday then we will add
     * the Sunday - Tuesday before and the Thursday - Saturday afterwards.
     *
     * @param date date to extrapolate from
     * @return List<Date> that represent a logical week.
     */
    public static List<Date> getDaysOfWeek(final Date date)
    {
        final List<Date> week = new ArrayList<Date>(7);
        final Day dayOfWeek = Day.getByDate(date);
        week.add(date);
        Date day = date; // set to reference date
        // get all preceding days
        for (int i = 1; i < dayOfWeek.ordinal(); i++)
        {
            day = DateUtil.dayBefore(day);
            week.add(day);
        }
        day = date;
        for (int i = dayOfWeek.ordinal(); i < 7; i++)
        {
            day = DateUtil.dayAfter(day);
            week.add(day);
        }
        Collections.sort(week); // sort in "natural order"
        return week;
    }

    public static boolean equalsIgnoreTime(final Date d1, final Date d2)
    {
        return DateUtil.zeroTime(d1).equals(DateUtil.zeroTime(d2));
    }

    public static List<Date> getDaysOfMonth(final Date date)
    {
        final List<Date> days = new ArrayList<Date>(31);
        final GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(DateUtil.zeroTime(date));
        final Month month = Month.values()[(gc.get(Calendar.MONTH))];
        gc.set(Calendar.DAY_OF_MONTH, 1);
        Date d = gc.getTime();
        for (int i = 1; i < month.getDaysInMonth(gc.get(Calendar.YEAR)); i++)
        {
            days.add(d);
            d = DateUtil.dayAfter(d);
        }

        Collections.sort(days); // sort in "natural order"

        return days;
    }

    public static List<Date> getXDaysBeforeDate(Date date, final int number)
    {
        final List<Date> days = new ArrayList<Date>(number);
        for (int i = 0; i < number; i++)
        {
            date = DateUtil.dayBefore(date);
            days.add(date);
        }

        return days;
    }

    public static List<Date> getXDaysAfterDate(final Date date, final int number)
    {
        final List<Date> days = new ArrayList<Date>(number);
        for (int i = 0; i < number; i++)
        {
            days.add(DateUtil.dayAfter(date));
        }

        return days;
    }

    public static Date daysFromToday(final int days)
    {
        return DateUtil.addDays(DateUtil.today(), days);
    }

    /**
     * sets all the time related fields to ZERO!
     *
     * @param date
     * @return Date with hours, minutes, seconds and ms set to ZERO!
     */
    public static Date zeroTime(final Date date)
    {
        return DateUtil.setTime(date, 0, 0, 0, 0);
    }

    public static Date setTimeHour(final Date date, final int hour)
    {
        return DateUtil.setTime(date, hour, 0, 0, 0);
    }

    /**
     * Set the time of the given Date
     *
     * @param date
     * @param hourOfDay
     * @param minute
     * @param second
     * @param ms
     * @return date with time set
     */
    public static Date setTime(final Date date, final int hourOfDay, final int minute, final int second, final int ms)
    {
        final GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(date);
        gc.set(Calendar.HOUR_OF_DAY, hourOfDay);
        gc.set(Calendar.MINUTE, minute);
        gc.set(Calendar.SECOND, second);
        gc.set(Calendar.MILLISECOND, ms);
        return gc.getTime();
    }

    /**
     * Get the Date before the given date;
     *
     * @param date
     * @return previous date
     */
    public static Date dayBefore(final Date date)
    {
        final GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(date);
        gc.roll(Calendar.DAY_OF_YEAR, false);
        return gc.getTime();
    }

    /**
     * Get the next Date after the given date;
     *
     * @param date
     * @return next date
     */
    public static Date dayAfter(final Date date)
    {
        final GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(date);
        gc.roll(Calendar.DAY_OF_YEAR, true);
        return gc.getTime();
    }

    /**
     * Convience method to get today.
     * Zero's the time out.
     */
    public static Date today()
    {
        return DateUtil.zeroTime(new Date());
    }

    public static Date now()
    {
        return new Date();
    }

    public static Date tommorrow()
    {
        return DateUtil.dayAfter(DateUtil.today());
    }

    public static Date thisTimeTommorrow()
    {
        return DateUtil.dayAfter(DateUtil.now());
    }

    public static Date yesterday()
    {
        return DateUtil.dayBefore(DateUtil.today());
    }

    public static Date thisTimeYesterday()
    {
        return DateUtil.dayBefore(DateUtil.now());
    }

    public static Date addDays(final Date date, final int days)
    {
        return addMilliSeconds(date, Interval.DAY.getMS() * days);
    }

    public static Date addHours(final Date date, final double hours)
    {
        return addMilliSeconds(date, Interval.HOUR.getDuration(hours));
    }

    public static Date addHours(final Date date, final int hours)
    {
        return addMilliSeconds(date, Interval.HOUR.getDuration(hours));
    }

    public static Date addMinutes(final Date date, final int minutes)
    {
        return addMilliSeconds(date, Interval.MINUTE.getDuration(minutes));
    }

    public static Date addSeconds(final Date date, final int seconds)
    {
        return addMilliSeconds(date, seconds * Interval.SECOND.getMS());
    }

    public static Date addMilliSeconds(final Date date, final long milliseconds)
    {
        return new Date(date.getTime() + milliseconds);
    }

    public static double duration(final Date start, final Date end, final Interval interval)
    {
        final double result;
        result = (double) (end.getTime() - start.getTime()) / (double) interval.getMS();
        return result;
    }

    public static int getYear(final Date d)
    {
        final GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(d);
        return gc.get(Calendar.YEAR);
    }

    public static Month getMonth(final Date d)
    {
        final GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(d);
        return Month.getByJavaCalendarEquivalent(gc.get(Calendar.MONTH));
    }

    public static int getDayOfMonth(final Date d)
    {
        final GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(d);
        return gc.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * This returns 12 hour time
     *
     * @param d date
     * @return hour in 12 hour time
     */
    public static int getHour(final Date d)
    {
        final GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(d);
        return gc.get(Calendar.HOUR);
    }

    /**
     * This returns 24 hour time
     *
     * @param d Date
     * @return hour of day extracted from Date
     */
    public static int getHourOfDay(final Date d)
    {
        final GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(d);
        return gc.get(Calendar.HOUR_OF_DAY);
    }

    public static int getWeekOfYear(final Date d)
    {
        final GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(d);
        return gc.get(Calendar.WEEK_OF_YEAR);
    }

    public static int getDayOfYear(final Date date)
    {
        final GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(date);
        return gc.get(Calendar.DAY_OF_YEAR);
    }

    private DateUtil()
    {
        // do not instantiate
    }

}
