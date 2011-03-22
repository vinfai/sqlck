/*
 * Created by IntelliJ IDEA.
 * User: tempmis
 * Date: Apr 12, 2002
 * Time: 2:38:44 PM
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package com.vertigrated.db;

import com.vertigrated.temporal.Date;
import com.vertigrated.temporal.Time;
import com.vertigrated.temporal.TimeStamp;
import com.vertigrated.text.StringUtil;

import java.util.*;


/**
 * This is a Select statment builder. This is a very simple implementation intended to help create simple single
 * table select statments, it only support selecting columns from a single table with very basic primary key type
 * where clause.
 */
public abstract class AbstractSelectBuilder extends WhereClause implements SelectBuilder
{
    public static final int ALL_ROWS = 0;

    private final Database db;
    private final String schema;
    private final String table;
    private int maxRows;
    protected final Set<String> columns;
    private final List<String> groupBy;
    private final List<String> having;
    private final List<String> orderBy;

    protected AbstractSelectBuilder(final Database db, final String schema, final String table)
    {
        this.db = db;
        this.schema = schema;
        this.table = table;
        this.columns = new TreeSet<String>();
        this.groupBy = new ArrayList<String>(25);
        this.having = new ArrayList<String>(25);
        this.orderBy = new ArrayList<String>(4);
        this.maxRows = ALL_ROWS;
    }

    int getColumnCount()
    {
        return this.columns.size();
    }

    public Set<String> getColumns()
    {
        return Collections.unmodifiableSet(this.columns);
    }

    public int getMaxRows()
    {
        return this.maxRows;
    }

    public void setMaxRows(final int maxRows)
    {
        this.maxRows = maxRows;
    }

    public String getCommand()
    {
        final StringBuilder sb = new StringBuilder(1024);
        sb.append("SELECT ");

        // if there are no supplied columns to select we select them all
        if (this.columns.isEmpty())
        {
            for (final Column c : this.db.getColumns(this.schema, this.table))
            {
                this.columns.add(c.getName());
            }
        }

        // add the columns to select
        final Iterator<String> sli = this.columns.iterator();
        while (sli.hasNext())
        {
            sb.append(sli.next());
            if (sli.hasNext())
            {
                sb.append(",");
            }
            sb.append(" ");
        }

        sb.append("FROM ");

        if (!StringUtil.isNullOrEmptyString(this.schema))
        {
            sb.append(this.schema).append(".");
        }

        sb.append(this.table);

        // where clause
        sb.append(super.toString());

        // group by clause
        if (!this.groupBy.isEmpty())
        {
            sb.append(" GROUP BY ");
            final Iterator<String> si = this.groupBy.iterator();
            while (si.hasNext())
            {
                sb.append(si.next());
                if (si.hasNext())
                {
                    sb.append(", ");
                }
            }
        }

        // having clause only if there is a groupby
        if (this.groupBy.isEmpty() && !this.having.isEmpty())
        {
            throw new RuntimeException("Can not have a 'HAVING' clause without a 'GROUP BY' clause!");
        }
        else
        {
            if (!this.having.isEmpty())
            {
                sb.append(" HAVING ");
                final Iterator<String> si = this.having.iterator();
                while (si.hasNext())
                {
                    sb.append(si.next());
                    if (si.hasNext())
                    {
                        sb.append(" AND ");
                    }
                }
            }
        }

        // order by clause
        if (!this.orderBy.isEmpty())
        {
            sb.append(" ORDER BY ");
            final Iterator<String> si = this.orderBy.iterator();
            while (si.hasNext())
            {
                sb.append(si.next());
                if (si.hasNext())
                {
                    sb.append(", ");
                }
            }
        }

        return sb.toString();
    }

    /**
     * adds a column to the list of columns to be selected from the table.
     *
     * @param column name of the column
     */
    public void addColumnName(final String column)
    {
        this.columns.add(column);
    }

    public void addColumnName(final String name, final String isNull)
    {
        this.addColumnNameAliasWithIsNull(name, null, "COALESCE", isNull, true);
    }

    public void addColumnName(final String name, final String alias, final String isNull)
    {
        this.addColumnNameAliasWithIsNull(name, alias, "COALESCE", isNull, true);
    }

    public void addColumnName(final String name, final Number isNull)
    {
        this.addColumnNameAliasWithIsNull(name, null, "COALESCE", isNull.toString(), false);
    }

    public void addColumnName(final String name, final String alias, final Number isNull)
    {
        this.addColumnNameAliasWithIsNull(name, alias, "COALESCE", isNull.toString(), false);
    }

    public void addColumnName(final String name, final Date isNull)
    {
        this.addColumnNameAliasWithIsNull(name, null, "COALESCE", isNull.toString(), true);
    }

    public void addColumnName(final String name, final String alias, final Date isNull)
    {
        this.addColumnNameAliasWithIsNull(name, alias, "COALESCE", isNull.toString(), true);
    }

    public void addColumnName(final String name, final Time isNull)
    {
        this.addColumnNameAliasWithIsNull(name, null, "COALESCE", isNull.toString(), true);
    }

    public void addColumnName(final String name, final String alias, final Time isNull)
    {
        this.addColumnNameAliasWithIsNull(name, alias, "COALESCE", isNull.toString(), true);
    }

    public void addColumnName(final String name, final TimeStamp isNull)
    {
        this.addColumnNameAliasWithIsNull(name, null, "COALESCE", isNull.toString(), true);
    }

    public void addColumnName(final String name, final String alias, final TimeStamp isNull)
    {
        this.addColumnNameAliasWithIsNull(name, alias, "COALESCE", isNull.toString(), true);
    }

    /**
     * this adds a column to the list to be selected from the table and support renaming the column with an alias
     *
     * @param name  name of the column
     * @param alias alias to the name of the column
     */
    public void addColumnNameAlias(final String name, final String alias)
    {
        this.addColumnName(name + " as " + alias);
    }

    protected void addColumnNameAliasWithIsNull(final String name, final String alias, final String funcName, final String isNullValue, final boolean wrap)
    {
        final StringBuilder sb = new StringBuilder(255);
        sb.append(funcName).append("(").append(name).append(",").append(wrap ? StringUtil.wrapWithSingleQuotes(isNullValue) : isNullValue).append(")");
        if (!StringUtil.isNullOrEmptyString(alias))
        {
            sb.append(" as ").append(alias);
        }
        this.addColumnName(sb.toString());
    }

    public void addGroupBy(final String name)
    {
        if (this.columns.contains(name))
        {
            this.groupBy.add(name);
        }
        else
        {
            throw new RuntimeException(String.format("%s is not in the selected columns list", name));
        }
    }

    public void addHaving(String name, Operator operator, String value)
    {
        this.having.add(String.format("%s%s'%s'", name, operator, value));
    }

    public void addHaving(String name, Operator operator, Number value)
    {
        this.having.add(String.format("%s%s%s", name, operator, value));
    }

    public void addHaving(String name, Operator operator, Date value)
    {
        this.having.add(String.format("%s%s'%s'", name, operator, value));
    }

    public void addHaving(String name, Operator operator, Time value)
    {
        this.having.add(String.format("%s%s'%s'", name, operator, value));
    }

    public void addHaving(String name, Operator operator, TimeStamp value)
    {
        throw new UnsupportedOperationException();
    }

    public void addOrderByAscending(final String column)
    {
        if (this.columns.contains(column))
        {
            this.orderBy.add(column + " " + "ASC");
        }
        else
        {
            throw new RuntimeException(String.format("%s is not in the selected columns list", column));
        }
    }

    public void addOrderByDescending(final String column)
    {
        if (this.columns.contains(column))
        {
            this.orderBy.add(column + " " + "DESC");
        }
        else
        {
            throw new RuntimeException(String.format("%s is not in the selected columns list", column));
        }
    }

    public int hashCode()
    {
        return this.toString().hashCode();
    }

    public String toString()
    {
        return this.getCommand();
    }
}
