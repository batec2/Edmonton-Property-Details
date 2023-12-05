package com.Github.cmpt305milestone2.Controllers;

import com.Github.cmpt305milestone2.AssessmentsModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.*;
import java.util.List;

public class ChartsController {
    private AssessmentsModel model;

    public ChartsController(AssessmentsModel model) {
        this.model = model;
    }

    public List<String> getNeighbourhoods() {
        return model.getNeighbourhoods();
    }

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
                }
            }
        } else {
            XYChart.Series series = new XYChart.Series();
            if (assessmentClass == null) {
                series.setName("All Property Values");
            } else {
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
        if(neighbourhood == null && assessmentClass == null) {
            chart.setTitle("All Property Values");
        } else if (assessmentClass == null) {
            chart.setTitle(neighbourhood);
        } else {
            chart.setTitle(assessmentClass);
        }
        return chart;
    }
}
