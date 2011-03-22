package com.vertigrated.temporal;

/**
 * Months of the year.
 */
public enum Month
{
    UNDEFINED("UNDEFINED", 0),
    JANUARY("January", 31),
    FEBUARY("Febuary", 28),
    MARCH("March", 31),
    APRIL("April", 30),
    MAY("May", 31),
    JUNE("June", 30),
    JULY("July", 31),
    AUGUST("August", 31),
    SEPTEMBER("September", 30),
    OCTOBER("October", 31),
    NOVEMEBER("November", 30),
    DECEMBER("Decemeber", 31);

    private final String name;
    private final int daysInMonth;

    private Month(final String name, final int daysInMonth)
    {
        this.name = name;
        this.daysInMonth = daysInMonth;
    }

    public static Month getByJavaCalendarEquivalent(final int jce)
    {
        return Month.values()[jce + 1];
    }

    /**
     * Example of how the enums can encapsulate operations that they should
     * support and can go WAYY beyond simple ints with names.
     *
     * @param year year to get number of days in month
     * @return number of days in month
     */
    public int getDaysInMonth(final int year)
    {
        int result = 0;

        // check leap year
        if ((((year % 4) == 0) && ((year % 100) != 0)) || ((year % 400) == 0))
        {
            if (this.equals(Month.FEBUARY))
            {
                result = this.daysInMonth + 1;
            }
            else
            {
                result = this.daysInMonth;
            }
        }
        else
        {
            result = this.daysInMonth;
        }

        return result;
    }

    /**
     * This returns the correct ordinal to match up with the stupid way the
     * standard java Calendar classes work! :( They are moronically zero
     * based!
     *
     * @return ordinal - 1
     */
    public int getJavaCalendarEquivilent()
    {
        return this.ordinal() - 1;
    }

    public String getName()
    {
        return this.name;
    }

}
