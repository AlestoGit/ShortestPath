package com.alesto.shortpath.util;

import com.alesto.shortpath.graph.Cell;
import com.alesto.shortpath.graph.Graph;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class CellLocationRandomizer {

    private Graph graph;

    private Random rnd = new Random();

    private Set<Coordinates> allCoordinates = new HashSet<>();

    private final int fieldWidth;
    private final int fieldHeight;

    public CellLocationRandomizer(Graph graph, int fieldWidth, int fieldHeight) {
        this.graph = graph;
        this.fieldWidth = fieldWidth;
        this.fieldHeight = fieldHeight;
    }

    /**
     * Main executive method.
     */
    public void execute() {

        List<Cell> cells = graph.getModel().getAllCells();

        for (Cell cell : cells) {
            Coordinates randomCoordinates = generateCoordinates();

            while(obstaclesAround(randomCoordinates)) {
                randomCoordinates = generateCoordinates();
            }

            allCoordinates.add(randomCoordinates);
            cell.relocate(randomCoordinates.x, randomCoordinates.y);
        }
    }

    /**
     * Checks whether given coordinates or its' neighbourhood
     * are currently engaged with any other Cell.
     *
     * @param newCoordinates
     * @return
     */
    private boolean obstaclesAround(Coordinates newCoordinates) {
        for (Coordinates coordinates : allCoordinates) {
            if(coordinates.x <= newCoordinates.x+30 && coordinates.x >= newCoordinates.x-30 && coordinates.y <= newCoordinates.y+30 && coordinates.y >= newCoordinates.y-30) {
                return true;
            }
        }
        return false;
    }

    /**
     * Generates random coordinates depending on the field size.
     * @return
     */
    private Coordinates generateCoordinates() {
        return new Coordinates(rnd.nextDouble() * fieldWidth, rnd.nextDouble() * fieldHeight);
    }

    private class Coordinates {
        double x;
        double y;

        public Coordinates(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }
}
