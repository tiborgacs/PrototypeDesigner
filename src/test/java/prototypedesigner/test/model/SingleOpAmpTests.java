package prototypedesigner.test.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import prototypedesigner.PrototypeDesigner.components.ComponentOrientation;
import prototypedesigner.PrototypeDesigner.components.SingleOpAmp;

public class SingleOpAmpTests {

    @Test
    public void testInstantiation() {
        SingleOpAmp ic = new SingleOpAmp();
        Assertions.assertEquals(8, ic.getTerminals().size());
        Assertions.assertTrue(
                ic.getTerminals().stream().anyMatch(terminal -> terminal.getIdentifier().endsWith("OS1")));
        Assertions.assertTrue(
                ic.getTerminals().stream().anyMatch(terminal -> terminal.getIdentifier().endsWith("INV")));
        Assertions.assertTrue(
                ic.getTerminals().stream().anyMatch(terminal -> terminal.getIdentifier().endsWith("NON")));
        Assertions.assertTrue(
                ic.getTerminals().stream().anyMatch(terminal -> terminal.getIdentifier().endsWith("VDD")));
        Assertions.assertTrue(
                ic.getTerminals().stream().anyMatch(terminal -> terminal.getIdentifier().endsWith("OS2")));
        Assertions.assertTrue(
                ic.getTerminals().stream().anyMatch(terminal -> terminal.getIdentifier().endsWith("OUT")));
        Assertions.assertTrue(
                ic.getTerminals().stream().anyMatch(terminal -> terminal.getIdentifier().endsWith("VCC")));
        Assertions.assertTrue(
                ic.getTerminals().stream().anyMatch(terminal -> terminal.getIdentifier().endsWith("NC")));
    }

    @Test
    public void testSchematicPlacement() {
        SingleOpAmp ic = new SingleOpAmp();
        ic.setSchX(0);
        ic.setSchY(0);
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("OS1") && terminal.getSchX() == 12 && terminal.getSchY() == 12));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("INV") && terminal.getSchX() == 12 && terminal.getSchY() == 36));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("NON") && terminal.getSchX() == 12 && terminal.getSchY() == 60));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("VDD") && terminal.getSchX() == 12 && terminal.getSchY() == 84));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("OS2") && terminal.getSchX() == 84 && terminal.getSchY() == 84));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("OUT") && terminal.getSchX() == 84 && terminal.getSchY() == 60));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("VCC") && terminal.getSchX() == 84 && terminal.getSchY() == 36));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("NC") && terminal.getSchX() == 84 && terminal.getSchY() == 12));
    }

    @Test
    public void testStripboardUp() {
        SingleOpAmp ic = new SingleOpAmp();
        ic.setStripboardOrientation(ComponentOrientation.UP);
        ic.setStrX(0);
        ic.setStrY(0);
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("OS1") && terminal.getStrX() == 0 && terminal.getStrY() == 0));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("INV") && terminal.getStrX() == 0 && terminal.getStrY() == 1));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("NON") && terminal.getStrX() == 0 && terminal.getStrY() == 2));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("VDD") && terminal.getStrX() == 0 && terminal.getStrY() == 3));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("OS2") && terminal.getStrX() == 3 && terminal.getStrY() == 3));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("OUT") && terminal.getStrX() == 3 && terminal.getStrY() == 2));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("VCC") && terminal.getStrX() == 3 && terminal.getStrY() == 1));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("NC") && terminal.getStrX() == 3 && terminal.getStrY() == 0));
    }

    @Test
    public void testProtoboardUp() {
        SingleOpAmp ic = new SingleOpAmp();
        ic.setProtoboardOrientation(ComponentOrientation.UP);
        ic.setProX(0);
        ic.setProY(0);
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("OS1") && terminal.getProX() == 0 && terminal.getProY() == 0));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("INV") && terminal.getProX() == 0 && terminal.getProY() == 1));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("NON") && terminal.getProX() == 0 && terminal.getProY() == 2));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("VDD") && terminal.getProX() == 0 && terminal.getProY() == 3));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("OS2") && terminal.getProX() == 3 && terminal.getProY() == 3));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("OUT") && terminal.getProX() == 3 && terminal.getProY() == 2));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("VCC") && terminal.getProX() == 3 && terminal.getProY() == 1));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("NC") && terminal.getProX() == 3 && terminal.getProY() == 0));
    }

    @Test
    public void testStripboardLeft() {
        SingleOpAmp ic = new SingleOpAmp();
        ic.setStripboardOrientation(ComponentOrientation.LEFT);
        ic.setStrX(0);
        ic.setStrY(0);
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("OS1") && terminal.getStrX() == 0 && terminal.getStrY() == 3));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("INV") && terminal.getStrX() == 1 && terminal.getStrY() == 3));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("NON") && terminal.getStrX() == 2 && terminal.getStrY() == 3));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("VDD") && terminal.getStrX() == 3 && terminal.getStrY() == 3));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("OS2") && terminal.getStrX() == 3 && terminal.getStrY() == 0));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("OUT") && terminal.getStrX() == 2 && terminal.getStrY() == 0));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("VCC") && terminal.getStrX() == 1 && terminal.getStrY() == 0));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("NC") && terminal.getStrX() == 0 && terminal.getStrY() == 0));
    }

    @Test
    public void testProtoboardLeft() {
        SingleOpAmp ic = new SingleOpAmp();
        ic.setProtoboardOrientation(ComponentOrientation.LEFT);
        ic.setProX(0);
        ic.setProY(0);
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("OS1") && terminal.getProX() == 0 && terminal.getProY() == 3));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("INV") && terminal.getProX() == 1 && terminal.getProY() == 3));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("NON") && terminal.getProX() == 2 && terminal.getProY() == 3));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("VDD") && terminal.getProX() == 3 && terminal.getProY() == 3));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("OS2") && terminal.getProX() == 3 && terminal.getProY() == 0));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("OUT") && terminal.getProX() == 2 && terminal.getProY() == 0));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("VCC") && terminal.getProX() == 1 && terminal.getProY() == 0));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("NC") && terminal.getProX() == 0 && terminal.getProY() == 0));
    }

    @Test
    public void testStripboardDown() {
        SingleOpAmp ic = new SingleOpAmp();
        ic.setStripboardOrientation(ComponentOrientation.DOWN);
        ic.setStrX(0);
        ic.setStrY(0);
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("OS1") && terminal.getStrX() == 3 && terminal.getStrY() == 3));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("INV") && terminal.getStrX() == 3 && terminal.getStrY() == 2));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("NON") && terminal.getStrX() == 3 && terminal.getStrY() == 1));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("VDD") && terminal.getStrX() == 3 && terminal.getStrY() == 0));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("OS2") && terminal.getStrX() == 0 && terminal.getStrY() == 0));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("OUT") && terminal.getStrX() == 0 && terminal.getStrY() == 1));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("VCC") && terminal.getStrX() == 0 && terminal.getStrY() == 2));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("NC") && terminal.getStrX() == 0 && terminal.getStrY() == 3));
    }

    @Test
    public void testProtoboardDown() {
        SingleOpAmp ic = new SingleOpAmp();
        ic.setProtoboardOrientation(ComponentOrientation.DOWN);
        ic.setProX(0);
        ic.setProY(0);
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("OS1") && terminal.getProX() == 3 && terminal.getProY() == 3));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("INV") && terminal.getProX() == 3 && terminal.getProY() == 2));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("NON") && terminal.getProX() == 3 && terminal.getProY() == 1));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("VDD") && terminal.getProX() == 3 && terminal.getProY() == 0));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("OS2") && terminal.getProX() == 0 && terminal.getProY() == 0));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("OUT") && terminal.getProX() == 0 && terminal.getProY() == 1));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("VCC") && terminal.getProX() == 0 && terminal.getProY() == 2));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("NC") && terminal.getProX() == 0 && terminal.getProY() == 3));
    }

    @Test
    public void testStripboardRight() {
        SingleOpAmp ic = new SingleOpAmp();
        ic.setStripboardOrientation(ComponentOrientation.RIGHT);
        ic.setStrX(0);
        ic.setStrY(0);
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("OS1") && terminal.getStrX() == 3 && terminal.getStrY() == 0));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("INV") && terminal.getStrX() == 2 && terminal.getStrY() == 0));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("NON") && terminal.getStrX() == 1 && terminal.getStrY() == 0));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("VDD") && terminal.getStrX() == 0 && terminal.getStrY() == 0));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("OS2") && terminal.getStrX() == 0 && terminal.getStrY() == 3));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("OUT") && terminal.getStrX() == 1 && terminal.getStrY() == 3));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("VCC") && terminal.getStrX() == 2 && terminal.getStrY() == 3));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("NC") && terminal.getStrX() == 3 && terminal.getStrY() == 3));
    }

    @Test
    public void testProtoboardRight() {
        SingleOpAmp ic = new SingleOpAmp();
        ic.setProtoboardOrientation(ComponentOrientation.RIGHT);
        ic.setProX(0);
        ic.setProY(0);
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("OS1") && terminal.getProX() == 3 && terminal.getProY() == 0));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("INV") && terminal.getProX() == 2 && terminal.getProY() == 0));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("NON") && terminal.getProX() == 1 && terminal.getProY() == 0));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("VDD") && terminal.getProX() == 0 && terminal.getProY() == 0));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("OS2") && terminal.getProX() == 0 && terminal.getProY() == 3));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("OUT") && terminal.getProX() == 1 && terminal.getProY() == 3));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("VCC") && terminal.getProX() == 2 && terminal.getProY() == 3));
        Assertions.assertTrue(ic.getTerminals().stream().anyMatch(terminal ->
                terminal.getIdentifier().endsWith("NC") && terminal.getProX() == 3 && terminal.getProY() == 3));
    }

}
