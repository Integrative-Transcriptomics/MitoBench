package view.visualizations;

import Logging.LogClass;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TabPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.net.URL;

public class ScatterPlot extends AChart{

    private final TabPane tabPaneStats;
    private ScatterChart<Number, Number> sc;

    public ScatterPlot(Stage stage, LogClass logClass, TabPane tabpane_statistics){
        super("", "",logClass);
        tabPaneStats = tabpane_statistics;

        URL url = this.getClass().getResource("/css/ColorsPCA.css");
        stage.getScene().getStylesheets().add(url.toExternalForm());

    }


    public void create(double lowerbound_x, double lowerbound_y, double upperbound_x, double upperbound_y){
        final NumberAxis xAxis = new NumberAxis(lowerbound_x, upperbound_x, 1);
        final NumberAxis yAxis = new NumberAxis(lowerbound_y, upperbound_y, 1);
        sc = new ScatterChart<>(xAxis,yAxis);
        xAxis.setLabel("PC 1");
        yAxis.setLabel("PC 2");
        sc.setTitle("");

        setContextMenu(getSc(), tabPaneStats);

    }

    public void addSeries(String name, Color color, double[] pc1, double[] pc2){
        XYChart.Series series = new XYChart.Series();
        series.setName(name);

        for(int i = 0; i < pc1.length; i++)
            series.getData().add(new XYChart.Data(pc1[i], pc2[i]));

        sc.getData().add(series);

    }


    /**
     * GETTER AND SETTER
     *
     */

    public ScatterChart<Number, Number> getSc() {
        return sc;
    }



}
