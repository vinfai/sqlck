package com.vertigrated.db.mssql;

import com.vertigrated.db.AbstractSelectBuilder;
import com.vertigrated.temporal.Date;

import java.util.Map;

class SelectBuilder extends AbstractSelectBuilder
{
    SelectBuilder(final Database db, final String schemaName, final String tableName)
    {
        super(db, schemaName, tableName);
    }

    SelectBuilder(final Database db, final String schemaName, final String tableName, final Database.DateTimeFormat dateFormat)
    {
        super(db, schemaName, tableName);
    }

    public void addDecodeColumnName(final String column, final String alias, final Map<Integer, String> decode, final String elcs)
    {
        if (decode.isEmpty())
        {
            throw new RuntimeException("decode map can not be empty");
        }
        final StringBuilder sb = new StringBuilder();
        sb.append("CASE ");
        sb.append(column);
        for (final int i : decode.keySet())
        {
            sb.append(" WHEN ").append(i).append(" THEN ").append("'").append(decode.get(i)).append("' ");
        }
        if (elcs == null || elcs.length() == 0)
        {
            sb.append(" ELSE 'UNDEFINED' ");
        }
        else
        {
            sb.append(" ELSE ").append("'").append(elcs).append("' ");
        }
        sb.append("END ").append(" AS ").append(alias);

        super.columns.add(sb.toString());
    }

    public void addColumnName(final String name, final String isNull)
    {
        super.addColumnNameAliasWithIsNull(name, null, "ISNULL", isNull, true);
    }

    public void addColumnName(final String name, final String alias, final String isNull)
    {
        super.addColumnNameAliasWithIsNull(name, alias, "ISNULL", isNull, true);
    }

    public void addColumnName(final String name, final Number isNull)
    {
        super.addColumnNameAliasWithIsNull(name, null, "ISNULL", isNull.toString(), false);
    }

    public void addColumnName(final String name, final String alias, final Number isNull)
    {
        super.addColumnNameAliasWithIsNull(name, alias, "ISNULL", isNull.toString(), false);
    }

    public void addColumnName(final String name, final Date isNull)
    {
        this.addColumnName(name, null, isNull);
    }

    public void addColumnName(final String name, final String alias, final Date isNull)
    {
        super.addColumnNameAliasWithIsNull(name, alias, "ISNULL", isNull.toString(), true);
    }
}
