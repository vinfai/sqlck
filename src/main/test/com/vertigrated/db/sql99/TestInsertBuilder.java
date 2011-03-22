package com.vertigrated.db.sql99;

import com.vertigrated.temporal.Date;
import com.vertigrated.temporal.DateUtil;
import com.vertigrated.temporal.Time;
import com.vertigrated.temporal.TimeStamp;
import org.testng.annotations.Test;

public class TestInsertBuilder
{
    @Test
    public void testInsert()
    {
        final InsertBuilder ib = new InsertBuilder("defaultSchema", "tableName");
        ib.setColumnValue("strcol", "a string");
        ib.setColumnValue("numcol", 1235);
        ib.setColumnValue("boolcol", true);
        ib.setColumnValue("datecol", new Date(DateUtil.BEGINNING_OF_TIME));
        ib.setColumnValue("timecol", new Time(12, 0, 0));
        ib.setColumnValue("timestamp", new TimeStamp(DateUtil.BEGINNING_OF_TIME, DateUtil.UTC));
        assert "INSERT INTO defaultSchema.tableName (strcol, numcol, boolcol, datecol, timecol, timestamp) VALUES ('a string', 1235, 'T', '0001-12-31', '12:00:00', '0001-01-01T00:00:00.000+00:00')".equals(ib.getCommand()) : ib.getCommand();
    }
}
