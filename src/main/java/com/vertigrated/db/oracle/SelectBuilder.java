package com.vertigrated.db.oracle;

import com.vertigrated.db.AbstractSelectBuilder;

import java.util.Date;
import java.util.Map;

class SelectBuilder extends AbstractSelectBuilder
{
    SelectBuilder(final Database db, final String tableName)
    {
        super(db, null, tableName);
    }

    SelectBuilder(final Database db, final String schemaName, final String tableName)
    {
        super(db, schemaName, tableName);
    }

    public void addColumnName(final String name, final String isNull)
    {
        super.addColumnNameAliasWithIsNull(name, null, "NVL", isNull, true);
    }

    public void addColumnName(final String name, final String alias, final String isNull)
    {
        super.addColumnNameAliasWithIsNull(name, alias, "NVL", isNull, true);
    }

    public void addColumnName(final String name, final Number isNull)
    {
        super.addColumnNameAliasWithIsNull(name, null, "NVL", isNull.toString(), false);
    }

    public void addColumnName(final String name, final String alias, final Number isNull)
    {
        super.addColumnNameAliasWithIsNull(name, null, "NVL", isNull.toString(), false);
    }

    public void addDecodeColumnName(String column, String alias, Map<Integer, String> decode, String elcs)
    {
        throw new UnsupportedOperationException();
    }

    public void addColumnName(final String name, final Date isNull)
    {
        throw new UnsupportedOperationException("com.vertigrated.db.oracle.SelectBuilder.addDecodeColumnName not implemented yet!");
    }

    public void addColumnName(final String name, final String alias, final Date isNull)
    {
        throw new UnsupportedOperationException("com.vertigrated.db.oracle.SelectBuilder.addDecodeColumnName not implemented yet!");
    }
}

