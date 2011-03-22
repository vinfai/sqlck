package com.vertigrated.db;

/**
 * any custom exception handlers should implement this interface to plug into the Database execute methods.
 */
public interface ExceptionHandler
{
    boolean handleException(final Exception e);
}
