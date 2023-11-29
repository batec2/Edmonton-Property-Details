package com.Github.cmpt305milestone2;

import atlantafx.base.theme.CupertinoDark;
import atlantafx.base.theme.NordDark;
import atlantafx.base.theme.PrimerDark;
import com.Github.cmpt305milestone2.Views.AssessmentsView;
import com.esri.arcgisruntime.ArcGISRuntimeEnvironment;
import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.BasemapStyle;
import com.esri.arcgisruntime.mapping.Viewpoint;
import com.esri.arcgisruntime.mapping.view.MapView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;


public class AssessmentsApplication extends Application {

    AssessmentsModel model;
    AssessmentsView view;
    AssessmentsController controller;


    /**
     * Creates UI objects and adds them to the stage
     * @param stage
     * @throws IOException
     */

    @Override
    public void start(Stage stage) throws IOException, SQLException {
        //CSS themes from here -> https://github.com/mkpaz/atlantafx
        Application.setUserAgentStylesheet(new CupertinoDark().getUserAgentStylesheet());
        model = new AssessmentsModel();
        controller = new AssessmentsController(model);
        view = new AssessmentsView(controller,model);
        stage.setTitle("Edmonton Property Assessments");
        Scene scene = new Scene(view.asParent());
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }
    public static void main(String[] args) {

        launch();
    }

}

