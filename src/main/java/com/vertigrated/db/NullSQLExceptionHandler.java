package com.vertigrated.db;

import java.sql.SQLException;

/**
 * This ExceptionHandler just "eats" exception and ignores them.
 * It returns true even though it ignores all exceptions.
 */
public class NullSQLExceptionHandler extends SQLExceptionHandler
{
    public NullSQLExceptionHandler()
    {
        super(null);
    }

    public boolean handleException(final SQLStatement sql, final SQLException e)
    {
        // do nothing, this is intentionally blank!
        return true;
    }
}
