package com.Github.cmpt305milestone2;

import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class MapView {
    private BorderPane view;

    private AssessmentsController aCont;

    public MapView(AssessmentsController aCont) {
        this.aCont = aCont;
        view = new BorderPane();
        Label placeHolder = new Label("Under Construction");
        view.setCenter(new VBox(placeHolder));
    }

    public BorderPane asBorderPane() {
        return this.view;
    }
}
