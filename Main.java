package sample;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Random;

public class Main extends Application {
    private XYChart.Series<Number,Number> hourDataSeries;
    private NumberAxis xAxis;
    private Timeline animation;
    private double hours = 0;
    private double minutes = 0;
    private double timeInHours = 0;
    private double prevY = 10;
    private double y = 10;


    private Random random = new Random();
//    @Override
//    public void start(Stage primaryStage) throws Exception{
//        xAxis = new NumberAxis(0, 24, 3);
//        final NumberAxis yAxis = new NumberAxis(0, 100, 10);
//        final LineChart<Number,Number> lc = new LineChart<Number,Number>(xAxis, yAxis);
//
//        lc.setCreateSymbols(false);
//        lc.setAnimated(false);
//        lc.setLegendVisible(false);
//        lc.setTitle("ACME Company Stock");
//
//        xAxis.setLabel("Time");
//        xAxis.setForceZeroInRange(false);
//        yAxis.setLabel("Share Price");
//        yAxis.setTickLabelFormatter(new NumberAxis.DefaultFormatter(yAxis, "$", null));
//
//        hourDataSeries = new XYChart.Series<Number,Number>();
//        hourDataSeries.setName("Hourly Data");
//        hourDataSeries.getData().add(new XYChart.Data<Number,Number>(timeInHours, prevY));
//        lc.getData().add(hourDataSeries);
//        BorderPane root = new BorderPane();
//        root.setCenter(lc);
//        primaryStage.setTitle("Test");
//        primaryStage.setScene(new Scene(root, 600, 400));
//        primaryStage.show();
//        animation = new Timeline();
//        animation.getKeyFrames().add(new KeyFrame(Duration.millis(1000 / 50), new EventHandler<ActionEvent>() {
//            @Override public void handle(ActionEvent actionEvent) {
//                // 6 minutes data per frame
//                for(int count = 0; count < 10; count++) {
//                    nextTime();
//                    plotTime();
//                }
//            }
//        }));
//        animation.setCycleCount(Animation.INDEFINITE);
//        animation.play();
//    }


    private  void nextTime() {
        if (minutes == 59) {
            hours++;
            minutes = 0;
        } else {
            minutes++;
        }
        timeInHours = hours + ((1d/60d) * minutes);
    }

    private void plotTime() {
        if ((timeInHours % 1) == 0) {
            // change of hour
            double oldY = y;
            y = prevY - 10 + (Math.random() * 20);
            prevY = oldY;
            if (y < 10 || y > 90) y = y - 10 + (Math.random() * 20);
            hourDataSeries.getData().add(new XYChart.Data<Number, Number>(timeInHours, prevY));
            // after 25hours delete old data
            if (timeInHours > 25) hourDataSeries.getData().remove(0);
            // every hour after 24 move range 1 hour
            if (timeInHours > 24) {
                xAxis.setLowerBound(xAxis.getLowerBound() + 1);
                xAxis.setUpperBound(xAxis.getUpperBound() + 1);
            }
        }
    }

    @Override
    public void start(Stage stage) throws Exception {

        stage.setScene(new Scene(new Controller(),600,400));
        stage.show();
    }
}
