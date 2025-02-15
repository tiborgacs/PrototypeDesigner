package prototypedesigner.PrototypeDesigner;

import javafx.scene.control.TreeItem;
import prototypedesigner.PrototypeDesigner.components.Coordinate;
import prototypedesigner.PrototypeDesigner.components.Wire;

public class SchematicsWiringItem {

    private Wire wire;
    private Coordinate coordinate;

    public SchematicsWiringItem() {
        // root
    }

    public SchematicsWiringItem(Wire wire) {
        this.wire = wire;
    }

    public SchematicsWiringItem(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public Wire getWire() {
        return wire;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }
}
