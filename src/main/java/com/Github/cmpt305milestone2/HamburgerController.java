package com.Github.cmpt305milestone2;

import javafx.beans.InvalidationListener;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class HamburgerController{
    BorderPane mainPane;
    AssessmentsView aView;
    HeatMapView mapView;

    BorderPane rootPane;

    Scene scene;

    Stage stage;

    public HamburgerController(BorderPane mainPane, AssessmentsView aView, HeatMapView mapView, Stage stage) {
        this.mainPane = mainPane;
        this.aView = aView;
        this.mapView = mapView;
        this.stage = stage;
    }

    public void setView(char option) {
        if(option == 't') {
            mainPane = aView.asBorderPane();
        } else if (option == 'm') {
            mainPane = mapView.asBorderPane();
        }
        rootPane.setCenter(mainPane);
        stage.setScene(scene);
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public void setRootPane(BorderPane pane) {
        this.rootPane = pane;
    }
}
