package com.Github.cmpt305milestone2.Views;

import com.Github.cmpt305milestone2.Controllers.AssessmentsController;
import com.Github.cmpt305milestone2.AssessmentsModel;
import com.Github.cmpt305milestone2.AutoCompleteTextField;
import com.Github.cmpt305milestone2.Controllers.ChartsController;
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
    private ChartsController controller;
    private AssessmentsModel model;

    /**
     * Takes refrences to model and controller objects, as-well sets UI
     * @param controller Controller object that takes actions from view and sends to model
     * @param model Model object that contains app data
     */
    public ChartsView(ChartsController controller, AssessmentsModel model){
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
        VBox pieChartValueByNeighbourhood = setByNeighbourhoodPieChartVBox();
        VBox pieChartByAssessmentClassVBox = setByAssessmentClassPieChartVBox();
        VBox barGraphByAssessmentClassVBox = setByAssessmentClassBarGraphVBox();

        pieChartByAssessmentClassVBox.setSpacing(10);
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
                } else if(chartCombo.getValue().equals("Values Ranges - By Assessment Class")) {
                    this.inputFields.getChildren().add(barGraphByAssessmentClassVBox);
                    this.chartVBox.getChildren().clear();
                }
            } else if (typeCombo.getValue().equals("Pie Chart")) {
                if(chartCombo.getValue().equals("Value Ranges - Show All")) {
                    this.chartVBox = setValueAllPieChart();
                    view.setCenter(this.chartVBox);
                } else if(chartCombo.getValue().equals("Value Ranges - By Neighbourhood")) {
                    this.inputFields.getChildren().add(pieChartValueByNeighbourhood);
                    this.chartVBox.getChildren().clear();
                } else if(chartCombo.getValue().equals("Values Ranges - By Assessment Class")) {
                    this.inputFields.getChildren().add(pieChartByAssessmentClassVBox);
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

    private VBox setByNeighbourhoodPieChartVBox() {
        List<String> neighbourhoods = model.getNeighbourhoods();

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

    private VBox setByAssessmentClassBarGraphVBox() {
        List<String> assessClassComboItems = new ArrayList<>(
                Arrays.asList("COMMERCIAL","RESIDENTIAL","OTHER RESIDENTIAL","NONRES MUNICIPAL/RES EDUCATION","FARMLAND"));

        Label aClassLabel = new Label("Assessment Class");

        ComboBox assessClassCombo = new ComboBox<>(FXCollections.observableArrayList(assessClassComboItems));


        Button graphButton = new Button("Show Chart");

        graphButton.setOnAction(e -> {
            String aClass = assessClassCombo.getValue().toString();

            this.chartVBox = setByAssessmentClassBarGraph(aClass);

            view.setCenter(this.chartVBox);
        });

        return new VBox(aClassLabel, assessClassCombo, graphButton);
    }

    private VBox setByAssessmentClassPieChartVBox() {
        List<String> assessClassComboItems = new ArrayList<>(
                Arrays.asList("COMMERCIAL","RESIDENTIAL","OTHER RESIDENTIAL","NONRES MUNICIPAL/RES EDUCATION","FARMLAND"));

        Label aClassLabel = new Label("Assessment Class");

        ComboBox assessClassCombo = new ComboBox<>(FXCollections.observableArrayList(assessClassComboItems));


        Button graphButton = new Button("Show Chart");

        graphButton.setOnAction(e -> {
            String aClass = assessClassCombo.getValue().toString();

            this.chartVBox = setValueByAssessmentClassPieChart(aClass);

            view.setCenter(this.chartVBox);
        });

        return new VBox(aClassLabel, assessClassCombo, graphButton);
    }

    private VBox setValueAllBarGraph() {
        chartVBox.getChildren().clear();

        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        VBox vBox = new VBox();

        XYChart.Series series = new XYChart.Series();
        series.setName("All Property Values");

        series.getData().add(new XYChart.Data("Less than $100,000", model.countByValue(0, 100000, null, null)));
        series.getData().add(new XYChart.Data("$100,000 to $249,999", model.countByValue(100000, 250000, null, null)));
        series.getData().add(new XYChart.Data("$250,000 to $499,999", model.countByValue(250000, 500000, null, null)));
        series.getData().add(new XYChart.Data("$500,000 to $749,999", model.countByValue(500000, 750000, null, null)));
        series.getData().add(new XYChart.Data("$750,000 to $999,999", model.countByValue(750000, 1000000, null, null)));
        series.getData().add(new XYChart.Data("More than $1,000,000", model.countByValue(1000000, null, null, null)));
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

        for(String neighbourhood: neighbourhoods) {
            if(!neighbourhood.isBlank()) {
                XYChart.Series series = new XYChart.Series();
                series.setName(neighbourhood);
                series.getData().add(new XYChart.Data("Less than $100,000", model.countByValue(0, 100000, neighbourhood, null)));
                series.getData().add(new XYChart.Data("$100,000 to $249,999", model.countByValue(100000, 250000, neighbourhood, null)));
                series.getData().add(new XYChart.Data("$250,000 to $499,999", model.countByValue(250000, 500000, neighbourhood, null)));
                series.getData().add(new XYChart.Data("$500,000 to $749,999", model.countByValue(500000, 750000, neighbourhood, null)));
                series.getData().add(new XYChart.Data("$750,000 to $999,999", model.countByValue(750000, 1000000, neighbourhood, null)));
                series.getData().add(new XYChart.Data("More than $1,000,000", model.countByValue(1000000, null, neighbourhood, null)));
                barChart.getData().add(series);
            }
        }
        vBox.getChildren().add(barChart);
        vBox.setAlignment(Pos.CENTER);
        return vBox;
    }

    private VBox setByAssessmentClassBarGraph(String aClass){
        chartVBox.getChildren().clear();

        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        VBox vBox = new VBox();

        XYChart.Series series = new XYChart.Series();
        series.setName(aClass);

        series.getData().add(new XYChart.Data("Less than $100,000", model.countByValue(0, 100000, null, aClass)));
        series.getData().add(new XYChart.Data("$100,000 to $249,999", model.countByValue(100000, 250000, null, aClass)));
        series.getData().add(new XYChart.Data("$250,000 to $499,999", model.countByValue(250000, 500000, null, aClass)));
        series.getData().add(new XYChart.Data("$500,000 to $749,999", model.countByValue(500000, 750000, null, aClass)));
        series.getData().add(new XYChart.Data("$750,000 to $999,999", model.countByValue(750000, 1000000, null, aClass)));
        series.getData().add(new XYChart.Data("More than $1,000,000", model.countByValue(1000000, null, null, aClass)));
        barChart.getData().add(series);

        vBox.getChildren().add(barChart);
        vBox.setAlignment(Pos.CENTER);
        return vBox;
    }

    private VBox setValueAllPieChart() {
        VBox vBox = new VBox();
        int total = model.countByValue(0, null, null, null);
        ObservableList<PieChart.Data> chartData = FXCollections.observableArrayList(
                        new PieChart.Data("Less than $100,000", (double) model.countByValue(0, 100000, null, null)/total),
                        new PieChart.Data("$100,000 to $249,999", (double) model.countByValue(100000, 250000, null, null)/total),
                        new PieChart.Data("$250,000 to $499,999", (double) model.countByValue(250000, 500000, null, null)/total),
                        new PieChart.Data("$500,000 to $749,999", (double) model.countByValue(500000, 750000, null, null)/total),
                        new PieChart.Data("$750,000 to $999,999", (double) model.countByValue(750000, 100000, null, null)/total),
                        new PieChart.Data("More than $1,000,000", (double) model.countByValue(1000000, null, null, null)/total));
        PieChart chart = new PieChart(chartData);
        chart.setTitle("All Property Values");
        vBox.getChildren().add(chart);
        vBox.setAlignment(Pos.CENTER);
        return vBox;
    }

    private VBox setValueByNeighbourhoodPieChart(String selectedNeighbourhood) {
        VBox vBox = new VBox();
        int total = model.countByValue(0, null, selectedNeighbourhood, null);
        ObservableList<PieChart.Data> chartData = FXCollections.observableArrayList(
                new PieChart.Data("Less than $100,000", (double) model.countByValue(0, 100000, selectedNeighbourhood, null)/total),
                new PieChart.Data("$100,000 to $249,999", (double) model.countByValue(100000, 250000, selectedNeighbourhood, null)/total),
                new PieChart.Data("$250,000 to $499,999", (double) model.countByValue(250000, 500000, selectedNeighbourhood, null)/total),
                new PieChart.Data("$500,000 to $749,999", (double) model.countByValue(500000, 750000, selectedNeighbourhood, null)/total),
                new PieChart.Data("$750,000 to $999,999", (double) model.countByValue(750000, 100000, selectedNeighbourhood, null)/total),
                new PieChart.Data("More than $1,000,000", (double) model.countByValue(1000000, null, selectedNeighbourhood, null)/total));
        PieChart chart = new PieChart(chartData);
        chart.setTitle(selectedNeighbourhood);
        vBox.getChildren().add(chart);
        vBox.setAlignment(Pos.CENTER);
        return vBox;
    }

    private VBox setValueByAssessmentClassPieChart(String aClass) {
        VBox vBox = new VBox();
        int total = model.countByValue(0, null,null, aClass);
        ObservableList<PieChart.Data> chartData = FXCollections.observableArrayList(
                new PieChart.Data("Less than $100,000", (double) model.countByValue(0, 100000, null, aClass)/total),
                new PieChart.Data("$100,000 to $249,999", (double) model.countByValue(100000, 250000, null, aClass)/total),
                new PieChart.Data("$250,000 to $499,999", (double) model.countByValue(250000, 500000, null, aClass)/total),
                new PieChart.Data("$500,000 to $749,999", (double) model.countByValue(500000, 750000, null, aClass)/total),
                new PieChart.Data("$750,000 to $999,999", (double) model.countByValue(750000, 1000000, null, aClass)/total),
                new PieChart.Data("More than $1,000,000", (double) model.countByValue(1000000, null, null, aClass)/total));
        PieChart chart = new PieChart(chartData);
        chart.setTitle(aClass);
        vBox.getChildren().add(chart);
        vBox.setAlignment(Pos.CENTER);
        return vBox;
    }
}
