/**
 * Copyright 2019 Esri
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy
 * of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package com.Github.cmpt305milestone2;

import com.Github.cmpt305milestone2.DAO.CsvPropertyAssessmentDAO;
import com.Github.cmpt305milestone2.Data.Property;
import com.Github.cmpt305milestone2.Data.PropertyAssessments;
import com.esri.arcgisruntime.ArcGISRuntimeEnvironment;
import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.geometry.SpatialReferences;
import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.BasemapStyle;
import com.esri.arcgisruntime.mapping.Viewpoint;
import com.esri.arcgisruntime.mapping.view.Graphic;
import com.esri.arcgisruntime.mapping.view.GraphicsOverlay;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.esri.arcgisruntime.symbology.SimpleLineSymbol;
import com.esri.arcgisruntime.symbology.SimpleMarkerSymbol;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class TestGIS extends Application {

    private CsvPropertyAssessmentDAO csvDAO = new CsvPropertyAssessmentDAO();
    private MapView mapView;

    public static void main(String[] args) {

        Application.launch(args);
    }

    @Override
    public void start(Stage stage) {

        // set the title and size of the stage and show it
        stage.setTitle("My Map App");
        stage.setWidth(800);
        stage.setHeight(700);
        stage.show();

        // create a JavaFX scene with a stack pane as the root node and add it to the scene
        StackPane stackPane = new StackPane();
        Scene scene = new Scene(stackPane);
        stage.setScene(scene);

        // Note: it is not best practice to store API keys in source code.
        // An API key is required to enable access to services, web maps, and web scenes hosted in ArcGIS Online.
        // If you haven't already, go to your developer dashboard to get your API key.
        // Please refer to https://developers.arcgis.com/java/get-started/ for more information
        String yourApiKey = "AAPK8c0d65d196244da28b26d6a3098f40582ZNJ5DF-VyaB3dmS_2wwjcZDrFAKwZ6HMw9iM-qhfl4J9KmdCk3-UU15Sa7ukWnx";
        ArcGISRuntimeEnvironment.setApiKey(yourApiKey);

        // create a MapView to display the map and add it to the stack pane
        mapView = new MapView();
        stackPane.getChildren().add(mapView);

        // create a map view to display the map and add it to the stack pane
        mapView = new MapView();
        stackPane.getChildren().add(mapView);
        ArcGISMap map = new ArcGISMap(BasemapStyle.ARCGIS_TOPOGRAPHIC);

        //Set map on mapView and locate in Edmonton
        mapView.setMap(map);
        mapView.setViewpoint(new Viewpoint(53.5461, -113.4937, 300000));

        // create a graphics overlay for propertiesand add it to the map view
        GraphicsOverlay graphicsOverlay = new GraphicsOverlay();
        mapView.getGraphicsOverlays().add(graphicsOverlay);


        // create a point geometry with a location and spatial reference
        Point point;
        SimpleMarkerSymbol.Style markerStyle = SimpleMarkerSymbol.Style.CIRCLE;
        int markerSize = 3;

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

        for(Property property : csvDAO.getAll()) {
            double longitude = Double.parseDouble(property.getLongitude());
            double latitude = Double.parseDouble(property.getLatitude());
            int value = property.getAssessedValue().intValue();
            if(value < 250000) {
                marker = markerSymbolRed;
            } else if (value < 500000) {
                marker = markerSymbolOrange;
            } else if (value < 750000) {
                marker = markerSymbolYellow;
            } else if (value < 1000000) {
                marker =markerSymbolYG;
            } else {
                marker = markerSymbolGreen;
            }
            point = new Point(longitude,latitude, SpatialReferences.getWgs84());

            // create a graphic with the point geometry and symbol
            Graphic pointGraphic = new Graphic(point, marker);

            // add the point graphic to the graphics overlay
            graphicsOverlay.getGraphics().add(pointGraphic);
        }
    }

    /**
     * Stops and releases all resources used in application.
     */
    @Override
    public void stop() {

        if (mapView != null) {
            mapView.dispose();
        }
    }
}
