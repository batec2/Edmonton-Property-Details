package com.Github.cmpt305milestone2;

import atlantafx.base.theme.PrimerDark;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;


public class AssessmentsApplication extends Application {

    AssessmentsModel model;
    AssessmentsView view;
    AssessmentsController controller;

    @Override
    public void start(Stage stage) throws IOException {
        //CSS themes from here -> https://github.com/mkpaz/atlantafx
        Application.setUserAgentStylesheet(new PrimerDark().getUserAgentStylesheet());
        model = new AssessmentsModel();
        controller = new AssessmentsController(model);
        view = new AssessmentsView(controller,model);

        stage.setTitle("Property Assessments");
        Scene scene2 = new Scene(view.asParent());
        stage.setScene(scene2);
        stage.setMaximized(true);
        stage.show();
        /*
        new Thread(()->{
            csvDao = new CsvPropertyAssessmentDAO("Property_Assessment_Data_2023.csv");
            apiToCSVToggle.setDisable(false);
        }).start();

         */
    }
    public static void main(String[] args) {
        launch();
    }
}

