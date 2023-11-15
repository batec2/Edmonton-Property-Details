package com.Github.cmpt305milestone2;

import com.Github.cmpt305milestone2.Data.Property;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.math.BigDecimal;
import java.util.List;

public class AssessmentsController {
    AssessmentsModel model;

    AssessmentsController(AssessmentsModel model){
        this.model = model;
    }

    public void resetData(){
        model.updateAll();
    }

    public void filterData(List<String> input){
        if(input.stream().allMatch(String::isBlank)){
            model.updateAll();
        }
        else{
            model.updateFiltered(input);
        }
    }

    public void pageUp(){
        model.updatePageUp();
    }

    public void pageDown(){
        model.updatePageDown();
    }
}
