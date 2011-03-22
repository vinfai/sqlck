package com.vertigrated.db;

import java.sql.SQLException;

/**
 * A SQL specific implementation of Exception handler. Prints details of the exception to System.err
 */
public class SQLExceptionHandler implements ExceptionHandler
{
    private String className;

    public SQLExceptionHandler(final Object object)
    {
        if (object != null)
        {
            this.className = object.getClass().getName();
        }
        else
        {
            this.className = "NO CLASSNAME SUPPLIED";
        }
    }

//  Interface ExceptionHandler


    public boolean handleException(final Exception e)
    {
        return this.handleException(null, (SQLException) e);
    }

    /**
     * This provides default behavior to write out the exception to the System.err and will ALWAYS return true;
     *
     * @param e
     * @return always return true
     */
    public boolean handleException(final SQLStatement sql, final SQLException e)
    {
        System.err.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.err.println(this.className);
        if (sql == null)
        {
            System.err.println("NO SQL STATMENT SUPPLIED");
        }
        else
        {
            System.err.println(sql.getCommand());
        }
        System.err.println(e);
        System.err.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        return true;
    }
}
