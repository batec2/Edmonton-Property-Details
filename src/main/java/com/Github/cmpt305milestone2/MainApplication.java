package com.Github.cmpt305milestone2;

import atlantafx.base.theme.CupertinoDark;
import com.Github.cmpt305milestone2.Views.AssessmentsView;
import com.Github.cmpt305milestone2.Views.HamburgerView;
import com.Github.cmpt305milestone2.Views.HeatMapView;
import javafx.application.Application;
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
        assController = new AssessmentsController(assModel);
        assView = new AssessmentsView(assController, assModel);
        mapView = new HeatMapView(assController, assModel);
        mainPane = assView.asBorderPane();
        burgerController = new HamburgerController(mainPane, assView, mapView, stage);
        burgerView = new HamburgerView(burgerController);
        stage.setTitle("Edmonton Property Assessments");
        rootPane.setLeft(burgerView.getSidebar());
        rootPane.setCenter(mainPane);
        Scene scene = new Scene(rootPane);
        burgerController.setScene(scene);
        burgerController.setRootPane(rootPane);
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

