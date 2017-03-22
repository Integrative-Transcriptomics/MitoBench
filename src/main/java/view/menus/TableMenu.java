package view.menus;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import org.apache.log4j.Logger;
import view.MitoBenchWindow;
import view.table.controller.TableControllerUserBench;


/**
 * Created by neukamm on 23.11.16.
 */
public class TableMenu {

    private Menu menuTable;
    private TableControllerUserBench tableController;
    private Logger LOG;

    public TableMenu(MitoBenchWindow mitoBenchWindow){
        menuTable = new Menu("Table");
        menuTable.setId("tableMenu");
        this.tableController = mitoBenchWindow.getTableControllerUserBench();
        LOG = mitoBenchWindow.getLogClass().getLogger(this.getClass());
        addSubMenus();
    }


    private void addSubMenus(){



        /*
                        Get selected rows

         */

        MenuItem getSelectedRows = new MenuItem("Get selected rows");
        getSelectedRows.setOnAction(t -> {
            try{
                LOG.info("Get only selected rows.");
                tableController.updateView(tableController.getTable().getSelectionModel().getSelectedItems());
            } catch (Exception e){
                e.printStackTrace();
            }
        });



        /*
                       Select all rows

         */

        MenuItem selectAllRows = new MenuItem("Select all rows");
        selectAllRows.setOnAction(t -> {
            try{
                LOG.info("Select all rows in user data table.");
                tableController.getTable().getSelectionModel().selectAll();
            } catch (Exception e){
                e.printStackTrace();
            }
        });



        /*
                        Reset table

         */

        MenuItem resetTable = new MenuItem("Reset table");
        resetTable.setId("reset_item");
        resetTable.setOnAction(t -> {
            try{
                tableController.resetTable();
                LOG.info("Reset user data table.");
            } catch (Exception e){
                e.printStackTrace();
            }
        });



        /*
                        Clear table

         */

        MenuItem cleanTable = new MenuItem("Clear table");
        cleanTable.setOnAction(t -> {
            try{
                LOG.info("Remove all data from data table.");
                tableController.cleartable();
            } catch (Exception e){
                e.printStackTrace();
            }
        });

        menuTable.getItems().addAll(getSelectedRows, selectAllRows, resetTable, cleanTable);
    }


    public Menu getMenuTable() {
        return menuTable;
    }


}
