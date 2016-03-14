package com.alesto.shortpath.graph.line;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.geometry.Point2D;

public class Angle extends DoubleBinding {
    private final DoubleProperty startX;
    private final DoubleProperty startY;
    private final DoubleProperty endX;
    private final DoubleProperty endY;

    Angle(DoubleProperty startX, DoubleProperty startY, DoubleProperty endX, DoubleProperty endY) {
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

        if (start.equals(end)) {
            return 0;
        }

        if (startX.get() < endX.get()) {
            Point2D X_AXIS_UNIT = new Point2D(startX.get() + 1, startY.get());

            double angle = start.angle(X_AXIS_UNIT, end);

            if (startY.get() > endY.get()) {
                angle = -angle;
            }

            return angle;
        } else {
            Point2D X_AXIS_UNIT = new Point2D(endX.get() + 1, endY.get());

            double angle = end.angle(X_AXIS_UNIT, start);

            if (endY.get() > startY.get()) {
                angle = -angle;
            }

            return angle;
        }
    }
}

