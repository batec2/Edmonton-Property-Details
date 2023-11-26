package com.Github.cmpt305milestone2;

import javafx.beans.InvalidationListener;
import javafx.scene.layout.BorderPane;

public class HamburgerController{
    BorderPane mainPane;
    AssessmentsView aView;
    HeatMapView mapView;

    public HamburgerController(BorderPane mainPane, AssessmentsView aView, HeatMapView mapView) {
        this.mainPane = mainPane;
        this.aView = aView;
        this.mapView = mapView;
    }

    public void setTableView() {
        mainPane = aView.asBorderPane();
    }

    public void setMapView() {
        mainPane = mapView.asBorderPane();
    }
}
