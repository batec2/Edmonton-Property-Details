package com.Github.cmpt305milestone2.Data;

public class QueryBuilder {
    String query;

    public QueryBuilder(String endpoint) {
        this.query = endpoint+"?$query=";
    }

    public QueryBuilder add(String sql, String value){
        StringBuilder stringBuilder = new StringBuilder();
        this.query = stringBuilder
                .append(query)
                .append(sql.replace(" ","%20"))
                .append("%20")
                .append(value.replace(" ","%20"))
                .append("%20").toString();
        return this;
    }

    public QueryBuilder add(String sql, int value){
        StringBuilder stringBuilder = new StringBuilder();
        this.query = stringBuilder
                .append(query)
                .append(sql.replace(" ","%20"))
                .append("%20")
                .append(value)
                .append("%20").toString();
        return this;
    }

    public QueryBuilder add(String sql, String value, int value2){
        StringBuilder stringBuilder = new StringBuilder();
        this.query = stringBuilder
                .append(query)
                .append(sql.replace(" ","%20"))
                .append("%20")
                .append(value)
                .append("='")
                .append(value2)
                .append("'%20").toString();
        return this;
    }
    public QueryBuilder add(String sql, String value, String value2){
        StringBuilder stringBuilder = new StringBuilder();
        this.query = stringBuilder
                .append(query)
                .append(sql.replace(" ","%20"))
                .append("%20")
                .append(value)
                .append("='")
                .append(value2.replace(" ","%20"))
                .append("'%20").toString();
        return this;
    }



    public String build(){
        return query;
    }
}
