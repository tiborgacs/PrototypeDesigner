package prototypedesigner.test.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import prototypedesigner.PrototypeDesigner.components.BipolarJunctionTransistor;
import prototypedesigner.PrototypeDesigner.components.ComponentOrientation;

public class BipolarJunctionTransistorTests {

    @Test
    public void testInstantiation() {
        BipolarJunctionTransistor bjt = new BipolarJunctionTransistor();
        Assertions.assertEquals(3, bjt.getTerminals().size());
        Assertions.assertTrue(bjt.getTerminals().stream().anyMatch(terminal -> terminal.getIdentifier().endsWith("C")));
        Assertions.assertTrue(bjt.getTerminals().stream().anyMatch(terminal -> terminal.getIdentifier().endsWith("B")));
        Assertions.assertTrue(bjt.getTerminals().stream().anyMatch(terminal -> terminal.getIdentifier().endsWith("E")));
    }

    @Test
    void testSchematicPlacement() {
        BipolarJunctionTransistor bjt = new BipolarJunctionTransistor();
        bjt.setSchX(0);
        bjt.setSchY(0);
        Assertions.assertTrue(bjt.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("E") && terminal.getSchX() == 36 && terminal.getSchY() == 48));
        Assertions.assertTrue(bjt.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("B") && terminal.getSchX() == 0 && terminal.getSchY() == 24));
        Assertions.assertTrue(bjt.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("C") && terminal.getSchX() == 36 && terminal.getSchY() == 0));
    }

    @Test
    void testStripboardPlacementUp() {
        BipolarJunctionTransistor bjt = new BipolarJunctionTransistor();
        bjt.setStripboardOrientation(ComponentOrientation.UP);
        bjt.setPinout("C_B_E");
        bjt.setStrX(0);
        bjt.setStrY(0);
        Assertions.assertTrue(bjt.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("E") && terminal.getStrX() == 2 && terminal.getStrY() == 0));
        Assertions.assertTrue(bjt.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("B") && terminal.getStrX() == 1 && terminal.getStrY() == 0));
        Assertions.assertTrue(bjt.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("C") && terminal.getStrX() == 0 && terminal.getStrY() == 0));
    }

    @Test
    void testProtoboardPlacementUp() {
        BipolarJunctionTransistor bjt = new BipolarJunctionTransistor();
        bjt.setProtoboardOrientation(ComponentOrientation.UP);
        bjt.setPinout("E_B_C");
        bjt.setProX(0);
        bjt.setProY(0);
        Assertions.assertTrue(bjt.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("E") && terminal.getProX() == 0 && terminal.getProY() == 0));
        Assertions.assertTrue(bjt.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("B") && terminal.getProX() == 1 && terminal.getProY() == 0));
        Assertions.assertTrue(bjt.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("C") && terminal.getProX() == 2 && terminal.getProY() == 0));
    }

    @Test
    void testStripboardPlacementDown() {
        BipolarJunctionTransistor bjt = new BipolarJunctionTransistor();
        bjt.setStripboardOrientation(ComponentOrientation.DOWN);
        bjt.setPinout("E_B_C");
        bjt.setStrX(0);
        bjt.setStrY(0);
        Assertions.assertTrue(bjt.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("E") && terminal.getStrX() == 2 && terminal.getStrY() == 0));
        Assertions.assertTrue(bjt.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("B") && terminal.getStrX() == 1 && terminal.getStrY() == 0));
        Assertions.assertTrue(bjt.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("C") && terminal.getStrX() == 0 && terminal.getStrY() == 0));
    }

    @Test
    void testProtoboardPlacementDown() {
        BipolarJunctionTransistor bjt = new BipolarJunctionTransistor();
        bjt.setProtoboardOrientation(ComponentOrientation.DOWN);
        bjt.setPinout("C_B_E");
        bjt.setProX(0);
        bjt.setProY(0);
        Assertions.assertTrue(bjt.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("E") && terminal.getProX() == 0 && terminal.getProY() == 0));
        Assertions.assertTrue(bjt.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("B") && terminal.getProX() == 1 && terminal.getProY() == 0));
        Assertions.assertTrue(bjt.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("C") && terminal.getProX() == 2 && terminal.getProY() == 0));
    }

    @Test
    void testStripboardPlacementRight() {
        BipolarJunctionTransistor bjt = new BipolarJunctionTransistor();
        bjt.setStripboardOrientation(ComponentOrientation.RIGHT);
        bjt.setPinout("C_B_E");
        bjt.setStrX(0);
        bjt.setStrY(0);
        Assertions.assertTrue(bjt.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("C") && terminal.getStrX() == 0 && terminal.getStrY() == 2));
        Assertions.assertTrue(bjt.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("B") && terminal.getStrX() == 0 && terminal.getStrY() == 1));
        Assertions.assertTrue(bjt.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("E") && terminal.getStrX() == 0 && terminal.getStrY() == 0));
    }

    @Test
    void testProtoboardPlacementRight() {
        BipolarJunctionTransistor bjt = new BipolarJunctionTransistor();
        bjt.setProtoboardOrientation(ComponentOrientation.RIGHT);
        bjt.setPinout("E_B_C");
        bjt.setProX(0);
        bjt.setProY(0);
        Assertions.assertTrue(bjt.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("E") && terminal.getProX() == 0 && terminal.getProY() == 2));
        Assertions.assertTrue(bjt.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("B") && terminal.getProX() == 0 && terminal.getProY() == 1));
        Assertions.assertTrue(bjt.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("C") && terminal.getProX() == 0 && terminal.getProY() == 0));
    }

    @Test
    void testStripboardPlacementLeft() {
        BipolarJunctionTransistor bjt = new BipolarJunctionTransistor();
        bjt.setStripboardOrientation(ComponentOrientation.LEFT);
        bjt.setPinout("C_B_E");
        bjt.setStrX(0);
        bjt.setStrY(0);
        Assertions.assertTrue(bjt.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("C") && terminal.getStrX() == 0 && terminal.getStrY() == 0));
        Assertions.assertTrue(bjt.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("B") && terminal.getStrX() == 0 && terminal.getStrY() == 1));
        Assertions.assertTrue(bjt.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("E") && terminal.getStrX() == 0 && terminal.getStrY() == 2));
    }

    @Test
    void testProtoboardPlacementLeft() {
        BipolarJunctionTransistor bjt = new BipolarJunctionTransistor();
        bjt.setProtoboardOrientation(ComponentOrientation.LEFT);
        bjt.setPinout("E_B_C");
        bjt.setProX(0);
        bjt.setProY(0);
        Assertions.assertTrue(bjt.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("E") && terminal.getProX() == 0 && terminal.getProY() == 0));
        Assertions.assertTrue(bjt.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("B") && terminal.getProX() == 0 && terminal.getProY() == 1));
        Assertions.assertTrue(bjt.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("C") && terminal.getProX() == 0 && terminal.getProY() == 2));
    }

}
