package com.Github.cmpt305milestone2.DAO;

public enum SQLEnum {
    OR("OR"),
    WH("WHERE"),
    SEL("SELECT"),
    AND("AND"),
    ORD("ORDER BY");

    public final String str;

    private SQLEnum(String str){
        this.str = str;
    }
}
