package com.vertigrated.db;

/**
 * This is the basic SQLStatement interface that allows all SQLStatement
 * objects to render themselves via the getCommand() operation.
 */
public interface SQLStatement
{
    /**
     * This enum indicates how to wrap a string.
     */
    static enum WRAP_WITH
    {
        SINGLE_QUOTES, DOUBLE_QUOTES, NONE
    }

    public String getCommand();
}

