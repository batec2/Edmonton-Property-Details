package com.Github.cmpt305milestone2;

import com.Github.cmpt305milestone2.DAO.DatabaseDAO;
import com.Github.cmpt305milestone2.DAO.DeprecatedDAO.ApiPropertyAssessmentDAO;
import com.Github.cmpt305milestone2.DAO.DeprecatedDAO.CsvPropertyAssessmentDAO;
import com.Github.cmpt305milestone2.DAO.DeprecatedDAO.PropertyAssessmentsDAO;
import com.Github.cmpt305milestone2.Data.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.List;


/**
 * Model for application that contains data to be displayed, has access to two DAOs, an API DAO, and CSV DAO
 */
public class AssessmentsModel {
    private ObservableList<Property> data;
    private DatabaseDAO dao;
    private CsvPropertyAssessmentDAO csvDao;
    private SimpleBooleanProperty csvLoaded = new SimpleBooleanProperty(true);
    /**
     * Initializes to start with API DAO as the default, as well gets all data as initial table data
     */
    public AssessmentsModel() throws SQLException {
        dao = new DatabaseDAO();
        List<Property> properties = dao.getAll();
        data = FXCollections.observableArrayList(properties);
        loadCsv();
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

    public Property getAssessment(double longitude, double latitude) {
        return csvDao.getAssessment(longitude, latitude);
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


    public List<String> getNeighbourhoods() {
        return csvDao.getNeighbourhoods();
    }
}
