package com.alesto.shortpath.view;

import com.alesto.shortpath.MainApp;
import com.alesto.shortpath.graph.ListenerManager;
import com.alesto.shortpath.util.RandomMapCreator;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.Pane;

public class MainController {

    private MainApp mainApp;

    /**
     * Interface elements initialized via FXML
     */
    @FXML
    private Pane mainPane;

    @FXML
    private ToggleButton addNodesButton, addConnectionButton;

    @FXML
    private Button clear, connect, findPath, createRandomMap;

    /**
     * Handlers for buttons
     */
    @FXML
    private void handleAddNodesButton() {
        if (addNodesButton.isSelected()) {
            ListenerManager.switchToAddingMode(mainApp.getGraph());
            addConnectionButton.setSelected(false);
        } else {
            ListenerManager.switchToDraggableMode(mainApp.getGraph());
        }
    }

    @FXML
    private void handleAddConnectionButton() {
        if (addConnectionButton.isSelected()) {
            ListenerManager.switchToConnectingMode(mainApp.getGraph());
            addNodesButton.setSelected(false);
        } else {
            ListenerManager.switchToDraggableMode(mainApp.getGraph());
        }
    }

    @FXML
    private void handleConnectionProcess() {
        mainApp.getGraph().addConnection();
    }

    @FXML
    private void findPath() {
        mainApp.getGraph().findPath();
        unselectAllButtons();
    }

    @FXML
    private void createRandomMap() {
        clear();
        RandomMapCreator creator = new RandomMapCreator(mainApp.getGraph(),mainPane);
        creator.createNewMap();
        unselectAllButtons();
    }

    @FXML
    private void clear() {
        mainApp.getGraph().getConnections().clear();
        mainApp.getGraph().getNodes().clear();
        mainPane.getChildren().clear();
    }

    private void unselectAllButtons() {
        addConnectionButton.setSelected(false);
        addConnectionButton.setSelected(false);
        ListenerManager.switchToDraggableMode(mainApp.getGraph());
    }

    /**
     * Basic Pane listeners
     */
    //todo special cursors
    @FXML
    private void enterPaneListener() {
        mainPane.setCursor(Cursor.HAND);
    }

    @FXML
    private void quitPaneListener() {
        mainPane.setCursor(Cursor.DEFAULT);
    }


    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    public Pane getMainPane() {
        return mainPane;
    }
}
