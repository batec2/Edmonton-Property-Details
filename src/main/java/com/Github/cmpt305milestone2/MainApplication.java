package com.Github.cmpt305milestone2;

import atlantafx.base.theme.CupertinoDark;
import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;


public class MainApplication extends Application {

    AssessmentsModel assModel;
    AssessmentsView assView;
    AssessmentsController assController;
    HamburgerView burgerView;
    HamburgerController burgerController;
    HeatMapView mapView;

    BorderPane mainPane;
    BorderPane rootPane;
    /**
     * Creates UI objects and adds them to the stage
     * @param stage
     * @throws IOException
     */

    @Override
    public void start(Stage stage) throws IOException {
        //CSS themes from here -> https://github.com/mkpaz/atlantafx
        Application.setUserAgentStylesheet(new CupertinoDark().getUserAgentStylesheet());
        rootPane = new BorderPane();
        assModel = new AssessmentsModel();
        assController = new AssessmentsController( assModel);
        assView = new AssessmentsView(assController, assModel);
        mapView = new HeatMapView(assController, assModel);
        mainPane = mapView.asBorderPane();
        burgerController = new HamburgerController(mainPane, assView, mapView);
        burgerView = new HamburgerView(burgerController);
        stage.setTitle("Edmonton Property Assessments");
        rootPane.setLeft(burgerView.sidebar);
        rootPane.setCenter(mainPane);
        //rootPane.getChildren().addAll(mainPane, burgerView.sidebar);
        Scene scene = new Scene(rootPane);
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void stop() {
        this.mapView.destroyMapView();
    }
}

