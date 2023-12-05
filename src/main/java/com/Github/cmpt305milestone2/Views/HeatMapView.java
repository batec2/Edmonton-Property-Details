package com.Github.cmpt305milestone2.Views;

import com.Github.cmpt305milestone2.Controllers.AssessmentsController;
import com.Github.cmpt305milestone2.AssessmentsModel;
import com.Github.cmpt305milestone2.AutoCompleteTextField;
import com.Github.cmpt305milestone2.Data.FruitTree;
import com.Github.cmpt305milestone2.Data.Money;
import com.Github.cmpt305milestone2.Data.Property;
import com.esri.arcgisruntime.ArcGISRuntimeEnvironment;
import com.esri.arcgisruntime.concurrent.ListenableFuture;
import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.geometry.SpatialReferences;
import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.BasemapStyle;
import com.esri.arcgisruntime.mapping.Viewpoint;
import com.esri.arcgisruntime.mapping.view.Graphic;
import com.esri.arcgisruntime.mapping.view.GraphicsOverlay;
import com.esri.arcgisruntime.mapping.view.IdentifyGraphicsOverlayResult;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.esri.arcgisruntime.symbology.SimpleMarkerSymbol;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Semaphore;

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
    private MapView mapView;
    private List<Spinner> spinners;
    private ListenableFuture<IdentifyGraphicsOverlayResult> identifyGraphics;
    Spinner<Integer> redSpinner,orangeSpinner,yellowSpinner,ygSpinner;
    AutoCompleteTextField neighbourhoodTextField;
    TextField crimeTextField,fruitTextField,weedTextField;
    ComboBox<String> assessClassCombo;

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
            //controller.switchDao(true);
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

    private void setTextfields(){
        List<String> neighbourhoods = model.getNeighbourhoods();
        neighbourhoodTextField = new AutoCompleteTextField();
        neighbourhoodTextField.getEntries().addAll(neighbourhoods);

        weedTextField = new TextField();
        weedTextField.setMaxWidth(1000);

        crimeTextField = new TextField();
        crimeTextField.setMaxWidth(1000);

        fruitTextField = new TextField();
        fruitTextField.setMaxWidth(1000);
    }

    private void setComboBox(){
        ArrayList<String> assessClassComboItems = new ArrayList<>(
                Arrays.asList(
                        "", "COMMERCIAL","RESIDENTIAL","OTHER RESIDENTIAL","NONRES MUNICIPAL/RES EDUCATION","FARMLAND"
                )
        );
        assessClassCombo = new ComboBox<>(FXCollections.observableArrayList(assessClassComboItems));
        assessClassCombo.getSelectionModel().selectFirst();
        assessClassCombo.setMaxWidth(1000);
    }

    private void setSpinners(){
        redSpinner = new Spinner<>(0,250000,200000,25000);
        redSpinner.getValueFactory().setValue(250000);
        redSpinner.setEditable(true);

        orangeSpinner = new Spinner<>(250001,500000,400000,25000);
        orangeSpinner.setEditable(true);

        yellowSpinner = new Spinner<>(500001,750000,600000,25000);
        orangeSpinner.setEditable(true);

        ygSpinner = new Spinner<>(750001,1250000,800000,25000);
        ygSpinner.setEditable(true);
    }
    /**
     * Creates and sets input fields behaviour, takes input for filtering dataset, search and reset buttons
     * are bound to booleans in the model to prevent multiple button presses
     */
    private void setInputFields(){
        setTextfields();
        setComboBox();
        setSpinners();

        Label neighbourhoodLabel  = new Label("Neighbourhood:");
        Label assessClassLabel = new Label("Assessment Class");
        Label weedLabel = new Label("Cannabis Stores");
        Label crimeLabel = new Label("Crime Rate");
        Label fruitLabel = new Label("Fruit Trees");

        //Colour Ranges
        Label colourRanges = new Label("Colour Ranges");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);

        Label redLabel = new Label("Red (Max): ");
        Label orangeLabel = new Label("Orange (Max): ");
        Label yellowLabel = new Label("Yellow (Max): ");
        Label ygLabel = new Label("Yellow-Green (Max): ");

        grid.add(redLabel,0,0);
        grid.add(redSpinner, 1, 0);
        grid.add(orangeLabel,0,1);
        grid.add(orangeSpinner,1,1);
        grid.add(yellowLabel,0, 2);
        grid.add(yellowSpinner,1,2);
        grid.add(ygLabel,0,3);
        grid.add(ygSpinner,1,3);
        Label greenLabel = new Label("Green defaults to rest.");

        //Search button
        Button searchButton = new Button("Search");
        searchButton.disableProperty().bind(controller.getLoading());//Disables button while data is loading

        //Gets all the values in the input texts and gets new list
        searchButton.setOnAction(e->{
            String neighbourhood = neighbourhoodTextField.getText();
            String assessClass = assessClassCombo.getValue();

            Integer red = redSpinner.getValue();
            Integer orange = orangeSpinner.getValue();
            Integer yellow = yellowSpinner.getValue();
            Integer yg = ygSpinner.getValue();
            ArrayList<Integer> ranges = new ArrayList<>(Arrays.asList(red,orange,yellow,yg));
            updateMap(neighbourhood, assessClass, ranges);
        });
        //Reset button
        Button resetButton = new Button("Reset");
        resetButton.disableProperty().bind(controller.getLoading());//Disables button while data is loading

        resetButton.setOnAction(e->{
            resetFields();
            searchButton.fire();
        });
        HBox hBoxResetSearch = new HBox(searchButton,resetButton);
        hBoxResetSearch.setSpacing(10);

        //Adds all elements to the inputFields VBox
        this.inputFields = new VBox(
                neighbourhoodLabel,
                neighbourhoodTextField,
                assessClassLabel,
                assessClassCombo,
                weedLabel,
                weedTextField,
                fruitLabel,
                fruitTextField,
                crimeLabel,
                crimeTextField,
                colourRanges,
                grid,
                greenLabel,
                hBoxResetSearch);
        this.inputFields.setSpacing(10);
    }

    /**
     * Resets all input fields to blank
     */
    private void resetFields(){
        neighbourhoodTextField.setText("");
        assessClassCombo.getSelectionModel().selectFirst();
        weedTextField.setText("");
        fruitTextField.setText("");
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
        mapView.setViewpoint(new Viewpoint(53.5461, -113.4937, 72223.819286));

        updateMap("","",List.of(200000,400000,600000,800000));
        this.mapVBox = new VBox(mapView);
        VBox.setVgrow(mapView, Priority.ALWAYS);


    }


    private void updateMap(@NotNull String neighbourhood, String assessClass, List<Integer>/*<String>*/ ranges) {
        //Semaphore to prevent dots from drawing until list is updated with new data
        Semaphore sem = controller.getSem();
        mapView.getGraphicsOverlays().clear();

        // create a graphics overlay for properties and add it to the map view
        GraphicsOverlay graphicsOverlay = new GraphicsOverlay();
        mapView.getGraphicsOverlays().add(graphicsOverlay);

        SimpleMarkerSymbol.Style markerStyle = SimpleMarkerSymbol.Style.CIRCLE;
        float markerSize = 4f;

        if (neighbourhood.isEmpty() && assessClass.isEmpty()) {
            this.controller.resetData(sem);
        }
        else{
            ArrayList<String> input = new ArrayList<>(Arrays.asList("", "", neighbourhood, assessClass, "", ""));
            this.controller.filterData(input,sem);
        }
        //List<Property> data = model.getData();
        new Thread(()->{
            try {
                sem.acquire();
                for (Property property : model.getData()) {
                    double longitude = Double.parseDouble(property.getLongitude());
                    double latitude = Double.parseDouble(property.getLatitude());
                    int value = property.getAssessedValue().intValue();
                    Color color;

                    if (value < ranges.get(0)) {
                        color = Color.RED;
                    } else if (value < ranges.get(1)) {
                        color = Color.ORANGE;
                    } else if (value < ranges.get(2)) {
                        color = Color.YELLOW;
                        ;
                    } else if (value < ranges.get(3)) {
                        color = Color.YELLOWGREEN;
                    } else {
                        color = Color.GREEN;
                    }
                    graphicsOverlay.getGraphics().add(
                            new Graphic(
                                    new Point(longitude, latitude, SpatialReferences.getWgs84()),
                                    new SimpleMarkerSymbol(markerStyle, color, markerSize)));
                }
                sem.release();
            }
            catch (Exception e){
                System.err.println(e);
                sem.release();
            }
        }).start();
        mapView.setOnMouseClicked(e -> {
            if(e.getButton() == MouseButton.PRIMARY && e.isStillSincePress()) {
                Point2D mapViewPoint = new Point2D(e.getX(), e.getY());
                identifyGraphics = mapView.identifyGraphicsOverlayAsync(graphicsOverlay, mapViewPoint, 5, false);
                identifyGraphics.addDoneListener(() -> Platform.runLater(this::PropertyInfoDialog));
            }
        });
    }

    /**
     * Generates a property information dialog pop-up when a valid property point is clicked on the map
     */
    private void PropertyInfoDialog() {
        try {
            IdentifyGraphicsOverlayResult result = identifyGraphics.get();
            List<Graphic> graphics = result.getGraphics();

            if(!graphics.isEmpty()) {
                var dialog = new Alert(Alert.AlertType.INFORMATION);
                dialog.initOwner(mapView.getScene().getWindow());
                dialog.setHeaderText(null);
                Point point = (Point) graphics.get(0).getGeometry();
                double longitude = point.getX();
                double latitude = point.getY();
                Property p = controller.getAssessment(longitude, latitude);

                if(p==null){
                    System.out.println("Nothing");
                    return;
                }

                dialog.setTitle("Property Information");
                dialog.getDialogPane().setContent(dialogContent(p));
                dialog.showAndWait();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Generates the text content for a property information pop-ip
     * @param p The property to show ingo for
     * @return Property information formatted into a grid pane
     */
    private @NotNull GridPane dialogContent(Property p) {
        GridPane grid = new GridPane();
        grid.addRow(0, new Label("Account Number: "), new Label(Integer.toString(p.getAccountNum())));
        grid.addRow(1, new Label("Address: "), new Label(p.getAddress().toString()));
        grid.addRow(2, new Label("Neighbourhood: "), new Label(p.getNeighbourhood()));
        grid.addRow(3, new Label("Ward: "), new Label(p.getWard()));
        grid.addRow(4, new Label("Assessed Value: "), new Label(Money.bigDecimalToMoney(p.getAssessedValue())));
        grid.addRow(5, new Label("Latitude: "), new Label(p.getGeoLocation().getLatitude()));
        grid.addRow(6, new Label("Longitude: "), new Label(p.getGeoLocation().getLongitude()));
        grid.addRow(7, new Label("Assessment Class 1: "),
                    new Label(p.getAssessment1() + " " + p.getAssessmentPercent1()));
        if(!p.getAssessment2().isEmpty()) {
            grid.addRow(8, new Label("Assessment Class 2: "),
                         new Label(p.getAssessment1() + " " + p.getAssessmentPercent2()));
        }
        if(!p.getAssessment3().isEmpty()) {
            grid.addRow(9, new Label("Assessment Class 3: "),
                        new Label(p.getAssessment1() + " " + p.getAssessmentPercent3()));
        }
        return grid;
    }
    /**
     * Gets the map view object, doesn't deep copy as this is used to
     * @return
     */
    public void destroyMapView() {
        if(this.mapView != null) {
            this.mapView.dispose();
        }
    }
}
