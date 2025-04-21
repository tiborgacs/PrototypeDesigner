package prototypedesigner.test.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import prototypedesigner.PrototypeDesigner.components.ComponentOrientation;
import prototypedesigner.PrototypeDesigner.components.MetalOxideSemiconductorFET;

public class MosfetTests {

    @Test
    public void testInstantiation() {
        MetalOxideSemiconductorFET mosfet = new MetalOxideSemiconductorFET();
        Assertions.assertEquals(3, mosfet.getTerminals().size());
        Assertions.assertTrue(mosfet.getTerminals().stream().anyMatch(terminal -> terminal.getIdentifier().endsWith("D")));
        Assertions.assertTrue(mosfet.getTerminals().stream().anyMatch(terminal -> terminal.getIdentifier().endsWith("S")));
        Assertions.assertTrue(mosfet.getTerminals().stream().anyMatch(terminal -> terminal.getIdentifier().endsWith("G")));
    }

    @Test
    void testSchematicPlacement() {
        MetalOxideSemiconductorFET mosfet = new MetalOxideSemiconductorFET();
        mosfet.setSchX(0);
        mosfet.setSchY(0);
        Assertions.assertTrue(mosfet.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("S") && terminal.getSchX() == 36 && terminal.getSchY() == 48));
        Assertions.assertTrue(mosfet.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("G") && terminal.getSchX() == 0 && terminal.getSchY() == 24));
        Assertions.assertTrue(mosfet.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("D") && terminal.getSchX() == 36 && terminal.getSchY() == 0));
    }

    @Test
    void testStripboardPlacementUp() {
        MetalOxideSemiconductorFET mosfet = new MetalOxideSemiconductorFET();
        mosfet.setStripboardOrientation(ComponentOrientation.UP);
        mosfet.setPinout("D_G_S");
        mosfet.setStrX(0);
        mosfet.setStrY(0);
        Assertions.assertTrue(mosfet.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("S") && terminal.getStrX() == 2 && terminal.getStrY() == 0));
        Assertions.assertTrue(mosfet.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("G") && terminal.getStrX() == 1 && terminal.getStrY() == 0));
        Assertions.assertTrue(mosfet.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("D") && terminal.getStrX() == 0 && terminal.getStrY() == 0));
    }

    @Test
    void testProtoboardPlacementUp() {
        MetalOxideSemiconductorFET mosfet = new MetalOxideSemiconductorFET();
        mosfet.setProtoboardOrientation(ComponentOrientation.UP);
        mosfet.setPinout("D_G_S");
        mosfet.setProX(0);
        mosfet.setProY(0);
        Assertions.assertTrue(mosfet.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("D") && terminal.getProX() == 0 && terminal.getProY() == 0));
        Assertions.assertTrue(mosfet.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("G") && terminal.getProX() == 1 && terminal.getProY() == 0));
        Assertions.assertTrue(mosfet.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("S") && terminal.getProX() == 2 && terminal.getProY() == 0));
    }

    @Test
    void testStripboardPlacementDown() {
        MetalOxideSemiconductorFET mosfet = new MetalOxideSemiconductorFET();
        mosfet.setStripboardOrientation(ComponentOrientation.DOWN);
        mosfet.setPinout("D_G_S");
        mosfet.setStrX(0);
        mosfet.setStrY(0);
        Assertions.assertTrue(mosfet.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("D") && terminal.getStrX() == 2 && terminal.getStrY() == 0));
        Assertions.assertTrue(mosfet.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("G") && terminal.getStrX() == 1 && terminal.getStrY() == 0));
        Assertions.assertTrue(mosfet.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("S") && terminal.getStrX() == 0 && terminal.getStrY() == 0));
    }

    @Test
    void testProtoboardPlacementDown() {
        MetalOxideSemiconductorFET mosfet = new MetalOxideSemiconductorFET();
        mosfet.setProtoboardOrientation(ComponentOrientation.DOWN);
        mosfet.setPinout("D_G_S");
        mosfet.setProX(0);
        mosfet.setProY(0);
        Assertions.assertTrue(mosfet.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("S") && terminal.getProX() == 0 && terminal.getProY() == 0));
        Assertions.assertTrue(mosfet.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("G") && terminal.getProX() == 1 && terminal.getProY() == 0));
        Assertions.assertTrue(mosfet.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("D") && terminal.getProX() == 2 && terminal.getProY() == 0));
    }

    @Test
    void testStripboardPlacementRight() {
        MetalOxideSemiconductorFET mosfet = new MetalOxideSemiconductorFET();
        mosfet.setStripboardOrientation(ComponentOrientation.RIGHT);
        mosfet.setPinout("D_G_S");
        mosfet.setStrX(0);
        mosfet.setStrY(0);
        Assertions.assertTrue(mosfet.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("D") && terminal.getStrX() == 0 && terminal.getStrY() == 2));
        Assertions.assertTrue(mosfet.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("G") && terminal.getStrX() == 0 && terminal.getStrY() == 1));
        Assertions.assertTrue(mosfet.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("S") && terminal.getStrX() == 0 && terminal.getStrY() == 0));
    }

    @Test
    void testProtoboardPlacementRight() {
        MetalOxideSemiconductorFET mosfet = new MetalOxideSemiconductorFET();
        mosfet.setProtoboardOrientation(ComponentOrientation.RIGHT);
        mosfet.setPinout("D_G_S");
        mosfet.setProX(0);
        mosfet.setProY(0);
        Assertions.assertTrue(mosfet.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("D") && terminal.getProX() == 0 && terminal.getProY() == 2));
        Assertions.assertTrue(mosfet.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("G") && terminal.getProX() == 0 && terminal.getProY() == 1));
        Assertions.assertTrue(mosfet.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("S") && terminal.getProX() == 0 && terminal.getProY() == 0));
    }

    @Test
    void testStripboardPlacementLeft() {
        MetalOxideSemiconductorFET mosfet = new MetalOxideSemiconductorFET();
        mosfet.setStripboardOrientation(ComponentOrientation.LEFT);
        mosfet.setPinout("D_G_S");
        mosfet.setStrX(0);
        mosfet.setStrY(0);
        Assertions.assertTrue(mosfet.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("D") && terminal.getStrX() == 0 && terminal.getStrY() == 0));
        Assertions.assertTrue(mosfet.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("G") && terminal.getStrX() == 0 && terminal.getStrY() == 1));
        Assertions.assertTrue(mosfet.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("S") && terminal.getStrX() == 0 && terminal.getStrY() == 2));
    }

    @Test
    void testProtoboardPlacementLeft() {
        MetalOxideSemiconductorFET mosfet = new MetalOxideSemiconductorFET();
        mosfet.setProtoboardOrientation(ComponentOrientation.LEFT);
        mosfet.setPinout("D_G_S");
        mosfet.setProX(0);
        mosfet.setProY(0);
        Assertions.assertTrue(mosfet.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("D") && terminal.getProX() == 0 && terminal.getProY() == 0));
        Assertions.assertTrue(mosfet.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("G") && terminal.getProX() == 0 && terminal.getProY() == 1));
        Assertions.assertTrue(mosfet.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("S") && terminal.getProX() == 0 && terminal.getProY() == 2));
    }

}
