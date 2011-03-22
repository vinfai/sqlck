package com.vertigrated.db.sql99;

import com.vertigrated.db.AbstractDatabase;

import javax.sql.DataSource;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This Database implementation should work with any ANSI SQL-99 compliant database. You can use it if there are no specialized
 * implementations for your prefered database, some advanced features might not be supported and will throw. The default
 * date time format is yyyy-MM-dd HH:mm:ss.SSS based on what a SimpleDateFormat object takes.
 * UnsupportedOperationExceptions.
 */
public class Database extends AbstractDatabase
{
    private static Database DATABASE;

    private final SimpleDateFormat sdf;

    public Database getInstance(final DataSource ds, final String defaultSchema)
    {
        if (DATABASE == null)
        {
            DATABASE = new Database(ds, defaultSchema);
        }
        return DATABASE;
    }

    private Database(final DataSource dataSource, final String defaultSchema)
    {
        super(dataSource, defaultSchema);
        this.sdf = new SimpleDateFormat(this.getDefaultDateTimeFormat());
    }

    /**
     * Get the default date time format for the database, currently this implementation uses "yyyy-MM-dd HH:mm:ss.SSS"
     *
     * @return this returns a pseudo ISO-8601 format "yyyy-MM-dd HH:mm:ss.SSS" that seems to be supported by just about every database.
     */
    public String getDefaultDateTimeFormat()
    {
        return "yyyy-MM-dd HH:mm:ss.SSS";
    }

    /**
     * Not implemented because there is no standard ANSI SQL-99 way to do this.
     *
     * @return throws UnsupportedOperationException
     */
    public int getNextVal(final String sequenceName)
    {
        throw new UnsupportedOperationException("this is not defined in ANSI SQL-99, this implementation doesn't support this method");
    }

    /**
     * Not implemented because there is no standard ANSI SQL-99 way to do this.
     *
     * @return throws UnsupportedOperationException
     */
    public Date getCurrentDatabaseDateTime()
    {
        throw new UnsupportedOperationException("this is not defined in ANSI SQL-99, this implementation doesn't support this method");
    }

    protected String formatDateTimeForInput(final Date date)
    {
        return this.sdf.format(date);
    }

    public DeleteBuilder createDeleteBuilder(final String tableName)
    {
        return new DeleteBuilder(null, tableName);
    }

    public DeleteBuilder createDeleteBuilder(final String schema, final String tableName)
    {
        return new DeleteBuilder(schema, tableName);
    }

    public InsertBuilder createInsertBuilder(final String tableName)
    {
        return new InsertBuilder(this.getDefaultSchema(), tableName);
    }

    public InsertBuilder createInsertBuilder(final String schema, final String tableName)
    {
        return new InsertBuilder(schema, tableName);
    }

    public SelectBuilder createSelectBuilder(final String schema, final String tableName)
    {
        return new SelectBuilder(this, schema, tableName);
    }

    public SelectBuilder createSelectBuilder(final String tableName)
    {
        return new SelectBuilder(this, this.getDefaultSchema(), tableName);
    }

    public UpdateBuilder createUpdateBuilder(final String tableName)
    {
        return new UpdateBuilder(this, null, tableName);
    }

    public UpdateBuilder createUpdateBuilder(final String schema, final String tableName)
    {
        return new UpdateBuilder(this, schema, tableName);
    }
}
