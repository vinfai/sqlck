package com.vertigrated.db;

import com.vertigrated.temporal.Date;
import com.vertigrated.temporal.Time;
import com.vertigrated.temporal.TimeStamp;
import com.vertigrated.text.CASE;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * This is the base class that provides a basic implementation of all classes that need to support selecting records
 * with a where clause.
 */
class WhereClause implements WhereBuilder
{
    private final List<ColumnValue> wcv;
    private final StringBuilder freeFormWhereClause;

    protected WhereClause()
    {
        this.wcv = new ArrayList<ColumnValue>();
        this.freeFormWhereClause = new StringBuilder();
    }

    public boolean equals(final Object object)
    {
        return object instanceof WhereClause && this.toString().equals(object.toString());
    }

    public int hashCode()
    {
        return this.toString().hashCode();
    }

    public String toString()
    {
        final StringBuilder sql = new StringBuilder(1024);

        // first off if there are no entries in the where column list AND
        // there is NO additional freeform where clause short circuit the logic
        // and just return an empty string since there is nothing to process
        if (this.wcv.isEmpty() && (this.freeFormWhereClause.length() == 0))
        {
            return "";
        }

        // we know there is a WHERE clause so lets start it off
        sql.append(" WHERE ");

        // if there is anything in the where column value list process it first
        if (!this.wcv.isEmpty())
        {
            final Iterator<ColumnValue> i = this.wcv.iterator();
            ColumnValue cv;
            while (i.hasNext())
            {
                cv = i.next();

                if (cv.caseSensitive)
                {
                    sql.append(cv);
                }
                else
                {
                    sql.append("lower(").append(cv.name).append(")").append(cv.operator);
                    sql.append(cv.value.toLowerCase());
                }

                if (i.hasNext())
                {
                    sql.append(" AND ");
                }
            }
        }

        // if there was something in the AND there is more in the freeform where clause
        // we need the AND otherwise there is only the freefrom clause
        if (!this.wcv.isEmpty() && (this.freeFormWhereClause.length() > 0))
        {
            sql.append(" AND ");
        }

        // append the freeform where clause if there is anything
        if (this.freeFormWhereClause.length() > 0)
        {
            sql.append(this.freeFormWhereClause.toString());
        }

        return sql.toString();
    }

//  Interface WhereBuilder

    public void addWhereEqual(final String column, final String value, final CASE kase)
    {
        if (CASE.SENSITIVE.equals(kase))
        {
            this.wcv.add(new ColumnValue(column, Operator.EQUALS, value, true, true));
        }
        else
        {
            this.wcv.add(new ColumnValue(column, Operator.EQUALS, value, false, true));
        }
    }

    public void addWhereEqual(final String column, final String value)
    {
        this.wcv.add(new ColumnValue(column, Operator.EQUALS, value, true, true));
    }

    public void addWhereEqual(final String column, final Number value)
    {
        this.wcv.add(new ColumnValue(column, Operator.EQUALS, value.toString(), true, false));
    }

    public void addWhereEqual(final String column, final boolean value)
    {
        this.wcv.add(new ColumnValue(column, Operator.EQUALS, value ? "T" : "F", true, true));
    }

    public void addWhereEqual(final String column, final Date value)
    {
        this.wcv.add(new ColumnValue(column, Operator.EQUALS, value.toString(), true, true));
    }

    public void addWhereEqual(final String column, final Time value)
    {
        this.wcv.add(new ColumnValue(column, Operator.EQUALS, value.toString(), true, true));
    }

    public void addWhereEqual(final String column, final TimeStamp value)
    {
        this.wcv.add(new ColumnValue(column, Operator.EQUALS, value.toString(), true, true));
    }

    public void addWhereLessThan(final String column, final Number value)
    {
        this.wcv.add(new ColumnValue(column, Operator.LESS_THAN, value.toString(), true, false));
    }

    public void addWhereLessThan(final String column, final Date value)
    {
        this.wcv.add(new ColumnValue(column, Operator.LESS_THAN, value.toString(), true, true));
    }

    public void addWhereLessThan(final String column, final Time value)
    {
        this.wcv.add(new ColumnValue(column, Operator.LESS_THAN, value.toString(), true, true));
    }

    public void addWhereLessThan(final String column, final TimeStamp value)
    {
        this.wcv.add(new ColumnValue(column, Operator.LESS_THAN, value.toString(), true, true));
    }

    public void addWhereGreaterThan(final String column, final Number value)
    {
        this.wcv.add(new ColumnValue(column, Operator.GREATER_THAN, value.toString(), true, false));
    }

    public void addWhereGreaterThan(final String column, final Date value)
    {
        this.wcv.add(new ColumnValue(column, Operator.GREATER_THAN, value.toString(), true, true));
    }

    public void addWhereGreaterThan(final String column, final Time value)
    {
        this.wcv.add(new ColumnValue(column, Operator.GREATER_THAN, value.toString(), true, true));
    }

    public void addWhereGreaterThan(final String column, final TimeStamp value)
    {
        this.wcv.add(new ColumnValue(column, Operator.GREATER_THAN, value.toString(), true, true));
    }

    public void addWhereNotEqual(final String column, final String value)
    {
        this.wcv.add(new ColumnValue(column, Operator.NOT_EQUAL, value, true, false));
    }

    public void addWhereNotEqual(final String column, final String value, final CASE kase)
    {
        if (CASE.SENSITIVE.equals(kase))
        {
            this.wcv.add(new ColumnValue(column, Operator.NOT_EQUAL, value, true, true));
        }
        else
        {
            this.wcv.add(new ColumnValue(column, Operator.NOT_EQUAL, value, false, true));
        }
    }

    public void addWhereNotEqual(final String column, final Number value)
    {
        this.wcv.add(new ColumnValue(column, Operator.NOT_EQUAL, value.toString(), true, false));
    }

    public void addWhereNotEqual(final String column, final Date value)
    {
        this.wcv.add(new ColumnValue(column, Operator.NOT_EQUAL, value.toString(), true, true));
    }

    public void addWhereNotEqual(final String column, final Time value)
    {
        this.wcv.add(new ColumnValue(column, Operator.NOT_EQUAL, value.toString(), true, true));
    }

    public void addWhereNotEqual(final String column, final TimeStamp value)
    {
        this.wcv.add(new ColumnValue(column, Operator.NOT_EQUAL, value.toString(), true, true));
    }

    public void addWhereLike(final String column, final String value)
    {
        this.wcv.add(new ColumnValue(column, Operator.LIKE, value, true, true));
    }

    public void addWhereBetween(final String column, final String start, final String end)
    {
        this.wcv.add(new ColumnValue(column, start, end, true));
    }

    public void addWhereBetween(final String column, final Number start, final Number end)
    {
        this.wcv.add(new ColumnValue(column, start, end));
    }

    public void addWhereBetween(final String column, final Date startDate, final Date endDate)
    {
        this.wcv.add(new ColumnValue(column, startDate.toString(), endDate.toString(), true));
    }

    public void addWhereBetween(final String column, final Time start, final Time end)
    {
        this.wcv.add(new ColumnValue(column, start.toString(), end.toString(), true));
    }

    public void addWhereBetween(final String column, final TimeStamp start, final TimeStamp end)
    {
        this.wcv.add(new ColumnValue(column, start.toString(), end.toString(), true));
    }

    public void addWhereIn(final String column, final List<String> list, final CASE kase)
    {
        final StringBuilder sb = new StringBuilder(512);
        sb.append("(");
        Iterator<String> i = list.iterator();
        while (i.hasNext())
        {
            sb.append("'");
            switch (kase)
            {
                case INSENSITIVE:
                    sb.append(i.next().toLowerCase());
                    break;
                case SENSITIVE:
                    sb.append(i.next());
                    break;
            }
            sb.append("'");
            if (i.hasNext())
            {
                sb.append(",");
            }
        }
        sb.append(")");

        this.wcv.add(new ColumnValue(column, Operator.IN, sb.toString(), CASE.SENSITIVE.equals(kase), false));
    }

    public void addWhereIn(final String column, final List<Number> list)
    {
        final StringBuilder sb = new StringBuilder(512);
        sb.append("(");
        Iterator<Number> i = list.iterator();
        while (i.hasNext())
        {
            sb.append(i.next());

            if (i.hasNext())
            {
                sb.append(",");
            }
        }
        sb.append(")");

        this.wcv.add(new ColumnValue(column, Operator.IN, sb.toString(), true, false));
    }

    public void addWhereIn(final String column, final SelectBuilder select)
    {
        if (((AbstractSelectBuilder) select).getColumnCount() != 1)
        {
            throw new RuntimeException("number of columns must be equal to one");
        }
        {
            final StringBuilder sb = new StringBuilder(255);
            sb.append(column).append(" IN (").append(select.getCommand()).append(")");

            this.wcv.add(new ColumnValue(column, Operator.IN, sb.toString(), true, false));
        }
    }

    public void appendFreeFormWhereClause(final String whereClause)
    {
        this.freeFormWhereClause.append(whereClause);
    }
}
