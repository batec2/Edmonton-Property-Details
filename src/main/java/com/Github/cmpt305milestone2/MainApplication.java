package com.Github.cmpt305milestone2;

import atlantafx.base.theme.CupertinoDark;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MainApplication extends Application {

    AssessmentsModel assModel;
    AssessmentsView assView;
    AssessmentsController assController;
    HamburgerView burgerView;
    HamburgerController burgerController;
    MapView mapView;

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
        mainPane = assView.asBorderPane();
        mapView = new MapView(assController);
        burgerController = new HamburgerController(mainPane, assView, mapView);
        burgerView = new HamburgerView(burgerController);
        ObservableList<BorderPane> mainPanes = FXCollections.observableList(List.of(mainPane));
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
}

