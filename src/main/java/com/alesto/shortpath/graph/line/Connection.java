package com.alesto.shortpath.graph.line;

import com.alesto.shortpath.graph.node.AnchorNode;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Rotate;

public class Connection {
    private final BoundLine line;
    private final Label label;
    private final Distance distance;
    private final Angle angle;
    private State state = State.DEFAULT;

    AnchorNode source, target;

    public Connection(AnchorNode source, AnchorNode target) {
        this.source = source;
        this.target = target;

        DoubleProperty startX = source.layoutXProperty();
        DoubleProperty startY = source.layoutYProperty();
        DoubleProperty endX = target.layoutXProperty();
        DoubleProperty endY = target.layoutYProperty();
        label = new Label("350");
        line = new BoundLine(source,target);
        distance = new Distance(startX, startY, endX, endY);
        angle = new Angle(startX, startY, endX, endY);


        label.textProperty().bind(
                distance.asString("%.0f")
        );

        label.setAlignment(Pos.CENTER);
        label.setTextAlignment(TextAlignment.CENTER);
        label.prefWidthProperty().bind(distance);
        label.setMouseTransparent(true);
        label.setTextFill(Color.GOLDENROD);

        label.layoutXProperty().bind(
                Bindings.when(line.startXProperty().lessThan(line.endXProperty())).then(line.startXProperty()).otherwise(line.endXProperty())
        );
        label.layoutYProperty().bind(
                Bindings.when(line.startXProperty().lessThan(line.endXProperty())).then(line.startYProperty()).otherwise(line.endYProperty())
        );

        Rotate rotate = new Rotate();
        rotate.setPivotX(0);
        rotate.setPivotY(0);
        rotate.angleProperty().bind(angle);

        label.getTransforms().add(rotate);
    }

    public static Connection createConnection(AnchorNode start, AnchorNode end) {
        Connection connection = new Connection(start, end);

        DoubleProperty distance = new SimpleDoubleProperty();
        distance.bind(connection.distanceProperty());

        start.getConnections().put(end,distance);
        end.getConnections().put(start,distance);

        return connection;
    }

    public Number getDistance() {
        return distance.get();
    }

    public Distance distanceProperty() {
        return distance;
    }

    public Label getLabel() {
        return label;
    }

    public BoundLine getLine() {
        return line;
    }

    public AnchorNode getSource() {
        return source;
    }

    public AnchorNode getTarget() {
        return target;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
        line.setStroke(state.getColor());
    }

    public enum State {
        DEFAULT(Color.GRAY.deriveColor(0, 1, 1, 0.5)), TRANSIT(Color.ORANGE.deriveColor(0, 1, 1, 0.5));

        private Color color;

        State(Color color) {
            this.color = color;
        }

        public Color getColor() {
            return color;
        }
    }
}
