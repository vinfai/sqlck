package com.vertigrated.orm;

import javax.sql.RowSet;

public abstract class AbstractFactory<T, K>
{
    protected abstract void mapRowSetToObject(final RowSet rs);

    public abstract boolean store(final T object);

    public abstract boolean delete(final T object);

    public abstract T getByPrimaryKey(final K key);
}
