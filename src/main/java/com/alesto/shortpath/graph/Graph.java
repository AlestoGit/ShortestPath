package com.alesto.shortpath.graph;

import com.alesto.shortpath.graph.line.Connection;
import com.alesto.shortpath.graph.node.AnchorNode;
import com.alesto.shortpath.util.PathSearch;
import com.alesto.shortpath.util.TwoNodeStorage;
import javafx.scene.layout.Pane;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Graph {
    private Pane mainPane;
    private Set<AnchorNode> nodeSet = new HashSet<>();
    private Set<Connection> connectionSet = new HashSet<>();

    public void addNode(AnchorNode node) {
        nodeSet.add(node);
        mainPane.getChildren().add(node);
    }

    public void addConnection() {
        //make our nodes' view default
        resetNodes();

        AnchorNode[] connectibleNodes = TwoNodeStorage.getAndClear();
        if(connectibleNodes != null) {
            AnchorNode start = connectibleNodes[0];
            AnchorNode end = connectibleNodes[1];

            addConnection(start,end);
        } else {
            //todo alert
        }
    }

    public void addConnection(AnchorNode start, AnchorNode end) {
        if(start.connectedTo(end)) {
            //todo alert already connected
        } else {
            Connection connection = Connection.createConnection(start,end);

            connectionSet.add(connection);

            mainPane.getChildren().add(connection.getLine());
            mainPane.getChildren().add(connection.getLabel());
        }
    }

    public void findPath() {
        //make our nodes' view default
        resetNodes();

        AnchorNode[] connectibleNodes = TwoNodeStorage.getAndClear();
        if(connectibleNodes != null) {
            List<AnchorNode> way = PathSearch.findPath(nodeSet,connectibleNodes[0],connectibleNodes[1]);
            way.get(0).setState(AnchorNode.State.DEPARTURE);
            way.get(way.size()-1).setState(AnchorNode.State.DESTINATION);
            for(int i = 1; i < way.size()-1; i++) {
                way.get(i).setState(AnchorNode.State.TRANSIT);
            }

            for(AnchorNode node : way) {
                for(Connection connection : connectionSet) {
                    if(connection.getSource().equals(node) && connection.getTarget().equals(node.getPrevious())
                           || connection.getTarget().equals(node) && connection.getSource().equals(node.getPrevious())) {
                        connection.setState(Connection.State.TRANSIT);
                    }
                }
            }
        } else {
            //todo alert
        }
    }

    /**
     * Bounces back the state of the nodes to DEFAULT state.
     */
    private void resetNodes() {
        for(AnchorNode node : nodeSet) {
            if(!node.getState().equals(AnchorNode.State.DEFAULT))
                node.setState(AnchorNode.State.DEFAULT);
        }

        for(Connection connection : connectionSet) {
            if(!connection.getState().equals(Connection.State.DEFAULT))
                connection.setState(Connection.State.DEFAULT);
        }
    }

    /**
     * Constructor
     * @param mainPane
     */

    public Graph(Pane mainPane) {
        this.mainPane = mainPane;
    }

    /**
     * GETTERS
     */

    public Set<AnchorNode> getNodes() {
        return nodeSet;
    }

    public Set<Connection> getConnections() {
        return connectionSet;
    }

    public Pane getMainPane() {
        return mainPane;
    }
}


