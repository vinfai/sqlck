package com.vertigrated.db;

import com.vertigrated.temporal.Date;
import com.vertigrated.temporal.Time;
import com.vertigrated.temporal.TimeStamp;

interface ColumnValueBuilder
{
    void setColumnValue(String column, String value);

    void setColumnValue(String column, Number value);

    void setColumnValue(String column, boolean value);

    void setColumnValue(String column, Date value);

    void setColumnValue(String column, Time value);

    void setColumnValue(String column, TimeStamp value);
}
