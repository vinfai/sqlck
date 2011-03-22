/**
 * Created by IntelliJ IDEA. User: Jarrod Date: Feb 6, 2003 Time: 12:57:52 PM To change this template use Options |
 * File Templates.
 */
package com.vertigrated.db.oracle;

import com.vertigrated.db.AbstractDatabase;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;


/**
 * OracleDatabase specific database implemenation, it returns OracleDatabase specific SQLStatement builders It can be considered a sort
 * of datasource factory to retreive connections from. It handles OracleDatabase specific optimizations
 */
public class Database extends AbstractDatabase
{
    private static Database DATABASE;

    public static Database getInstance(final DataSource ds, final String defaultSchema)
    {
        //TODO: put a check here and make sure it is actually an instance of an Oracle Datasource
        if (DATABASE == null)
        {
            DATABASE = new Database(ds, defaultSchema);
        }
        return DATABASE;
    }

    private Database(final DataSource dataSource, final String defaultSchema)
    {
        super(dataSource, defaultSchema);
    }

    protected String formatDateTimeForInput(Date date)
    {
        throw new UnsupportedOperationException("com.vertigrated.db.oracle.Database.formatDateTimeForInput not implemented yet!");
    }

    public int getNextVal(final String sequenceName)
    {
        Connection cn = null;
        Statement stmt = null;
        ResultSet rs = null;
        final int nextVal;
        try
        {
            cn = this.getConnection();
            stmt = cn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            rs = stmt.executeQuery("SELECT " + sequenceName + ".NEXTVAL FROM DUAL");
            nextVal = rs.getInt(1);
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
        finally
        {
            try
            {
                if (rs != null)
                {
                    rs.close();
                }
                if (stmt != null)
                {
                    stmt.close();
                }
                if (cn != null)
                {
                    cn.close();
                }
            }
            catch (SQLException e)
            {
                // we really don't care about this
            }
        }
        return nextVal;
    }

    public Date getCurrentDatabaseDateTime()
    {
        final Date date;
        final String sql = "SELECT SYSDATE FROM DUAL";
        final Connection cn = this.getConnection();
        Statement stmt = null;
        ResultSet rs = null;
        try
        {
            stmt = cn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            rs = stmt.executeQuery(sql);
            rs.next();
            date = rs.getTimestamp(1);
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
        finally
        {
            try
            {
                if (rs != null)
                {
                    rs.close();
                }
                if (stmt != null)
                {
                    stmt.close();
                }
                if (cn != null)
                {
                    cn.close();
                }
            }
            catch (SQLException e)
            {
                // we really don't care about this
            }
        }
        return date;
    }

    public DeleteBuilder createDeleteBuilder(String tableName)
    {
        return this.createDeleteBuilder(this.getDefaultSchema(), tableName);
    }

    public DeleteBuilder createDeleteBuilder(String schema, String tableName)
    {
        return new DeleteBuilder(schema, tableName);
    }

    public InsertBuilder createInsertBuilder(String tableName)
    {
        return this.createInsertBuilder(this.getDefaultSchema(), tableName);
    }

    public InsertBuilder createInsertBuilder(String schema, String tableName)
    {
        return new InsertBuilder(schema, tableName);
    }

    public SelectBuilder createSelectBuilder(String tableName)
    {
        return this.createSelectBuilder(this.getDefaultSchema(), tableName);
    }

    public SelectBuilder createSelectBuilder(String schema, String tableName)
    {
        return new SelectBuilder(this, schema, tableName);
    }

    public UpdateBuilder createUpdateBuilder(String tableName)
    {
        return this.createUpdateBuilder(this.getDefaultSchema(), tableName);
    }

    public UpdateBuilder createUpdateBuilder(String schema, String tableName)
    {
        return new UpdateBuilder(this, schema, tableName);
    }
}

