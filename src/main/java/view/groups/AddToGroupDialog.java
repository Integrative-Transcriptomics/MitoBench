package view.groups;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import view.table.TableController;


/**
 * Created by neukamm on 26.11.2016.
 */
public class AddToGroupDialog {


    private ComboBox comboBox;
    private Stage dialog;
    private TableController controller;


    public AddToGroupDialog(GroupController groupController, ObservableList groupItems, TableController tableController){

        this.controller = tableController;
        dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        // Boxes
        VBox dialogVbox = new VBox(20);
        HBox hBox = new HBox(20);


        // Elements
        ObservableList<String> comboEntries = FXCollections.observableArrayList();

        for(String s : groupController.getGroupnames()){
            comboEntries.add(s);
        }

        //comboBox = new ComboBox(comboEntries);
        comboBox = new ComboBox<String>();
        comboBox.getItems().addAll(groupController.getGroupnames());
        comboBox.setEditable(true);


        Button okButton = new Button("OK");
        addButtonListener(okButton, groupController, groupItems);

        hBox.getChildren().addAll(new Label("Select group"), comboBox);
        dialogVbox.getChildren().addAll(hBox, okButton);
        dialogVbox.setAlignment(Pos.CENTER);

        Scene dialogScene = new Scene(dialogVbox, 250, 80);
        dialog.setScene(dialogScene);
        dialog.show();
    }

    private void addButtonListener(Button okButton, GroupController groupController, ObservableList groupItems){


        okButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                // add elements to group
                groupController.addElements(groupItems, comboBox.getValue().toString());
                controller.updateTable(controller.createNewEntryList(comboBox.getValue().toString()));
                dialog.close();
            }
        });


        DropShadow shadow = new DropShadow();
        //Adding the shadow when the mouse cursor is on
        okButton.addEventHandler(MouseEvent.MOUSE_ENTERED,
                new EventHandler<MouseEvent>() {
                    @Override public void handle(MouseEvent e) {
                        okButton.setEffect(shadow);
                    }
                });
        //Removing the shadow when the mouse cursor is off
        okButton.addEventHandler(MouseEvent.MOUSE_EXITED,
                new EventHandler<MouseEvent>() {
                    @Override public void handle(MouseEvent e) {
                        okButton.setEffect(null);
                    }
                });

    }

    public ComboBox getComboBox() {
        return comboBox;
    }
}