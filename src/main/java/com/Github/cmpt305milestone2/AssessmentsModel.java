package com.Github.cmpt305milestone2;

import com.Github.cmpt305milestone2.DAO.ApiPropertyAssessmentDAO;
import com.Github.cmpt305milestone2.DAO.CsvPropertyAssessmentDAO;
import com.Github.cmpt305milestone2.Data.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableBooleanValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class AssessmentsModel {
    private ObservableList<Property> data;
    private ApiPropertyAssessmentDAO apiDao;
    private CsvPropertyAssessmentDAO csvDao;
    private SimpleBooleanProperty csvLoaded = new SimpleBooleanProperty(true);

    public AssessmentsModel(){
        apiDao = new ApiPropertyAssessmentDAO();
        List<Property> properties = apiDao.getAll();
        data = FXCollections.observableArrayList(properties);
        loadCsv();
    }

    public void updateAll(){
         data.clear();
         apiDao.setOffset(0);
         data.setAll(apiDao.getAll());
    }

    public void updateFiltered(List<String> input){
        data.clear();
        apiDao.setOffset(0);
        data.setAll(apiDao.getSearchResults(input));
    }

    public void updatePageUp(){
        data.clear();
        apiDao.setOffset(apiDao.getOffset()+100);
        data.setAll(apiDao.pageCurrentQuery());
    }

    public void updatePageDown(){
        data.clear();
        apiDao.setOffset(apiDao.getOffset()!=0? apiDao.getOffset()-100 : apiDao.getOffset());
        data.setAll(apiDao.pageCurrentQuery());
    }

    private void loadCsv(){
        new Thread(()->{
            csvDao = new CsvPropertyAssessmentDAO("Property_Assessment_Data_2023.csv");
            csvLoaded.set(false);
            System.out.println("Loaded");
        }).start();
    }
    public ObservableList<Property> getData(){
        return data;
    }

    public SimpleBooleanProperty getCsvLoaded(){
        return csvLoaded;
    }

}
