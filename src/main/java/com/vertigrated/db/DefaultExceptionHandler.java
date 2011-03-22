package com.vertigrated.db;

/**
 * A default implementation of ExceptionHandler that prints out the context (class) and message of an exception to System.err
 */
public class DefaultExceptionHandler implements ExceptionHandler
{
    private String className;

    public DefaultExceptionHandler(final Class cls)
    {
        this.className = cls.getClass().getName();
    }

    public boolean handleException(final Exception e)
    {
        System.err.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.err.println(this.className);
        System.err.println(e.getMessage());
        System.err.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        return true;
    }
}
