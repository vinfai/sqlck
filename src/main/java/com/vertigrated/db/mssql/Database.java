package com.vertigrated.db.mssql;

import com.vertigrated.db.AbstractDatabase;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Factory for Microsoft specialized builder objects.
 */
public class Database extends AbstractDatabase
{
    private static Map<String, Database> DATABASES;

    static
    {
        DATABASES = new HashMap<String, Database>();
    }

    public static Database getInstance(DataSource ds, final String defaultSchema)
    {
        // this is just to prevent NPE later on.
        if (ds == null)
        {
            throw new RuntimeException("DataSource can not be null!");
        }

        // this is a basic singleton pattern
        Database db = DATABASES.get(defaultSchema.toLowerCase());
        if (db == null)
        {
            db = new Database(ds, defaultSchema);
            DATABASES.put(defaultSchema.toLowerCase(), db);
        }
        return db;
    }

    /**
     * This is from http://www.sqljunkies.ddj.com/Article/6676BEAE-1967-402D-9578-9A1C7FD826E5.scuk
     * even though there are what appear to be 2 digit year fields, SQL Server actually reports and parses
     * those as 4 digit years.
     * <pre>
     * Style ID     Style Type
     * --------     ----------
     * 0 or 100      mon dd yyyy hh:miAM (or PM)
     * 101           mm/dd/yy
     * 102           yy.mm.dd
     * 103           dd/mm/yy
     * 104           dd.mm.yy
     * 105           dd-mm-yy
     * 106           dd mon yy
     * 107           Mon dd, yy
     * 108           hh:mm:ss
     * 9 or 109      mon dd yyyy hh:mi:ss:mmmAM (or PM)
     * 110           mm-dd-yy
     * 111           yy/mm/dd
     * 112           yymmdd
     * 13 or 113     dd mon yyyy hh:mm:ss:mmm(24h)
     * 114           hh:mi:ss:mmm(24h)
     * 20 or 120     yyyy-mm-dd hh:mi:ss(24h)
     * 21 or 121     yyyy-mm-dd hh:mi:ss.mmm(24h)
     * 126           yyyy-mm-ddThh:mm:ss.mmm(no spaces)
     * 130           dd mon yyyy hh:mi:ss:mmmAM
     * 131           dd/mm/yy hh:mi:ss:mmmAM
     * </pre>
     */
    public enum DateTimeFormat
    {
        STYLE_0(0, "MMM dd yyyy hh:mma"),
        STYLE_9(9, "MMM dd yyyy hh:mm:ss:SSSa"),
        STYLE_13(13, "MMM dd yyyy HH:mm:ss:SSSa"),
        STYLE_20(20, "yyyy-MM-dd HH:mm:ss"),
        STYLE_21(21, "yyyy-MM-dd HH:mm:ss.SSS"),
        STYLE_100(100, "MMM dd yyyy hh:mma"),
        STYLE_101(101, "MM/dd/yyyy"),
        STYLE_102(102, "yyyy.MM.dd"),
        STYLE_103(103, "dd/MM/yyyy"),
        STYLE_104(104, "dd.MM.yyyy"),
        STYLE_105(105, "dd-MM-yyyy"),
        STYLE_106(106, "dd MMM yyyy"),
        STYLE_107(107, "MMM dd, yyyy"),
        STYLE_108(108, "hh:mm:ss"),
        STYLE_109(109, "MMM dd yyyy hh:mm:ss:SSSa"),
        STYLE_110(110, "MM-dd-yyyy"),
        STYLE_111(111, "yyyy/MM/dd"),
        STYLE_112(112, "yyyyMMdd"),
        STYLE_113(113, "MMM dd yyyy HH:mm:ss:SSSa"),
        STYLE_114(114, "HH:mm:ss.SSS"),
        STYLE_120(120, "yyyy-MM-dd HH:mm:ss"),
        STYLE_121(121, "yyyy-MM-dd HH:mm:ss.SSS"),
        STYLE_126(126, "yyyy-MM-dd'T'hh:mm:ss:SSS"),
        STYLE_130(130, "dd MMM yyyy hh:mm:ss:SSSa"),
        STYLE_131(131, "dd/MM/yyyy hh:mm:ss:SSSa");

        private final int id;
        private final String format;

        private DateTimeFormat(final int id, final String format)
        {
            this.id = id;
            this.format = format;
        }

        public int getId()
        {
            return id;
        }

        public String toString()
        {
            return this.format;
        }
    }

    private Database(final DataSource dataSource, final String defaultSchema)
    {
        super(dataSource, defaultSchema);
    }

    /**
     * Since SQL Server doesn't support independant sequence generation like Oracle does we emulate it with a special
     * table and function.
     * This method requries a table named "sequence" with two columns. An column called name for the name of the sequence,
     * and a column called value to hold the value of the sequence.
     * <p/>
     * Here is the DDL.
     * <p/>
     * <pre>
     * CREATE TABLE [dbo].[sequence]
     * (
     * [name] [varchar](50) NOT NULL,
     * [value] [int] NOT NULL DEFAULT ((0))
     * )
     * </pre>
     * <p/>
     * Then here is the stored procedure to increment and retrive the sequence value in an atomic fashion.
     * <p/>
     * <pre>
     * CREATE PROCEDURE nextval
     * &#x40name varchar(50)
     * AS
     * BEGIN
     * BEGIN TRANSACTION
     * SET NOCOUNT ON;
     * UPDATE sequence SET value = value + 1 WHERE name = @name;
     * SELECT value FROM sequence WHERE name = @name;
     * COMMIT
     * END
     * </pre>
     */
    public int getNextVal(final String sequenceName)
    {
        int nextID = 0;
        Connection cn = this.getConnection();
        Statement stmt = null;
        ResultSet rs = null;

        try
        {
            stmt = cn.createStatement();
            rs = stmt.executeQuery(String.format("execute nextval @@name = %s", sequenceName));

            if (rs.next())
            {
                nextID = rs.getInt("sequence_value");
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
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
                // don't really care
            }
        }

        return nextID;
    }

    public Date getCurrentDatabaseDateTime()
    {
        final String sql = "SELECT CURRENT_TIMESTAMP as cdt";
        Date result = null;
        try
        {
            final Statement stmt = this.getConnection().createStatement();
            final ResultSet rs = stmt.executeQuery(sql);
            rs.next();
            result = new Date(rs.getTimestamp("cdt").getTime());
            rs.close();
            stmt.close();
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
        return result;
    }

    public DeleteBuilder createDeleteBuilder(String tableName)
    {
        return new DeleteBuilder(this.getDefaultSchema(), tableName);
    }

    public DeleteBuilder createDeleteBuilder(String schema, String tableName)
    {
        return new DeleteBuilder(schema, tableName);
    }

    public InsertBuilder createInsertBuilder(String tableName)
    {
        return new InsertBuilder(this.getDefaultSchema(), tableName);
    }

    public InsertBuilder createInsertBuilder(String schema, String tableName)
    {
        return new InsertBuilder(schema, tableName);
    }

    public com.vertigrated.db.SelectBuilder createSelectBuilder(String schema, String tableName)
    {
        return new SelectBuilder(this, schema, tableName);
    }

    public SelectBuilder createSelectBuilder(String tableName)
    {
        return new SelectBuilder(this, this.getDefaultSchema(), tableName);
    }

    public UpdateBuilder createUpdateBuilder(String tableName)
    {
        return new UpdateBuilder(this, this.getDefaultSchema(), tableName);
    }

    public UpdateBuilder createUpdateBuilder(String schema, String tableName)
    {
        return new UpdateBuilder(this, schema, tableName);
    }
}
