package com.vertigrated.db.mssql;

import com.vertigrated.db.AbstractInsertBuilder;

/**
 * Microsoft SQL Server specific InsertBuilder
 */
class InsertBuilder extends AbstractInsertBuilder
{
    InsertBuilder(final String schemaName, final String tableName)
    {
        super(schemaName, tableName);
    }
}
