package com.Github.cmpt305milestone2.Views;

import com.Github.cmpt305milestone2.HamburgerController;
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

public class HamburgerView{

    BorderPane sidebar;

    public HamburgerView(HamburgerController controller) {

        BorderPane mainContent = new BorderPane();
        sidebar = new BorderPane();

        JFXButton[] jfxButtons = {
                new JFXButton("Table View"),
                new JFXButton("Map View"),
                new JFXButton("Buy Drugs"),};

        JFXHamburger hamburger = new JFXHamburger();
        HamburgerNextArrowBasicTransition transition = new HamburgerNextArrowBasicTransition(hamburger);
        transition.setRate(-1);

        hamburger.setAlignment(Pos.CENTER_LEFT);
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
                    jfxButton.setContentDisplay(ContentDisplay.LEFT);
                }
            }
        });

        ScrollPane scrollPane = new ScrollPane();
        VBox vBox = new VBox();
        scrollPane.setStyle("-fx-background-color: #D3D3D3;");
        scrollPane.setContent(vBox);

        vBox.getChildren().add(hamburger);
        vBox.getChildren().addAll(jfxButtons);

        for (JFXButton jfxButton : jfxButtons) {
            jfxButton.setMaxWidth(Double.MAX_VALUE);
            jfxButton.setStyle("-fx-text-fill: black");
            jfxButton.setRipplerFill(Color.valueOf("#40E0D0"));
            VBox.setVgrow(jfxButton, Priority.ALWAYS);
            jfxButton.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            jfxButton.setOnMouseClicked(event -> {
                if(jfxButton.getText().equals("Table View")){
                    controller.setView('t');
                }
                else if(jfxButton.getText().equals("Map View")) {
                    controller.setView('m');
                }
            });
        }

        vBox.setFillWidth(true);

        Label labelHoverOverTest = new Label("Testing label");

        VBox vbox2 = new VBox();
        vbox2.getChildren().addAll(labelHoverOverTest);
        vbox2.setAlignment(Pos.CENTER_LEFT);

        mainContent.setCenter(vbox2);
        sidebar.setLeft(scrollPane);
        }

    public BorderPane getSidebar() {
        return sidebar;
    }

}