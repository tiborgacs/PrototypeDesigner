package prototypedesigner.test.checks;

import javafx.util.Pair;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import prototypedesigner.PrototypeDesigner.CircuitDesign;
import prototypedesigner.PrototypeDesigner.ProtoboardDot;
import prototypedesigner.PrototypeDesigner.ProtoboardTrace;
import prototypedesigner.PrototypeDesigner.components.*;
import prototypedesigner.PrototypeDesigner.controller.ProtoboardController;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ProtoboardTests {

    @Test
    public void testProtoboardCollecting() throws NoSuchFieldException, IllegalAccessException {
        ProtoboardController controller = new ProtoboardController();
        CircuitDesign design = new CircuitDesign();
        Field designField = controller.getClass().getDeclaredField("design");
        designField.setAccessible(true);
        designField.set(controller, design);
        BipolarJunctionTransistor q1 = new BipolarJunctionTransistor();
        q1.setPinout("E_B_C");
        q1.setProtoboardOrientation(ComponentOrientation.RIGHT);
        q1.setProX(2);
        q1.setProY(1);
        for (Terminal t: q1.getTerminals())
            Assertions.assertEquals(2, t.getProX());
        Assertions.assertEquals(1,
                q1.getTerminals().stream().filter(t -> t.getIdentifier().endsWith("C")).findFirst().get().getProY());
        Assertions.assertEquals(2,
                q1.getTerminals().stream().filter(t -> t.getIdentifier().endsWith("B")).findFirst().get().getProY());
        Assertions.assertEquals(3,
                q1.getTerminals().stream().filter(t -> t.getIdentifier().endsWith("E")).findFirst().get().getProY());
        Resistor r1 = new Resistor();
        r1.setProtoboardOrientation(ComponentOrientation.UP);
        r1.setSpanningOnProtoboard(true);
        r1.setStartOnProtoboard(new Coordinate(1, 0));
        r1.setEndOnProtoboard(new Coordinate(1, 1));
        NonPolarizedCapacitor c1 = new NonPolarizedCapacitor();
        c1.setProtoboardOrientation(ComponentOrientation.LEFT);
        c1.setSpanningOnProtoboard(true);
        c1.setStartOnProtoboard(new Coordinate(0, 2));
        c1.setEndOnProtoboard(new Coordinate(1, 2));
        Resistor r2 = new Resistor();
        r2.setProtoboardOrientation(ComponentOrientation.UP);
        r2.setSpanningOnProtoboard(true);
        r2.setStartOnProtoboard(new Coordinate(1, 3));
        r2.setEndOnProtoboard(new Coordinate(1, 4));
        Resistor r3 = new Resistor();
        r3.setProtoboardOrientation(ComponentOrientation.UP);
        r3.setSpanningOnProtoboard(true);
        r3.setStartOnProtoboard(new Coordinate(3, 0));
        r3.setEndOnProtoboard(new Coordinate(3, 1));
        Resistor r4 = new Resistor();
        r4.setProtoboardOrientation(ComponentOrientation.UP);
        r4.setSpanningOnProtoboard(true);
        r4.setStartOnProtoboard(new Coordinate(3, 3));
        r4.setEndOnProtoboard(new Coordinate(3, 4));
        NonPolarizedCapacitor c2 = new NonPolarizedCapacitor();
        c2.setProtoboardOrientation(ComponentOrientation.LEFT);
        c2.setSpanningOnProtoboard(true);
        c2.setStartOnProtoboard(new Coordinate(3, 2));
        c2.setEndOnProtoboard(new Coordinate(4, 2));
        design.setProtoboardComponents(new ArrayList<>());
        design.getProtoboardComponents().add(r1);
        design.getProtoboardComponents().add(c1);
        design.getProtoboardComponents().add(r2);
        design.getProtoboardComponents().add(q1);
        design.getProtoboardComponents().add(r3);
        design.getProtoboardComponents().add(c2);
        design.getProtoboardComponents().add(r4);
        ProtoboardTrace t1 = new ProtoboardTrace();
        t1.getDots().add(new ProtoboardDot(1, 1));
        ProtoboardDot common = new ProtoboardDot(1, 2);
        t1.getDots().add(common);
        t1.getDots().add(new ProtoboardDot(1, 3));
        ProtoboardTrace t2 = new ProtoboardTrace();
        t2.getDots().add(common);
        t2.getDots().add(new ProtoboardDot(2, 2));
        ProtoboardTrace t3 = new ProtoboardTrace(); // VCC
        t3.getDots().add(new ProtoboardDot(1, 0));
        t3.getDots().add(new ProtoboardDot(2, 0));
        t3.getDots().add(new ProtoboardDot(3, 0));
        ProtoboardTrace t4 = new ProtoboardTrace();
        t4.getDots().add(new ProtoboardDot(2, 1));
        t4.getDots().add(new ProtoboardDot(3, 1));
        t4.getDots().add(new ProtoboardDot(3, 2));
        ProtoboardTrace t5 = new ProtoboardTrace();
        t5.getDots().add(new ProtoboardDot(2, 3));
        t5.getDots().add(new ProtoboardDot(3, 3));
        ProtoboardTrace t6 = new ProtoboardTrace(); // GND
        t6.getDots().add(new ProtoboardDot(1, 4));
        t6.getDots().add(new ProtoboardDot(2, 4));
        t6.getDots().add(new ProtoboardDot(3, 4));
        design.setConnectionsOnProtoboard(new ArrayList<>());
        design.getConnectionsOnProtoboard().add(t1);
        design.getConnectionsOnProtoboard().add(t2);
        design.getConnectionsOnProtoboard().add(t3);
        design.getConnectionsOnProtoboard().add(t4);
        design.getConnectionsOnProtoboard().add(t5);
        design.getConnectionsOnProtoboard().add(t6);
        design.setViasOnProtoboard(new ArrayList<>());
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
                q1.getTerminals().stream().filter(t -> t.getIdentifier().endsWith("B")).findFirst().get()));
        Assertions.assertEquals(4, pair.getValue().size());
        Assertions.assertTrue(pair.getValue().stream().anyMatch(t -> t.getComponent() == c1));
        Assertions.assertTrue(pair.getValue().stream().anyMatch(t -> t.getComponent() == r1));
        Assertions.assertTrue(pair.getValue().stream().anyMatch(t -> t.getComponent() == r2));
    }
}
