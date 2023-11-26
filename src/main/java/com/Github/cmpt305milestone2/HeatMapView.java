package com.Github.cmpt305milestone2;

import com.Github.cmpt305milestone2.Data.Money;
import com.Github.cmpt305milestone2.Data.Property;
import com.esri.arcgisruntime.ArcGISRuntimeEnvironment;
import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.geometry.SpatialReferences;
import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.BasemapStyle;
import com.esri.arcgisruntime.mapping.Viewpoint;
import com.esri.arcgisruntime.mapping.view.Graphic;
import com.esri.arcgisruntime.mapping.view.GraphicsOverlay;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.esri.arcgisruntime.symbology.SimpleMarkerSymbol;
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
import javafx.scene.paint.Color;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * View object for Application, sets UI objects and behaviour on UI objects when interacted with by the user
 */
public class HeatMapView {
    private BorderPane view;
    private VBox vBoxLeft;
    private VBox inputFields;
    private VBox mapVBox;
    private AssessmentsController controller;
    private AssessmentsModel model;
    MapView mapView;


    /**
     * Takes refrences to model and controller objects, as-well sets UI
     * @param controller Controller object that takes actions from view and sends to model
     * @param model Model object that contains app data
     */
    public HeatMapView(AssessmentsController controller, AssessmentsModel model){
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

        if(!controller.getIsCSV()) {
            controller.switchDao(true);
        }

        setMap();
        view.setCenter(this.mapVBox);

        setVboxLeft();
        view.setLeft(this.vBoxLeft);

    }

    /**
     * Sets all items in the left vbox including the mode selector and all input fields
     */
    private void setVboxLeft(){
        setInputFields();

        this.vBoxLeft = new VBox(
                this.inputFields);

        this.vBoxLeft.setSpacing(10);
        this.vBoxLeft.setPadding(new Insets(10));
    }

    /**
     * Creates and sets input fields behaviour, takes input for filtering dataset, search and reset buttons
     * are bound to booleans in the model to prevent multiple button presses
     */
    private void setInputFields(){
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

        //Colour Ranges
        Label colourRanges = new Label("Colour Ranges");

        Label redLabel = new Label("Red (Max): ");
        TextField redTextField = new TextField();
        redTextField.setText("250000");
        HBox hBoxRed = new HBox(redLabel,redTextField);
        hBoxRed.setSpacing(10);

        Label orangeLabel = new Label("Orange (Max): ");
        TextField orangeTextField = new TextField();
        orangeTextField.setText("500000");
        HBox hBoxOrange = new HBox(orangeLabel,orangeTextField);
        hBoxOrange.setSpacing(10);

        Label yellowLabel = new Label("Yellow (Max): ");
        TextField yellowTextField = new TextField();
        yellowTextField.setText("750000");
        HBox hBoxYellow = new HBox(yellowLabel,yellowTextField);
        hBoxYellow.setSpacing(10);

        Label ygLabel = new Label("Yellow-Green (Max): ");
        TextField ygTextField = new TextField();
        ygTextField.setText("1000000");
        HBox hBoxYG = new HBox(ygLabel,ygTextField);
        hBoxYG.setSpacing(10);

        Label greenLabel = new Label("Green defaults to rest.");

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
            String assessClass = assessClassCombo.getValue();
            String red = redTextField.getText();
            String orange = orangeTextField.getText();
            String yellow = yellowTextField.getText();
            String yg = ygTextField.getText();
            ArrayList<String> ranges = new ArrayList<>(Arrays.asList(red,orange,yellow,yg));
            updateMap(assessClass, ranges);
        });
        //Adds all elements to the inputFields VBox
        this.inputFields = new VBox(
                assessClassLabel,
                assessClassCombo,
                colourRanges,
                hBoxRed,
                hBoxOrange,
                hBoxYellow,
                hBoxYG,
                greenLabel,
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
     * Sets Columns and cell values as well binds table to model so table is automatically updated with changes
     * to the model
     */
    private void setMap(){
        String apiKey = "AAPK8c0d65d196244da28b26d6a3098f40582ZNJ5DF-VyaB3dmS_2wwjcZDrFAKwZ6HMw9iM-qhfl4J9KmdCk3-UU15Sa7ukWnx";
        ArcGISRuntimeEnvironment.setApiKey(apiKey);



        mapView = new MapView();

        ArcGISMap map = new ArcGISMap(BasemapStyle.ARCGIS_TOPOGRAPHIC);

        //Set map on mapView and locate in Edmonton
        mapView.setMap(map);
        mapView.setViewpoint(new Viewpoint(53.5461, -113.4937, 300000));

        this.mapVBox = new VBox(mapView);
        VBox.setVgrow(mapView, Priority.ALWAYS);

    }

    private void updateMap(String assessClass, List<String> ranges) {
        mapView.getGraphicsOverlays().clear();

        // create a graphics overlay for propertiesand add it to the map view
        GraphicsOverlay graphicsOverlay = new GraphicsOverlay();
        mapView.getGraphicsOverlays().add(graphicsOverlay);


        // create a point geometry with a location and spatial reference
        Point point;
        SimpleMarkerSymbol.Style markerStyle = SimpleMarkerSymbol.Style.CIRCLE;
        int markerSize = 2;

        // create an opaque orange point symbol with a opaque blue outline symbol
        SimpleMarkerSymbol markerSymbolRed =
                new SimpleMarkerSymbol(markerStyle, Color.RED, markerSize);
        SimpleMarkerSymbol markerSymbolOrange =
                new SimpleMarkerSymbol(markerStyle, Color.ORANGE, markerSize);
        SimpleMarkerSymbol markerSymbolYellow =
                new SimpleMarkerSymbol(markerStyle, Color.YELLOW, markerSize);
        SimpleMarkerSymbol markerSymbolYG =
                new SimpleMarkerSymbol(markerStyle, Color.YELLOWGREEN, markerSize);
        SimpleMarkerSymbol markerSymbolGreen =
                new SimpleMarkerSymbol(markerStyle, Color.GREEN, markerSize);
        SimpleMarkerSymbol marker;

        if (assessClass.isEmpty()) {
            this.controller.resetData();
        }
        else{
            ArrayList<String> input = new ArrayList<>(Arrays.asList("", "", "", assessClass, "", ""));
            this.controller.filterData(input);
        }


        for(Property property : model.getData()) {
            double longitude = Double.parseDouble(property.getLongitude());
            double latitude = Double.parseDouble(property.getLatitude());
            int value = property.getAssessedValue().intValue();
            if (value < Integer.parseInt(ranges.get(0))) {
                marker = markerSymbolRed;
            } else if (value < Integer.parseInt(ranges.get(1))) {
                marker = markerSymbolOrange;
            } else if (value < Integer.parseInt(ranges.get(2))) {
                marker = markerSymbolYellow;
            } else if (value < Integer.parseInt(ranges.get(3))) {
                marker = markerSymbolYG;
            } else {
                marker = markerSymbolGreen;
            }
            point = new Point(longitude, latitude, SpatialReferences.getWgs84());

            // create a graphic with the point geometry and symbol
            Graphic pointGraphic = new Graphic(point, marker);

            // add the point graphic to the graphics overlay
            graphicsOverlay.getGraphics().add(pointGraphic);
        }
    }

}
