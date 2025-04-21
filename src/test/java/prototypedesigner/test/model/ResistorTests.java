package prototypedesigner.test.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import prototypedesigner.PrototypeDesigner.components.ComponentOrientation;
import prototypedesigner.PrototypeDesigner.components.Coordinate;
import prototypedesigner.PrototypeDesigner.components.Resistor;

public class ResistorTests {

    @Test
    public void testInstantiation() {
        Resistor resistor = new Resistor();
        Assertions.assertEquals(2, resistor.getTerminals().size());
    }

    @Test
    public void testSchematicsHorizontalPlacement() {
        Resistor r1 = new Resistor();
        r1.setSchematicsOrientation(ComponentOrientation.LEFT);
        r1.setSchX(0);
        r1.setSchY(0);
        Assertions.assertTrue(r1.getTerminals().stream().anyMatch(terminal ->
                terminal.getSchX() == 0 && terminal.getSchY() == 12));
        Assertions.assertTrue(r1.getTerminals().stream().anyMatch(terminal ->
                terminal.getSchX() == 48 && terminal.getSchY() == 12));
        Resistor r2 = new Resistor();
        r2.setSchematicsOrientation(ComponentOrientation.RIGHT);
        r2.setSchX(0);
        r2.setSchY(0);
        Assertions.assertTrue(r2.getTerminals().stream().anyMatch(terminal ->
                terminal.getSchX() == 0 && terminal.getSchY() == 12));
        Assertions.assertTrue(r2.getTerminals().stream().anyMatch(terminal ->
                terminal.getSchX() == 48 && terminal.getSchY() == 12));
    }

    @Test
    public void testSchematicsVerticalPlacement() {
        Resistor r1 = new Resistor();
        r1.setSchematicsOrientation(ComponentOrientation.UP);
        r1.setSchX(0);
        r1.setSchY(0);
        Assertions.assertTrue(r1.getTerminals().stream().anyMatch(terminal ->
                terminal.getSchX() == 12 && terminal.getSchY() == 0));
        Assertions.assertTrue(r1.getTerminals().stream().anyMatch(terminal ->
                terminal.getSchX() == 12 && terminal.getSchY() == 48));
        Resistor r2 = new Resistor();
        r2.setSchematicsOrientation(ComponentOrientation.DOWN);
        r2.setSchX(0);
        r2.setSchY(0);
        Assertions.assertTrue(r2.getTerminals().stream().anyMatch(terminal ->
                terminal.getSchX() == 12 && terminal.getSchY() == 0));
        Assertions.assertTrue(r2.getTerminals().stream().anyMatch(terminal ->
                terminal.getSchX() == 12 && terminal.getSchY() == 48));
    }

    @Test
    public void testStripboardHorizontalPlacement() {
        Resistor r1 = new Resistor();
        r1.setSpanningOnStripboard(true);
        r1.setStripboardOrientation(ComponentOrientation.LEFT);
        r1.setStartOnStripboard(new Coordinate(0, 0));
        r1.setEndOnStripboard(new Coordinate(1, 0));
        Assertions.assertTrue(r1.getTerminals().stream().anyMatch(terminal ->
                terminal.getStrX() == 0 && terminal.getStrY() == 0));
        Assertions.assertTrue(r1.getTerminals().stream().anyMatch(terminal ->
                terminal.getStrX() == 1 && terminal.getStrY() == 0));
        Resistor r2 = new Resistor();
        r2.setSpanningOnStripboard(true);
        r2.setStripboardOrientation(ComponentOrientation.RIGHT);
        r2.setStartOnStripboard(new Coordinate(0, 0));
        r2.setEndOnStripboard(new Coordinate(1, 0));
        Assertions.assertTrue(r2.getTerminals().stream().anyMatch(terminal ->
                terminal.getStrX() == 0 && terminal.getStrY() == 0));
        Assertions.assertTrue(r2.getTerminals().stream().anyMatch(terminal ->
                terminal.getStrX() == 1 && terminal.getStrY() == 0));
    }

    @Test
    public void testStripboardVerticalPlacement() {
        Resistor r1 = new Resistor();
        r1.setSpanningOnStripboard(true);
        r1.setStripboardOrientation(ComponentOrientation.UP);
        r1.setStartOnStripboard(new Coordinate(0, 0));
        r1.setEndOnStripboard(new Coordinate(0, 1));
        Assertions.assertTrue(r1.getTerminals().stream().anyMatch(terminal ->
                terminal.getStrX() == 0 && terminal.getStrY() == 0));
        Assertions.assertTrue(r1.getTerminals().stream().anyMatch(terminal ->
                terminal.getStrX() == 0 && terminal.getStrY() == 1));
        Resistor r2 = new Resistor();
        r2.setSpanningOnStripboard(true);
        r2.setStripboardOrientation(ComponentOrientation.DOWN);
        r2.setStartOnStripboard(new Coordinate(0, 0));
        r2.setEndOnStripboard(new Coordinate(0, 1));
        Assertions.assertTrue(r2.getTerminals().stream().anyMatch(terminal ->
                terminal.getStrX() == 0 && terminal.getStrY() == 0));
        Assertions.assertTrue(r2.getTerminals().stream().anyMatch(terminal ->
                terminal.getStrX() == 0 && terminal.getStrY() == 1));
    }

    @Test
    public void testProtoboardHorizontalPlacement() {
        Resistor r1 = new Resistor();
        r1.setSpanningOnProtoboard(true);
        r1.setProtoboardOrientation(ComponentOrientation.LEFT);
        r1.setStartOnProtoboard(new Coordinate(0, 0));
        r1.setEndOnProtoboard(new Coordinate(1, 0));
        Assertions.assertTrue(r1.getTerminals().stream().anyMatch(terminal ->
                terminal.getProX() == 0 && terminal.getProY() == 0));
        Assertions.assertTrue(r1.getTerminals().stream().anyMatch(terminal ->
                terminal.getProX() == 1 && terminal.getProY() == 0));
        Resistor r2 = new Resistor();
        r2.setSpanningOnProtoboard(true);
        r2.setProtoboardOrientation(ComponentOrientation.RIGHT);
        r2.setStartOnProtoboard(new Coordinate(0, 0));
        r2.setEndOnProtoboard(new Coordinate(1, 0));
        Assertions.assertTrue(r2.getTerminals().stream().anyMatch(terminal ->
                terminal.getProX() == 0 && terminal.getProY() == 0));
        Assertions.assertTrue(r2.getTerminals().stream().anyMatch(terminal ->
                terminal.getProX() == 1 && terminal.getProY() == 0));
    }

    @Test
    public void testProtoboardVerticalPlacement() {
        Resistor r1 = new Resistor();
        r1.setSpanningOnProtoboard(true);
        r1.setProtoboardOrientation(ComponentOrientation.UP);
        r1.setStartOnProtoboard(new Coordinate(0, 0));
        r1.setEndOnProtoboard(new Coordinate(0, 1));
        Assertions.assertTrue(r1.getTerminals().stream().anyMatch(terminal ->
                terminal.getProX() == 0 && terminal.getProY() == 0));
        Assertions.assertTrue(r1.getTerminals().stream().anyMatch(terminal ->
                terminal.getProX() == 0 && terminal.getProY() == 1));
        Resistor r2 = new Resistor();
        r2.setSpanningOnProtoboard(true);
        r2.setProtoboardOrientation(ComponentOrientation.DOWN);
        r2.setStartOnProtoboard(new Coordinate(0, 0));
        r2.setEndOnProtoboard(new Coordinate(0, 1));
        Assertions.assertTrue(r2.getTerminals().stream().anyMatch(terminal ->
                terminal.getProX() == 0 && terminal.getProY() == 0));
        Assertions.assertTrue(r2.getTerminals().stream().anyMatch(terminal ->
                terminal.getProX() == 0 && terminal.getProY() == 1));
    }

}
