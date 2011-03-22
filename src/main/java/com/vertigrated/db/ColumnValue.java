package com.vertigrated.db;

import com.vertigrated.text.StringUtil;

class ColumnValue
{

    public final String name;
    public final String operator;
    public final String value;
    public final boolean caseSensitive;
    public final String strrep;

    ColumnValue(final String name, final Number value)
    {
        this(name, Operator.EQUALS, value.toString(), true, false);
    }

    ColumnValue(final String name, final Operator operator, final Number value)
    {
        this(name, operator, value.toString(), true, false);
    }

    ColumnValue(final String name, final String value, final boolean wrap)
    {
        this(name, Operator.EQUALS, value, true, wrap);
    }

    ColumnValue(final String name, final Number start, final Number end)
    {
        this(name, Operator.BETWEEN, start + " AND " + end, true, false);
    }

    ColumnValue(final String name, final String start, final String end, boolean caseSensitive)
    {
        this(name, Operator.BETWEEN, "'" + (caseSensitive ? start : start.toLowerCase()) + "' AND '" + (caseSensitive ? end : end.toLowerCase()) + "'", caseSensitive, false);
    }

    ColumnValue(final String name, final Operator operator, final String value, final boolean caseSensitive, final boolean wrap)
    {
        this.name = name;
        this.operator = operator.toString();
        this.value = wrap ? StringUtil.wrapWithSingleQuotes(value) : value;
        this.caseSensitive = caseSensitive;
        this.strrep = this.name + this.operator + this.value;
    }

    public int hashCode()
    {
        return this.strrep.hashCode();
    }

    public boolean equals(final Object obj)
    {
        return obj instanceof ColumnValue && this.equals((ColumnValue) obj);
    }

    public boolean equals(final ColumnValue cv)
    {
        return cv != null && this.name.equals(cv.name) && this.operator.equals(cv.operator) && this.value.equals(cv.value) && this.caseSensitive == cv.caseSensitive;
    }

    public String toString()
    {
        return this.name + this.operator + this.value;
    }
}
