package com.alesto.shortpath.util;



import com.alesto.shortpath.graph.node.AnchorNode;
import javafx.beans.property.DoubleProperty;

import java.util.*;

/**
 * Created on 15.02.2016.
 */
public class PathSearch {
    private static Queue<AnchorNode> queue = new LinkedList<>();

    /**
     * This is the only single method to determine the shortest path
     * from start node to finish node. If such a path exists - method
     * returns List with all nodes belonged to this path. Other way it returns null;
     *
     * @param nodeList
     * @param start
     * @param finish
     * @return
     */
    public static List<AnchorNode> findPath(Collection<AnchorNode> nodeList, AnchorNode start, AnchorNode finish) {
        //setting default max value to every node.
        resetDistance(nodeList);

        determineDistances(nodeList,start);

        if(finish.getPrevious() == null) {
            return null;
        } else {
            return getTrace(finish);
        }
    }

    /**
     * For every calculation we reset Distance variable (which name is
     * a little bit ambiguous. This variable contains shortest distance
     * from the Start node to the current node possessing this variable).
     * @param nodeList
     */
    private static void resetDistance(Collection<AnchorNode> nodeList) {
        for (AnchorNode node : nodeList) {
            node.setDistance(Integer.MAX_VALUE);
            node.setPrevious(null);
        }
    }

    /**
     * Calculates and overrides Distance from the Start node;
     *
     * @param nodeList
     * @param start
     */
    private static void determineDistances(Collection<AnchorNode> nodeList, AnchorNode start) {
        start.setDistance(0);
        queue.add(start);

        while(!queue.isEmpty()) {
            AnchorNode current = queue.remove();
            for(Map.Entry<AnchorNode,DoubleProperty> connections : current.getConnections().entrySet()) {
                AnchorNode connected = connections.getKey();
                double distanceBetween = connections.getValue().doubleValue();

                if(!connected.isChecked() && connected.getDistance() > current.getDistance() + distanceBetween) {
                    connected.setDistance(current.getDistance()+distanceBetween);
                    connected.setPrevious(current);
                    queue.add(connected);
                }
            }
        }
    }

    /**
     * Creates a List containing all nodes on the shortest path from start to finish.
     *
     * @param finish
     * @return
     */
    private static List<AnchorNode> getTrace(AnchorNode finish) {
        List<AnchorNode> trace = new ArrayList<>();

        AnchorNode current = finish;
        trace.add(current);

        while(current.getPrevious() != null) {
            trace.add(0,current.getPrevious());
            current = current.getPrevious();
        }

        return trace;
    }
}
