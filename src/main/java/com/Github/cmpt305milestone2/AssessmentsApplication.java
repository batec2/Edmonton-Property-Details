package com.Github.cmpt305milestone2;

import atlantafx.base.theme.Dracula;
import atlantafx.base.theme.PrimerDark;
import com.Github.cmpt305milestone2.DAO.ApiPropertyAssessmentDAO;
import com.Github.cmpt305milestone2.Data.Property;
import io.github.palexdev.materialfx.controls.MFXPaginatedTableView;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.css.themes.MFXThemeManager;
import io.github.palexdev.materialfx.css.themes.Themes;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class AssessmentsApplication extends Application {
    TableView table;
    ObservableList<Property> data;

    ApiPropertyAssessmentDAO apiDao;

    @Override
    public void start(Stage stage) throws IOException {
        //CSS themes from here -> https://github.com/mkpaz/atlantafx
        Application.setUserAgentStylesheet(new Dracula().getUserAgentStylesheet());
        apiDao = new ApiPropertyAssessmentDAO();

        BorderPane root = new BorderPane();
        Scene scene = new Scene(root);
        setTable();
        root.setCenter(table);

        /*
        Pagination pagination = new Pagination();
        pagination.setPageCount(10);
        pagination.setMaxPageIndicatorCount(5);
        root.setBottom(pagination);
        */

        Button button = new Button("stuff");
        button.setOnAction(e-> {
            //FXCollections.observableArrayList(new ApiPropertyAssessmentDAO().getByNeightbourhood("MEADOWLARK PARK")
            data.clear();
            data.addAll(apiDao.getByNeightbourhood("MEADOWLARK PARK"));
            System.out.println(data);
        });

        Button button1 = new Button("Prev");
        button1.setOnAction(e->{
            data.clear();
            apiDao.setOffset(apiDao.getOffset()!=0? apiDao.getOffset()-100 : apiDao.getOffset());
            data.setAll(apiDao.pageCurrentQuery());
        });

        Button button2 = new Button("Next");
        button2.setOnAction(e->{
            data.clear();
            apiDao.setOffset(apiDao.getOffset()+100);
            data.setAll(apiDao.pageCurrentQuery());
        });

        ArrayList<String> filters = new ArrayList<>();
        filters.add("Get All");
        filters.add("Neighbourhood");
        filters.add("Assessment Class");
        filters.add("Ward");

        TextField filterItem = new TextField();
        filterItem.setDisable(true);

        ComboBox filterBox = new ComboBox(FXCollections.observableArrayList(filters));
        filterBox.setOnAction(e->{
            switch(filterBox.getValue().toString()){
                case "Get All"->filterItem.setDisable(true);
                case "Neighbourhood"->filterItem.setDisable(false);
                case "Assessment Class"->filterItem.setDisable(false);
                case "Ward"->filterItem.setDisable(false);
            }
        });
        filterBox.getSelectionModel().selectFirst();

        Button filterButton = new Button("Filter");

        filterButton.setOnAction(e->{
            switch(filterBox.getValue().toString()){
                case "Get All"->{
                    data.clear();
                    data.setAll(apiDao.getAll());
                }
                case "Neighbourhood"->{
                    if (!filterItem.getText().isBlank()) {
                        data.clear();
                        data.setAll(apiDao.getByNeightbourhood(filterItem.getText()));
                    }
                }
                case "Assessment Class"->{
                    if (!filterItem.getText().isBlank()) {
                        data.clear();
                        data.setAll(apiDao.getByAssessmentClass(filterItem.getText()));
                    }
                }
                case "Ward" ->{
                    if (!filterItem.getText().isBlank()) {
                        System.out.println("Ward");
                    }
                }
            }
        });

        filterButton.setAlignment(Pos.BASELINE_RIGHT);

        HBox hBoxInput = new HBox(filterItem,filterBox);
        hBoxInput.setSpacing(10);

        HBox hBoxFilter = new HBox(filterButton);
        hBoxFilter.setAlignment(Pos.BOTTOM_RIGHT);

        VBox vBoxLeft = new VBox(hBoxInput,hBoxFilter);
        vBoxLeft.setSpacing(10);
        vBoxLeft.setPadding(new Insets(10));

        root.setLeft(vBoxLeft);

        HBox hBox = new HBox(button1,button2);
        hBox.setAlignment(Pos.BASELINE_CENTER);
        hBox.setPadding(new Insets(10));
        hBox.setSpacing(10);
        root.setBottom(hBox);

        stage.setTitle("Property Assessments");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }


    public static void main(String[] args) {
        launch();
    }

    void pageTable(int index){
        data.clear();
        apiDao.setOffset(index*100);
        data.setAll(apiDao.getAll());
    }

    void setTable(){
        List<Property> properties = apiDao.getAll();
        data = FXCollections.observableArrayList(properties);
        table = new TableView();

        TableColumn<Property, String> accountNum = new TableColumn<>("Account Number");
        accountNum.setMinWidth(120);
        accountNum.setCellValueFactory(new PropertyValueFactory<>("accountNum"));

        TableColumn<Property, String> suite = new TableColumn<>("Suite");
        suite.setMinWidth(120);
        suite.setCellValueFactory(new PropertyValueFactory<>("suite"));

        TableColumn<Property, String> houseNum = new TableColumn<>("House Number");
        houseNum.setMinWidth(120);
        houseNum.setCellValueFactory(new PropertyValueFactory<>("houseNum"));

        TableColumn<Property, String> streetName = new TableColumn<>("Street Name");
        streetName.setMinWidth(120);
        streetName.setCellValueFactory(new PropertyValueFactory<>("streetName"));

        TableColumn<Property, String> garage = new TableColumn<>("Garage");
        garage.setMinWidth(120);
        garage.setCellValueFactory(new PropertyValueFactory<>("garage"));

        TableColumn<Property, String> neighbourhoodID = new TableColumn<>("Neighbourhood ID");
        neighbourhoodID.setMinWidth(120);
        neighbourhoodID.setCellValueFactory(new PropertyValueFactory<>("neighbourhoodID"));

        TableColumn<Property, String> neighbourhood = new TableColumn<>("Neighbourhood");
        neighbourhood.setMinWidth(120);
        neighbourhood.setCellValueFactory(new PropertyValueFactory<>("neighbourhood"));

        TableColumn<Property, String> ward = new TableColumn<>("Ward");
        ward.setMinWidth(120);
        ward.setCellValueFactory(new PropertyValueFactory<>("ward"));

        TableColumn<Property, BigDecimal> assessedValue = new TableColumn<>("Assessed Value");
        assessedValue.setMinWidth(120);
        assessedValue.setCellValueFactory(new PropertyValueFactory<>("assessedValue"));

        TableColumn<Property, String> latitude = new TableColumn<>("Latitude");
        latitude.setMinWidth(120);
        latitude.setCellValueFactory(new PropertyValueFactory<>("latitude"));

        TableColumn<Property, String> longitude = new TableColumn<>("Longitude");
        longitude.setMinWidth(120);
        longitude.setCellValueFactory(new PropertyValueFactory<>("longitude"));

        TableColumn<Property, String> point = new TableColumn<>("Point");
        point.setMinWidth(120);
        point.setCellValueFactory(new PropertyValueFactory<>("point"));

        TableColumn<Property, String> assessment1 = new TableColumn<>("Assessment 1");
        assessment1.setMinWidth(120);
        assessment1.setCellValueFactory(new PropertyValueFactory<>("assessment1"));

        TableColumn<Property, String> assessmentPercent1 = new TableColumn<>("Assessment Percent1");
        assessmentPercent1.setMinWidth(120);
        assessmentPercent1.setCellValueFactory(new PropertyValueFactory<>("assessmentPercent1"));

        TableColumn<Property, String> assessment2 = new TableColumn<>("Assessment 2");
        assessment2.setMinWidth(120);
        assessment2.setCellValueFactory(new PropertyValueFactory<>("assessment2"));

        TableColumn<Property, String> assessmentPercent2 = new TableColumn<>("Assessment Percent2");
        assessmentPercent2.setMinWidth(120);
        assessmentPercent2.setCellValueFactory(new PropertyValueFactory<>("assessmentPercent2"));

        TableColumn<Property, String> assessment3 = new TableColumn<>("Assessment 3");
        assessment3.setMinWidth(120);
        assessment3.setCellValueFactory(new PropertyValueFactory<>("assessment3"));

        TableColumn<Property, String> assessmentPercent3 = new TableColumn<>("Assessment Percent3");
        assessmentPercent3.setMinWidth(120);
        assessmentPercent3.setCellValueFactory(new PropertyValueFactory<>("assessmentPercent3"));

        table.getColumns().setAll(accountNum,suite,houseNum,streetName,garage,
                neighbourhoodID,neighbourhood,ward,assessedValue,latitude,longitude,point,
                assessment1,assessmentPercent1,assessment2,assessmentPercent2,assessment3,assessmentPercent3);
        table.setItems(data);
    }
}

