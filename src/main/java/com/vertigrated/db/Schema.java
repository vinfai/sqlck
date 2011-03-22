package com.vertigrated.db;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * This class represents the details about a specific schema.
 */
public class Schema
{
    private final String catalog;
    private final String name;
    private final List<Table> tables;

    Schema(final String catalog, final String name, final List<Table> tables)
    {
        this.catalog = catalog;
        this.name = name;
        this.tables = Collections.unmodifiableList(tables);
    }

    public String getCatalog()
    {
        return this.catalog;
    }

    public String getName()
    {
        return name;
    }

    public List<Table> getTables()
    {
        return tables;
    }

    public String toString()
    {
        final StringBuilder sb = new StringBuilder(1024);
        sb.append("{").append("schema").append(",");
        sb.append(this.name).append(",[");
        final Iterator<Table> iter = this.tables.iterator();
        while (iter.hasNext())
        {
            sb.append(iter.next());
            if (iter.hasNext())
            {
                sb.append(",");
            }
        }
        sb.append("]}");
        return sb.toString();
    }

}
