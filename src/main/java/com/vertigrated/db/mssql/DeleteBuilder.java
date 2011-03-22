package com.vertigrated.db.mssql;

import com.vertigrated.db.AbstractDeleteBuilder;

class DeleteBuilder extends AbstractDeleteBuilder
{
    DeleteBuilder(final String schema, final String table)
    {
        super(schema, table);
    }
}
