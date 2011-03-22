package com.vertigrated.db.oracle;

import com.vertigrated.db.AbstractDeleteBuilder;

class DeleteBuilder extends AbstractDeleteBuilder
{
    DeleteBuilder(String schemaName, String tableName)
    {
        super(schemaName, tableName);
    }
}
