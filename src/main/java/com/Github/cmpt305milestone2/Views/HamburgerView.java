package com.Github.cmpt305milestone2.Views;

import com.Github.cmpt305milestone2.Controllers.HamburgerController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerNextArrowBasicTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class HamburgerView{

    BorderPane sidebar;

    public HamburgerView(HamburgerController controller) {
        double iconSize = 40;
        BorderPane mainContent = new BorderPane();
        sidebar = new BorderPane();
        ImageView tableIcon = new ImageView("file:files/icon_table.png");
        tableIcon.setFitWidth(iconSize);
        tableIcon.setFitHeight(iconSize);
        String tableString = "Property Assessments Table";

        ImageView mapIcon = new ImageView("file:files/icon_map.png");
        mapIcon.setFitWidth(iconSize);
        mapIcon.setFitHeight(iconSize);
        String mapString = "Interactive Map View";

        ImageView chartIcon = new ImageView("file:files/icon_chart.png");
        chartIcon.setFitWidth(iconSize);
        chartIcon.setFitHeight(iconSize);
        String chartString = "Graphs and Charts";

        JFXButton[] jfxButtons = {
                new JFXButton(tableString, tableIcon),
                new JFXButton(mapString, mapIcon),
                new JFXButton(chartString, chartIcon)};

        Label newline = new Label("");
        Label copyright = new Label("\u00a9 2023 Dan Simons Productions");
        copyright.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        copyright.setStyle("-fx-text-fill: white");
        JFXHamburger hamburger = new JFXHamburger();
        HamburgerNextArrowBasicTransition transition = new HamburgerNextArrowBasicTransition(hamburger);
        transition.setRate(-1);

        hamburger.setAlignment(Pos.CENTER);
        hamburger.setPadding(new Insets(5));
        hamburger.setStyle("-fx-background-color: #2D2633;");

        hamburger.setOnMouseClicked(event -> {
            transition.setRate(transition.getRate() * -1);
            transition.play();
            if (transition.getRate() == -1) {
                for (JFXButton jfxButton : jfxButtons) {
                    jfxButton.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                    jfxButton.setAlignment(Pos.CENTER_LEFT);
                    jfxButton.setStyle("-fx-text-fill: white");
                }
                copyright.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            } else {
                for (JFXButton jfxButton : jfxButtons) {
                    jfxButton.setContentDisplay(ContentDisplay.LEFT);
                    jfxButton.setAlignment(Pos.CENTER_LEFT);
                    jfxButton.setStyle("-fx-text-fill: white");
                }
                copyright.setContentDisplay(ContentDisplay.LEFT);
                copyright.setAlignment(Pos.CENTER);
            }
        });

        ScrollPane scrollPane = new ScrollPane();
        VBox vBox = new VBox();
        scrollPane.setStyle("-fx-background-color: #2D2633;");
        scrollPane.setContent(vBox);

        vBox.getChildren().add(hamburger);
        vBox.getChildren().addAll(jfxButtons);
        vBox.getChildren().add(newline);
        vBox.getChildren().add(copyright);

        for (JFXButton jfxButton : jfxButtons) {
            jfxButton.setMaxWidth(Double.MAX_VALUE);
            jfxButton.setStyle("-fx-text-fill: black");
            jfxButton.setRipplerFill(Color.valueOf("#40E0D0"));
            VBox.setVgrow(jfxButton, Priority.ALWAYS);
            jfxButton.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            jfxButton.setOnMouseClicked(event -> {
                if(jfxButton.getText().equals(tableString)){
                    controller.setView('t');
                }
                else if(jfxButton.getText().equals(mapString)) {
                    controller.setView('m');
                }
                if(jfxButton.getText().equals(chartString)) {
                    controller.setView('c');
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