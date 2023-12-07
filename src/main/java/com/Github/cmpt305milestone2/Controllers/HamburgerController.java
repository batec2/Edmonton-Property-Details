package com.Github.cmpt305milestone2.Controllers;

import com.Github.cmpt305milestone2.Views.AssessmentsView;
import com.Github.cmpt305milestone2.Views.ChartsView;
import com.Github.cmpt305milestone2.Views.HeatMapView;
import javafx.beans.InvalidationListener;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Controller for the hamburger meny
 * Authors: Crush Bate, Neal Hamacher, Dan Simons
 */
public class HamburgerController{
    BorderPane mainPane;
    AssessmentsView aView;
    HeatMapView mapView;
    ChartsView chartsView;
    BorderPane rootPane;

    Scene scene;

    Stage stage;

    /**
     * Initializes the hamburger controller, this as well as the main JavaFX stage so that it can change what is
     * being displayed in the main pane.
     * @param mainPane The currently showing pane in the main area
     * @param aView assesments viewer
     * @param mapView map viewer
     * @param chartsView charts viewer
     * @param stage stage for the JavaFX application
     */
    public HamburgerController(BorderPane mainPane, AssessmentsView aView, HeatMapView mapView, ChartsView chartsView,
                               Stage stage) {
        this.mainPane = mainPane;
        this.aView = aView;
        this.mapView = mapView;
        this.chartsView = chartsView;
        this.stage = stage;
    }

    /**
     * Sets the view showing up in the main border pane
     * @param option Selected view for the main pane
     */
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

    /**
     * Resets the JavaFX scene when a selection is made from the menu
     * @param scene The JavaFX application's scene
     */
    public void setScene(Scene scene) {
        this.scene = scene;
    }

    /**
     * Sets the root pane (the main border pane that contains the hamburger menu and the currently displayed
     * pane in the main area)
     * @param pane The root pane
     */
    public void setRootPane(BorderPane pane) {
        this.rootPane = pane;
    }
}
