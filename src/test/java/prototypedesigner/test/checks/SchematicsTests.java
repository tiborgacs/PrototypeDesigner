package prototypedesigner.test.checks;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import prototypedesigner.PrototypeDesigner.CircuitDesign;
import prototypedesigner.PrototypeDesigner.components.*;
import prototypedesigner.PrototypeDesigner.controller.SchematicsController;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SchematicsTests {

    @Test
    public void testSchematicCollecting() throws NoSuchFieldException, IllegalAccessException {
        SchematicsController controller = new SchematicsController();
        CircuitDesign design = new CircuitDesign();
        Field designField = controller.getClass().getDeclaredField("design");
        designField.setAccessible(true);
        designField.set(controller, design);
        NonPolarizedCapacitor c1 = new NonPolarizedCapacitor();
        Resistor r1 = new Resistor();
        Resistor r2 = new Resistor();
        Wire w1 = new Wire();
        w1.getConnectedComponents().add(r1.getTerminals().get(0));
        w1.getConnectedComponents().add(r2.getTerminals().get(1));
        BipolarJunctionTransistor q1 = new BipolarJunctionTransistor();
        Wire w2 = new Wire();
        w2.getConnectedComponents().add(c1.getTerminals().get(1));
        q1.getTerminals().stream().filter(t -> t.getIdentifier().endsWith("B"))
                .findFirst().ifPresent(t -> w2.getConnectedComponents().add(t));
        w1.connectToWire(w2, new Coordinate());
        Resistor r3 = new Resistor();
        Wire vcc = new Wire();
        vcc.getConnectedComponents().add(r1.getTerminals().get(1));
        vcc.getConnectedComponents().add(r3.getTerminals().get(1));
        Wire w3 = new Wire();
        w3.getConnectedComponents().add(r3.getTerminals().get(0));
        q1.getTerminals().stream().filter(t -> t.getIdentifier().endsWith("C"))
                .findFirst().ifPresent(t -> w3.getConnectedComponents().add(t));
        Resistor r4 = new Resistor();
        Wire w4 = new Wire();
        w4.getConnectedComponents().add(r4.getTerminals().get(1));
        q1.getTerminals().stream().filter(t -> t.getIdentifier().endsWith("E"))
                .findFirst().ifPresent(t -> w4.getConnectedComponents().add(t));
        NonPolarizedCapacitor c2 = new NonPolarizedCapacitor();
        Wire w5 = new Wire();
        w5.getConnectedComponents().add(c2.getTerminals().get(0));
        w3.connectToWire(w5, new Coordinate());
        Wire gnd = new Wire();
        gnd.getConnectedComponents().add(r2.getTerminals().get(0));
        gnd.getConnectedComponents().add(r4.getTerminals().get(0));
        design.setSchematicsComponents(new ArrayList<>());
        design.getSchematicsComponents().add(c1);
        design.getSchematicsComponents().add(r1);
        design.getSchematicsComponents().add(r2);
        design.getSchematicsComponents().add(r3);
        design.getSchematicsComponents().add(q1);
        design.getSchematicsComponents().add(r4);
        design.getSchematicsComponents().add(c2);
        design.setConnectionsOnSchematics(new ArrayList<>());
        design.getConnectionsOnSchematics().add(w1);
        design.getConnectionsOnSchematics().add(w2);
        design.getConnectionsOnSchematics().add(w3);
        design.getConnectionsOnSchematics().add(w4);
        design.getConnectionsOnSchematics().add(w5);
        design.getConnectionsOnSchematics().add(vcc);
        design.getConnectionsOnSchematics().add(gnd);
        List<Set<Terminal>> connections = controller.getConnections();
        Assertions.assertEquals(5, connections.size());
        Assertions.assertTrue(connections.stream().filter(set -> set.size() == 4)
                .anyMatch(set -> set.stream().anyMatch(terminal -> terminal.getIdentifier().endsWith("B"))));
        Assertions.assertTrue(connections.stream().filter(set -> set.size() == 3)
                .anyMatch(set -> set.stream().anyMatch(terminal -> terminal.getIdentifier().endsWith("C"))));
        Assertions.assertEquals(3, connections.stream().filter(set -> set.size() == 2).count());
    }
}