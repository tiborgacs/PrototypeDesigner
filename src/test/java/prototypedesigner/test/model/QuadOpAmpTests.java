package prototypedesigner.test.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import prototypedesigner.PrototypeDesigner.components.ComponentOrientation;
import prototypedesigner.PrototypeDesigner.components.DualOpAmp;
import prototypedesigner.PrototypeDesigner.components.QuadOpAmp;

public class QuadOpAmpTests {

    @Test
    public void testInstantiation() {
        QuadOpAmp ic = new QuadOpAmp();
        Assertions.assertEquals(14, ic.getTerminals().size());
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("OUT1")));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("INV1")));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("NON1")));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("VCC")));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("NON2")));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("INV2")));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("OUT2")));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("OUT3")));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("INV3")));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("NON3")));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("VDD")));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("NON4")));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("INV4")));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("OUT4")));
    }

    @Test
    public void testSchematicPlacement() {
        QuadOpAmp ic = new QuadOpAmp();
        ic.setSchX(0);
        ic.setSchY(0);
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("OUT1") && terminal.getSchX() == 12 && terminal.getSchY() == 12));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("INV1") && terminal.getSchX() == 12 && terminal.getSchY() == 36));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("NON1") && terminal.getSchX() == 12 && terminal.getSchY() == 60));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("VCC") && terminal.getSchX() == 12 && terminal.getSchY() == 84));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("NON2") && terminal.getSchX() == 12 && terminal.getSchY() == 108));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("INV2") && terminal.getSchX() == 12 && terminal.getSchY() == 132));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("OUT2") && terminal.getSchX() == 12 && terminal.getSchY() == 156));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("OUT3") && terminal.getSchX() == 84 && terminal.getSchY() == 156));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("INV3") && terminal.getSchX() == 84 && terminal.getSchY() == 132));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("NON3") && terminal.getSchX() == 84 && terminal.getSchY() == 108));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("VDD") && terminal.getSchX() == 84 && terminal.getSchY() == 84));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("NON4") && terminal.getSchX() == 84 && terminal.getSchY() == 60));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("INV4") && terminal.getSchX() == 84 && terminal.getSchY() == 36));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("OUT4") && terminal.getSchX() == 84 && terminal.getSchY() == 12));
    }

    @Test
    public void testStripboardUp() {
        QuadOpAmp ic = new QuadOpAmp();
        ic.setStripboardOrientation(ComponentOrientation.UP);
        ic.setStrX(0);
        ic.setStrY(0);
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("OUT1") && terminal.getStrX() == 0 && terminal.getStrY() == 0));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("INV1") && terminal.getStrX() == 0 && terminal.getStrY() == 1));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("NON1") && terminal.getStrX() == 0 && terminal.getStrY() == 2));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("VCC") && terminal.getStrX() == 0 && terminal.getStrY() == 3));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("NON2") && terminal.getStrX() == 0 && terminal.getStrY() == 4));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("INV2") && terminal.getStrX() == 0 && terminal.getStrY() == 5));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("OUT2") && terminal.getStrX() == 0 && terminal.getStrY() == 6));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("OUT3") && terminal.getStrX() == 3 && terminal.getStrY() == 6));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("INV3") && terminal.getStrX() == 3 && terminal.getStrY() == 5));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("NON3") && terminal.getStrX() == 3 && terminal.getStrY() == 4));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("VDD") && terminal.getStrX() == 3 && terminal.getStrY() == 3));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("NON4") && terminal.getStrX() == 3 && terminal.getStrY() == 2));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("INV4") && terminal.getStrX() == 3 && terminal.getStrY() == 1));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("OUT4") && terminal.getStrX() == 3 && terminal.getStrY() == 0));
    }

    @Test
    public void testProtoboardUp() {
        QuadOpAmp ic = new QuadOpAmp();
        ic.setProtoboardOrientation(ComponentOrientation.UP);
        ic.setProX(0);
        ic.setProY(0);
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("OUT1") && terminal.getProX() == 0 && terminal.getProY() == 0));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("INV1") && terminal.getProX() == 0 && terminal.getProY() == 1));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("NON1") && terminal.getProX() == 0 && terminal.getProY() == 2));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("VCC") && terminal.getProX() == 0 && terminal.getProY() == 3));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("NON2") && terminal.getProX() == 0 && terminal.getProY() == 4));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("INV2") && terminal.getProX() == 0 && terminal.getProY() == 5));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("OUT2") && terminal.getProX() == 0 && terminal.getProY() == 6));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("OUT3") && terminal.getProX() == 3 && terminal.getProY() == 6));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("INV3") && terminal.getProX() == 3 && terminal.getProY() == 5));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("NON3") && terminal.getProX() == 3 && terminal.getProY() == 4));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("VDD") && terminal.getProX() == 3 && terminal.getProY() == 3));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("NON4") && terminal.getProX() == 3 && terminal.getProY() == 2));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("INV4") && terminal.getProX() == 3 && terminal.getProY() == 1));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("OUT4") && terminal.getProX() == 3 && terminal.getProY() == 0));
    }

    @Test
    public void testStripboardLeft() {
        QuadOpAmp ic = new QuadOpAmp();
        ic.setStripboardOrientation(ComponentOrientation.LEFT);
        ic.setStrX(0);
        ic.setStrY(0);
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("OUT1") && terminal.getStrX() == 0 && terminal.getStrY() == 3));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("INV1") && terminal.getStrX() == 1 && terminal.getStrY() == 3));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("NON1") && terminal.getStrX() == 2 && terminal.getStrY() == 3));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("VCC") && terminal.getStrX() == 3 && terminal.getStrY() == 3));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("NON2") && terminal.getStrX() == 4 && terminal.getStrY() == 3));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("INV2") && terminal.getStrX() == 5 && terminal.getStrY() == 3));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("OUT2") && terminal.getStrX() == 6 && terminal.getStrY() == 3));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("OUT3") && terminal.getStrX() == 6 && terminal.getStrY() == 0));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("INV3") && terminal.getStrX() == 5 && terminal.getStrY() == 0));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("NON3") && terminal.getStrX() == 4 && terminal.getStrY() == 0));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("VDD") && terminal.getStrX() == 3 && terminal.getStrY() == 0));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("NON4") && terminal.getStrX() == 2 && terminal.getStrY() == 0));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("INV4") && terminal.getStrX() == 1 && terminal.getStrY() == 0));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("OUT4") && terminal.getStrX() == 0 && terminal.getStrY() == 0));
    }

    @Test
    public void testProtoboardLeft() {
        QuadOpAmp ic = new QuadOpAmp();
        ic.setProtoboardOrientation(ComponentOrientation.LEFT);
        ic.setProX(0);
        ic.setProY(0);
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("OUT1") && terminal.getProX() == 0 && terminal.getProY() == 3));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("INV1") && terminal.getProX() == 1 && terminal.getProY() == 3));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("NON1") && terminal.getProX() == 2 && terminal.getProY() == 3));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("VCC") && terminal.getProX() == 3 && terminal.getProY() == 3));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("NON2") && terminal.getProX() == 4 && terminal.getProY() == 3));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("INV2") && terminal.getProX() == 5 && terminal.getProY() == 3));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("OUT2") && terminal.getProX() == 6 && terminal.getProY() == 3));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("OUT3") && terminal.getProX() == 6 && terminal.getProY() == 0));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("INV3") && terminal.getProX() == 5 && terminal.getProY() == 0));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("NON3") && terminal.getProX() == 4 && terminal.getProY() == 0));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("VDD") && terminal.getProX() == 3 && terminal.getProY() == 0));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("NON4") && terminal.getProX() == 2 && terminal.getProY() == 0));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("INV4") && terminal.getProX() == 1 && terminal.getProY() == 0));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("OUT4") && terminal.getProX() == 0 && terminal.getProY() == 0));
    }

    @Test
    public void testStripboardDown() {
        QuadOpAmp ic = new QuadOpAmp();
        ic.setStripboardOrientation(ComponentOrientation.DOWN);
        ic.setStrX(0);
        ic.setStrY(0);
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("OUT1") && terminal.getStrX() == 3 && terminal.getStrY() == 6));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("INV1") && terminal.getStrX() == 3 && terminal.getStrY() == 5));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("NON1") && terminal.getStrX() == 3 && terminal.getStrY() == 4));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("VCC") && terminal.getStrX() == 3 && terminal.getStrY() == 3));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("NON2") && terminal.getStrX() == 3 && terminal.getStrY() == 2));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("INV2") && terminal.getStrX() == 3 && terminal.getStrY() == 1));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("OUT2") && terminal.getStrX() == 3 && terminal.getStrY() == 0));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("OUT3") && terminal.getStrX() == 0 && terminal.getStrY() == 0));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("INV3") && terminal.getStrX() == 0 && terminal.getStrY() == 1));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("NON3") && terminal.getStrX() == 0 && terminal.getStrY() == 2));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("VDD") && terminal.getStrX() == 0 && terminal.getStrY() == 3));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("NON4") && terminal.getStrX() == 0 && terminal.getStrY() == 4));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("INV4") && terminal.getStrX() == 0 && terminal.getStrY() == 5));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("OUT4") && terminal.getStrX() == 0 && terminal.getStrY() == 6));
    }

    @Test
    public void testProtoboardDown() {
        QuadOpAmp ic = new QuadOpAmp();
        ic.setProtoboardOrientation(ComponentOrientation.DOWN);
        ic.setProX(0);
        ic.setProY(0);
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("OUT1") && terminal.getProX() == 3 && terminal.getProY() == 6));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("INV1") && terminal.getProX() == 3 && terminal.getProY() == 5));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("NON1") && terminal.getProX() == 3 && terminal.getProY() == 4));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("VCC") && terminal.getProX() == 3 && terminal.getProY() == 3));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("NON2") && terminal.getProX() == 3 && terminal.getProY() == 2));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("INV2") && terminal.getProX() == 3 && terminal.getProY() == 1));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("OUT2") && terminal.getProX() == 3 && terminal.getProY() == 0));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("OUT3") && terminal.getProX() == 0 && terminal.getProY() == 0));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("INV3") && terminal.getProX() == 0 && terminal.getProY() == 1));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("NON3") && terminal.getProX() == 0 && terminal.getProY() == 2));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("VDD") && terminal.getProX() == 0 && terminal.getProY() == 3));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("NON4") && terminal.getProX() == 0 && terminal.getProY() == 4));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("INV4") && terminal.getProX() == 0 && terminal.getProY() == 5));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("OUT4") && terminal.getProX() == 0 && terminal.getProY() == 6));
    }

    @Test
    public void testStripboardRight() {
        QuadOpAmp ic = new QuadOpAmp();
        ic.setStripboardOrientation(ComponentOrientation.RIGHT);
        ic.setStrX(0);
        ic.setStrY(0);
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("OUT1") && terminal.getStrX() == 6 && terminal.getStrY() == 0));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("INV1") && terminal.getStrX() == 5 && terminal.getStrY() == 0));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("NON1") && terminal.getStrX() == 4 && terminal.getStrY() == 0));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("VCC") && terminal.getStrX() == 3 && terminal.getStrY() == 0));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("NON2") && terminal.getStrX() == 2 && terminal.getStrY() == 0));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("INV2") && terminal.getStrX() == 1 && terminal.getStrY() == 0));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("OUT2") && terminal.getStrX() == 0 && terminal.getStrY() == 0));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("OUT3") && terminal.getStrX() == 0 && terminal.getStrY() == 3));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("INV3") && terminal.getStrX() == 1 && terminal.getStrY() == 3));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("NON3") && terminal.getStrX() == 2 && terminal.getStrY() == 3));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("VDD") && terminal.getStrX() == 3 && terminal.getStrY() == 3));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("NON4") && terminal.getStrX() == 4 && terminal.getStrY() == 3));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("INV4") && terminal.getStrX() == 5 && terminal.getStrY() == 3));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("OUT4") && terminal.getStrX() == 6 && terminal.getStrY() == 3));
    }

    @Test
    public void testProtoboardRight() {
        QuadOpAmp ic = new QuadOpAmp();
        ic.setProtoboardOrientation(ComponentOrientation.RIGHT);
        ic.setProX(0);
        ic.setProY(0);
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("OUT1") && terminal.getProX() == 6 && terminal.getProY() == 0));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("INV1") && terminal.getProX() == 5 && terminal.getProY() == 0));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("NON1") && terminal.getProX() == 4 && terminal.getProY() == 0));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("VCC") && terminal.getProX() == 3 && terminal.getProY() == 0));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("NON2") && terminal.getProX() == 2 && terminal.getProY() == 0));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("INV2") && terminal.getProX() == 1 && terminal.getProY() == 0));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("OUT2") && terminal.getProX() == 0 && terminal.getProY() == 0));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("OUT3") && terminal.getProX() == 0 && terminal.getProY() == 3));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("INV3") && terminal.getProX() == 1 && terminal.getProY() == 3));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("NON3") && terminal.getProX() == 2 && terminal.getProY() == 3));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("VDD") && terminal.getProX() == 3 && terminal.getProY() == 3));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("NON4") && terminal.getProX() == 4 && terminal.getProY() == 3));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("INV4") && terminal.getProX() == 5 && terminal.getProY() == 3));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("OUT4") && terminal.getProX() == 6 && terminal.getProY() == 3));
    }

}
