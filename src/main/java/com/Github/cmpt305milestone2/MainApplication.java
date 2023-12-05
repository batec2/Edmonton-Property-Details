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


public class MainApplication extends Application {

    AssessmentsModel assessmentsModel;
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

    @Override
    public void start(Stage stage) throws IOException, SQLException {
        //CSS themes from here -> https://github.com/mkpaz/atlantafx
        Application.setUserAgentStylesheet(new CupertinoDark().getUserAgentStylesheet());
        rootPane = new BorderPane();

        assessmentsModel = new AssessmentsModel();
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

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void stop() {
        this.mapView.destroyMapView();
    }
}

