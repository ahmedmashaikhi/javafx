package sample;

import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.Random;

public class Controller extends BorderPane {
    private NumberAxis yAxis = new NumberAxis(0,10,1);
    private CategoryAxis xAxis = new CategoryAxis();
    private BarChart<String,Number> barChartEx = new BarChart<>(xAxis,yAxis);
    private ObservableList<XYChart.Data<String,Number>> seriesData = FXCollections.observableArrayList(parm -> new Observable[]{parm.YValueProperty()});
    private XYChart.Series<String,Number> series = new XYChart.Series<>(seriesData);
    private Button plotButton = new Button("Plot");
    private final Random random = new Random();
    public Controller()
    {
        plotButton.setOnAction(this::plotNewData);
        series.setName("Sales");
        series.getData().addAll(
                createNewData("Apple",random.ints(100,1000).findFirst().getAsInt(),Color.RED),
                createNewData("Samsung",random.ints(100,1000).findFirst().getAsInt(),Color.GREEN),
                createNewData("Huwaii",random.ints(100,1000).findFirst().getAsInt(),Color.PURPLE)
                 );
        barChartEx.setAnimated(false);
        yAxis.setAutoRanging(false);
        yAxis.setTickLabelFont(Font.font("Arial",FontWeight.BOLD,11));
        xAxis.setTickLabelFont(Font.font("Arial",FontWeight.BOLD,11));
        yAxis.tickUnitProperty().bind(yAxis.upperBoundProperty().subtract(yAxis.lowerBoundProperty()).divide(10));
        yAxis.upperBoundProperty().bind(Bindings.createDoubleBinding(()-> 1.15 * seriesData.stream().mapToDouble(value -> value.getYValue().doubleValue()).max().orElse(10.0),seriesData));
        barChartEx.setTitle("Mobiles Companies Sales");
        barChartEx.getData().add(series);
        final HBox topPanel= new HBox(5);
        topPanel.setAlignment(Pos.CENTER_LEFT);
        topPanel.setPadding(new Insets(10));
        topPanel.getChildren().addAll(plotButton);
        setTop(topPanel);
        setCenter(barChartEx);
    }

    private XYChart.Data<String, Number> createNewData(String xValue, double yValue, Color barColor) {
        XYChart.Data<String,Number> data = new XYChart.Data<>(xValue,yValue,barColor);
        Label label = new Label();
        label.setFont(Font.font("Arial", FontWeight.BOLD,10));
        label.textProperty().bind(data.YValueProperty().asString());
        StackPane pane = new StackPane();
        pane.setAlignment(Pos.TOP_CENTER);
        StackPane.setAlignment(label,Pos.TOP_CENTER);
        pane.setStyle("-fx-background-color:rgb("+barColor.getRed() * 255+ ","+barColor.getGreen() * 255+ ","+barColor.getBlue() * 255+ ")");
        pane.setPadding(new Insets(-1.2 * (label.getFont().getSize()),0,0,0));
        pane.getChildren().add(label);
        data.setNode(pane);
        return data;
    };

    private void plotNewData(ActionEvent event)
    {
     series.getData().forEach(data ->data.setYValue(random.ints(100,1000).findFirst().getAsInt()) );
    }
}
