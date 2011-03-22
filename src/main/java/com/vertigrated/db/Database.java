package com.vertigrated.db;

import javax.sql.RowSet;
import java.sql.Connection;
import java.util.Date;
import java.util.List;

/**
 * This is the master factory interface that is used to create concrete instances
 * of all the builder objects and retrieve metadata about the Datasource object that
 * this class is a Wrapper around.
 */
public interface Database
{
    public String getDefaultSchema();

    public Connection getConnection();

    public int getNextVal(String sequenceName);

    public Date getCurrentDatabaseDateTime();

    public DeleteBuilder createDeleteBuilder(String tableName);

    public DeleteBuilder createDeleteBuilder(String schema, String tableName);

    public InsertBuilder createInsertBuilder(String tableName);

    public InsertBuilder createInsertBuilder(String schema, String tableName);

    public SelectBuilder createSelectBuilder(String schema, String tableName);

    public SelectBuilder createSelectBuilder(String tableName);

    public UpdateBuilder createUpdateBuilder(String tableName);

    public UpdateBuilder createUpdateBuilder(String schema, String tableName);

    public int execute(SQLStatement sql);

    public int execute(SQLStatement sql, SQLExceptionHandler exceptionHandler);

    public List<String> executeBatch(List<SQLStatement> sql);

    public List<String> executeBatch(List<SQLStatement> sql, boolean allowFailures);

    public List<String> executeBatch(List<SQLStatement> sql, SQLExceptionHandler exceptionHandler);

    public List<String> executeBatch(List<SQLStatement> sql, boolean allowFailures, SQLExceptionHandler exceptionHandler);

    public RowSet executeSelect(SelectBuilder sql);

    public List<Schema> getSchemas();

    public List<Table> getTables(final String schema);

    public List<Table> getViews(final String schema);

    public List<Column> getColumns(final String schema, final String tableName);
}
