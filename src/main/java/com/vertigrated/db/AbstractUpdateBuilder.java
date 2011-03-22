package com.vertigrated.db;

import com.vertigrated.temporal.Date;
import com.vertigrated.temporal.Time;
import com.vertigrated.temporal.TimeStamp;
import com.vertigrated.text.StringUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Basic generalized implementation of UpdateBuiler.
 * This should generate clean ANSI-SQL99 update statements.
 * Vendor specific implementations can inherit from this and override
 * the getCommand() method to customize or specialize its behavior.
 */
public abstract class AbstractUpdateBuilder extends WhereClause implements UpdateBuilder
{
    private final AbstractDatabase db;
    private final String schema;
    private final String table;
    private final List<ColumnValue> values;
    private final WhereClause whereClause;

    protected AbstractUpdateBuilder(final AbstractDatabase database, final String schema, final String tableName)
    {
        this.db = database;
        this.schema = schema;
        this.table = tableName;
        this.values = new ArrayList<ColumnValue>();
        this.whereClause = new WhereClause();
    }

    public void setColumnValue(final String column, final String value)
    {
        this.values.add(new ColumnValue(column, value, true));
    }

    public void setColumnValue(final String column, final Number value)
    {
        this.values.add(new ColumnValue(column, value));
    }

    public void setColumnValue(final String column, final boolean value)
    {
        this.values.add(new ColumnValue(column, value ? "T" : "F", true));
    }

    public void setColumnValue(final String column, final Date value)
    {
        this.values.add(new ColumnValue(column, value.toString(), true));
    }

    public void setColumnValue(final String column, final Time value)
    {
        this.values.add(new ColumnValue(column, value.toString(), true));
    }

    public void setColumnValue(final String column, final TimeStamp value)
    {
        this.values.add(new ColumnValue(column, value.toString(), true));
    }

    public void setDecrementColumnValue(final String column, final Number value)
    {
        this.values.add(new ColumnValue(column, column + " - " + value, false));
    }

    public void setIncrementColumnValue(final String column, final Number value)
    {
        this.values.add(new ColumnValue(column, column + " + " + value, false));
    }

    public String getCommand()
    {
        final StringBuilder sql = new StringBuilder(100);
        sql.append("UPDATE ");
        if (!StringUtil.isNullOrEmptyString(this.schema))
        {
            sql.append(this.schema).append(".");
        }
        sql.append(this.table).append(" SET ");


        final Iterator<ColumnValue> i = values.iterator();
        while (i.hasNext())
        {
            sql.append(i.next().toString());

            if (i.hasNext())
            {
                sql.append(", ");
            }
        }

        sql.append(super.toString());

        return sql.toString();
    }

    public String toString()
    {
        return this.getCommand();
    }
}
