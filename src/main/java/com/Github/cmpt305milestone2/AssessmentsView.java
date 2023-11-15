package com.Github.cmpt305milestone2;

import atlantafx.base.controls.ToggleSwitch;
import com.Github.cmpt305milestone2.Data.Property;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;

public class AssessmentsView {
    private BorderPane view;
    private TableView table;
    private AssessmentsController controller;
    private AssessmentsModel model;
    public AssessmentsView(AssessmentsController controller,AssessmentsModel model){
        this.controller = controller;
        this.model = model;
        setStage();
    }

    public Parent asParent() {
        return view ;
    }

    private void setStage(){
        this.view = new BorderPane();
        setTable();
        view.setCenter(this.table);
        view.setLeft(setVboxLeft());
        view.setBottom(setBoxPager());
    }

    private VBox setVboxLeft(){
        VBox vBoxLeft = new VBox(
                setModeSelector(),
                setInputFields());

        vBoxLeft.setSpacing(10);
        vBoxLeft.setPadding(new Insets(10));

        return vBoxLeft;
    }

    private HBox setModeSelector(){
        ToggleSwitch apiToCSVToggle = new ToggleSwitch();
        apiToCSVToggle.disableProperty().bind(model.getCsvLoaded());
        apiToCSVToggle.selectedProperty().addListener((observable, oldValue, newValue) ->{
            model.switchDao(newValue);
        });
        Label csvLabel = new Label("CSV");
        Label apiLabel = new Label("API");
        HBox hBoxCSVtoAPI= new HBox(apiLabel,apiToCSVToggle,csvLabel);
        hBoxCSVtoAPI.setSpacing(5);
        hBoxCSVtoAPI.setAlignment(Pos.CENTER);
        return hBoxCSVtoAPI;
    }

    private VBox setInputFields(){
        Label accountLabel  = new Label("Account Number:");
        TextField accountTextField = new TextField("");

        Label addressLabel  = new Label("Address:");
        TextField addressTextField = new TextField("");

        Label neighbourhoodLabel  = new Label("Neighbourhood:");
        TextField neighbourhoodTextField = new TextField("");

        ArrayList<String> assessClassComboItems = new ArrayList<>(
                Arrays.asList(
                        "", "COMMERCIAL","RESIDENTIAL","OTHER RESIDENTIAL","NONRES MUNICIPAL/RES EDUCATION","FARMLAND"
                )
        );

        Label assessClassLabel = new Label("Assessment Class");
        ComboBox<String> assessClassCombo = new ComboBox<>(FXCollections.observableArrayList(assessClassComboItems));
        assessClassCombo.getSelectionModel().selectFirst();

        Label valueRange = new Label("Assessed Value Range");

        TextField minTextField = new TextField();
        minTextField.setPromptText("Min");
        TextField maxTextField = new TextField();

        maxTextField.setPromptText("Max");
        HBox hBoxMinMax = new HBox(minTextField,maxTextField);

        Button resetButton = new Button("Reset");
        resetButton.setOnAction(e->{
            controller.resetData();
            accountTextField.setText("");
            addressTextField.setText("");
            neighbourhoodTextField.setText("");
            assessClassCombo.getSelectionModel().selectFirst();
            minTextField.setText("");
            maxTextField.setText("");
        });

        Button searchButton = new Button("Search");
        HBox hBoxResetSearch = new HBox(searchButton,resetButton);
        searchButton.setOnAction(e->{
            String account = accountTextField.getText();
            String address = addressTextField.getText();
            String neighbourhood = neighbourhoodTextField.getText();
            String assessClass = assessClassCombo.getValue();
            String min = minTextField.getText();
            String max = maxTextField.getText();
            ArrayList<String> input = new ArrayList<>(Arrays.asList(account,address,neighbourhood,assessClass,min,max));
            controller.filterData(input);
        });

        return new VBox(
                accountLabel,
                accountTextField,
                addressLabel,
                addressTextField,
                neighbourhoodLabel,
                neighbourhoodTextField,
                assessClassLabel,
                assessClassCombo,
                valueRange,
                hBoxMinMax,
                hBoxResetSearch);
    }

    private HBox setBoxPager(){
        Button prevButton = new Button("Prev");
        prevButton.setOnAction(e->controller.pageDown());

        Button nextButton = new Button("Next");
        nextButton.setOnAction(e->controller.pageUp());

        HBox hBoxPager = new HBox(prevButton,nextButton);
        hBoxPager.setAlignment(Pos.BASELINE_CENTER);
        hBoxPager.setPadding(new Insets(10));
        hBoxPager.setSpacing(10);

        return hBoxPager;
    }

    private void setTable(){

        table = new TableView();

        TableColumn<Property, String> accountNum = new TableColumn<>("Account Number");
        accountNum.setMinWidth(90);
        accountNum.setCellValueFactory(new PropertyValueFactory<>("accountNum"));

        TableColumn<Property, String> address = new TableColumn<>("Address");
        address.setMinWidth(200);
        address.setCellValueFactory(new PropertyValueFactory<>("address"));

        TableColumn<Property, String> garage = new TableColumn<>("Garage");
        garage.setMinWidth(10);
        garage.setCellValueFactory(new PropertyValueFactory<>("garage"));

        TableColumn<Property, String> neighbourWard = new TableColumn<>("Neighbourhood");
        neighbourWard.setMinWidth(200);
        neighbourWard.setCellValueFactory(new PropertyValueFactory<>("neighbourWard"));

        TableColumn<Property, BigDecimal> assessedValue = new TableColumn<>("Assessed Value");
        assessedValue.setMinWidth(90);
        assessedValue.setCellValueFactory(new PropertyValueFactory<>("assessedValue"));

        TableColumn<Property, String> latitude = new TableColumn<>("Latitude");
        latitude.setMinWidth(150);
        latitude.setCellValueFactory(new PropertyValueFactory<>("latitude"));

        TableColumn<Property, String> longitude = new TableColumn<>("Longitude");
        longitude.setMinWidth(150);
        longitude.setCellValueFactory(new PropertyValueFactory<>("longitude"));

        TableColumn<Property, String> assessmentClass = new TableColumn<>("Assessment Class");
        assessmentClass.setMinWidth(200);
        assessmentClass.setCellValueFactory(new PropertyValueFactory<>("assessmentClass"));

        table.getColumns().setAll(accountNum,address,garage, neighbourWard,assessedValue,latitude,longitude,assessmentClass);
        table.setItems(model.getData());
        VBox.setVgrow(table, Priority.ALWAYS);
    }
}
