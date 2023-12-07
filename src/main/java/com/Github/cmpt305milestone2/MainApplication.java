package com.Github.cmpt305milestone2;

import atlantafx.base.theme.CupertinoDark;
import com.Github.cmpt305milestone2.Controllers.AssessmentsController;
import com.Github.cmpt305milestone2.Controllers.ChartsController;
import com.Github.cmpt305milestone2.Controllers.HamburgerController;
import com.Github.cmpt305milestone2.Views.AssessmentsView;
import com.Github.cmpt305milestone2.Views.ChartsView;
import com.Github.cmpt305milestone2.Views.HamburgerView;
import com.Github.cmpt305milestone2.Views.HeatMapView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Main function for the JavaFX application.
 * Author: Neal Hamacher, Crush Bate, Dan Simons
 */
public class MainApplication extends Application {

    Model assessmentsModel;
    AssessmentsView assessmentsView;
    AssessmentsController assessmentsController;
    HamburgerView burgerView;
    HamburgerController burgerController;
    HeatMapView mapView;
    ChartsView chartView;

    ChartsController chartsController;
    BorderPane mainPane;
    BorderPane rootPane;
    /**
     * Creates UI objects and adds them to the stage
     * @param stage
     * @throws IOException
     */

    /**
     * Initializes the models, viewers, and controllers for the JavaFX application
     * @param stage The stage returned from the JavaFX launch process
     * @throws IOException
     * @throws SQLException
     */
    @Override
    public void start(Stage stage) throws IOException, SQLException {
        //CSS themes from here -> https://github.com/mkpaz/atlantafx
        Application.setUserAgentStylesheet(new CupertinoDark().getUserAgentStylesheet());
        rootPane = new BorderPane();

        assessmentsModel = new Model();
        assessmentsController = new AssessmentsController(assessmentsModel);
        assessmentsView = new AssessmentsView(assessmentsController, assessmentsModel);

        mapView = new HeatMapView(assessmentsController, assessmentsModel);

        chartsController = new ChartsController(assessmentsModel);
        chartView = new ChartsView(chartsController);

        burgerController = new HamburgerController(mainPane, assessmentsView, mapView, chartView, stage);
        burgerView = new HamburgerView(burgerController);

        mainPane = assessmentsView.asBorderPane();

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

    /**
     * Launches the JavaFX Application
     * @param args Command line args
     */
    public static void main(String[] args) {
        launch();
    }

    /**
     * Stops the JavaFX application
     */
    @Override
    public void stop() {
        this.mapView.destroyMapView();
    }
}

