package com.vertigrated.temporal;

/**
 * Intervals of Time
 */
public enum Interval
{
    UNDEFINED("UNDEFINED", 0),
    MILLISECOND("MILLISECOND", 1l),
    SECOND("SECOND", 1000l),
    MINUTE("MINUTE", 60000l),
    HOUR("HOUR", 3600000l),
    DAY("DAY", 86400000l),
    WEEK("WEEK", 604800000l),
    MONTH("MONTH", 2419200000l),
    YEAR("YEAR", 31536000000l),
    DECADE("DECADE", 315360000000l);

    private String name;
    private long ms;

    private Interval(final String name, final long ms)
    {
        this.name = name;
        this.ms = ms;
    }

    public String getName()
    {
        return this.name;
    }

    public long getMS()
    {
        return this.ms;
    }

    public long getDuration(final int duration)
    {
        return this.ms * duration;
    }

    public long getDuration(final long duration)
    {
        return this.ms * duration;
    }

    public long getDuration(final double duration)
    {
        return (long) (this.ms * duration);
    }

    public double fromMS(final long ms)
    {
        return (double) ms / (double) this.ms;
    }
}
