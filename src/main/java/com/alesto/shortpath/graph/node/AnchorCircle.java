package com.alesto.shortpath.graph.node;

import javafx.beans.property.DoubleProperty;
import javafx.scene.Cursor;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;

public class AnchorCircle extends Circle {

    AnchorCircle(Color color) {
        super(10);
        setColor(color);
        setStrokeWidth(2);
        setStrokeType(StrokeType.OUTSIDE);
    }

    public void setColor(Color color) {
        setFill(color.deriveColor(1, 1, 1, 0.7));
        setStroke(color);
    }
}

