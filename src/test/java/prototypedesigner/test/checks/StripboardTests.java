package prototypedesigner.test.checks;

import javafx.util.Pair;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;
import prototypedesigner.PrototypeDesigner.CircuitDesign;
import prototypedesigner.PrototypeDesigner.StripboardTrace;
import prototypedesigner.PrototypeDesigner.components.*;
import prototypedesigner.PrototypeDesigner.controller.StripboardController;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StripboardTests {

    @Test
    public void testStripboardCollecting() throws NoSuchFieldException, IllegalAccessException {
        StripboardController controller = new StripboardController();
        CircuitDesign design = new CircuitDesign();
        Field designField = controller.getClass().getDeclaredField("design");
        designField.setAccessible(true);
        designField.set(controller, design);
        StripboardTrace t1 = new StripboardTrace(0, 0, 8);
        StripboardTrace t2 = new StripboardTrace(0, 1, 8);
        StripboardTrace t3 = new StripboardTrace(0, 2, 8);
        StripboardTrace t4 = new StripboardTrace(0, 3, 8);
        StripboardTrace t5 = new StripboardTrace(0, 4, 8);
        StripboardTrace t6 = new StripboardTrace(0, 5, 8);
        StripboardTrace t7 = new StripboardTrace(0, 6, 8);
        Assertions.assertEquals(8, t5.getW());
        StripboardTrace t5b = t5.cutAt(5, 4);
        Assertions.assertEquals(2, t5.getW());
        Assertions.assertEquals(6, t5.getX());
        Assertions.assertNotNull(t5b);
        Assertions.assertEquals(5, t5b.getW());
        Assertions.assertEquals(0, t5b.getX());
        Assertions.assertEquals(t5.getY(), t5b.getY());
        design.setConnectionsOnStripboard(new ArrayList<>());
        design.getConnectionsOnStripboard().add(t1);
        design.getConnectionsOnStripboard().add(t2);
        design.getConnectionsOnStripboard().add(t3);
        design.getConnectionsOnStripboard().add(t4);
        design.getConnectionsOnStripboard().add(t5);
        design.getConnectionsOnStripboard().add(t5b);
        design.getConnectionsOnStripboard().add(t6);
        design.getConnectionsOnStripboard().add(t7);
        BipolarJunctionTransistor q1 = new BipolarJunctionTransistor();
        q1.setPinout("E_B_C");
        q1.setStripboardOrientation(ComponentOrientation.RIGHT);
        q1.setStrX(1);
        q1.setStrY(0);
        Assertions.assertEquals(1, q1.getTerminals().get(0).getStrX());
        Assertions.assertEquals(1, q1.getTerminals().get(1).getStrX());
        Assertions.assertEquals(1, q1.getTerminals().get(2).getStrX());
        Assertions.assertEquals(0,
                q1.getTerminals().stream().filter(t -> t.getIdentifier().endsWith("C")).findFirst().get().getStrY());
        Assertions.assertEquals(1,
                q1.getTerminals().stream().filter(t -> t.getIdentifier().endsWith("B")).findFirst().get().getStrY());
        Assertions.assertEquals(2,
                q1.getTerminals().stream().filter(t -> t.getIdentifier().endsWith("E")).findFirst().get().getStrY());
        Resistor r1 = new Resistor();
        r1.setStripboardOrientation(ComponentOrientation.UP);
        r1.setSpanningOnStripboard(true);
        r1.setStartOnStripboard(new Coordinate(3, 1));
        r1.setEndOnStripboard(new Coordinate(3, 4));
        Resistor r2 = new Resistor();
        r2.setStripboardOrientation(ComponentOrientation.UP);
        r2.setSpanningOnStripboard(true);
        r2.setStartOnStripboard(new Coordinate(2, 1));
        r2.setEndOnStripboard(new Coordinate(2, 6));
        Resistor r3 = new Resistor();
        r3.setStripboardOrientation(ComponentOrientation.UP);
        r3.setSpanningOnStripboard(true);
        r3.setStartOnStripboard(new Coordinate(4, 0));
        r3.setEndOnStripboard(new Coordinate(4, 4));
        Resistor r4 = new Resistor();
        r4.setStripboardOrientation(ComponentOrientation.UP);
        r4.setSpanningOnStripboard(true);
        r4.setStartOnStripboard(new Coordinate(5, 2));
        r4.setEndOnStripboard(new Coordinate(5, 6));
        NonPolarizedCapacitor c1 = new NonPolarizedCapacitor();
        c1.setStripboardOrientation(ComponentOrientation.UP);
        c1.setSpanningOnStripboard(true);
        c1.setStartOnStripboard(new Coordinate(7, 1));
        c1.setEndOnStripboard(new Coordinate(7, 3));
        NonPolarizedCapacitor c2 = new NonPolarizedCapacitor();
        c2.setStripboardOrientation(ComponentOrientation.UP);
        c2.setSpanningOnStripboard(true);
        c2.setStartOnStripboard(new Coordinate(6, 0));
        c2.setEndOnStripboard(new Coordinate(6, 4));
        design.setStripboardComponents(new ArrayList<>());
        design.getStripboardComponents().add(q1);
        design.getStripboardComponents().add(r1);
        design.getStripboardComponents().add(r2);
        design.getStripboardComponents().add(r3);
        design.getStripboardComponents().add(r4);
        design.getStripboardComponents().add(c1);
        design.getStripboardComponents().add(c2);
        design.setLinksOnStripboard(new ArrayList<>());
        List<Set<Terminal>> sch = new ArrayList<>();
        Set<Terminal> diff = new HashSet<>();
        diff.add(q1.getTerminals().get(0));
        diff.add(q1.getTerminals().get(1));
        diff.add(q1.getTerminals().get(2));
        sch.add(diff);
        List<Pair<Set<Terminal>, Set<Terminal>>> connections = controller.checkConnections(sch);
        Assertions.assertEquals(1, connections.size());
        Pair<Set<Terminal>, Set<Terminal>> pair = connections.get(0);
        for (Terminal t: q1.getTerminals())
            Assertions.assertTrue(pair.getKey().contains(t));
        Assertions.assertTrue(pair.getValue().contains(
                q1.getTerminals().stream().filter(t -> t.getIdentifier().endsWith("C")).findFirst().get()));
        Assertions.assertEquals(3, pair.getValue().size());
        Assertions.assertTrue(pair.getValue().stream().anyMatch(t -> t.getComponent() == c2));
        Assertions.assertTrue(pair.getValue().stream().anyMatch(t -> t.getComponent() == r3));
    }
}