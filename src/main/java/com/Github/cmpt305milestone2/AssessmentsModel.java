package com.Github.cmpt305milestone2;

import com.Github.cmpt305milestone2.DAO.ApiPropertyAssessmentDAO;
import com.Github.cmpt305milestone2.DAO.CsvPropertyAssessmentDAO;
import com.Github.cmpt305milestone2.Data.Property;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Arrays;
import java.util.List;

public class AssessmentsModel {
    private ObservableList<Property> data;
    private ApiPropertyAssessmentDAO apiDao;
    private CsvPropertyAssessmentDAO csvDao;

    public AssessmentsModel(){
        apiDao = new ApiPropertyAssessmentDAO();
        csvDao = new CsvPropertyAssessmentDAO("Property_Assessment_Data_2023.csv");
        List<Property> properties = apiDao.getAll();
        data = FXCollections.observableArrayList(properties);
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

    public ObservableList<Property> getData(){
        return this.data;
    }

}
