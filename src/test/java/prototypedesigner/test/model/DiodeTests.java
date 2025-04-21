package prototypedesigner.test.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import prototypedesigner.PrototypeDesigner.components.ComponentOrientation;
import prototypedesigner.PrototypeDesigner.components.Diode;

public class DiodeTests {

    @Test
    public void testInstantiation() {
        Diode diode = new Diode();
        Assertions.assertEquals(2, diode.getTerminals().size());
        Assertions.assertTrue(diode.getTerminals().stream().anyMatch(terminal -> terminal.getIdentifier().endsWith("A")));
        Assertions.assertTrue(diode.getTerminals().stream().anyMatch(terminal -> terminal.getIdentifier().endsWith("C")));
    }

    @Test
    public void testSchematicPlacementUp() {
        Diode diode = new Diode();
        diode.setSchematicsOrientation(ComponentOrientation.UP);
        diode.setSchX(0);
        diode.setSchY(0);
        Assertions.assertTrue(diode.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("A") && terminal.getSchX() == 12 && terminal.getSchY() == 24));
        Assertions.assertTrue(diode.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("C") && terminal.getSchX() == 12 && terminal.getSchY() == 0));
    }

    @Test
    public void testSchematicPlacementDown() {
        Diode diode = new Diode();
        diode.setSchematicsOrientation(ComponentOrientation.DOWN);
        diode.setSchX(0);
        diode.setSchY(0);
        Assertions.assertTrue(diode.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("A") && terminal.getSchX() == 12 && terminal.getSchY() == 0));
        Assertions.assertTrue(diode.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("C") && terminal.getSchX() == 12 && terminal.getSchY() == 24));
    }

    @Test
    public void testSchematicPlacementLeft() {
        Diode diode = new Diode();
        diode.setSchematicsOrientation(ComponentOrientation.LEFT);
        diode.setSchX(0);
        diode.setSchY(0);
        Assertions.assertTrue(diode.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("A") && terminal.getSchX() == 24 && terminal.getSchY() == 12));
        Assertions.assertTrue(diode.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("C") && terminal.getSchX() == 0 && terminal.getSchY() == 12));
    }

    @Test
    public void testSchematicPlacementRight() {
        Diode diode = new Diode();
        diode.setSchematicsOrientation(ComponentOrientation.RIGHT);
        diode.setSchX(0);
        diode.setSchY(0);
        Assertions.assertTrue(diode.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("A") && terminal.getSchX() == 0 && terminal.getSchY() == 12));
        Assertions.assertTrue(diode.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("C") && terminal.getSchX() == 24 && terminal.getSchY() == 12));
    }

}
