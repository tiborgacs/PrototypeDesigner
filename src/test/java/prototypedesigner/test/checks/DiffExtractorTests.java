package prototypedesigner.test.checks;

import javafx.util.Pair;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import prototypedesigner.PrototypeDesigner.components.Terminal;
import prototypedesigner.PrototypeDesigner.controller.DifferenceExtractor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DiffExtractorTests {

    @Test
    public void testDiff() {
        Terminal t1 = new Terminal();
        t1.setIdentifier("t1");
        Terminal t2 = new Terminal();
        t2.setIdentifier("t2");
        Terminal t3 = new Terminal();
        t3.setIdentifier("t3");
        Terminal t4 = new Terminal();
        t4.setIdentifier("t4");
        Terminal t5 = new Terminal();
        t5.setIdentifier("t5");
        Terminal t6 = new Terminal();
        t6.setIdentifier("t6");
        List<Set<Terminal>> schematicWiring = new ArrayList<>();
        Set<Terminal> network1 = new HashSet<>();
        network1.add(t1);
        network1.add(t2);
        schematicWiring.add(network1);
        Set<Terminal> network2 = new HashSet<>();
        network2.add(t3);
        network2.add(t4);
        schematicWiring.add(network2);
        Set<Terminal> network3 = new HashSet<>();
        network3.add(t5);
        network3.add(t6);
        schematicWiring.add(network3);
        List<Set<Terminal>> testBoardWiring = new ArrayList<>();
        Set<Terminal> network4 = new HashSet<>();
        network4.add(t4);
        network4.add(t5);
        network4.add(t6);
        testBoardWiring.add(network4);
        Set<Terminal> network5 = new HashSet<>();
        network5.add(t1);
        network5.add(t2);
        network5.add(t3);
        testBoardWiring.add(network5);
        testBoardWiring.add(new HashSet<>());
        List<Pair<Set<Terminal>, Set<Terminal>>> diff = DifferenceExtractor
                .extractDifferences(schematicWiring, testBoardWiring);
        Assertions.assertFalse(diff.isEmpty());
        Assertions.assertEquals(3, diff.size());
        for (Pair<Set<Terminal>, Set<Terminal>> diffEntry: diff) {
            Assertions.assertEquals(2, diffEntry.getKey().size());
            Assertions.assertEquals(3, diffEntry.getValue().size());
        }
    }

    @Test
    public void testNoDiff() {
        Terminal t1 = new Terminal();
        t1.setIdentifier("t1");
        Terminal t2 = new Terminal();
        t2.setIdentifier("t2");
        Terminal t3 = new Terminal();
        t3.setIdentifier("t3");
        Terminal t4 = new Terminal();
        t4.setIdentifier("t4");
        Terminal t5 = new Terminal();
        t5.setIdentifier("t5");
        Terminal t6 = new Terminal();
        t6.setIdentifier("t6");
        List<Set<Terminal>> schematicWiring = new ArrayList<>();
        Set<Terminal> network1 = new HashSet<>();
        network1.add(t1);
        network1.add(t2);
        schematicWiring.add(network1);
        Set<Terminal> network2 = new HashSet<>();
        network2.add(t3);
        network2.add(t4);
        schematicWiring.add(network2);
        Set<Terminal> network3 = new HashSet<>();
        network3.add(t5);
        network3.add(t6);
        schematicWiring.add(network3);
        List<Set<Terminal>> testBoardWiring = new ArrayList<>();
        Set<Terminal> network4 = new HashSet<>();
        network4.add(t5);
        network4.add(t6);
        testBoardWiring.add(network4);
        Set<Terminal> network5 = new HashSet<>();
        network5.add(t1);
        network5.add(t2);
        testBoardWiring.add(network5);
        Set<Terminal> network6 = new HashSet<>();
        network6.add(t4);
        network6.add(t3);
        testBoardWiring.add(network6);
        List<Pair<Set<Terminal>, Set<Terminal>>> diff = DifferenceExtractor
                .extractDifferences(schematicWiring, testBoardWiring);
        Assertions.assertTrue(diff.isEmpty());
    }
}
