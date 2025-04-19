package prototypedesigner.test.builders;

import javafx.util.Pair;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import prototypedesigner.PrototypeDesigner.components.*;

import java.util.ArrayList;
import java.util.Collections;

public class WireBuilderTests {

    @Test
    public void testWireBuilder() {
        WireBuilder wb = new WireBuilder(1, 1);
        Assertions.assertEquals(1, wb.getWire().getSchPoints().getLast().getX());
        Assertions.assertEquals(1, wb.getWire().getSchPoints().getLast().getY());
        wb.addCoordinates(2, 2);
        Assertions.assertEquals(1, wb.getWire().getSchPoints().getLast().getX());
        Assertions.assertEquals(2, wb.getWire().getSchPoints().getLast().getY());
        Assertions.assertEquals(2, wb.getWire().getSchPoints().size());
        WireBuilder.ConnectionContainer container = wb.checkForConnections(
                new ArrayList<>(), new ArrayList<>());
        Assertions.assertNull(container);
    }

    @Test
    public void testCrossing() {
        Wire wire = new Wire();
        wire.getSchPoints().add(new Coordinate(0, 1));
        wire.getSchPoints().add(new Coordinate(2, 1));
        WireBuilder wb = new WireBuilder(1, 0);
        wb.addCoordinates(1, 2);
        WireBuilder.ConnectionContainer container = wb.checkForConnections(
                Collections.singletonList(wire), new ArrayList<>());
        Assertions.assertFalse(container.isEmpty());
        Assertions.assertEquals(1, container.getIntersectionCandidates().size());
        Pair<Wire, Coordinate> pair = container.getIntersectionCandidates().get(0);
        Assertions.assertSame(wire, pair.getKey());
        Assertions.assertEquals(1, pair.getValue().getX());
        Assertions.assertEquals(1, pair.getValue().getY());
    }

    @Test
    public void testConnecting() {
        Resistor r1 = new Resistor();
        r1.setSchematicsOrientation(ComponentOrientation.UP);
        r1.setSchX(0); // terminal x = x + 12
        r1.setSchY(0); // terminal y = y, y = y + 48
        WireBuilder wb = new WireBuilder(new Coordinate(12, 0));
        wb.addCoordinates(24, 0);
        WireBuilder.ConnectionContainer container = wb.checkForConnections(
                new ArrayList<>(), Collections.singletonList(r1));
        Assertions.assertFalse(container.isEmpty());
        Assertions.assertTrue(container.getIntersectionCandidates().isEmpty());
        Assertions.assertEquals(1, container.getComponentTerminals().size());
        Assertions.assertSame(r1, container.getComponentTerminals().get(0).getComponent());
        Assertions.assertSame(r1.getTerminals().get(0), container.getComponentTerminals().get(0));
    }
}
