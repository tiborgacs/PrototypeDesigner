package prototypedesigner.test.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import prototypedesigner.PrototypeDesigner.components.Coordinate;
import prototypedesigner.PrototypeDesigner.components.Wire;

public class SchematicWireTests {

    @Test
    public void testConnection() {
        Wire w1 = new Wire();
        Wire w2 = new Wire();
        Coordinate intersection = new Coordinate(0, 0);
        w1.connectToWire(w2, intersection);
        Assertions.assertTrue(w1.getConnectedWires().contains(w2));
        Assertions.assertTrue(w2.getConnectedWires().contains(w1));
        Assertions.assertTrue(w1.getIntersections().contains(intersection));
        Assertions.assertTrue(w2.getIntersections().contains(intersection));
    }

    @Test
    public void testAddingCoordinates() {
        Wire wire = new Wire();
        wire.drawSch(0, 0);
        wire.drawSch(2, 1);
        Assertions.assertEquals(2, wire.getSchPoints().getLast().getX());
        Assertions.assertEquals(0, wire.getSchPoints().getLast().getY());
        wire.drawSch(1, 2);
        Assertions.assertEquals(2, wire.getSchPoints().getLast().getX());
        Assertions.assertEquals(2, wire.getSchPoints().getLast().getY());
    }

    @Test
    public void testIntersection() {
        Coordinate a = new Coordinate(0, 1);
        Coordinate b = new Coordinate(2, 1);
        Coordinate c = new Coordinate(1, 0);
        Coordinate d = new Coordinate(1, 2);
        Coordinate intersection = Wire.intersects(a, b, c, d);
        Assertions.assertNotNull(intersection);
        Assertions.assertEquals(1, intersection.getX());
        Assertions.assertEquals(1, intersection.getY());
    }

    @Test
    public void testNoIntersection() {
        Coordinate a = new Coordinate(0, 1);
        Coordinate b = new Coordinate(2, 1);
        Coordinate c = new Coordinate(3, 0);
        Coordinate d = new Coordinate(3, 2);
        Coordinate intersection = Wire.intersects(a, b, c, d);
        Assertions.assertNull(intersection);
    }

}
