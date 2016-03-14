package com.alesto.shortpath.graph.node;

import javafx.beans.property.DoubleProperty;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.*;

public class AnchorNode extends Pane {

    //used for short path algorithm
    private Map<AnchorNode,DoubleProperty> connections = new HashMap<>();

    private State state = State.DEFAULT;

    private Node view;

    private boolean checked = false;
    private double distance;
    private AnchorNode previous;

    private AnchorNode() {
        AnchorCircle view = new AnchorCircle(Color.DODGERBLUE);

        setView(view);
    }

    public static AnchorNode createNode(double x, double y) {
        AnchorNode node = new AnchorNode();

        node.setLayoutX(x);
        node.setLayoutY(y);

        return node;
    }

    public static AnchorNode createNode(MouseEvent event) {
        return createNode(event.getX(),event.getY());
    }

    public boolean connectedTo(AnchorNode node) {
        for(AnchorNode connected : connections.keySet()) {
            if(node.equals(connected)) {
                return true;
            }
        }
        return false;
    }

    public void setView(Node view) {
        this.view = view;
        getChildren().add(view);

    }

    public Node getView() {
        return view;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public AnchorNode getPrevious() {
        return previous;
    }

    public void setPrevious(AnchorNode previous) {
        this.previous = previous;
    }

    public Map<AnchorNode, DoubleProperty> getConnections() {
        return connections;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
        ((AnchorCircle)view).setColor(state.getColor());
    }

    /**
    * Node can have next states:
    * DEFAULT - basic state for created node;
    * LINKABLE - state that all selected nodes take.
    * (maximum two)
    * DEPARTURE - departure point of path finding.
    * DESTINATION - destination point of path finding.
    * TRANSIT - state that take only those nodes through
    * which lays shortest path between two chosen nodes.
    */
    public enum State {
        DEFAULT(Color.DODGERBLUE), LINKABLE(Color.SEAGREEN), DEPARTURE(Color.SPRINGGREEN),DESTINATION(Color.SALMON),TRANSIT(Color.ORANGE);

        private Color color;

        State(Color color) {
            this.color = color;
        }

        public Color getColor() {
            return color;
        }
    }
}
