package com.Github.cmpt305milestone2.Views;

import atlantafx.base.controls.ToggleSwitch;
import com.Github.cmpt305milestone2.AssessmentsController;
import com.Github.cmpt305milestone2.AssessmentsModel;
import com.Github.cmpt305milestone2.Data.Money;
import com.Github.cmpt305milestone2.Data.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.io.File;
import java.util.*;

/**
 * View object for Application, sets UI objects and behaviour on UI objects when interacted with by the user
 */
public class AssessmentsView {
    private BorderPane view;
    private VBox vBoxLeft;
    private VBox inputFields;
    private HBox modeSelector;
    private HBox hBoxPager;
    private VBox tableVBox;
    private AssessmentsController controller;
    private AssessmentsModel model;

    /**
     * Takes refrences to model and controller objects, as-well sets UI
     * @param controller Controller object that takes actions from view and sends to model
     * @param model Model object that contains app data
     */
    public AssessmentsView(AssessmentsController controller,AssessmentsModel model){
        this.controller = controller;
        this.model = model;
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
        setTable();
        view.setCenter(this.tableVBox);

        setVboxLeft();
        view.setLeft(this.vBoxLeft);

        setBoxPager();
        view.setBottom(this.hBoxPager);
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
     * Creates and sets data source toggle switch behaviour, binds the disabled
     * property to a boolean in the model to act as a mutex, to prevent CSV usage
     * before csv is properly loaded in.
     */
    @Deprecated
    private void setModeSelector(){
        ToggleSwitch apiToCSVToggle = new ToggleSwitch();

        //Defaults to API
        if(controller.getIsCSV()) {
            controller.switchDao(false);
        }

        //Disables toggle until thread that loads in CSV completes
        apiToCSVToggle.disableProperty().bind(this.model.getCsvLoaded());
        //Switches which dao is used by the model
        apiToCSVToggle.selectedProperty()
                .addListener((observable, oldValue, newValue) ->{
                    this.controller.switchDao(newValue);
                    resetFields();//empties input fields
        });
        Label csvLabel = new Label("CSV");
        Label apiLabel = new Label("API");

        this.modeSelector= new HBox(apiLabel,apiToCSVToggle,csvLabel);
        this.modeSelector.setSpacing(10);
        this.modeSelector.setAlignment(Pos.CENTER);
    }

    /**
     * Creates and sets input fields behaviour, takes input for filtering dataset, search and reset buttons
     * are bound to booleans in the model to prevent multiple button presses
     */
    private void setInputFields(){
        //Account number
        Label accountLabel  = new Label("Account Number:");
        TextField accountTextField = new TextField("");

        //Address
        Label addressLabel  = new Label("Address:");
        TextField addressTextField = new TextField("");
        addressTextField.setPromptText("(Suite# House# Street)");

        //Neighbourhood
        Label neighbourhoodLabel  = new Label("Neighbourhood:");

        /*
        AutoCompleteTextField neighbourhoodTextField = new AutoCompleteTextField();
        new Thread(() -> {
            List<String> neighbourhoods = model.getNeighbourhoods();
            Collections.sort(neighbourhoods);
            neighbourhoodTextField.getEntries().addAll(neighbourhoods);
        }).start();
         */
        TextField neighbourhoodTextField = new TextField("");

        //Assessment Class
        ArrayList<String> assessClassComboItems = new ArrayList<>(
                Arrays.asList(
                        "", "COMMERCIAL","RESIDENTIAL","OTHER RESIDENTIAL","NONRES MUNICIPAL/RES EDUCATION","FARMLAND"
                )
        );

        Label assessClassLabel = new Label("Assessment Class");
        ComboBox<String> assessClassCombo = new ComboBox<>(FXCollections.observableArrayList(assessClassComboItems));
        assessClassCombo.getSelectionModel().selectFirst();
        assessClassCombo.setMaxWidth(1000);

        //Assessed value
        Label valueRange = new Label("Assessed Value Range");
        TextField minTextField = new TextField();
        minTextField.setPromptText("Min");
        TextField maxTextField = new TextField();
        maxTextField.setPromptText("Max");

        HBox hBoxMinMax = new HBox(minTextField,maxTextField);
        hBoxMinMax.setSpacing(10);


        //Search and Reset Button
        Button resetButton = new Button("Reset");
        resetButton.disableProperty().bind(controller.getLoading());//Disables button while data is loading
        resetButton.setOnAction(e->{
            this.controller.resetData();
            resetFields();
        });

        Button searchButton = new Button("Search");
        searchButton.disableProperty().bind(controller.getLoading());//Disables button while data is loading
        HBox hBoxResetSearch = new HBox(searchButton,resetButton);
        hBoxResetSearch.setSpacing(10);

        //Gets all the values in the input texts and gets new list
        searchButton.setOnAction(e->{
            String account = accountTextField.getText();
            String address = addressTextField.getText();
            String neighbourhood = neighbourhoodTextField.getText();
            String assessClass = assessClassCombo.getValue();
            String min = minTextField.getText();
            String max = maxTextField.getText();
            ArrayList<String> input = new ArrayList<>(Arrays.asList(account,address,neighbourhood,assessClass,min,max));
            this.controller.filterData(input);
        });
        //Adds all elements to the inputFields VBox
        this.inputFields = new VBox(
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
        this.inputFields.setSpacing(10);
    }


    /**
     * Resets all input fields to blank
     */
    private void resetFields(){
        //text fields not in separate nodes
        this.inputFields.getChildren()
                .filtered(n->n instanceof TextField)
                .forEach(n->((TextField)n).setText(""));
        //Sets combo box to first select which is blank
        this.inputFields.getChildren()
                .filtered(n->n instanceof ComboBox<?>)
                .forEach(n->((ComboBox<?>)n).getSelectionModel().selectFirst());
        //clears the min and max text fields
        this.inputFields.getChildren()
                .filtered(n->n instanceof HBox)
                .forEach(n->((HBox)n).getChildren()
                        .filtered(field->field instanceof TextField)
                        .forEach(field->((TextField)field).setText("")));
    }

    /**
     * Sets Bottom pager behaviour, only available when using api, buttons are disabled while data loads into the table
     * to prevent multiple button presses by the user
     */
    private void setBoxPager(){
        Button prevButton = new Button("Prev");
        prevButton.disableProperty().bind(this.controller.getLoadingPrev());
        prevButton.setOnAction(e->this.controller.pageDown());

        Button nextButton = new Button("Next");
        nextButton.disableProperty().bind(this.controller.getLoadingNext());
        nextButton.setOnAction(e->this.controller.pageUp());

        this.hBoxPager = new HBox(prevButton,nextButton);
        this.hBoxPager.setAlignment(Pos.BASELINE_CENTER);
        this.hBoxPager.setPadding(new Insets(10));
        this.hBoxPager.setSpacing(10);
    }

    /**
     * Sets Columns and cell values as well binds table to model so table is automatically updated with changes
     * to the model
     */
    private void setTable(){
        TableView table = new TableView();

        TableColumn<Property, String> accountNum = new TableColumn<>("Account #");
        accountNum.setMinWidth(90);
        accountNum.setCellValueFactory(new PropertyValueFactory<>("accountNum"));

        TableColumn<Property, String> address = new TableColumn<>("Address");
        address.setMinWidth(200);
        address.setCellValueFactory(new PropertyValueFactory<>("address"));

        TableColumn<Property, String> garage = new TableColumn<>("Garage");
        garage.setMinWidth(75);
        garage.setCellValueFactory(new PropertyValueFactory<>("garage"));

        TableColumn<Property, String> neighbourWard = new TableColumn<>("Neighbourhood");
        neighbourWard.setMinWidth(200);
        neighbourWard.setCellValueFactory(new PropertyValueFactory<>("neighbourWard"));

        TableColumn<Property, String> assessedValue = new TableColumn<>("Assessed Value");
        assessedValue.setMinWidth(90);
        assessedValue.setCellValueFactory(cell->new SimpleObjectProperty<>(Money.bigDecimalToMoney(cell.getValue().getAssessedValue())));

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
        table.setItems(this.model.getData());
        table.setFixedCellSize(40);//this is needed to prevent index error

        this.tableVBox = new VBox(table);
        table.setPlaceholder(setTablePlaceholder());
        VBox.setVgrow(table, Priority.ALWAYS);
    }

    /**
     * Sets what is shown when table is empty
     * @return VBox containing objects to be shown
     */
    private VBox setTablePlaceholder(){
        //image that shows
        Image image = new Image(new File("files/giphy.gif").toURI().toString());
        ImageView imageView = new ImageView(image);
        //text
        Label noResults = new Label("No Results!");
        VBox placeholder = new VBox(imageView,noResults);
        placeholder.setAlignment(Pos.CENTER);
        return placeholder;
    }
}
