package view.charts;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.*;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TabPane;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.File;
import java.util.*;

/**
 * Created by neukamm on 29.11.16.
 */
public class StackedBar {

    private List< XYChart.Series<String, Number>> seriesList = new ArrayList<>();
    private StackedBarChart<String, Number> sbc;
    private CategoryAxis xAxis;
    private NumberAxis yAxis;
    private double orgSceneX, orgSceneY;
    private double orgTranslateX, orgTranslateY;
    private TabPane tabPane;
    private ChartController chartController;
    Stage stage;

    public StackedBar(String title, TabPane vBox, ChartController chartController, Stage stage) {
        tabPane = vBox;
        this.chartController = chartController;
        this.stage = stage;

        xAxis = new CategoryAxis();
        yAxis = new NumberAxis();
        sbc = new StackedBarChart<String, Number>(xAxis, yAxis);

        sbc.setTitle(title);
        sbc.prefWidthProperty().bind(tabPane.widthProperty());
        sbc.setAnimated(false);
        sbc.setCategoryGap(20);

        yAxis.setTickUnit(1);
        yAxis.setTickLabelFormatter(new StringConverter<Number>() {
            @Override public String toString(Number object) {
                if(object.intValue()!=object.doubleValue())
                    return "";

                return ""+(object.intValue());
            }

            @Override public Number fromString(String string) {
                Number val = Double.parseDouble(string);
                return val.intValue();
            }
        });

        yAxis.setMinorTickVisible(false);
        xAxis.setTickMarkVisible(false);
        setContextMenu(stage);

    }



    public void addSerie( List<XYChart.Data<String, Number>> data, String name){
        XYChart.Series<String, Number> series = new XYChart.Series<String, Number>();
        series.setName(name);

        for(int i = 0; i < data.size(); i++){
            series.getData().add(data.get(i));
        }

        this.seriesList.add(series);
    }


    public void clearData(){
        sbc.getData().clear();
        seriesList.clear();
        xAxis.getCategories().clear();
    }



    private void setContextMenu(Stage stage){


        //adding a context menu item to the chart
        final MenuItem saveAsPng = new MenuItem("Save as png");
        saveAsPng.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent event) {
                // todo: select location for png
                FileChooser fileChooser = new FileChooser();
                //Show save file dialog
                File file = fileChooser.showSaveDialog(stage);

                if(file != null){
                    chartController.saveAsPng(sbc.snapshot(new SnapshotParameters(), null), file);
                }
                //
            }
        });

        final ContextMenu menu = new ContextMenu(
                saveAsPng
        );

        sbc.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent event) {
                if (MouseButton.SECONDARY.equals(event.getButton())) {
                    menu.show(tabPane, event.getScreenX(), event.getScreenY());
                }
            }
        });



    }




    /**
     * This method cares about the drag and drop option of teh barplot
     *
     */

    public void setDragAndMove(){
        sbc.setCursor(Cursor.HAND);
        sbc.setOnMousePressed(circleOnMousePressedEventHandler);
        sbc.setOnMouseDragged(circleOnMouseDraggedEventHandler);

    }


    /**
     *
     * Definitions of mouse events
     *
     */

    EventHandler<MouseEvent> circleOnMousePressedEventHandler =
            new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent t) {
                    orgSceneX = t.getSceneX();
                    orgSceneY = t.getSceneY();
                    orgTranslateX = ((StackedBarChart)(t.getSource())).getTranslateX();
                    orgTranslateY = ((StackedBarChart)(t.getSource())).getTranslateY();
                }
            };

    EventHandler<MouseEvent> circleOnMouseDraggedEventHandler =
            new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent t) {
                    double offsetX = t.getSceneX() - orgSceneX;
                    double offsetY = t.getSceneY() - orgSceneY;
                    double newTranslateX = orgTranslateX + offsetX;
                    double newTranslateY = orgTranslateY + offsetY;

                    ((StackedBarChart)(t.getSource())).setTranslateX(newTranslateX);
                    ((StackedBarChart)(t.getSource())).setTranslateY(newTranslateY);
                }
            };


    public void setCategories(String[] groups){
        xAxis.setCategories(FXCollections.observableArrayList(groups));
    }


    public List<XYChart.Series<String, Number>> getSeriesList() {
        return seriesList;
    }

    public StackedBarChart<String, Number> getSbc() {
        return sbc;
    }

    public void addTooltip(Event t){

        for (final XYChart.Series<String, Number> series : sbc.getData()) {
            for (final XYChart.Data<String, Number> data : series.getData()) {
                Tooltip tooltip = new Tooltip();
                data.getNode().setOnMouseMoved(new EventHandler<MouseEvent>(){
                    @Override
                    public void handle(MouseEvent event) {
                        // +15 moves the tooltip 15 pixels below the mouse cursor;
                        // if you don't change the y coordinate of the tooltip, you
                        // will see constant screen flicker
                        tooltip.show(data.getNode(), event.getScreenX(), event.getScreenY() + 15);
                        tooltip.setText(series.getName() + " | " + data.getYValue().toString());
                    }
                });
                data.getNode().setOnMouseExited(new EventHandler<MouseEvent>(){
                    @Override
                    public void handle(MouseEvent event){
                        tooltip.hide();
                    }
                });

            }
        }
    }

}
