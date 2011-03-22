package com.vertigrated.db;

/**
 * This is the interface that needs to be implemented to create a concrete
 * UpdateBuilder.
 */
public interface UpdateBuilder extends ColumnValueBuilder, WhereBuilder, SQLStatement
{
    public void setDecrementColumnValue(String column, Number value);

    public void setIncrementColumnValue(String column, Number value);
}
