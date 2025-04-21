package prototypedesigner.test.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import prototypedesigner.PrototypeDesigner.components.ComponentOrientation;
import prototypedesigner.PrototypeDesigner.components.JunctionFieldEffectTransistor;

public class JfetTests {

    @Test
    public void testInstantiation() {
        JunctionFieldEffectTransistor jfet = new JunctionFieldEffectTransistor();
        Assertions.assertEquals(3, jfet.getTerminals().size());
        Assertions.assertTrue(jfet.getTerminals().stream().anyMatch(terminal -> terminal.getIdentifier().endsWith("D")));
        Assertions.assertTrue(jfet.getTerminals().stream().anyMatch(terminal -> terminal.getIdentifier().endsWith("S")));
        Assertions.assertTrue(jfet.getTerminals().stream().anyMatch(terminal -> terminal.getIdentifier().endsWith("G")));
    }

    @Test
    void testSchematicPlacement() {
        JunctionFieldEffectTransistor jfet = new JunctionFieldEffectTransistor();
        jfet.setSchX(0);
        jfet.setSchY(0);
        Assertions.assertTrue(jfet.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("S") && terminal.getSchX() == 36 && terminal.getSchY() == 48));
        Assertions.assertTrue(jfet.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("G") && terminal.getSchX() == 0 && terminal.getSchY() == 24));
        Assertions.assertTrue(jfet.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("D") && terminal.getSchX() == 36 && terminal.getSchY() == 0));
    }

    @Test
    void testStripboardPlacementUp() {
        JunctionFieldEffectTransistor jfet = new JunctionFieldEffectTransistor();
        jfet.setStripboardOrientation(ComponentOrientation.UP);
        jfet.setPinout("D_G_S");
        jfet.setStrX(0);
        jfet.setStrY(0);
        Assertions.assertTrue(jfet.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("S") && terminal.getStrX() == 2 && terminal.getStrY() == 0));
        Assertions.assertTrue(jfet.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("G") && terminal.getStrX() == 1 && terminal.getStrY() == 0));
        Assertions.assertTrue(jfet.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("D") && terminal.getStrX() == 0 && terminal.getStrY() == 0));
    }

    @Test
    void testProtoboardPlacementUp() {
        JunctionFieldEffectTransistor jfet = new JunctionFieldEffectTransistor();
        jfet.setProtoboardOrientation(ComponentOrientation.UP);
        jfet.setPinout("D_G_S");
        jfet.setProX(0);
        jfet.setProY(0);
        Assertions.assertTrue(jfet.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("D") && terminal.getProX() == 0 && terminal.getProY() == 0));
        Assertions.assertTrue(jfet.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("G") && terminal.getProX() == 1 && terminal.getProY() == 0));
        Assertions.assertTrue(jfet.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("S") && terminal.getProX() == 2 && terminal.getProY() == 0));
    }

    @Test
    void testStripboardPlacementDown() {
        JunctionFieldEffectTransistor jfet = new JunctionFieldEffectTransistor();
        jfet.setStripboardOrientation(ComponentOrientation.DOWN);
        jfet.setPinout("D_G_S");
        jfet.setStrX(0);
        jfet.setStrY(0);
        Assertions.assertTrue(jfet.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("D") && terminal.getStrX() == 2 && terminal.getStrY() == 0));
        Assertions.assertTrue(jfet.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("G") && terminal.getStrX() == 1 && terminal.getStrY() == 0));
        Assertions.assertTrue(jfet.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("S") && terminal.getStrX() == 0 && terminal.getStrY() == 0));
    }

    @Test
    void testProtoboardPlacementDown() {
        JunctionFieldEffectTransistor jfet = new JunctionFieldEffectTransistor();
        jfet.setProtoboardOrientation(ComponentOrientation.DOWN);
        jfet.setPinout("D_G_S");
        jfet.setProX(0);
        jfet.setProY(0);
        Assertions.assertTrue(jfet.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("S") && terminal.getProX() == 0 && terminal.getProY() == 0));
        Assertions.assertTrue(jfet.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("G") && terminal.getProX() == 1 && terminal.getProY() == 0));
        Assertions.assertTrue(jfet.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("D") && terminal.getProX() == 2 && terminal.getProY() == 0));
    }

    @Test
    void testStripboardPlacementRight() {
        JunctionFieldEffectTransistor jfet = new JunctionFieldEffectTransistor();
        jfet.setStripboardOrientation(ComponentOrientation.RIGHT);
        jfet.setPinout("D_G_S");
        jfet.setStrX(0);
        jfet.setStrY(0);
        Assertions.assertTrue(jfet.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("D") && terminal.getStrX() == 0 && terminal.getStrY() == 2));
        Assertions.assertTrue(jfet.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("G") && terminal.getStrX() == 0 && terminal.getStrY() == 1));
        Assertions.assertTrue(jfet.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("S") && terminal.getStrX() == 0 && terminal.getStrY() == 0));
    }

    @Test
    void testProtoboardPlacementRight() {
        JunctionFieldEffectTransistor jfet = new JunctionFieldEffectTransistor();
        jfet.setProtoboardOrientation(ComponentOrientation.RIGHT);
        jfet.setPinout("D_G_S");
        jfet.setProX(0);
        jfet.setProY(0);
        Assertions.assertTrue(jfet.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("D") && terminal.getProX() == 0 && terminal.getProY() == 2));
        Assertions.assertTrue(jfet.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("G") && terminal.getProX() == 0 && terminal.getProY() == 1));
        Assertions.assertTrue(jfet.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("S") && terminal.getProX() == 0 && terminal.getProY() == 0));
    }

    @Test
    void testStripboardPlacementLeft() {
        JunctionFieldEffectTransistor jfet = new JunctionFieldEffectTransistor();
        jfet.setStripboardOrientation(ComponentOrientation.LEFT);
        jfet.setPinout("D_G_S");
        jfet.setStrX(0);
        jfet.setStrY(0);
        Assertions.assertTrue(jfet.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("D") && terminal.getStrX() == 0 && terminal.getStrY() == 0));
        Assertions.assertTrue(jfet.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("G") && terminal.getStrX() == 0 && terminal.getStrY() == 1));
        Assertions.assertTrue(jfet.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("S") && terminal.getStrX() == 0 && terminal.getStrY() == 2));
    }

    @Test
    void testProtoboardPlacementLeft() {
        JunctionFieldEffectTransistor jfet = new JunctionFieldEffectTransistor();
        jfet.setProtoboardOrientation(ComponentOrientation.LEFT);
        jfet.setPinout("D_G_S");
        jfet.setProX(0);
        jfet.setProY(0);
        Assertions.assertTrue(jfet.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("D") && terminal.getProX() == 0 && terminal.getProY() == 0));
        Assertions.assertTrue(jfet.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("G") && terminal.getProX() == 0 && terminal.getProY() == 1));
        Assertions.assertTrue(jfet.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("S") && terminal.getProX() == 0 && terminal.getProY() == 2));
    }

}
