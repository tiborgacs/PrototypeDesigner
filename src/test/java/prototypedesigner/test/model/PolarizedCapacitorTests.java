package prototypedesigner.test.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import prototypedesigner.PrototypeDesigner.components.ComponentOrientation;
import prototypedesigner.PrototypeDesigner.components.Coordinate;
import prototypedesigner.PrototypeDesigner.components.PolarizedCapacitor;

public class PolarizedCapacitorTests {

    @Test
    public void testInstantiation() {
        PolarizedCapacitor capacitor = new PolarizedCapacitor();
        Assertions.assertEquals(2, capacitor.getTerminals().size());
        Assertions.assertTrue(capacitor.getTerminals().stream().anyMatch(terminal -> terminal.getIdentifier().endsWith("A")));
        Assertions.assertTrue(capacitor.getTerminals().stream().anyMatch(terminal -> terminal.getIdentifier().endsWith("C")));
    }

    @Test
    public void testSchematicPlacementUp() {
        PolarizedCapacitor capacitor = new PolarizedCapacitor();
        capacitor.setSchematicsOrientation(ComponentOrientation.UP);
        capacitor.setSchX(0);
        capacitor.setSchY(0);
        Assertions.assertTrue(capacitor.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("A") && terminal.getSchX() == 12 && terminal.getSchY() == 0));
        Assertions.assertTrue(capacitor.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("C") && terminal.getSchX() == 12 && terminal.getSchY() == 24));
    }

    @Test
    public void testSchematicPlacementDown() {
        PolarizedCapacitor capacitor = new PolarizedCapacitor();
        capacitor.setSchematicsOrientation(ComponentOrientation.DOWN);
        capacitor.setSchX(0);
        capacitor.setSchY(0);
        Assertions.assertTrue(capacitor.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("A") && terminal.getSchX() == 12 && terminal.getSchY() == 24));
        Assertions.assertTrue(capacitor.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("C") && terminal.getSchX() == 12 && terminal.getSchY() == 0));
    }

    @Test
    public void testSchematicPlacementLeft() {
        PolarizedCapacitor capacitor = new PolarizedCapacitor();
        capacitor.setSchematicsOrientation(ComponentOrientation.LEFT);
        capacitor.setSchX(0);
        capacitor.setSchY(0);
        Assertions.assertTrue(capacitor.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("A") && terminal.getSchX() == 0 && terminal.getSchY() == 12));
        Assertions.assertTrue(capacitor.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("C") && terminal.getSchX() == 24 && terminal.getSchY() == 12));
    }

    @Test
    public void testSchematicPlacementRight() {
        PolarizedCapacitor capacitor = new PolarizedCapacitor();
        capacitor.setSchematicsOrientation(ComponentOrientation.RIGHT);
        capacitor.setSchX(0);
        capacitor.setSchY(0);
        Assertions.assertTrue(capacitor.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("A") && terminal.getSchX() == 24 && terminal.getSchY() == 12));
        Assertions.assertTrue(capacitor.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("C") && terminal.getSchX() == 0 && terminal.getSchY() == 12));
    }

}
