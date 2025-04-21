package prototypedesigner.test.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import prototypedesigner.PrototypeDesigner.components.ComponentOrientation;
import prototypedesigner.PrototypeDesigner.components.Coordinate;
import prototypedesigner.PrototypeDesigner.components.NonPolarizedCapacitor;

public class NonPolarizedCapacitorTests {

    @Test
    public void testInstantiation() {
        NonPolarizedCapacitor resistor = new NonPolarizedCapacitor();
        Assertions.assertEquals(2, resistor.getTerminals().size());
    }

    @Test
    public void testSchematicsHorizontalPlacement() {
        NonPolarizedCapacitor c1 = new NonPolarizedCapacitor();
        c1.setSchematicsOrientation(ComponentOrientation.LEFT);
        c1.setSchX(0);
        c1.setSchY(0);
        Assertions.assertTrue(c1.getTerminals().stream().anyMatch(terminal ->
                terminal.getSchX() == 0 && terminal.getSchY() == 12));
        Assertions.assertTrue(c1.getTerminals().stream().anyMatch(terminal ->
                terminal.getSchX() == 24 && terminal.getSchY() == 12));
        NonPolarizedCapacitor c2 = new NonPolarizedCapacitor();
        c2.setSchematicsOrientation(ComponentOrientation.RIGHT);
        c2.setSchX(0);
        c2.setSchY(0);
        Assertions.assertTrue(c2.getTerminals().stream().anyMatch(terminal ->
                terminal.getSchX() == 0 && terminal.getSchY() == 12));
        Assertions.assertTrue(c2.getTerminals().stream().anyMatch(terminal ->
                terminal.getSchX() == 24 && terminal.getSchY() == 12));
    }

    @Test
    public void testSchematicsVerticalPlacement() {
        NonPolarizedCapacitor c1 = new NonPolarizedCapacitor();
        c1.setSchematicsOrientation(ComponentOrientation.UP);
        c1.setSchX(0);
        c1.setSchY(0);
        Assertions.assertTrue(c1.getTerminals().stream().anyMatch(terminal ->
                terminal.getSchX() == 12 && terminal.getSchY() == 0));
        Assertions.assertTrue(c1.getTerminals().stream().anyMatch(terminal ->
                terminal.getSchX() == 12 && terminal.getSchY() == 24));
        NonPolarizedCapacitor c2 = new NonPolarizedCapacitor();
        c2.setSchematicsOrientation(ComponentOrientation.DOWN);
        c2.setSchX(0);
        c2.setSchY(0);
        Assertions.assertTrue(c2.getTerminals().stream().anyMatch(terminal ->
                terminal.getSchX() == 12 && terminal.getSchY() == 0));
        Assertions.assertTrue(c2.getTerminals().stream().anyMatch(terminal ->
                terminal.getSchX() == 12 && terminal.getSchY() == 24));
    }

    @Test
    public void testStripboardHorizontalPlacement() {
        NonPolarizedCapacitor c1 = new NonPolarizedCapacitor();
        c1.setSpanningOnStripboard(true);
        c1.setStripboardOrientation(ComponentOrientation.LEFT);
        c1.setStartOnStripboard(new Coordinate(0, 0));
        c1.setEndOnStripboard(new Coordinate(1, 0));
        Assertions.assertTrue(c1.getTerminals().stream().anyMatch(terminal ->
                terminal.getStrX() == 0 && terminal.getStrY() == 0));
        Assertions.assertTrue(c1.getTerminals().stream().anyMatch(terminal ->
                terminal.getStrX() == 1 && terminal.getStrY() == 0));
        NonPolarizedCapacitor c2 = new NonPolarizedCapacitor();
        c2.setSpanningOnStripboard(true);
        c2.setStripboardOrientation(ComponentOrientation.RIGHT);
        c2.setStartOnStripboard(new Coordinate(0, 0));
        c2.setEndOnStripboard(new Coordinate(1, 0));
        Assertions.assertTrue(c2.getTerminals().stream().anyMatch(terminal ->
                terminal.getStrX() == 0 && terminal.getStrY() == 0));
        Assertions.assertTrue(c2.getTerminals().stream().anyMatch(terminal ->
                terminal.getStrX() == 1 && terminal.getStrY() == 0));
    }

    @Test
    public void testStripboardVerticalPlacement() {
        NonPolarizedCapacitor c1 = new NonPolarizedCapacitor();
        c1.setSpanningOnStripboard(true);
        c1.setStripboardOrientation(ComponentOrientation.UP);
        c1.setStartOnStripboard(new Coordinate(0, 0));
        c1.setEndOnStripboard(new Coordinate(0, 1));
        Assertions.assertTrue(c1.getTerminals().stream().anyMatch(terminal ->
                terminal.getStrX() == 0 && terminal.getStrY() == 0));
        Assertions.assertTrue(c1.getTerminals().stream().anyMatch(terminal ->
                terminal.getStrX() == 0 && terminal.getStrY() == 1));
        NonPolarizedCapacitor c2 = new NonPolarizedCapacitor();
        c2.setSpanningOnStripboard(true);
        c2.setStripboardOrientation(ComponentOrientation.DOWN);
        c2.setStartOnStripboard(new Coordinate(0, 0));
        c2.setEndOnStripboard(new Coordinate(0, 1));
        Assertions.assertTrue(c2.getTerminals().stream().anyMatch(terminal ->
                terminal.getStrX() == 0 && terminal.getStrY() == 0));
        Assertions.assertTrue(c2.getTerminals().stream().anyMatch(terminal ->
                terminal.getStrX() == 0 && terminal.getStrY() == 1));
    }

    @Test
    public void testProtoboardHorizontalPlacement() {
        NonPolarizedCapacitor c1 = new NonPolarizedCapacitor();
        c1.setSpanningOnProtoboard(true);
        c1.setProtoboardOrientation(ComponentOrientation.LEFT);
        c1.setStartOnProtoboard(new Coordinate(0, 0));
        c1.setEndOnProtoboard(new Coordinate(1, 0));
        Assertions.assertTrue(c1.getTerminals().stream().anyMatch(terminal ->
                terminal.getProX() == 0 && terminal.getProY() == 0));
        Assertions.assertTrue(c1.getTerminals().stream().anyMatch(terminal ->
                terminal.getProX() == 1 && terminal.getProY() == 0));
        NonPolarizedCapacitor c2 = new NonPolarizedCapacitor();
        c2.setSpanningOnProtoboard(true);
        c2.setProtoboardOrientation(ComponentOrientation.RIGHT);
        c2.setStartOnProtoboard(new Coordinate(0, 0));
        c2.setEndOnProtoboard(new Coordinate(1, 0));
        Assertions.assertTrue(c2.getTerminals().stream().anyMatch(terminal ->
                terminal.getProX() == 0 && terminal.getProY() == 0));
        Assertions.assertTrue(c2.getTerminals().stream().anyMatch(terminal ->
                terminal.getProX() == 1 && terminal.getProY() == 0));
    }

    @Test
    public void testProtoboardVerticalPlacement() {
        NonPolarizedCapacitor c1 = new NonPolarizedCapacitor();
        c1.setSpanningOnProtoboard(true);
        c1.setProtoboardOrientation(ComponentOrientation.UP);
        c1.setStartOnProtoboard(new Coordinate(0, 0));
        c1.setEndOnProtoboard(new Coordinate(0, 1));
        Assertions.assertTrue(c1.getTerminals().stream().anyMatch(terminal ->
                terminal.getProX() == 0 && terminal.getProY() == 0));
        Assertions.assertTrue(c1.getTerminals().stream().anyMatch(terminal ->
                terminal.getProX() == 0 && terminal.getProY() == 1));
        NonPolarizedCapacitor c2 = new NonPolarizedCapacitor();
        c2.setSpanningOnProtoboard(true);
        c2.setProtoboardOrientation(ComponentOrientation.DOWN);
        c2.setStartOnProtoboard(new Coordinate(0, 0));
        c2.setEndOnProtoboard(new Coordinate(0, 1));
        Assertions.assertTrue(c2.getTerminals().stream().anyMatch(terminal ->
                terminal.getProX() == 0 && terminal.getProY() == 0));
        Assertions.assertTrue(c2.getTerminals().stream().anyMatch(terminal ->
                terminal.getProX() == 0 && terminal.getProY() == 1));
    }

}
