package com.alesto.shortpath.graph;

import com.alesto.shortpath.graph.node.AnchorNode;
import com.alesto.shortpath.util.TwoNodeStorage;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class ListenerManager {

    private static Delta dragDelta = new Delta();

    public static void switchToAddingMode(Graph graph) {
        enableAddingNodes(graph);

        for (AnchorNode node : graph.getNodes()) {
            node.setOnMouseEntered(null);
            node.setOnMousePressed(null);
            node.setOnMouseClicked(null);
            node.setOnMouseDragged(null);
            node.setOnMouseReleased(null);
        }
    }

    public static void switchToConnectingMode(Graph graph) {
        disableAddingNodes(graph);

        for (AnchorNode node : graph.getNodes()) {
            node.setOnMouseEntered(null);
            node.setOnMousePressed(null);
            node.setOnMouseClicked(onMouseClickedNodeHandlerD);
            node.setOnMouseDragged(null);
            node.setOnMouseReleased(null);
        }
    }

    public static void switchToDraggableMode(Graph graph) {
        disableAddingNodes(graph);

        for (AnchorNode node : graph.getNodes()) {
            node.setOnMouseEntered(onMouseEnterNodeHandlerD);
            node.setOnMousePressed(onMousePressedNodeHandlerD);
            node.setOnMouseClicked(null);
            node.setOnMouseDragged(onMouseDraggedNodeHandlerD);
            node.setOnMouseReleased(onMouseReleaseNodeHandlerD);
        }
    }

    public static void setDefaultNodeListeners(Graph graph) {
        switchToDraggableMode(graph);
        for (AnchorNode node : graph.getNodes()) {
            node.setOnMouseExited(onMouseExitedNodeHandlerD);
        }
    }

    private static void enableAddingNodes(Graph graph) {
        graph.getMainPane().setOnMouseClicked(event ->
        {
            AnchorNode node = AnchorNode.createNode(event);
            graph.addNode(node);
        });
    }

    private static void disableAddingNodes(Graph graph) {
        graph.getMainPane().setOnMouseClicked(null);
    }

    /*****************************************************/
    /******************** HANDLERS
     /*****************************************************/
    /**
     * Node handlers for Connecting mode.
     */
    private static EventHandler<MouseEvent> onMouseClickedNodeHandlerD = event -> {
        if (event.getButton().equals(MouseButton.PRIMARY)) {
            AnchorNode node = (AnchorNode) event.getSource();
            TwoNodeStorage.processClick(node);
        }
    };


    /**
     * Node handlers for Dragging mode.
     */
    private static EventHandler<MouseEvent> onMouseReleaseNodeHandlerD = event -> {

        if (!event.isPrimaryButtonDown()) {
            //todo good hand cursor
            ((Node) event.getSource()).setCursor(Cursor.HAND);
        }
    };
    private static EventHandler<MouseEvent> onMouseEnterNodeHandlerD = event -> {

        if (!event.isPrimaryButtonDown()) {
            //todo good hand cursor
            ((Node) event.getSource()).setCursor(Cursor.HAND);
        }
    };

    private static EventHandler<MouseEvent> onMousePressedNodeHandlerD = event -> {
        AnchorNode node = (AnchorNode) event.getSource();
        dragDelta.formerTranslateX = node.getLayoutX();
        dragDelta.formerTranslateY = node.getLayoutY();
        dragDelta.formerEventSceneX = event.getSceneX();
        dragDelta.formerEventSceneY = event.getSceneY();
        node.setCursor(Cursor.MOVE);

        //todo grabbed cursor
    };

    private static EventHandler<MouseEvent> onMouseDraggedNodeHandlerD = event -> {

        AnchorNode node = (AnchorNode) event.getSource();

        double xx = event.getSceneX() - dragDelta.formerEventSceneX + dragDelta.formerTranslateX;
        double yy = event.getSceneY() - dragDelta.formerEventSceneY + dragDelta.formerTranslateY;

        if(xx > 0 && xx  < ((Pane)node.getParent()).getWidth())
        node.setLayoutX(xx);
        if(yy > 0 && yy < ((Pane)node.getParent()).getHeight())
        node.setLayoutY(yy);

    };

    private static EventHandler<MouseEvent> onMouseExitedNodeHandlerD = event -> {
        //todo basic cursor
        if (!event.isPrimaryButtonDown()) {
            ((Node) event.getSource()).setCursor(Cursor.DEFAULT);
        }
    };


    // records relative x and y co-ordinates.
    static class Delta {
        double formerTranslateX, formerTranslateY, formerEventSceneX, formerEventSceneY;
    }


}
