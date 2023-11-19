package com.Github.cmpt305milestone2;

import com.Github.cmpt305milestone2.DAO.ApiPropertyAssessmentDAO;
import com.Github.cmpt305milestone2.DAO.CsvPropertyAssessmentDAO;
import com.Github.cmpt305milestone2.DAO.PropertyAssessmentsDAO;
import com.Github.cmpt305milestone2.Data.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.List;


/**
 * Model for application that contains data to be displayed, has access to two DAOs, an API DAO, and CSV DAO
 */
public class AssessmentsModel {
    private ObservableList<Property> data;
    private ApiPropertyAssessmentDAO apiDao;
    private CsvPropertyAssessmentDAO csvDao;
    private PropertyAssessmentsDAO dao;
    private SimpleBooleanProperty csvLoaded = new SimpleBooleanProperty(true);
    /**
     *
     */
    public AssessmentsModel(){
        apiDao = new ApiPropertyAssessmentDAO();
        dao = apiDao;
        List<Property> properties = apiDao.getAll();
        data = FXCollections.observableArrayList(properties);
        loadCsv();
    }

    /**
     * Switches the DAO which is used by the model
     * @param isCSV true for CSV, false for API
     */
    public void switchDao(boolean isCSV){
        if(isCSV){
            dao = csvDao;
            updateAll();
        }
        else{
            dao = apiDao;
            updateAll();
        }
    }

    /**
     * Sets the table with unfiltered data from the DAO
     */
    public void updateAll(){
         apiDao.setOffset(0);
         List<Property> items = dao.getAll();
         data.clear();
         data.setAll(items);
    }

    /**
     * Sets the table with filtered data from the DAO
     * @param input
     */
    public void updateFiltered(List<String> input){
        apiDao.setOffset(0);
        List<Property> items = dao.getSearchResults(input);
        data.clear();
        data.setAll(items);
    }

    /**
     * Only available when using the api DAO,
     * increments the data forward retrieved from the API DAO and updates the table
     */
    public void updatePageUp(){
        if(apiDao.pageCurrentQuery().size()==500){
            apiDao.setOffset(apiDao.getOffset() + 500);
            List<Property> items = apiDao.pageCurrentQuery();
            data.clear();
            data.setAll(items);
        }
    }

    /**
     * Only available when using the api DAO,
     * increments the data backwards retrieved from the API DAO and updates the table
     */
    public void updatePageDown(){
        if(apiDao.getOffset()!=0){
            apiDao.setOffset(apiDao.getOffset() - 500);
            List<Property> items = apiDao.pageCurrentQuery();
            data.clear();
            data.setAll(items);
        }
    }

    /**
     * Loads the csv into the CSV DAO, uses thread to slow down on startup
     */
    private void loadCsv(){
        new Thread(()->{
            csvDao = new CsvPropertyAssessmentDAO("Property_Assessment_Data_2023.csv");
            csvLoaded.set(false);
            System.out.println("Loaded");
        }).start();
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
}
