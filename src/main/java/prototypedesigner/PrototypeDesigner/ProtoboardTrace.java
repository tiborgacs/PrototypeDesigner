package prototypedesigner.PrototypeDesigner;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import prototypedesigner.PrototypeDesigner.components.DrawableOnProtoboard;

import java.util.LinkedList;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude
@JsonIdentityInfo(property = "identifier", generator = ObjectIdGenerators.PropertyGenerator.class)
public class ProtoboardTrace implements DrawableOnProtoboard {

    private static int counter = 0;
    private String identifier;
    {
        identifier = "trace#" + ++counter;
    }
    private LinkedList<ProtoboardDot> dots = new LinkedList<>();

    public ProtoboardTrace(ProtoboardDot first) {
        dots.add(first);
    }

    public ProtoboardTrace cut(ProtoboardDot a, ProtoboardDot b) {
        if (dots.contains(a) && dots.contains(b)) {
            int minIdx = Math.min(dots.indexOf(a), dots.indexOf(b));
            int maxIdx = Math.max(dots.indexOf(a), dots.indexOf(b));
            LinkedList<ProtoboardDot> sublist1 = new LinkedList<>(dots.subList(0, minIdx + 1));
            LinkedList<ProtoboardDot> sublist2 = new LinkedList<>(dots.subList(maxIdx, dots.size()));
            if (sublist1.size() < 2) {
                dots = sublist2;
                return null;
            } else {
                dots = sublist1;
                if (sublist2.size() < 2) return null;
                else {
                    ProtoboardTrace newTrace = new ProtoboardTrace();
                    newTrace.dots = sublist2;
                    return newTrace;
                }
            }
        }
        return null;
    }

    @Override
    public void drawOnProtoboard(GraphicsContext context) {
        context.setFill(Color.SILVER); // TODO: highlight?
        for (int i = 0; i < dots.size() - 1; i++) {
            ProtoboardDot dot = dots.get(i);
            ProtoboardDot linked = dots.get(i+1);
            if (dot.isNeighborTop(linked)) context.fillRect(dot.getX()*24+9, linked.getY()*24+15, 6, 18);
            if (dot.isNeighborBottom(linked)) context.fillRect(dot.getX()*24+9, dot.getY()*24+15, 6, 18);
            if (dot.isNeighborLeft(linked)) context.fillRect(linked.getX()*24+15, dot.getY()*24+9, 18, 6);
            if (dot.isNeighborRight(linked)) context.fillRect(dot.getX()*24+15, dot.getY()*24+9, 18, 6);
        }
    }
}
