package com.Github.cmpt305milestone2;

import atlantafx.base.theme.CupertinoDark;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;


public class MainApplication extends Application {

    AssessmentsModel model;
    AssessmentsView view;
    AssessmentsController controller;

    StackPane root;
    /**
     * Creates UI objects and adds them to the stage
     * @param stage
     * @throws IOException
     */

    @Override
    public void start(Stage stage) throws IOException {
        //CSS themes from here -> https://github.com/mkpaz/atlantafx
        Application.setUserAgentStylesheet(new CupertinoDark().getUserAgentStylesheet());
        root = new StackPane();
        model = new AssessmentsModel();
        controller = new AssessmentsController(model);
        view = new AssessmentsView(controller,model);
        stage.setTitle("Edmonton Property Assessments");
        root.getChildren().add(view.asParent());
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }
    public static void main(String[] args) {

        launch();
    }
}

