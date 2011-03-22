package com.vertigrated.db;

import com.vertigrated.temporal.Date;
import com.vertigrated.temporal.Time;
import com.vertigrated.temporal.TimeStamp;
import com.vertigrated.text.CASE;

import java.util.List;

/**
 * This interface represents the methods called to build a where clause.
 * Any builder that needs a where clause should implement this interface.
 *
 * @see WhereClause
 */
interface WhereBuilder
{
    public void addWhereEqual(String column, String value, CASE kase);

    public void addWhereEqual(String column, String value);

    public void addWhereEqual(String column, Number value);

    public void addWhereEqual(String column, boolean value);

    public void addWhereEqual(String column, Date value);

    public void addWhereEqual(String column, Time value);

    public void addWhereEqual(String column, TimeStamp value);

    public void addWhereLessThan(String column, Number value);

    public void addWhereLessThan(String column, Date value);

    public void addWhereLessThan(String column, Time value);

    public void addWhereLessThan(String column, TimeStamp value);

    public void addWhereGreaterThan(String column, Number value);

    public void addWhereGreaterThan(String column, Date value);

    public void addWhereGreaterThan(String column, Time value);

    public void addWhereGreaterThan(String column, TimeStamp value);

    public void addWhereNotEqual(String column, String value);

    public void addWhereNotEqual(String column, String value, CASE kase);

    public void addWhereNotEqual(String column, Number value);

    public void addWhereNotEqual(String column, Date value);

    public void addWhereNotEqual(String column, Time value);

    public void addWhereNotEqual(String column, TimeStamp timeStamp);

    public void addWhereLike(String column, String value);

    public void addWhereBetween(String column, String start, String end);

    public void addWhereBetween(String column, Number start, Number end);

    public void addWhereBetween(String column, Date start, Date end);

    public void addWhereBetween(String column, Time start, Time end);

    public void addWhereBetween(String column, TimeStamp start, TimeStamp end);

    public void addWhereIn(String column, List<String> list, CASE kase);

    public void addWhereIn(String column, List<Number> list);

    public void addWhereIn(String column, SelectBuilder select);

    /**
     * This method is here for the rare occasion that a method is not available
     * to create the desired where clause, it is deprecated and may be completely
     * removed at anytime.
     *
     * @param whereClause where clause to append
     * @deprecated do not use unless absolutely neccesary
     */
    public void appendFreeFormWhereClause(String whereClause);
}
