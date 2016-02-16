package com.alesto.shortpath.util;

import com.alesto.shortpath.graph.Cell;

import java.util.*;

/**
 * Created on 15.02.2016.
 */
public class PathSearch {
    private static Queue<Cell> queue = new LinkedList<>();

    /**
     * this is the only single method to determine the shortest path
     * from start cell to finish cell. If such a path exists - method
     * returns List with all cells belonged to this path. Other way it returns null;
     *
     * @param cellList
     * @param start
     * @param finish
     * @return
     */
    public static List<Cell> findPath(Collection<Cell> cellList, Cell start, Cell finish) {
        //setting default max value to every cell.
        resetDistance(cellList);

        determineDistances(cellList,start);

        if(finish.getPrevious() == null) {
            return null;
        } else {
            return getTrace(finish);
        }
    }

    /**
     * For every calculation we reset Distance variable (which name is
     * a little bit ambiguous. This variable contains shortest distance
     * from the Start cell to the current cell possessing this variable).
     * @param cellList
     */
    private static void resetDistance(Collection<Cell> cellList) {
        for (Cell cell : cellList) {
            cell.setDistance(Integer.MAX_VALUE);
            cell.setPrevious(null);
        }
    }

    /**
     * Calculates and overrides Distance from the Start cell;
     *
     * @param cellList
     * @param start
     */
    private static void determineDistances(Collection<Cell> cellList, Cell start) {
        start.setDistance(0);
        queue.add(start);

        while(!queue.isEmpty()) {
            Cell current = queue.remove();
            for(Map.Entry<Cell,Integer> connections : current.getConnections().entrySet()) {
                Cell connected = connections.getKey();
                int distanceBetween = connections.getValue();

                if(!connected.isChecked() && connected.getDistance() > current.getDistance() + distanceBetween) {
                    connected.setDistance(current.getDistance()+distanceBetween);
                    connected.setPrevious(current);
                    queue.add(connected);
                }
            }
        }
    }

    /**
     * Creates a List containing all cells on the shortest path from start to finish.
     *
     * @param finish
     * @return
     */
    private static List<Cell> getTrace(Cell finish) {
        List<Cell> trace = new ArrayList<>();

        Cell current = finish;
        trace.add(current);

        while(current.getPrevious() != null) {
            trace.add(0,current.getPrevious());
            current = current.getPrevious();
        }

        return trace;
    }

    /**
     * As far as it is not Java EE project I decided not to use jUnit and tested it with old plain main.
     */
    /*
    public static void main(String[] args) {
        Map<Integer, Cell> map = new HashMap<>();
        //add cells
        for (int i = 0; i < 13; i++) {
            map.put(i+1,new Cell(""+(i+1)));
        }

        //add connections
        map.get(1).addConnection(map.get(5),9);
        map.get(2).addConnection(map.get(5),4).addConnection(map.get(4), 3).addConnection(map.get(3),3);
        map.get(3).addConnection(map.get(4),3).addConnection(map.get(7), 15).addConnection(map.get(6),12);
        map.get(4).addConnection(map.get(9),6);
        map.get(5).addConnection(map.get(9),9);
        map.get(6).addConnection(map.get(8),1);
        map.get(7).addConnection(map.get(8),7).addConnection(map.get(9),4).addConnection(map.get(11), 12).addConnection(map.get(12),3);
        map.get(8).addConnection(map.get(13),9);
        map.get(9).addConnection(map.get(10),2).addConnection(map.get(11),5);
        map.get(10).addConnection(map.get(11),3);
        map.get(12).addConnection(map.get(13),4);

        //test
        List<Cell> trace = findPath(map.values(),map.get(2),map.get(13));
        List<Cell> trace2 = findPath(map.values(),map.get(3),map.get(11));
        List<Cell> trace3 = findPath(map.values(),map.get(1),map.get(6));
        printResult(trace);
        printResult(trace2);
        printResult(trace3);
    }

    //test method
    private static void printResult(List<Cell> trace) {
        boolean flag = false;
        for (Cell cell : trace) {
            if(flag) {
                System.out.print(" -> ");
            }
            System.out.print(cell.getCellId());
            flag = true;
        }
        System.out.println(";");
    }*/
}
