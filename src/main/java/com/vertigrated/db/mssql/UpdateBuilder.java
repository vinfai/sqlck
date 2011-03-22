package com.vertigrated.db.mssql;

import com.vertigrated.db.AbstractDatabase;
import com.vertigrated.db.AbstractUpdateBuilder;

class UpdateBuilder extends AbstractUpdateBuilder
{
    UpdateBuilder(final AbstractDatabase database, final String schema, final String tableName)
    {
        super(database, schema, tableName);
    }
}
