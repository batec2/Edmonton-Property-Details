package com.Github.cmpt305milestone2.DAO;

/**
 * Class for building queries to be sent to API, uses SQL-like syntax
 */
public class QueryBuilder {
    String query;

    /**
     * Takes the url for the API calls to be sent to
     * @param endpoint URL for API
     */
    public QueryBuilder(String endpoint) {
        /**
         * Starting Query which is base of all the queries
         * COALESCE replaces any empty suite cells with an empty string
         * Creates a new column for substring searching for the address filter
         */
        this.query = endpoint+"?$query=SELECT *,(COALESCE(suite, '')||' '||house_number||' '||street_name) AS address ";
    }

    /**
     * Adds a SQL function supported by SoQL for example ORDER BY to the current query
     * Example ORDER BY(sql) Col_Name(value)
     * @param sql SQL function
     * @param value string parameter for SQL function
     * @return returns this QueryBuilder object
     */
    public QueryBuilder add(String sql, String value){
        StringBuilder stringBuilder = new StringBuilder();
        this.query = stringBuilder
                .append(query)
                .append(sql)
                .append(" ")
                .append(value)
                .append(" ").toString();
        return this;
    }

    /**
     * Adds a SQL function supported by SoQL for example LIMIT,OFFSET to the current query
     * Example LIMIT(sql) 100(value)
     * @param sql SQL function
     * @param value int parameter for SQL function
     * @return returns this QueryBuilder object
     */
    public QueryBuilder add(String sql, int value){
        StringBuilder stringBuilder = new StringBuilder();
        this.query = stringBuilder
                .append(query)
                .append(sql)
                .append(" ")
                .append(value)
                .append(" ").toString();
        return this;
    }

    /**
     * Adds a SQL function supported by SoQL for example WHERE to the current query
     * Mainly used to filter for entries matching value2
     * Example WHERE(sql) col_name(value)='100'(value2)
     * @param sql SQL function
     * @param value int parameter for SQL function
     * @return returns this QueryBuilder object
     */
    public QueryBuilder add(String sql, String value, int value2){
        StringBuilder stringBuilder = new StringBuilder();
        this.query = stringBuilder
                .append(query)
                .append(sql)
                .append(" ")
                .append(value)
                .append("='")
                .append(value2)
                .append("' ").toString();
        return this;
    }

    /**
     * Adds a SQL function supported by SoQL for example WHERE to the current query
     * Mainly used to filter for entries matching value2
     * Example WHERE(sql) col_name(value)='MACEWAN'(value2)
     * @param sql SQL function
     * @param value String parameter for SQL function
     * @return returns this QueryBuilder object
     */
    public QueryBuilder add(String sql, String value, String value2){
        StringBuilder stringBuilder = new StringBuilder();
        this.query = stringBuilder
                .append(query)
                .append(sql)
                .append(" ")
                .append(value)
                .append("='")
                .append(value2)
                .append("' ").toString();
        return this;
    }

    /**
     * Adds a WHERE to the start of the query, used to filter data
     * @return returns this QueryBuilder object
     */
    public QueryBuilder addWhere(){
        this.query = this.query+"WHERE ";
        return this;
    }

    /**
     * Adds a filter for any account number starting with the value indicated, an 'AND' is added if
     * this is first filter to be added to query
     * @param value Account number to be filtered
     * @param first boolean if this is the first filter to be added
     * @return returns this QueryBuilder object
     */
    public QueryBuilder addAccountNumber(String value,boolean first){
        //Filters for data starting with the value
        this.query = first?this.query+" "+"account_number LIKE'"+value+"%' "
                :this.query+" "+"AND account_number LIKE'"+value+"%' ";
        return this;
    }

    /**
     * Adds a filter for address that uses substring search, an 'AND' is added if
     * this is first filter to be added to query
     * @param value address to be filtered
     * @param first boolean if this is the first filter to be added
     * @return returns this QueryBuilder object
     */
    public QueryBuilder addAddress(String value,boolean first){
        this.query = first?this.query+" address LIKE '%"+value.toUpperCase()+"%' "
                :this.query+" AND address LIKE '%"+value.toUpperCase()+"%' ";
        return this;
    }
    /**
     * Adds a filter for neighbourhood uses substring search, an 'AND' is added if
     * this is first filter to be added to query
     * @param value neighbourhood to be filtered
     * @param first boolean if this is the first filter to be added
     * @return returns this QueryBuilder object
     */
    public QueryBuilder addNeighbourhood(String value,boolean first){
        this.query = first?this.query+" "+"neighbourhood LIKE'%"+value.toUpperCase()+"%' "
                :this.query+" "+"AND neighbourhood LIKE'%"+value.toUpperCase()+"%' ";
        return this;
    }
    /**
     * Adds a filter for Assessment Class checks if any of the assessment classes match, an 'AND' is added if
     * this is first filter to be added to query
     * @param value Assessment Class to be filtered
     * @param first boolean if this is the first filter to be added
     * @return returns this QueryBuilder object
     */
    public QueryBuilder addAssessmentClass(String value,boolean first){
        if(first){
            this.query = this.query+
                    "(mill_class_1='"+value.toUpperCase()+ "'"+
                    "OR mill_class_2='"+value.toUpperCase()+ "'"+
                    "OR mill_class_3='"+value.toUpperCase()+"') ";
        }
        else{
            this.query = this.query+
                    "AND (mill_class_1='"+value.toUpperCase()+ "'"+
                    "OR mill_class_2='"+value.toUpperCase()+ "'"+
                    "OR mill_class_3='"+value.toUpperCase()+"') ";
        }
        return this;
    }
    /**
     * Adds a filter for minimum Assessed Value, an 'AND' is added if
     * this is first filter to be added to query
     * @param value minimum assessed value
     * @param first boolean if this is the first filter to be added
     * @return returns this QueryBuilder object
     */
    public QueryBuilder addAssessedMin(String value,boolean first){
        this.query = first?this.query+" "+"assessed_value>='"+value+"' "
                :this.query+" "+"AND assessed_value>='"+value+"' ";
        return this;
    }
    /**
     * Adds a filter for maximum Assessed Value, an 'AND' is added if
     * this is first filter to be added to query
     * @param value maximum assessed value
     * @param first boolean if this is the first filter to be added
     * @return returns this QueryBuilder object
     */
    public QueryBuilder addAssessedMax(String value,boolean first){
        this.query = first?this.query+" "+"assessed_value<='"+value+"' "
                :this.query+" "+"AND assessed_value<='"+value+"' ";
        return this;
    }

    /**
     * Replaces characters in query with URI encoding
     * @return Returns query String
     */
    public String build(){
        System.out.println(query);
        return query
                .replace("%","%25")
                .replace("|","%7C")
                .replace(" ","%20")
                .replace("'","%27")
                .replace(">","%3E")
                .replace("<","%3C");
    }
}
