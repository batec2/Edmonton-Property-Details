package com.Github.cmpt305milestone2.Controllers;

import com.Github.cmpt305milestone2.Model;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.*;
import java.util.List;

/**
 * Controller for a ChartsView object. Enables getting list of neighbourhoods for autocomplete text fields and input
 * checking, getting a bar graph, and getting a pie chart.
 * Author: Neal Hamacher
 */
public class ChartsController {
    private Model model;

    public ChartsController(Model model) {
        this.model = model;
    }

    /**
     * Gets a list of all neighbourhoods from the database
     * @return List of neighbourhoods
     */
    public List<String> getNeighbourhoods() {
        return model.getNeighbourhoods();
    }

    /**
     * Makes and returns a bar graph showing number property assessments falling into different ranges of assessed
     * values. Can be configured to show all properties, up to five neighbourhoods, or a particular assessment class.
     * @param neighbourhoods neighbourhoods to filter by, null if filtering by assessment class or showing all
     * @param assessmentClass assessment class to filter by, null if filtering by neighbourhoods or showing all
     * @return bar graph showing properties divided into assessed value ranges
     */
    public BarChart<String, Number> getBarGraph(List<String> neighbourhoods, String assessmentClass) {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);


        if(neighbourhoods != null) {
            for(String neighbourhood: neighbourhoods) {
                if(!neighbourhood.isBlank()) {
                    XYChart.Series series = new XYChart.Series();
                    series.setName(neighbourhood);
                    series.getData().add(new XYChart.Data("Less than $100,000",
                            model.countByValue(0, 100000, neighbourhood, null)));
                    series.getData().add(new XYChart.Data("$100,000 to $249,999",
                            model.countByValue(100000, 250000, neighbourhood, null)));
                    series.getData().add(new XYChart.Data("$250,000 to $499,999",
                            model.countByValue(250000, 500000, neighbourhood, null)));
                    series.getData().add(new XYChart.Data("$500,000 to $749,999",
                            model.countByValue(500000, 750000, neighbourhood, null)));
                    series.getData().add(new XYChart.Data("$750,000 to $999,999",
                            model.countByValue(750000, 1000000, neighbourhood, null)));
                    series.getData().add(new XYChart.Data("More than $1,000,000",
                            model.countByValue(1000000, null, neighbourhood, null)));
                    barChart.getData().add(series);
                    barChart.setTitle("PROPERTY VALUES - BY NEIGHBOURHOOD");
                }
            }
        } else {
            XYChart.Series series = new XYChart.Series();
            if (assessmentClass == null) {
                barChart.setTitle("PROPERTY VALUES - ALL");
            } else {
                barChart.setTitle("PROPERTY VALUES - BY ASSESSMENT CLASS");
                series.setName(assessmentClass);
            }
            series.getData().add(new XYChart.Data("Less than $100,000",
                    model.countByValue(0, 100000, null, assessmentClass)));
            series.getData().add(new XYChart.Data("$100,000 to $249,999",
                    model.countByValue(100000, 250000, null, assessmentClass)));
            series.getData().add(new XYChart.Data("$250,000 to $499,999",
                    model.countByValue(250000, 500000, null, assessmentClass)));
            series.getData().add(new XYChart.Data("$500,000 to $749,999",
                    model.countByValue(500000, 750000, null, assessmentClass)));
            series.getData().add(new XYChart.Data("$750,000 to $999,999",
                    model.countByValue(750000, 1000000, null, assessmentClass)));
            series.getData().add(new XYChart.Data("More than $1,000,000",
                    model.countByValue(1000000, null, null, assessmentClass)));
            barChart.getData().add(series);
        }
        return barChart;
    }

    /**
     * Builds and returns a pie chart showing property value percentages for all properties, a specific neighbourhood,
     * or a particular assessment class
     * @param neighbourhood neighbourhood to filter by, null if not applicable
     * @param assessmentClass assessment class to filter by, null if not applicable
     * @return pie chart of assessed val
     */
    public PieChart getPieChart(String neighbourhood, String assessmentClass) {
        int total = model.countByValue(0, null, null, null);
        ObservableList<PieChart.Data> chartData = FXCollections.observableArrayList(
                new PieChart.Data("Less than $100,000",
                        (double) model.countByValue(0, 100000, neighbourhood, assessmentClass)/total),
                new PieChart.Data("$100,000 to $249,999",
                        (double) model.countByValue(100000, 250000, neighbourhood, assessmentClass)/total),
                new PieChart.Data("$250,000 to $499,999",
                        (double) model.countByValue(250000, 500000, neighbourhood, assessmentClass)/total),
                new PieChart.Data("$500,000 to $749,999",
                        (double) model.countByValue(500000, 750000, neighbourhood, assessmentClass)/total),
                new PieChart.Data("$750,000 to $999,999",
                        (double) model.countByValue(750000, 1000000, neighbourhood, assessmentClass)/total),
                new PieChart.Data("More than $1,000,000",
                        (double) model.countByValue(1000000, null, neighbourhood, assessmentClass)/total));
        PieChart chart = new PieChart(chartData);
        StringBuilder title = new StringBuilder("PROPERTY VALUES - ");
        if(neighbourhood == null && assessmentClass == null) {
            title.append("ALL");
        }
        if (assessmentClass != null) {
            title.append(assessmentClass).append(" PROPERTIES");
        }
        if (neighbourhood != null) {
            title.append(neighbourhood).append(" NEIGHBOURHOOD");
        }
        chart.setTitle(title.toString());
        return chart;
    }
}
