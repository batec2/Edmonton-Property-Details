package com.Github.cmpt305milestone2.DAO;

import com.Github.cmpt305milestone2.Data.Property;
import com.Github.cmpt305milestone2.Database.Database;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;


public class DatabaseDAO{

    private int limit = -1;//Amount of data received per API request
    private int offset = 0;//Offset of the data for paging
    private String currentQuery;
    private List<String> currentItems;
    private Database database;

    public DatabaseDAO() throws SQLException{
        database = new Database();
        currentItems = Arrays.asList("","","","","","");
    }

    /**
     * Gets unfiltered data, limited by the limit variable (500)
     * @return Returns a list of properties sorted by account number
     */
    public List<Property> getAll() throws SQLException{
        currentQuery = new QueryBuilder()
                .add("ORDER BY","CAST(account_number AS INTEGER)")
                .add("LIMIT",limit)
                .add("OFFSET",offset)
                .buildQuery();
        List<Property> properties = database.queryPropertyAssessments(currentQuery);

        //Resets filters
        currentItems = Arrays.asList("","","","","","");
        if(properties==null){
            return new ArrayList<>();
        }
        return properties;
    }

    /**
     * Takes a list of inputs from UI and creates a query based on inputs.
     * After query is made it is sent to api to get matching items
     * @param input Takes in a list of inputs to filter by
     * @return Returns a filtered list of properties sorted by account number
     */
    public List<Property> getSearchResults(List<String> input) throws SQLException {
        currentItems = sanitizeInput(input);//replaces single quotes
        if(!checkInput(input)){
            return new ArrayList<>();
        }
        QueryBuilder qBuilder = new QueryBuilder().addWhere();
        boolean first = true;
        for(int i=0;i<currentItems.size();i++){
            switch(i){
                //Account number
                case 0:
                    if(!currentItems.get(i).isBlank()){
                        qBuilder.addAccountNumber(currentItems.get(i),first);
                        first=false;
                    }
                    break;
                //Address
                case 1:
                    if(!currentItems.get(i).isBlank()){
                        qBuilder.addAddress(currentItems.get(i),first);
                        first=false;
                    }
                    break;
                //Neighbourhood
                case 2:
                    if(!currentItems.get(i).isBlank()){
                        qBuilder.addNeighbourhood(currentItems.get(i),first);
                        first=false;
                    }
                    break;
                //Assessment Class
                case 3:
                    if(!currentItems.get(i).isBlank()){
                        qBuilder.addAssessmentClass(currentItems.get(i),first);
                        first=false;
                    }
                    break;
                //Min Assessed Value
                case 4:
                    if(!currentItems.get(i).isBlank()){
                        qBuilder.addAssessedMin(currentItems.get(i),first);
                        first=false;
                    }
                    break;
                //Max Assessed Value
                case 5:
                    if(!currentItems.get(i).isBlank()){
                        qBuilder.addAssessedMax(currentItems.get(i),first);
                        first=false;
                    }
                    break;
            }
        }

        currentQuery = qBuilder
                .add("ORDER BY","CAST(account_number AS INTEGER)")
                .add("LIMIT",limit)
                .add("OFFSET",offset)
                .buildQuery();

        List<Property> properties = database.queryPropertyAssessments(currentQuery);
        return properties==null?new ArrayList<>():properties;
    }

    /**
     * Re-queries current query, if there are no filters it queries for all items and returns filtered items
     * This is used in conjunction with getOffset and setOffset to page through data
     * @return Returns a list of properties sorted by account number
     */
    public List<Property> pageCurrentQuery() throws SQLException{
        //if no filters then gets all else gets the filtered items
        return currentItems.stream().allMatch(String::isBlank)?this.getAll():this.getSearchResults(currentItems);
    }

    public List<Property> filterLongitudeLatitude(double longitude,double latitude) throws SQLException{
        QueryBuilder qBuilder = new QueryBuilder().addWhere();
        currentQuery = qBuilder
                .add("","longitude",String.valueOf(longitude))
                .add("AND","latitude",String.valueOf(latitude))
                .add("ORDER BY","CAST(account_number AS INTEGER)")
                .add("LIMIT",limit)
                .add("OFFSET",offset)
                .buildQuery();

        List<Property> properties = database.queryPropertyAssessments(currentQuery);
        return properties==null?new ArrayList<>():properties;
    }

    /**
     * Gets count from a query
     * @param query query to get count from
     * @return int count
     */
    public int getCount(String query){
        int count;
        try{
            count = database.getCount(query);
        }
        catch (SQLException e){
            System.out.println("SQL Error"+e);
            return 0;
        }
        return count;
    }

    /**
     * Gets a list of neighbourhoods
     * @return List of neighbourhoods
     */
    public List<String> getNeighbourhoods(){
        List<String> neighbourhoods;
        try{
            neighbourhoods = database.getColumn("SELECT DISTINCT neighbourhood FROM PropertyAssessments","neighbourhood");
        }
        catch(SQLException e){
            System.out.println("ERROR");
            return new ArrayList<>();
        }

        return neighbourhoods;
    }

    /**
     * Gets the current offset
     * @return int offset
     */
    public int getOffset() {
        return offset;
    }

    /**
     * Sets the offset of the query sent to api
     * @param offset amount to offset the data
     */
    public void setOffset(int offset) {
        this.offset = offset;
    }

    /**
     * Replaces all single quotes from the input with an escaped single quote
     * @param input List of strings
     * @return List of strings
     */
    public List<String> sanitizeInput(List<String> input){
        List<String> sanitized = new ArrayList<>();
        input.forEach(item->sanitized.add(item.replace("'","''")));
        return sanitized;
    }

    /**
     * Checks input for invalid special characters
     * @param input List of strings
     * @return Returns true if the inputs are valid, false otherwise
     */
    public boolean checkInput(List<String> input){
        for(String item:input){
            if(Pattern.compile("[^\sA-Za-z0-9'-]").matcher(item).find()){//regex check for any character not in the brackets
                System.out.println(item);
                return false;
            }
        }
        return true;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

}
