package com.vertigrated.db.sql99;

import com.vertigrated.db.AbstractSelectBuilder;

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

    public void addDecodeColumnName(String column, String alias, Map<Integer, String> decode, String elcs)
    {
        throw new UnsupportedOperationException();
    }
}
