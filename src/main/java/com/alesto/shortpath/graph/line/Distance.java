package com.alesto.shortpath.graph.line;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.geometry.Point2D;

public class Distance extends DoubleBinding {
    private final DoubleProperty startX;
    private final DoubleProperty startY;
    private final DoubleProperty endX;
    private final DoubleProperty endY;

    Distance(DoubleProperty startX, DoubleProperty startY, DoubleProperty endX, DoubleProperty endY) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;

        super.bind(startX, startY, endX, endY);
    }

    @Override
    protected double computeValue() {
        Point2D start = new Point2D(startX.get(), startY.get());
        Point2D end = new Point2D(endX.get(), endY.get());

        return start.distance(end);
    }
}
