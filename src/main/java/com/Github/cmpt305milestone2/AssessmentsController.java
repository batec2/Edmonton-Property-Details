package com.Github.cmpt305milestone2;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class AssessmentsController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}