package com.vertigrated.db.mssql;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import org.testng.annotations.Test;

public class TestInsertBuilder
{
    @Test
    public void testInsertBuilderDates()
    {
        final SQLServerDataSource ds = new SQLServerDataSource();
        ds.setServerName("localhost");
        ds.setPortNumber(1269);
        ds.setDatabaseName("AdventureWorks");
        ds.setUser("sa");
        ds.setPassword("");

        final Database db = Database.getInstance(ds, "AdventureWorks");
        final InsertBuilder ib = db.createInsertBuilder("Person", "Contact");
        ib.setColumnValue("FirstName", "Jarrod");
        ib.setColumnValue("MiddleName", "Hoke");
        ib.setColumnValue("LastName", "Roberson");
        assert "INSERT INTO Person.Contact (FirstName, MiddleName, LastName) VALUES ('Jarrod', 'Hoke', 'Roberson')".equals(ib.getCommand()) : ib.getCommand();
    }
}
