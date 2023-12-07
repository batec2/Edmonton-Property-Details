package com.Github.cmpt305milestone2.Views;

import com.Github.cmpt305milestone2.Controllers.AssessmentsController;
import com.Github.cmpt305milestone2.Model;
import com.Github.cmpt305milestone2.AutoCompleteTextField;
import com.Github.cmpt305milestone2.Data.Money;
import com.Github.cmpt305milestone2.Data.Property;
import com.esri.arcgisruntime.ArcGISRuntimeEnvironment;
import com.esri.arcgisruntime.concurrent.ListenableFuture;
import com.esri.arcgisruntime.geometry.*;
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
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
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
    private Model model;
    private MapView mapView;
    private ListenableFuture<IdentifyGraphicsOverlayResult> identifyGraphics;
    Spinner<Integer> redSpinner,orangeSpinner,yellowSpinner,ygSpinner, fruitSpinner, weedSpinner, crimeSpinner;
    AutoCompleteTextField neighbourhoodTextField, crimeTextField, fruitTextField;
    ComboBox<String> assessClassCombo;
    CheckBox weedCheck;
    ImageView loading;



    /**
     * Takes refrences to model and controller objects, as-well sets UI
     * @param controller Controller object that takes actions from view and sends to model
     * @param model Model object that contains app data
     */
    public HeatMapView(AssessmentsController controller, Model model){
        this.controller = controller;
        this.model = model;

        setStage();
    }

    /**
     * Returns Root node(BorderPane)
     * @return BorderPane root node
     */
    public BorderPane asBorderPane() { return view;}

    /**
     * Sets the items for the root node
     */
    private void setStage(){
        this.view = new BorderPane();

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

    private void setTextFields(){
        List<String> neighbourhoods = controller.getNeighbourhoods();
        neighbourhoodTextField = new AutoCompleteTextField();
        neighbourhoodTextField.getEntries().addAll(neighbourhoods);

        List<String> crimeTypes = controller.getCrimeTypes();
        crimeTextField = new AutoCompleteTextField();
        crimeTextField.setMaxWidth(1000);
        crimeTextField.getEntries().addAll(crimeTypes);

        List<String> fruitTreeTypes = controller.getFruitTreeTypes();
        fruitTextField = new AutoCompleteTextField();
        fruitTextField.setMaxWidth(1000);
        fruitTextField.getEntries().addAll(fruitTreeTypes);
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
        weedSpinner = new Spinner<>(0,2000,0,10);
        weedSpinner.setEditable(true);

        fruitSpinner = new Spinner<>(0, 2000, 0, 10);
        fruitSpinner.setEditable(true);

        crimeSpinner = new Spinner<>(0, 2000, 0, 10);
        crimeSpinner.setEditable(true);

        redSpinner = new Spinner<>(0,250000,200000,25000);
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
        setTextFields();
        setComboBox();
        setSpinners();
        setLoading();
        Label neighbourhoodLabel  = new Label("Neighbourhood");
        Label assessClassLabel = new Label("Assessment Class");
        this.weedCheck = new CheckBox("Near Cannabis Store?");
        weedCheck.setIndeterminate(false);
        Label weedDistance = new Label("Cannabis Store Distance (m) ");
        Label fruitLabel = new Label("Fruit Trees");
        Label fruitDistance = new Label("Fruit Tree Distance (m) ");
        Label crimeLabel = new Label("Nearby Criminal Activity");
        Label crimeDistance = new Label("Crime Distance (m) ");

        GridPane weedGrid = new GridPane();
        weedGrid.setAlignment(Pos.CENTER);
        weedGrid.addRow(0, weedDistance, weedSpinner);

        GridPane fruitGrid = new GridPane();
        fruitGrid.setAlignment(Pos.CENTER);
        fruitGrid.addRow(0, fruitDistance, fruitSpinner);

        GridPane crimeGrid = new GridPane();
        crimeGrid.setAlignment(Pos.CENTER);
        crimeGrid.addRow(0, crimeDistance, crimeSpinner);
        //Colour Ranges
        Label colourRanges = new Label("Colour Ranges");

        GridPane valueRangeGrid = new GridPane();
        valueRangeGrid.setAlignment(Pos.CENTER);

        Label redLabel = new Label("Red (Max): ");
        Label orangeLabel = new Label("Orange (Max): ");
        Label yellowLabel = new Label("Yellow (Max): ");
        Label ygLabel = new Label("Yellow-Green (Max): ");

        valueRangeGrid.add(redLabel,0,0);
        valueRangeGrid.add(redSpinner, 1, 0);
        valueRangeGrid.add(orangeLabel,0,1);
        valueRangeGrid.add(orangeSpinner,1,1);
        valueRangeGrid.add(yellowLabel,0, 2);
        valueRangeGrid.add(yellowSpinner,1,2);
        valueRangeGrid.add(ygLabel,0,3);
        valueRangeGrid.add(ygSpinner,1,3);
        Label greenLabel = new Label("Green defaults to rest.");

        //Search button
        Button searchButton = new Button("Search");
        searchButton.disableProperty().bind(controller.getLoading());//Disables button while data is loading

        //Gets all the values in the input texts and gets new list
        searchButton.setOnAction(e->{
            String neighbourhood = neighbourhoodTextField.getText();
            String assessClass = assessClassCombo.getValue();
            String weedRadius;
            if(weedCheck.isSelected()) {
                weedRadius = weedSpinner.getValue().toString();
            } else {
                weedRadius = "";
            }
            String treeType = fruitTextField.getText();
            String treeRadius;
            if(treeType.isBlank()) {
                treeRadius = "";
            } else {
                treeRadius = fruitSpinner.getValue().toString();
            }
            String crimeType = crimeTextField.getText();
            String crimeRadius;
            if(crimeType.isBlank()) {
                crimeRadius = "";
            } else {
                crimeRadius = crimeSpinner.getValue().toString();
            }
            ArrayList<String> inputs = new ArrayList<>(Arrays.asList("", "", neighbourhood, assessClass, "", "", weedRadius,
                    treeType, treeRadius, crimeType, crimeRadius));
            Integer red = redSpinner.getValue();
            Integer orange = orangeSpinner.getValue();
            Integer yellow = yellowSpinner.getValue();
            Integer yg = ygSpinner.getValue();
            ArrayList<Integer> ranges = new ArrayList<>(Arrays.asList(red,orange,yellow,yg));
            updateMap(inputs, ranges);
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
                weedCheck,
                weedGrid,
                fruitLabel,
                fruitTextField,
                fruitGrid,
                crimeLabel,
                crimeTextField,
                crimeGrid,
                colourRanges,
                valueRangeGrid,
                greenLabel,
                hBoxResetSearch,
                loading
        );
        this.inputFields.setSpacing(10);
    }

    /**
     * Resets all input fields to blank
     */
    private void resetFields(){
        neighbourhoodTextField.setText("");
        assessClassCombo.getSelectionModel().selectFirst();
        weedCheck.setSelected(false);
        fruitTextField.setText("");
        crimeTextField.setText("");
        weedSpinner.getValueFactory().setValue(0);
        fruitSpinner.getValueFactory().setValue(0);
        crimeSpinner.getValueFactory().setValue(0);
        redSpinner.getValueFactory().setValue(200000);
        orangeSpinner.getValueFactory().setValue(400000);
        yellowSpinner.getValueFactory().setValue(600000);
        ygSpinner.getValueFactory().setValue(800000);

    }

    /**
     * Sets Columns and cell values as well binds table to model so table is automatically updated with changes
     * to the model
     */
    private void setMap(){
        String apiKey = loadApiKey();
        ArcGISRuntimeEnvironment.setApiKey(apiKey);

        mapView = new MapView();

        ArcGISMap map = new ArcGISMap(BasemapStyle.ARCGIS_TOPOGRAPHIC);

        //Set map on mapView and locate in Edmonton
        mapView.setMap(map);
        mapView.setViewpoint(new Viewpoint(53.5461, -113.4937, 72223.819286));

        //updateMap("","",List.of(200000,400000,600000,800000));
        this.mapVBox = new VBox(mapView);
        VBox.setVgrow(mapView, Priority.ALWAYS);


    }

    /**
     * Loads the ArcGIS API key from text file located in files directory
     * @return API key
     */
    private String loadApiKey() {
        try {
            Scanner keyScanner = new Scanner(Paths.get("files/apiKey.txt"));
            return keyScanner.nextLine();
        }
        catch(IOException e) {
            System.out.println("Couldn't load API Key.");
            return "";
        }
    }

    /**
     * Updates the points on the heat map based on filter criteria and colour coding
     * @param inputs List of filter criteria
     * @param ranges Assessed value cutoffs for colour coding of properties
     */
    private void updateMap(@NotNull List<String> inputs, List<Integer> ranges) {
        //Semaphore to prevent dots from drawing until list is updated with new data
        Semaphore sem = controller.getSem();
        mapView.getGraphicsOverlays().clear();
        boolean reset = true;

        // create a graphics overlay for properties and add it to the map view
        GraphicsOverlay graphicsOverlay = new GraphicsOverlay();
        mapView.getGraphicsOverlays().add(graphicsOverlay);

        SimpleMarkerSymbol.Style markerStyle = SimpleMarkerSymbol.Style.CIRCLE;
        float markerSize = 4f;

        for(String input : inputs) {
            if(!input.isBlank()) {
                this.controller.resetData(sem);
                loading.setVisible(true);
                reset = false;
                System.out.println(input);
                break;
            }
        }
        if(!reset){
            this.controller.filterData(inputs, sem);
            loading.setVisible(true);
        }

        //List<Property> data = model.getData();
        new Thread(()->{
            try {
                sem.acquire();
                loading.setVisible(false);
                List<Property> data = model.getData();
                for (Property property:data) {
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
                    Graphic graphic =   new Graphic(
                            new Point(longitude, latitude, SpatialReferences.getWgs84()),
                            new SimpleMarkerSymbol(markerStyle, color, markerSize));
                    graphic.getAttributes().put("NAME",property.getAccountNum());

                    graphicsOverlay.getGraphics().add(graphic);
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

    public void setLoading(){
        //image that shows
        Image image = new Image(new File("files/YouTube_loading_symbol_3_(transparent).gif").toURI().toString());
        loading = new ImageView(image);
        loading.setVisible(false);
    }
}
