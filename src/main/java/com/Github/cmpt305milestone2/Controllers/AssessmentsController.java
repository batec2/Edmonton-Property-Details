package com.Github.cmpt305milestone2.Controllers;

import com.Github.cmpt305milestone2.AssessmentsModel;
import com.Github.cmpt305milestone2.Data.Property;
import javafx.beans.property.SimpleBooleanProperty;
import java.util.List;

/**
 * Controller for application talks to model, controller threads functions in order to prevent
 * button spam from UI by setting observable values to true or false
 */
public class AssessmentsController {
    private AssessmentsModel model;
    private SimpleBooleanProperty loadingNext = new SimpleBooleanProperty(false);
    private SimpleBooleanProperty loadingPrev = new SimpleBooleanProperty(false);
    private SimpleBooleanProperty loading = new SimpleBooleanProperty(false);

    private boolean isCSV = false;

    /**
     * Takes a AssessmentsModel object
     * @param model model that holds data for application
     */
    public AssessmentsController(AssessmentsModel model){
        this.model = model;
    }

    /**
     * Resets UI to initial state which as a default gets the unfiltered data,sets leading to true until
     * model is finishes updating
     */
    public void resetData(){
        loading.set(true);
            //new Thread(()->{
            try {
                model.updateAll();
            }
            catch (Exception e){
                System.out.println(e);
            }
            loading.set(false);
        //}).start();
    }

    /**
     * Passes Input from UI to the model to be filtered,sets leading to true until
     * model is finishes updating
     * @param input List of strings contained filters
     */
    public void filterData(List<String> input){
        //loading.set(true);
        //new Thread(()->{
        if(input.stream().allMatch(String::isBlank)){
            try {
                model.updateAll();
            }
            catch (Exception e){
                System.out.println(e);
            }
        }
        else{
            try {
                model.updateFiltered(input);
            }
            catch (Exception e){
                System.out.println(e);
            }
        }
        //loading.set(false);
        //}).start();
    }

    public Property getAssessment(double longitude, double latitude) {
        return model.getAssessment(longitude, latitude);

    }

    /**
     * Pages Data by a set amount, Sets both loadingPrev and loadingNext to true until
     * model finishes updating
     */
    public void pageUp(){
        loadingPrev.set(true);
        loadingNext.set(true);
        new Thread(()->{
            try {
                model.updatePageUp();
            }
            catch (Exception e){
                System.out.println(e);
            }
            loadingPrev.set(false);
            loadingNext.set(false);
        }).start();
    }

    /**
     * Pages data back by a set amount, Sets both loadingPrev and loadingNext to true until
     * model finishes updating
     */
    public void pageDown(){
        loadingPrev.set(true);
        loadingNext.set(true);
        new Thread(()->{
            try{
                model.updatePageDown();
            }
            catch (Exception e){
                System.out.println(e);
            }
            loadingPrev.set(false);
            loadingNext.set(false);
        }).start();
    }

    /**
     * Switches DAO used by the model, true for CSV false for API, Sets both loadingPrev and loadingNext to true until
     * model finishes updating
     * @param isCSV true for CSV false for API
     */
    @Deprecated
    public void switchDao(boolean isCSV){
        try {
            model.switchDao(isCSV);
        }
        catch (Exception e){
            System.out.println(e);
        }
        if(isCSV){
            loadingNext.set(true);
            loadingPrev.set(true);
        }
        else{
            loadingNext.set(false);
            loadingPrev.set(false);
        }
        this.isCSV = isCSV;

    }
    /**
     * Gets an observable boolean loadingNext, that indicates if the next page of data is loading
     * @return SimpleBooleanProperty an observable boolean that updates listeners of changes
     */
    public SimpleBooleanProperty getLoadingNext(){
        return loadingNext;
    }
    /**
     * Gets an observable boolean loadingPrev, that indicates if the previous page of data is loading
     * @return SimpleBooleanProperty an observable boolean that updates listeners of changes
     */
    public SimpleBooleanProperty getLoadingPrev(){
        return loadingPrev;
    }
    /**
     * Gets an observable boolean loading, that indicates if data is loading
     * @return SimpleBooleanProperty an observable boolean that updates listeners of changes
     */
    public SimpleBooleanProperty getLoading(){
        return loading;
    }

    public boolean getIsCSV() {return isCSV; }
}