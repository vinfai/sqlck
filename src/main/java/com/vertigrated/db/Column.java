package com.vertigrated.db;

/**
 * This class represets the details about a column in a table.
 */
public class Column implements Comparable
{
    private final String name;
    private final Integer ordinal;
    private final int sqlType;
    private final String sqlTypeName;
    private final Integer size;
    private final boolean nullable;

    Column(final String name, final int ordinal, final int sqlType, final String sqlTypeName, final int size, final String nullable)
    {
        this.name = name;
        this.ordinal = ordinal;
        this.sqlType = sqlType;
        this.sqlTypeName = sqlTypeName;
        this.size = size;
        this.nullable = nullable.equals("YES");
    }

    public String getName()
    {
        return name;
    }

    public int getOrdinal()
    {
        return ordinal;
    }

    public int getSqlType()
    {
        return sqlType;
    }

    public String getSqlTypeName()
    {
        return sqlTypeName;
    }

    public int getSize()
    {
        return this.size;
    }

    public boolean isNullable()
    {
        return this.nullable;
    }

    public int compareTo(final Object o)
    {
        return this.compareTo((Column) o);
    }

    public int compareTo(final Column c)
    {
        return this.ordinal.compareTo(c.ordinal);
    }

    public String toString()
    {
        final StringBuilder sb = new StringBuilder(1024);
        sb.append("{").append("column").append(",{name,");
        sb.append(this.name).append("},{ordinal,");
        sb.append(this.ordinal).append("},{sqlType,");
        sb.append(this.sqlType).append("},{sqlTypeName,");
        sb.append(this.sqlTypeName).append("},{size,");
        sb.append(this.size).append("},{nullable,");
        sb.append(this.nullable).append("}}");
        return sb.toString();
    }
}
