package com.Github.cmpt305milestone2;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerNextArrowBasicTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class HamburgerView extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        StackPane root = new StackPane();
        BorderPane mainContent = new BorderPane();
        BorderPane sidebar = new BorderPane();

        JFXButton[] jfxButtons = {
                new JFXButton("Some text"),
                new JFXButton("Some text"),
                new JFXButton("Some text"),};

        JFXHamburger hamburger = new JFXHamburger();
        HamburgerNextArrowBasicTransition transition = new HamburgerNextArrowBasicTransition(hamburger);
        transition.setRate(-1);

        hamburger.setAlignment(Pos.CENTER_RIGHT);
        hamburger.setPadding(new Insets(5));
        hamburger.setStyle("-fx-background-color: #fff;");

        hamburger.setOnMouseClicked(event -> {
            transition.setRate(transition.getRate() * -1);
            transition.play();
            if (transition.getRate() == -1) {
                for (JFXButton jfxButton : jfxButtons) {
                    jfxButton.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                }
            } else {
                for (JFXButton jfxButton : jfxButtons) {
                    jfxButton.setContentDisplay(ContentDisplay.RIGHT);
                }
            }
        });

        ScrollPane scrollPane = new ScrollPane();
        VBox vBox = new VBox();
        scrollPane.setContent(vBox);

        vBox.getStyleClass().add("content_scene_right");
        vBox.getChildren().add(hamburger);
        vBox.getChildren().addAll(jfxButtons);

        for (JFXButton jfxButton : jfxButtons) {
            jfxButton.setMaxWidth(Double.MAX_VALUE);
            jfxButton.setRipplerFill(Color.valueOf("#40E0D0"));
            VBox.setVgrow(jfxButton, Priority.ALWAYS);
            jfxButton.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        }
        vBox.setFillWidth(true);

        Label labelHoverOverTest = new Label("Testing label");

        VBox vbox2 = new VBox();
        vbox2.getChildren().addAll(labelHoverOverTest);
        vbox2.setAlignment(Pos.CENTER_RIGHT);

        mainContent.setCenter(vbox2);
        sidebar.setRight(scrollPane);

        root.getChildren().addAll(mainContent, sidebar);

        Scene scene = new Scene(root);

        primaryStage.setMinWidth(400);
        primaryStage.setMinHeight(400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}