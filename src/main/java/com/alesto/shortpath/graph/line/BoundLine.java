package com.alesto.shortpath.graph.line;

import com.alesto.shortpath.graph.node.AnchorNode;
import javafx.beans.binding.Bindings;
import javafx.geometry.Bounds;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;

public class BoundLine extends Line {
    BoundLine(AnchorNode source, AnchorNode target) {
        startXProperty().bind(Bindings.createDoubleBinding(() -> {
            Bounds b = source.getBoundsInParent();
            return b.getMinX() + b.getWidth() / 3 ;
        }, source.boundsInParentProperty()));
        startYProperty().bind(Bindings.createDoubleBinding(() -> {
            Bounds b = source.getBoundsInParent();
            return b.getMinY() + b.getHeight()/3;
        }, source.boundsInParentProperty()));
        endXProperty().bind(Bindings.createDoubleBinding(() -> {
            Bounds b = target.getBoundsInParent();
            return b.getMinX() + b.getWidth() / 3 ;
        }, target.boundsInParentProperty()));
        endYProperty().bind(Bindings.createDoubleBinding(() -> {
            Bounds b = target.getBoundsInParent();
            return b.getMinY() + b.getHeight() / 3 ;
        }, target.boundsInParentProperty()));

        setStrokeWidth(2);
        setStroke(Color.GRAY.deriveColor(0, 1, 1, 0.5));
        setStrokeLineCap(StrokeLineCap.BUTT);
        getStrokeDashArray().setAll(10.0, 5.0);
        setMouseTransparent(true);
    }
}
