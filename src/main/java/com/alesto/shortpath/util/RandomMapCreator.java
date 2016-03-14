package com.alesto.shortpath.util;

import com.alesto.shortpath.graph.Graph;
import com.alesto.shortpath.graph.ListenerManager;
import com.alesto.shortpath.graph.node.AnchorNode;
import javafx.scene.layout.Pane;

import java.util.Random;

public class RandomMapCreator {

    private Graph graph;
    private Pane mainPane;

    private Random random = new Random();

    public RandomMapCreator(Graph graph, Pane mainPane) {
        this.graph = graph;
        this.mainPane = mainPane;
    }

    public void createNewMap() {
        addNodes();
        addConnections();
        ListenerManager.setDefaultNodeListeners(graph);
    }

    private void addNodes() {
        int nodeQuantity = 10 + random.nextInt(10);
        for (int i = 0; i < nodeQuantity; i++) {
            double x = random.nextDouble()*mainPane.getWidth();
            double y = random.nextDouble()*mainPane.getHeight();
            graph.addNode(AnchorNode.createNode(x,y));
        }

    }

    private void addConnections() {
        for(AnchorNode node : graph.getNodes()) {
            for(AnchorNode connectible : graph.getNodes()) {
                if(random.nextDouble() < 0.1 && node != connectible) {
                    graph.addConnection(node, connectible);
                }
            }
        }
    }
}
