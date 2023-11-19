package com.Github.cmpt305milestone2.DAO;

public class QueryBuilder {
    String query;

    public QueryBuilder(String endpoint) {
        this.query = endpoint+"?$query=SELECT *,(COALESCE(suite, '')||' '||house_number||' '||street_name) AS address ";
    }

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

    public QueryBuilder addWhere(){
        this.query = this.query+"WHERE ";
        return this;
    }

    public QueryBuilder addAccountNumber(String value,boolean first){
        this.query = first?this.query+" "+"account_number LIKE'"+value+"%' "
                :this.query+" "+"AND account_number LIKE'"+value+"%' ";
        return this;
    }

    public QueryBuilder addAddress(String value,boolean first){
        this.query = first?this.query+" address LIKE '%"+value.toUpperCase()+"%' "
                :this.query+" AND address LIKE '%"+value.toUpperCase()+"%' ";
        return this;
    }
    // | = %7C space = %20 % = %25
    public QueryBuilder addNeighbourhood(String value,boolean first){
        this.query = first?this.query+" "+"neighbourhood LIKE'%"+value.toUpperCase()+"%' "
                :this.query+" "+"AND neighbourhood LIKE'"+value.toUpperCase()+"%' ";
        return this;
    }

    public QueryBuilder addAssessmentClass(String value,boolean first){
        if(first){
            this.query = this.query+
                    "(mill_class_1='"+value.toUpperCase()+ "'"+
                    "OR mill_class_2='"+value.toUpperCase()+ "'"+
                    "OR mill_class_2='"+value.toUpperCase()+"') ";
        }
        else{
            this.query = this.query+
                    "AND (mill_class_1='"+value.toUpperCase()+ "'"+
                    "OR mill_class_2='"+value.toUpperCase()+ "'"+
                    "OR mill_class_2='"+value.toUpperCase()+"') ";
        }
        return this;
    }

    public QueryBuilder addAssessedMin(String value,boolean first){
        this.query = first?this.query+" "+"assessed_value>='"+value+"' "
                :this.query+" "+"AND assessed_value>='"+value+"' ";
        return this;
    }

    public QueryBuilder addAssessedMax(String value,boolean first){
        this.query = first?this.query+" "+"assessed_value<='"+value+"' "
                :this.query+" "+"AND assessed_value<='"+value+"' ";
        return this;
    }

    public String build(){
        return query
                .replace("%","%25")
                .replace("|","%7C")
                .replace(" ","%20")
                .replace("'","%27")
                .replace(">","%3E")
                .replace("<","%3C");
    }
}
