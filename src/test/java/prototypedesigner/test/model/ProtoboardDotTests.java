package prototypedesigner.test.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import prototypedesigner.PrototypeDesigner.ProtoboardDot;

public class ProtoboardDotTests {

    @Test
    public void testHorizontalNeighbors() {
        ProtoboardDot dot1 = new ProtoboardDot(1, 1);
        ProtoboardDot dot2 = new ProtoboardDot(2, 1);
        Assertions.assertTrue(dot1.isNeighbor(dot2));
        Assertions.assertTrue(dot2.isNeighbor(dot1));
        Assertions.assertTrue(dot1.isNeighborRight(dot2));
        Assertions.assertTrue(dot2.isNeighborLeft(dot1));
        Assertions.assertFalse(dot2.isNeighborRight(dot1));
        Assertions.assertFalse(dot1.isNeighborLeft(dot2));
    }

    @Test
    public void testVerticalNeighbors() {
        ProtoboardDot dot1 = new ProtoboardDot(1, 1);
        ProtoboardDot dot2 = new ProtoboardDot(1, 2);
        Assertions.assertTrue(dot1.isNeighbor(dot2));
        Assertions.assertTrue(dot2.isNeighbor(dot1));
        Assertions.assertTrue(dot1.isNeighborBottom(dot2));
        Assertions.assertTrue(dot2.isNeighborTop(dot1));
        Assertions.assertFalse(dot2.isNeighborBottom(dot1));
        Assertions.assertFalse(dot1.isNeighborTop(dot2));
    }

    @Test
    public void testNotNeighbors() {
        ProtoboardDot dot1 = new ProtoboardDot(1, 1);
        ProtoboardDot dot2 = new ProtoboardDot(2, 2);
        Assertions.assertFalse(dot1.isNeighbor(dot2));
        Assertions.assertFalse(dot2.isNeighbor(dot1));
        Assertions.assertFalse(dot1.isNeighborBottom(dot2));
        Assertions.assertFalse(dot1.isNeighborTop(dot2));
        Assertions.assertFalse(dot1.isNeighborLeft(dot2));
        Assertions.assertFalse(dot1.isNeighborRight(dot2));
        Assertions.assertFalse(dot2.isNeighborBottom(dot1));
        Assertions.assertFalse(dot2.isNeighborTop(dot1));
        Assertions.assertFalse(dot2.isNeighborLeft(dot1));
        Assertions.assertFalse(dot2.isNeighborRight(dot1));
    }

}
