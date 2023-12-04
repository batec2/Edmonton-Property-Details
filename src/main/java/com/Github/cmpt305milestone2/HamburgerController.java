package com.Github.cmpt305milestone2;

import com.Github.cmpt305milestone2.Views.AssessmentsView;
import com.Github.cmpt305milestone2.Views.ChartsView;
import com.Github.cmpt305milestone2.Views.HeatMapView;
import javafx.beans.InvalidationListener;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class HamburgerController{
    BorderPane mainPane;
    AssessmentsView aView;
    HeatMapView mapView;
    ChartsView chartsView;
    BorderPane rootPane;

    Scene scene;

    Stage stage;

    public HamburgerController(BorderPane mainPane, AssessmentsView aView, HeatMapView mapView, ChartsView chartsView, Stage stage) {
        this.mainPane = mainPane;
        this.aView = aView;
        this.mapView = mapView;
        this.chartsView = chartsView;
        this.stage = stage;
    }

    public void setView(char option) {
        if(option == 't') {
            mainPane = aView.asBorderPane();
        } else if (option == 'm') {
            mainPane = mapView.asBorderPane();
        } else if (option == 'c') {
            mainPane = chartsView.asBorderPane();
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
