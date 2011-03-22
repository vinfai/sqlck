package com.vertigrated.db;

public enum Operator
{
    EQUALS(" = "),
    NOT_EQUAL(" <> "),
    LIKE(" LIKE "),
    GREATER_THAN(" > "),
    LESS_THAN(" < "),
    LESS_THAN_OR_EQUAL(" <= "),
    GREATER_THAN_OR_EQUAL(" >= "),
    IN(" IN "),
    NOT_IN(" NOT IN "),
    BETWEEN(" BETWEEN ");

    private final String opstr;

    Operator(final String opstr)
    {
        this.opstr = opstr;
    }

    public String toString()
    {
        return this.opstr;
    }
}
