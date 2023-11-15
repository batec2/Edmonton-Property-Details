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
        Scene scene = new Scene(view.asParent());
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }
}

