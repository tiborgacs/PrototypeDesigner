package prototypedesigner.PrototypeDesigner.components;

import javafx.util.Pair;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

import static prototypedesigner.PrototypeDesigner.Utility.tail;

public class WireBuilder {

    @Getter
    private Wire wire = new Wire();

    public WireBuilder(Coordinate start) {
        wire.getSchPoints().add(start);
        wire.setHighlighted(true);
    }

    public  WireBuilder(int x, int y) {
        wire.drawSch(x, y);
        wire.setHighlighted(true);
    }

    public Wire addCoordinates(int x, int y) {
        Coordinate last = tail(wire.getSchPoints());
        if (last.getX() != x || last.getY() != y)
            wire.drawSch(x, y);
        return wire;
    }

    public ConnectionContainer checkForConnections(List<Wire> wires, List<Component> components) {
        ConnectionContainer container = null;
        for (Wire wire : wires) {
            for (int i = 0; i < wire.getSchPoints().size() - 1; i++) {
                for (int j = 0; j < this.wire.getSchPoints().size() - 1; j++) {
                    Coordinate intersection = Wire.intersects(
                            this.wire.getSchPoints().get(j),
                            this.wire.getSchPoints().get(j+1),
                            wire.getSchPoints().get(i),
                            wire.getSchPoints().get(i+1));
                    if (intersection != null) {
                        if (container == null) container = new ConnectionContainer(this.wire);
                        container.intersectionCandidates.add(new Pair<>(wire, intersection));
                    }
                }
            }
        }
        for (Coordinate c: wire.getSchPoints()) {
            for (Component component : components) {
                for (Terminal terminal : component.getTerminals()) {
                    if (terminal.getSchX() == c.getX() && terminal.getSchY() == c.getY()) {
                        if (container == null)
                            container = new ConnectionContainer(this.wire);
                        if (!container.componentTerminals.contains(terminal))
                            container.componentTerminals.add(terminal);
                    }
                }
            }
        }
        return container;
    }

    @Getter
    public class ConnectionContainer {
        private Wire wire;
        private List<Pair<Wire, Coordinate>> intersectionCandidates;
        private List<Terminal> componentTerminals;

        public ConnectionContainer(Wire wire) {
            this.wire = wire;
            intersectionCandidates = new ArrayList<>();
            componentTerminals = new ArrayList<>();
        }

        public boolean isEmpty() {
            return intersectionCandidates.isEmpty() && componentTerminals.isEmpty();
        }
    }
}
