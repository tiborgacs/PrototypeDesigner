package prototypedesigner.test.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import prototypedesigner.PrototypeDesigner.ProtoboardDot;
import prototypedesigner.PrototypeDesigner.ProtoboardTrace;

public class ProtoboardTraceTests {

    @Test
    public void cutTest() {
        ProtoboardDot dot1 = new ProtoboardDot(0, 0);
        ProtoboardDot dot2 = new ProtoboardDot(1, 0);
        ProtoboardDot dot3 = new ProtoboardDot(2, 0);
        ProtoboardDot dot4 = new ProtoboardDot(3, 0);
        ProtoboardDot dot5 = new ProtoboardDot(4, 0);
        ProtoboardTrace trace = new ProtoboardTrace(dot1);
        trace.getDots().add(dot2);
        trace.getDots().add(dot3);
        trace.getDots().add(dot4);
        trace.getDots().add(dot5);
        Assertions.assertEquals(5, trace.getDots().size());
        ProtoboardTrace newTrace = trace.cut(dot3, dot4);
        Assertions.assertNotNull(newTrace);
        Assertions.assertEquals(3, trace.getDots().size());
        Assertions.assertEquals(2, newTrace.getDots().size());
    }

    @Test
    public void shrinkTest() {
        ProtoboardDot dot1 = new ProtoboardDot(0, 0);
        ProtoboardDot dot2 = new ProtoboardDot(1, 0);
        ProtoboardDot dot3 = new ProtoboardDot(2, 0);
        ProtoboardDot dot4 = new ProtoboardDot(3, 0);
        ProtoboardDot dot5 = new ProtoboardDot(4, 0);
        ProtoboardTrace trace = new ProtoboardTrace(dot1);
        trace.getDots().add(dot2);
        trace.getDots().add(dot3);
        trace.getDots().add(dot4);
        trace.getDots().add(dot5);
        Assertions.assertEquals(5, trace.getDots().size());
        ProtoboardTrace newTrace = trace.cut(dot4, dot5);
        Assertions.assertEquals(4, trace.getDots().size());
        Assertions.assertNull(newTrace);
        newTrace = trace.cut(dot4, dot5); // No operation
        Assertions.assertEquals(4, trace.getDots().size());
        Assertions.assertNull(newTrace);
    }
}
