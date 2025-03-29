package prototypedesigner.PrototypeDesigner;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import lombok.Getter;
import lombok.Setter;
import prototypedesigner.PrototypeDesigner.components.Coordinate;
import prototypedesigner.PrototypeDesigner.components.DrawableOnProtoboard;

@Getter
@Setter
public class ProtoboardVia implements DrawableOnProtoboard {

    private ProtoboardDot start;
    private ProtoboardDot end;

    public ProtoboardVia(ProtoboardDot start, ProtoboardDot end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public void drawOnProtoboard(GraphicsContext context) {
        context.setFill(Color.BLACK);
        context.fillRoundRect(start.getX()*24+9, start.getY()*24+9, Math.abs(start.getX() - end.getX())*24+6, Math.abs(start.getY() - end.getY())*24+6, 6, 6);
    }
}
