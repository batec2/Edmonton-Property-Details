package com.Github.cmpt305milestone2.Views;

import com.Github.cmpt305milestone2.AutoCompleteTextField;
import com.Github.cmpt305milestone2.Controllers.ChartsController;
import javafx.collections.FXCollections;
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
    private String chartType;

    /**
     * Takes refrences to model and controller objects, as-well sets UI
     * @param controller Controller object that takes actions from view and sends to model
     */
    public ChartsView(ChartsController controller){
        this.controller = controller;
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
        VBox vBoxBarGraphByNeighbourhood = setVBoxBarGraphByNeighbourhood();
        VBox vBoxPieChartByNeighbourhood = setVBoxPieChartByNeighbourhood();
        VBox vBoxByAssessmentClass = setVBoxByAssessmentClass();

        vBoxBarGraphByNeighbourhood.setSpacing(10);
        vBoxPieChartByNeighbourhood.setSpacing(10);
        vBoxByAssessmentClass.setSpacing(10);

        //Gets all the values in the input texts and gets new list
        selectButton.setOnAction(e->{
            this.inputFields.getChildren().clear();
            this.inputFields.getChildren().add(typeFields);
            this.chartType = typeCombo.getValue();
            if(chartCombo.getValue().equals("Value Ranges - Show All")) {
                view.setCenter(this.chartVBox);
                if (chartType.equals("Bar Graph")) {
                    this.chartVBox = setBarGraph(null, null);
                } else if (typeCombo.getValue().equals("Pie Chart")) {
                    this.chartVBox = setPieChart(null, null);
                }
                view.setCenter(this.chartVBox);
            } else if (chartCombo.getValue().equals("Value Ranges - By Neighbourhood")){
                if (chartType.equals("Bar Graph")) {
                    this.inputFields.getChildren().add(vBoxBarGraphByNeighbourhood);
                    this.chartVBox.getChildren().clear();
                } else if (typeCombo.getValue().equals("Pie Chart")) {
                    this.inputFields.getChildren().add(vBoxPieChartByNeighbourhood);
                    this.chartVBox.getChildren().clear();
                }
                this.inputFields.setSpacing(10);
            } else if (chartCombo.getValue().equals("Values Ranges - By Assessment Class")) {
                this.inputFields.getChildren().add(vBoxByAssessmentClass);
                this.chartVBox.getChildren().clear();
                this.inputFields.setSpacing(10);
            }
        });
    }

    /**
     * THIS IS A DOCSTRING
     * @return
     */
    private VBox setVBoxBarGraphByNeighbourhood() {
        List<String> neighbourhoods = controller.getNeighbourhoods();

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

            this.chartVBox = setBarGraph(selectedNeighbourhoods, null);
            view.setCenter(this.chartVBox);
        });
        return new VBox(neighbourhood1Label, neighbourhood1TextField,
                    neighbourhood2Label, neighbourhood2TextField,
                    neighbourhood3Label, neighbourhood3TextField,
                    neighbourhood4Label, neighbourhood4TextField,
                    neighbourhood5Label, neighbourhood5TextField,
                    graphButton);
    }

    /**
     * sets the input fields for piechart by neighbourhood
     * @return Returns the vbox with elements for piechart by neighbourhood
     */
    private VBox setVBoxPieChartByNeighbourhood() {
        List<String> neighbourhoods = controller.getNeighbourhoods();

        Label neighbourhoodLabel = new Label("Neighbourhood");
        AutoCompleteTextField neighbourhoodTextField = new AutoCompleteTextField();
        neighbourhoodTextField.getEntries().addAll(neighbourhoods);

        Button graphButton = new Button("Show Graph");

        graphButton.setOnAction(e -> {
            String selectedNeighbourhood = neighbourhoodTextField.getText();
            if(neighbourhoods.contains(selectedNeighbourhood)) {
                this.chartVBox = setPieChart(selectedNeighbourhood, null);
            }
            else {
                this.chartVBox = new VBox();
            }
            view.setCenter(this.chartVBox);
        });
        return new VBox(neighbourhoodLabel, neighbourhoodTextField,
                graphButton);
    }

    /**
     * sets the input fields for getting the data by assessment class
     * @return returns a VBox
     */
    private VBox setVBoxByAssessmentClass() {
        List<String> assessClassComboItems = new ArrayList<>(
                Arrays.asList("COMMERCIAL","RESIDENTIAL","OTHER RESIDENTIAL","NONRES MUNICIPAL/RES EDUCATION","FARMLAND"));

        Label aClassLabel = new Label("Assessment Class");

        ComboBox assessClassCombo = new ComboBox<>(FXCollections.observableArrayList(assessClassComboItems));


        Button graphButton = new Button("Show Chart");

        graphButton.setOnAction(e -> {
            String aClass = assessClassCombo.getValue().toString();
            if(this.chartType.equals("Bar Graph")) {
                this.chartVBox = setBarGraph(null, aClass);
            } else if (this.chartType.equals("Pie Chart")) {
                this.chartVBox = setPieChart(null, aClass);
            }
            view.setCenter(this.chartVBox);
        });

        return new VBox(aClassLabel, assessClassCombo, graphButton);
    }

    /**
     * Builds a VBox for the main pane that contains a bar graph
     * @param neighbourhoods
     * @param assessmentClass
     * @return
     */
    private VBox setBarGraph(List<String> neighbourhoods, String assessmentClass) {
        chartVBox.getChildren().clear();

        VBox vBox = new VBox();
        BarChart<String, Number> barChart = controller.getBarGraph(neighbourhoods, assessmentClass);
        vBox.getChildren().add(barChart);
        vBox.setAlignment(Pos.CENTER);

        return vBox;
    }

    /**
     * Makes a vBox containing a pie chart showing property value distributions. Can be setup to show all properties
     * in Edmonton, a particular neighbourhood, or an assessment class
     * @param neighbourhood name of neighbourhood, null if showing assessment class or all
     * @param assessmentClass assessment class, null is showing neighbourhood or all
     * @return vBox containing pie chart
     */
    private VBox setPieChart(String neighbourhood, String assessmentClass) {
        VBox vBox = new VBox();
        vBox.getChildren().add(controller.getPieChart(neighbourhood, assessmentClass));
        vBox.setAlignment(Pos.CENTER);
        return vBox;
    }
}
