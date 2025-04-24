package prototypedesigner.PrototypeDesigner;

import lombok.Getter;
import prototypedesigner.PrototypeDesigner.components.Coordinate;
import prototypedesigner.PrototypeDesigner.components.Wire;

/**
 * Tree item to list wires and their coordinates in the same table.
 */
@Getter
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

}
