package gui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testfx.api.FxRobot;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.isVisible;

/**
 * Created by peltzer on 21/12/2016.
 */
public class GUITestSteps {
    private static final Logger LOG = LoggerFactory.getLogger(GUITestSteps.class);


    private final FxRobot robot;

    private int stepno;

    public GUITestSteps(final FxRobot robot) {
        this.robot = robot;
    }

    public void part1BasicStuff() {
        step("Open application", () -> {
            verifyThat("#mainBorderPane", isVisible());
        });
        step("Test Table is visible", () -> {
            verifyThat("#mainEntryTable", isVisible());
        });
        step("TreeView Button exists", () -> {
            verifyThat("#treeViewOpenCloseLabel", isVisible());
        });
        step("MenuBar exists", () -> {
            verifyThat("#menuBar", isVisible());
        });
    }

    public void part2MenuInteraction() {
        step("Open File Menu", () -> {
            robot.clickOn("#fileMenu");
            verifyThat("#fileMenu", isVisible());
        });
        step("Open Table Menu", () -> {
            robot.clickOn("#tableMenu");
            verifyThat("#tableMenu", isVisible());
        });
        step("Open Graphics Menu", () -> {
            robot.clickOn("#graphicsMenu");
            verifyThat("#graphicsMenu", isVisible());
        });
        step("Open Help Menu", () -> {
            robot.clickOn("#helpMenu");
            verifyThat("#helpMenu", isVisible());
        });
    }

    public void part3AboutDialogueTests() {
        step("Open About Dialogue", () -> {
            robot.clickOn("#helpMenu");
            robot.clickOn("#aboutMenuItem");
            verifyThat("#aboutDialogue", isVisible());
            robot.clickOn("#aboutDialogue" + "button");
        });
    }

    public void part4TreeViewTests() {
        step("Open TreeView", () -> {
            robot.clickOn("#treeViewOpenCloseLabel");
            //Now our dropdown menu should appear!
            verifyThat("#treeviewSearchPane", isVisible());
            robot.clickOn("#treeviewSearchField").write("U,V,X,W");
            robot.clickOn("#treeviewApplyButton");
            robot.clickOn("#treeViewOpenCloseLabel");
        });
    }


    private void step(final String step, final Runnable runnable) {
        ++stepno;
        LOG.info("STEP {}: Begin - {}", stepno, step);
        runnable.run();
        LOG.info("STEP {}:   End - {}", stepno, step);
    }
}
