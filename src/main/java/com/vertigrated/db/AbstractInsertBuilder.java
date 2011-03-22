package com.vertigrated.db;

import com.vertigrated.temporal.Date;
import com.vertigrated.temporal.Time;
import com.vertigrated.temporal.TimeStamp;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Basic generalized implementation of InsertBuiler.
 * This should generate clean ANSI-SQL99 insert statements.
 * Vendor specific implementations can inherit from this and override
 * the getCommand() method to customize or specialize its behavior.
 */
public abstract class AbstractInsertBuilder implements InsertBuilder
{
    private final String schema;
    private final String table;
    private final List<ColumnValue> values;

    protected AbstractInsertBuilder(final String schema, final String table)
    {
        this.schema = schema;
        this.table = table;
        this.values = new ArrayList<ColumnValue>();
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

    public String getCommand()
    {
        final StringBuilder sql = new StringBuilder(1024);
        sql.append("INSERT INTO ");
        if (this.schema != null && this.schema.length() > 0)
        {
            sql.append(this.schema).append(".");
        }
        sql.append(this.table).append(" (");

        Iterator<ColumnValue> i = this.values.iterator();
        while (i.hasNext())
        {
            sql.append(i.next().name);

            if (i.hasNext())
            {
                sql.append(", ");
            }
        }

        sql.append(") VALUES (");
        i = values.iterator();

        while (i.hasNext())
        {
            sql.append(i.next().value);

            if (i.hasNext())
            {
                sql.append(", ");
            }
        }

        sql.append(")");

        return sql.toString();
    }

    public String toString()
    {
        return this.getCommand() + ";";
    }

}
