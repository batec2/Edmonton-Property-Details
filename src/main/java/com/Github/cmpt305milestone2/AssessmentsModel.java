package com.Github.cmpt305milestone2;

import com.Github.cmpt305milestone2.DAO.ApiPropertyAssessmentDAO;
import com.Github.cmpt305milestone2.DAO.CsvPropertyAssessmentDAO;
import com.Github.cmpt305milestone2.DAO.PropertyAssessmentsDAO;
import com.Github.cmpt305milestone2.Data.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.List;


public class AssessmentsModel {
    private ObservableList<Property> data;
    private ApiPropertyAssessmentDAO apiDao;
    private CsvPropertyAssessmentDAO csvDao;
    private PropertyAssessmentsDAO dao;
    private SimpleBooleanProperty csvLoaded = new SimpleBooleanProperty(true);
    private SimpleBooleanProperty usingCSV = new SimpleBooleanProperty(false);

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
     *
     * @param isCSV
     */
    public void switchDao(boolean isCSV){
        if(isCSV){
            dao = csvDao;
            usingCSV.set(true);
            updateAll();
        }
        else{
            dao = apiDao;
            usingCSV.set(false);
            updateAll();
        }
    }

    /**
     *
     */
    public void updateAll(){
         data.clear();
         apiDao.setOffset(0);
         data.setAll(dao.getAll());
    }

    /**
     *
     * @param input
     */
    public void updateFiltered(List<String> input){
        data.clear();
        apiDao.setOffset(0);
        data.setAll(dao.getSearchResults(input));
    }

    /**
     *
     */
    public void updatePageUp(){
        if(apiDao.pageCurrentQuery().size()==500){
            data.clear();
            apiDao.setOffset(apiDao.getOffset()+500);
            data.setAll(apiDao.pageCurrentQuery());
        }
    }

    /**
     *
     */
    public void updatePageDown(){
        if(apiDao.getOffset()!=0){
            data.clear();
            apiDao.setOffset(apiDao.getOffset()-500);
            data.setAll(apiDao.pageCurrentQuery());
        }
    }

    /**
     *
     */
    private void loadCsv(){
        new Thread(()->{
            csvDao = new CsvPropertyAssessmentDAO("Property_Assessment_Data_2023.csv");
            csvLoaded.set(false);
            System.out.println("Loaded");
        }).start();
    }

    /**
     *
     * @return
     */
    public ObservableList<Property> getData(){
        return data;
    }

    /**
     *
     * @return
     */
    public SimpleBooleanProperty getCsvLoaded(){
        return csvLoaded;
    }

    /**
     *
     * @return
     */
    public SimpleBooleanProperty getUsingCSV(){
        return usingCSV;
    }

}
