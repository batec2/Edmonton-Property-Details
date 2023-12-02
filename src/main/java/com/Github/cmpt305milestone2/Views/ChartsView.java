package com.Github.cmpt305milestone2.Views;

import com.Github.cmpt305milestone2.AssessmentsController;
import com.Github.cmpt305milestone2.AssessmentsModel;
import com.Github.cmpt305milestone2.AutoCompleteTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.util.*;

/**
 * View object for Application, sets UI objects and behaviour on UI objects when interacted with by the user
 */
public class ChartsView {
    private BorderPane view;
    private VBox vBoxLeft;
    private VBox inputFields;
    private VBox chartVBox;
    private AssessmentsController controller;
    private AssessmentsModel model;

    /**
     * Takes refrences to model and controller objects, as-well sets UI
     * @param controller Controller object that takes actions from view and sends to model
     * @param model Model object that contains app data
     */
    public ChartsView(AssessmentsController controller, AssessmentsModel model){
        this.controller = controller;
        this.model = model;
        this.chartVBox = new VBox();
        setStage();
    }

    /**
     * Returns Root node(BorderPane)
     * @return BorderPane root node
     */
    public Parent asParent() {
        return view ;
    }

    public BorderPane asBorderPane() { return view;}
    /**
     * Sets the items for the root node
     */
    private void setStage(){
        this.view = new BorderPane();
        //view.setPadding(new Insets(0,0,0,40));
        view.setCenter(this.chartVBox);

        setVboxLeft();
        view.setLeft(this.vBoxLeft);
    }

    /**
     * Sets all items in the left vbox including the mode selector and all input fields
     */
    private void setVboxLeft(){
        Label filterLabel = new Label("Property Filters");
        filterLabel.setStyle("-fx-font-weight: bold;-fx-font-size: 24;");

        setInputFields();

        this.vBoxLeft = new VBox(
                filterLabel,
                this.inputFields);

        this.vBoxLeft.setSpacing(10);
        this.vBoxLeft.setPadding(new Insets(10));
    }

    /**
     * Creates and sets input fields behaviour, takes input for filtering dataset, search and reset buttons
     * are bound to booleans in the model to prevent multiple button presses
     */
    private void setInputFields(){
        //Chart Types
        Label typeLabel  = new Label("Chart Types:");
        String[] types = {"Bar Graph", "Pie Chart"};
        ComboBox<String> typeCombo = new ComboBox<>(FXCollections.observableArrayList(types));
        typeCombo.setValue("");

        String[] charts = {"Value Ranges - Show All", "Value Ranges - By Neighbourhood",
                                  "Values Ranges - By Assessment Class"};
        ComboBox<String> chartCombo = new ComboBox<>(FXCollections.observableArrayList(charts));
        chartCombo.setValue("");

        Button selectButton = new Button("Select");

        VBox typeFields = new VBox(typeLabel, typeCombo, chartCombo, selectButton, new Separator());
        typeFields.setSpacing(10);
        this.inputFields = new VBox(typeFields);
        this.inputFields.setSpacing(10);

        //Compare neighbourhoods
        VBox barGraphValueByNeighbourhood = setValueByNeighbourhoodBarGraphVBox();
        VBox pieChartValueByNeighbourhood = setValueByNeighbourhoodPieChartVBox();
        barGraphValueByNeighbourhood.setSpacing(10);
        VBox barGraphFields = new VBox();
        barGraphFields.setSpacing(10);
        pieChartValueByNeighbourhood.setSpacing(10);

        //Gets all the values in the input texts and gets new list
        selectButton.setOnAction(e->{
            this.inputFields.getChildren().clear();
            this.inputFields.getChildren().add(typeFields);
            if(typeCombo.getValue().equals("Bar Graph")) {
                if(chartCombo.getValue().equals("Value Ranges - Show All")) {
                    this.chartVBox = setValueAllBarGraph();
                    view.setCenter(this.chartVBox);
                } else if(chartCombo.getValue().equals("Value Ranges - By Neighbourhood")) {
                    this.inputFields.getChildren().add(barGraphValueByNeighbourhood);
                    this.chartVBox.getChildren().clear();
                }
            } else if (typeCombo.getValue().equals("Pie Chart")) {
                if(chartCombo.getValue().equals("Value Ranges - Show All")) {
                    this.chartVBox = setValueAllPieChart();
                    view.setCenter(this.chartVBox);
                } else if(chartCombo.getValue().equals("Value Ranges - By Neighbourhood")) {
                    this.inputFields.getChildren().add(pieChartValueByNeighbourhood);
                    this.chartVBox.getChildren().clear();
                }
            }
        });
    }

    /**
     * THIS IS A DOCSTRING
     * @return
     */
    private VBox setValueByNeighbourhoodBarGraphVBox() {
        List<String> neighbourhoods = model.getNeighbourhoods();
        Collections.sort(neighbourhoods);

        Label neighbourhood1Label = new Label("Neighbourhood 1");
        AutoCompleteTextField neighbourhood1TextField = new AutoCompleteTextField();
        neighbourhood1TextField.getEntries().addAll(neighbourhoods);

        Label neighbourhood2Label = new Label("Neighbourhood 2");
        AutoCompleteTextField neighbourhood2TextField = new AutoCompleteTextField();
        neighbourhood2TextField.getEntries().addAll(neighbourhoods);

        Label neighbourhood3Label = new Label("Neighbourhood 3");
        AutoCompleteTextField neighbourhood3TextField = new AutoCompleteTextField();
        neighbourhood3TextField.getEntries().addAll(neighbourhoods);

        Label neighbourhood4Label = new Label("Neighbourhood 4");
        AutoCompleteTextField neighbourhood4TextField = new AutoCompleteTextField();
        neighbourhood4TextField.getEntries().addAll(neighbourhoods);

        Label neighbourhood5Label = new Label("Neighbourhood 5");
        AutoCompleteTextField neighbourhood5TextField = new AutoCompleteTextField();
        neighbourhood5TextField.getEntries().addAll(neighbourhoods);

        Button graphButton = new Button("Show Graph");

        graphButton.setOnAction(e -> {
            List<String> selectedNeighbourhoods = new ArrayList<>(List.of(neighbourhood1TextField.getText(),
                    neighbourhood2TextField.getText(),
                    neighbourhood3TextField.getText(),
                    neighbourhood4TextField.getText(),
                    neighbourhood5TextField.getText()));

            selectedNeighbourhoods = selectedNeighbourhoods.stream()
                    .filter(nhood -> neighbourhoods.contains(nhood))
                    .toList();

            this.chartVBox = setValueByNeighbourhoodBarGraph(selectedNeighbourhoods);
            view.setCenter(this.chartVBox);
        });

        return new VBox(neighbourhood1Label, neighbourhood1TextField,
                    neighbourhood2Label, neighbourhood2TextField,
                    neighbourhood3Label, neighbourhood3TextField,
                    neighbourhood4Label, neighbourhood4TextField,
                    neighbourhood5Label, neighbourhood5TextField,
                    graphButton);
    }

    private VBox setValueByNeighbourhoodPieChartVBox() {
        List<String> neighbourhoods = model.getNeighbourhoods();
        Collections.sort(neighbourhoods);

        Label neighbourhoodLabel = new Label("Neighbourhood");
        AutoCompleteTextField neighbourhoodTextField = new AutoCompleteTextField();
        neighbourhoodTextField.getEntries().addAll(neighbourhoods);

        Button graphButton = new Button("Show Graph");

        graphButton.setOnAction(e -> {
            String selectedNeighbourhood = neighbourhoodTextField.getText();
            if(neighbourhoods.contains(selectedNeighbourhood)) {
                this.chartVBox = setValueByNeighbourhoodPieChart(selectedNeighbourhood);
            }
            else {
                this.chartVBox = new VBox();
            }

            view.setCenter(this.chartVBox);
        });

        return new VBox(neighbourhoodLabel, neighbourhoodTextField,
                graphButton);
    }

    private VBox setValueAllBarGraph() {
        chartVBox.getChildren().clear();

        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        VBox vBox = new VBox();

        XYChart.Series series = new XYChart.Series();
        series.setName("All Property Values");

        series.getData().add(new XYChart.Data("Less than $100,000", 10));
        series.getData().add(new XYChart.Data("$100,000 to $249,999", 20));
        series.getData().add(new XYChart.Data("$250,000 to $499,999", 30));
        series.getData().add(new XYChart.Data("$500,000 to $749,999", 40));
        series.getData().add(new XYChart.Data("$750,000 to $999,999", 50));
        series.getData().add(new XYChart.Data("More than $1,000,000", 60));
        barChart.getData().add(series);

        vBox.getChildren().add(barChart);
                vBox.setAlignment(Pos.CENTER);
                return vBox;
    }
    /**
     * Sets Columns and cell values as well binds table to model so table is automatically updated with changes
     * to the model
     */
    private VBox setValueByNeighbourhoodBarGraph(List<String> neighbourhoods){
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        VBox vBox = new VBox();
        /*
        //setup valueRanges
        XYChart.Series series1 = new XYChart.Series();
        series1.setName("< $100,000");

        XYChart.Series series2 = new XYChart.Series();
        series2.setName("$100,001 - $250,000");

        XYChart.Series series3 = new XYChart.Series();
        series3.setName("$250,001 - $500,000");

        XYChart.Series series4 = new XYChart.Series();
        series4.setName("$500,001 - $750,000");

        XYChart.Series series5 = new XYChart.Series();
        series5.setName("$500,001 - $750,000");

        XYChart.Series series6 = new XYChart.Series();
        series6.setName("$750,001 - $1,000,000");

        //get data and add
        for (String neighbourhood : neighbourhoods) {
            //if (!neighbourhood.isEmpty()) {
                series1.getData().add(new XYChart.Data(neighbourhood, 10));
                series2.getData().add(new XYChart.Data(neighbourhood, 20));
                series3.getData().add(new XYChart.Data(neighbourhood, 30));
                series4.getData().add(new XYChart.Data(neighbourhood, 40));
                series5.getData().add(new XYChart.Data(neighbourhood, 50));
                series6.getData().add(new XYChart.Data(neighbourhood, 60));
            //}
        }

        barChart.getData().addAll(series1, series2, series3, series4, series5, series6);
        */
        for(String neighbourhood: neighbourhoods) {
            if(!neighbourhood.isBlank()) {
                XYChart.Series series = new XYChart.Series();
                series.setName(neighbourhood);
                series.getData().add(new XYChart.Data("Less than $100,000", 10));
                series.getData().add(new XYChart.Data("$100,000 to $249,999", 20));
                series.getData().add(new XYChart.Data("$250,000 to $499,999", 30));
                series.getData().add(new XYChart.Data("$500,000 to $749,999", 40));
                series.getData().add(new XYChart.Data("$750,000 to $999,999", 50));
                series.getData().add(new XYChart.Data("More than $1,000,000", 60));
                barChart.getData().add(series);
            }
        }
        vBox.getChildren().add(barChart);
        vBox.setAlignment(Pos.CENTER);
        return vBox;

    }

    private VBox setValueAllPieChart() {
        VBox vBox = new VBox();

        ObservableList<PieChart.Data> chartData = FXCollections.observableArrayList(
                        new PieChart.Data("Less than $100,000", 1),
                        new PieChart.Data("$100,000 to $249,999", 1),
                        new PieChart.Data("$250,000 to $499,999", 1),
                        new PieChart.Data("$500,000 to $749,999", 1),
                        new PieChart.Data("$750,000 to $999,999", 1),
                        new PieChart.Data("More than $1,000,000", 1));
        PieChart chart = new PieChart(chartData);
        chart.setTitle("All Property Values");
        vBox.getChildren().add(chart);
        vBox.setAlignment(Pos.CENTER);
        return vBox;
    }

    private VBox setValueByNeighbourhoodPieChart(String selectedNeighbourhood) {
        VBox vBox = new VBox();

        ObservableList<PieChart.Data> chartData = FXCollections.observableArrayList(
                new PieChart.Data("Less than $100,000", 1),
                new PieChart.Data("$100,000 to $249,999", 2),
                new PieChart.Data("$250,000 to $499,999", 3),
                new PieChart.Data("$500,000 to $749,999", 4),
                new PieChart.Data("$750,000 to $999,999", 5),
                new PieChart.Data("More than $1,000,000", 6));
        PieChart chart = new PieChart(chartData);
        chart.setTitle(selectedNeighbourhood);
        vBox.getChildren().add(chart);
        vBox.setAlignment(Pos.CENTER);
        return vBox;
    }
}
