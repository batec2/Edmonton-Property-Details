package com.Github.cmpt305milestone2;

import com.Github.cmpt305milestone2.DAO.DatabaseDAO;
import com.Github.cmpt305milestone2.DAO.DeprecatedDAO.CsvPropertyAssessmentDAO;
import com.Github.cmpt305milestone2.Data.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;


/**
 * Model for application that contains data to be displayed, has access to two DAOs, an API DAO, and CSV DAO
 */
public class Model {
    private ObservableList<Property> data;
    private DatabaseDAO dao;
    private CsvPropertyAssessmentDAO csvDao;
    private SimpleBooleanProperty csvLoaded = new SimpleBooleanProperty(true);

    private List<String> neighbourhoods, crimeTypes, fruitTreeTypes;
    /**
     * Initializes to start with API DAO as the default, as well gets all data as initial table data
     */
    public Model() throws SQLException {
        dao = new DatabaseDAO();
        List<Property> properties = dao.getAll();
        neighbourhoods = dao.getNeighbourhoods();
        Collections.sort(neighbourhoods);
        crimeTypes = dao.getCrimeTypes();
        Collections.sort(crimeTypes);
        fruitTreeTypes = dao.getFruitTreeTypes();
        Collections.sort(fruitTreeTypes);
        data = FXCollections.observableArrayList(properties);
        //loadCsv();
    }

    /**
     * Switches the DAO which is used by the model
     * @param isCSV true for CSV, false for API
     */
    @Deprecated
    public void switchDao(boolean isCSV)throws SQLException{
        if(isCSV){
            //dao = csvDao;
            updateAll();
        }
        else{
            //dao = apiDao;
            updateAll();
        }
    }

    /**
     * Sets the table with unfiltered data from the DAO
     */
    public void updateAll() throws SQLException{
         dao.setOffset(0);
         List<Property> items = dao.getAll();
         data.clear();
         data.setAll(items);
    }

    /**
     * Sets the table with filtered data from the DAO
     * @param input List of Strings to filter data by
     */
    public void updateFiltered(List<String> input)throws SQLException{
        dao.setOffset(0);
        List<Property> items = dao.getSearchResults(input);
        data.clear();
        data.setAll(items);
    }

    /**
     * Gets a property assessment based on its location
     * @param longitude Longitude (N/S) of the property
     * @param latitude Latitude (E/W) of the property
     * @return the property assessment at the given location
     */
    public Property getAssessment(double longitude, double latitude) {
        try{
            System.out.println(dao.filterLongitudeLatitude(longitude,latitude).get(0));
            return dao.filterLongitudeLatitude(longitude,latitude).get(0);
        }
        catch (Exception e){
            System.out.println(e);
        }
        return null;
    }

    /**
     * Only available when using the api DAO,
     * increments the data forward retrieved from the API DAO and updates the table
     */
    public void updatePageUp() throws SQLException{
        if(dao.pageCurrentQuery().size()==500){
            dao.setOffset(dao.getOffset() + 500);
            List<Property> items = dao.pageCurrentQuery();
            data.clear();
            data.setAll(items);
        }
    }

    /**
     * Only available when using the api DAO,
     * increments the data backwards retrieved from the API DAO and updates the table
     */
    public void updatePageDown()throws SQLException{
        if(dao.getOffset()!=0){
            dao.setOffset(dao.getOffset() - 500);
            List<Property> items = dao.pageCurrentQuery();
            data.clear();
            data.setAll(items);
        }
    }

    /**
     * Loads the csv into the CSV DAO, uses thread to slow down on startup
     */
    @Deprecated
    private void loadCsv(){
        //new Thread(()->{
            csvDao = new CsvPropertyAssessmentDAO("files/Property_Assessment_Data_2023.csv");
            csvLoaded.set(false);
            System.out.println("Loaded");
            //notify();
       // }).start();
    }

    /**
     * Gets the ObservableList from the model
     * @return ObservableList that updates listeners on changes
     */
    public ObservableList<Property> getData(){
        return data;
    }
    /**
     * Gets an observable boolean csvloaded, that indicates if data finished loading into the DAO
     * @return SimpleBooleanProperty an observable boolean that updates listeners of changes
     */
    public SimpleBooleanProperty getCsvLoaded(){
        return csvLoaded;
    }


    /**
     * Gets a list of all neighbourhood names in the database
     * @return List of neighbourhood names
     */
    public List<String> getNeighbourhoods() {
        return neighbourhoods;
    }

    /**
     * Gets a list of all crime types
     * @return list of crime types
     */
    public List<String> getCrimeTypes() { return crimeTypes;};

    /**
     * Gets a list of all edible fruit tree types
     * @return list of fruit tree types
     */
    public List<String> getFruitTreeTypes() { return fruitTreeTypes; }

    /**
     * Gets a count of all properties with assessed value greater than min and less than max.  Additionally is able
     * to be filtered by neighbourhood or assessment class
     * @param min minumum assessed value
     * @param max maximum assessed value + 1, null for no limit
     * @param neighbourhood neighbourhood name, null for all neighbourhoods
     * @param aClass assessment class, null for all assesment classes
     * @return count of properties meeting the criteria
     */
    public int countByValue(Integer min, Integer max, String neighbourhood, String aClass) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT COUNT(account_number) as count from PropertyAssessments where assessed_value >=")
                .append(min);
        if(max != null) {
            query.append(" AND assessed_value <")
                    .append(max);
        }
        if(neighbourhood != null) {
            query.append(" AND neighbourhood = '")
                    .append(neighbourhood)
                    .append("'");
        }
        if(aClass != null) {
            query.append(" AND (mill_class_1 = '")
                    .append(aClass)
                    .append("' OR mill_class_2 = '")
                    .append(aClass)
                    .append("' OR mill_class_3 = '")
                    .append(aClass)
                    .append("')");
        }
        System.out.println(query);
        return dao.getCount(query.toString());
    }
}
