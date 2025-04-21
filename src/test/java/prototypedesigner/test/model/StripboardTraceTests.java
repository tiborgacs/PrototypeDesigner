package prototypedesigner.test.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import prototypedesigner.PrototypeDesigner.StripboardTrace;

public class StripboardTraceTests {

    @Test
    public void testCut() {
        StripboardTrace trace = new StripboardTrace(0, 0, 5);
        Assertions.assertEquals(5, trace.getW());
        StripboardTrace newTrace = trace.cutAt(2, 0);
        Assertions.assertEquals(2, trace.getW());
        Assertions.assertEquals(2, newTrace.getW());
        Assertions.assertEquals(trace.getY(), newTrace.getY());
        Assertions.assertEquals(0, newTrace.getX());
        Assertions.assertEquals(3, trace.getX()); // remainder
    }

    @Test
    public void testNoCut() {
        StripboardTrace trace = new StripboardTrace(0, 0, 5);
        StripboardTrace newTrace = trace.cutAt(2, 1);
        Assertions.assertEquals(5, trace.getW());
        Assertions.assertNull(newTrace);
        newTrace = trace.cutAt(5, 0);
        Assertions.assertEquals(5, trace.getW());
        Assertions.assertNull(newTrace);
    }

    @Test
    public void testShrink() {
        StripboardTrace trace = new StripboardTrace(0, 0, 5);
        StripboardTrace newTrace = trace.cutAt(4, 0);
        Assertions.assertEquals(4, trace.getW());
        Assertions.assertNull(newTrace);
        newTrace = trace.cutAt(0, 0);
        Assertions.assertEquals(3, trace.getW());
        Assertions.assertEquals(1, trace.getX());
        Assertions.assertNull(newTrace);
    }

}
