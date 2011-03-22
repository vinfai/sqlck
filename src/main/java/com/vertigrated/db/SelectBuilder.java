package com.vertigrated.db;

import com.vertigrated.temporal.Date;
import com.vertigrated.temporal.Time;
import com.vertigrated.temporal.TimeStamp;

import java.util.Map;

/**
 * This is the interface that needs to be implemented to create a implementation
 * specific SelectBuilder. Each specific implementation should be put in its own
 * package and the actual implementation should be called SelectBuilder.
 *
 * @see AbstractSelectBuilder
 */
public interface SelectBuilder extends WhereBuilder, SQLStatement
{
    int getMaxRows();

    void setMaxRows(int maxRows);

    void addColumnName(String column);

    void addColumnNameAlias(String name, String alias);

    void addColumnName(String name, String isNull);

    void addColumnName(String name, String alias, String isNull);

    void addColumnName(String name, Number isNull);

    void addColumnName(String name, String alias, Number isNull);

    void addColumnName(String name, Date isNull);

    void addColumnName(String name, String alias, Date isNull);

    void addColumnName(String name, Time isNull);

    void addColumnName(String name, String alias, Time isNull);

    void addColumnName(String name, TimeStamp isNull);

    void addColumnName(String name, String alias, TimeStamp isNull);

    void addDecodeColumnName(final String column, final String alias, final Map<Integer, String> decode, final String elcs);

    void addGroupBy(String name);

    void addHaving(String name, Operator operator, String value);

    void addHaving(String name, Operator operator, Number value);

    void addHaving(String name, Operator operator, Date value);

    void addHaving(String name, Operator operator, Time value);

    void addHaving(String name, Operator operator, TimeStamp value);

    void addOrderByAscending(String column);

    void addOrderByDescending(String column);
}
