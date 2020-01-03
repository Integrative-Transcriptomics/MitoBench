package view.menus;

import controller.FileReaderController;
import controller.HGListController;
import io.dialogues.Export.ExportDialogue;
import io.dialogues.Import.IImportDialogue;
import io.dialogues.Import.IImportDialogueFactory;
import io.dialogues.Import.ImportDialogueFactoryImpl;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import view.MitoBenchWindow;
import view.dialogues.settings.HGListDialogue;
import view.dialogues.settings.SqlQueryBuilderWindow;

public class Toolbarpane extends ToolBar {

    private final FileReaderController fileReaderController;
    private final Stage stage;
    private final MitoBenchWindow mito;
    private Button goBackBtn;
    private Button openFileBtn;
    private Button saveprojectBtn;
    private IImportDialogueFactory importDialogueFactory;
    private Button loadFromDatabase;
    private Button settings;


    public Toolbarpane(FileReaderController fileReaderController, MitoBenchWindow mitoBenchWindow){
        create();
        addFunctionality();

        this.fileReaderController = fileReaderController;
        this.stage = mitoBenchWindow.getPrimaryStage();
        this.mito = mitoBenchWindow;
        importDialogueFactory = new ImportDialogueFactoryImpl();

    }


    private void create(){
        openFileBtn = new Button();
        saveprojectBtn = new Button();
        //goBackBtn = new Button();
        loadFromDatabase = new Button();
        settings = new Button();

        //Set the icon/graphic for the ToolBar Buttons.
        ImageView view_open = new ImageView(new Image(getClass().getResourceAsStream("/icons/open.png")));
        view_open.setFitHeight(20);
        view_open.setFitWidth(20);
        openFileBtn.setGraphic(view_open);

        ImageView view_save_file = new ImageView(new Image(getClass().getResourceAsStream("/icons/save.png")));
        view_save_file.setFitHeight(20);
        view_save_file.setFitWidth(20);
        saveprojectBtn.setGraphic(view_save_file);

        //goBackBtn.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/icons/left-arrow-angle.png"))));

        ImageView view_database = new ImageView(new Image(getClass().getResourceAsStream("/icons/database.png")));
        view_database.setFitHeight(20);
        view_database.setFitWidth(20);
        loadFromDatabase.setGraphic(view_database);

        ImageView view_settings = new ImageView(new Image(getClass().getResourceAsStream("/icons/settings.png")));
        view_settings.setFitHeight(20);
        view_settings.setFitWidth(20);
        settings.setGraphic(view_settings);

        openFileBtn.setTooltip(new Tooltip("Import file"));
        saveprojectBtn.setTooltip(new Tooltip("Export all data"));
        //goBackBtn.setTooltip(new Tooltip("Go one filtering step back"));
        loadFromDatabase.setTooltip(new Tooltip("Download data from database"));
        settings.setTooltip(new Tooltip("Set Haplogroup list"));
        this.getItems().addAll(openFileBtn, saveprojectBtn, loadFromDatabase, settings);
    }


    private void addFunctionality() {
        openFileBtn.setOnAction(e -> {
            IImportDialogue importDialogue;
            importDialogue = importDialogueFactory.create(stage);
            importDialogue.start();
           fileReaderController.openFile(importDialogue.getSelectedFile());


        });

        saveprojectBtn.setOnAction(t -> {

            if(mito.getTableControllerUserBench().getTable().getItems().size() != 0){
                ExportDialogue exportDialogue = new ExportDialogue(mito, true);
                try {
                    exportDialogue.start(new Stage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });

//        goBackBtn.setOnAction(e -> {
//            mito.getTableControllerUserBench().resetToUnfilteredData();
//        });

        loadFromDatabase.setOnAction(t -> {
            SqlQueryBuilderWindow sqlQueryBuilderWindow = new SqlQueryBuilderWindow(mito);

        });

        settings.setOnAction(t -> {
            HGListDialogue hgListDialogue = new HGListDialogue("Custom Haplogroup list", mito.getLogClass());
            HGListController hgListController = new HGListController(hgListDialogue, mito.getChartController(), mito);
            mito.getTabpane_statistics().getTabs().add(hgListDialogue.getTab());
            mito.getTabpane_statistics().getSelectionModel().select(hgListDialogue.getTab());


        });
    }



}
