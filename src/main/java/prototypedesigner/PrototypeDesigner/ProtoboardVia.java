package prototypedesigner.PrototypeDesigner;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import prototypedesigner.PrototypeDesigner.components.Coordinate;
import prototypedesigner.PrototypeDesigner.components.DrawableOnProtoboard;

/**
 * Models horizontal or vertical wire links between traces on the prototype board.
 */
@Getter
@Setter
@NoArgsConstructor
@JsonInclude
@JsonIdentityInfo(property = "identifier", generator = ObjectIdGenerators.PropertyGenerator.class)
public class ProtoboardVia implements DrawableOnProtoboard {

    private ProtoboardDot start;
    private ProtoboardDot end;

    private static int counter = 0;
    private String identifier;

    /**
     * Sets the counter with a starting value for generating identifiers.
     * @param counter starting value (exclusive)
     */
    public static void setCounter(int counter) {
        ProtoboardVia.counter = counter;
    }

    public ProtoboardVia(ProtoboardDot start, ProtoboardDot end) {
        this.start = start;
        this.end = end;
        identifier = "via#" + ++counter;
    }

    @Override
    public void drawOnProtoboard(GraphicsContext context) {
        context.setFill(Color.BLACK);
        context.fillRoundRect(start.getX()*24+9, start.getY()*24+9, Math.abs(start.getX() - end.getX())*24+6, Math.abs(start.getY() - end.getY())*24+6, 6, 6);
    }
}
