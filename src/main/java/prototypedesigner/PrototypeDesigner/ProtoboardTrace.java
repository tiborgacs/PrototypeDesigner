package prototypedesigner.PrototypeDesigner;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import lombok.Getter;
import lombok.Setter;
import prototypedesigner.PrototypeDesigner.components.DrawableOnProtoboard;

import java.util.LinkedList;

@Getter
@Setter
public class ProtoboardTrace implements DrawableOnProtoboard {

    private LinkedList<ProtoboardDot> dots = new LinkedList<>();

    public ProtoboardTrace() {}

    public ProtoboardTrace(ProtoboardDot first) {
        dots.add(first);
    }

    public void cut() {
        // TODO: cut into 2 objects
    }

    @Override
    public void drawOnProtoboard(GraphicsContext context) {
        context.setFill(Color.SILVER); // TODO: highlight?
        for (int i = 0; i < dots.size() - 1; i++) {
            ProtoboardDot dot = dots.get(i);
            ProtoboardDot linked = dots.get(i+1);
            if (dot.isNeighborTop(linked)) context.fillRect(dot.getX()*24+9, linked.getY()*24+15, 6, 18);
            if (dot.isNeighborBottom(linked)) context.fillRect(dot.getX()*24+9, linked.getY()*24+15, 6, 18);
            if (dot.isNeighborLeft(linked)) context.fillRect(linked.getX()*24+15, dot.getY()*24+9, 18, 6);
            if (dot.isNeighborRight(linked)) context.fillRect(dot.getX()*24+15, dot.getY()*24+9, 18, 6);
        }
    }
}
