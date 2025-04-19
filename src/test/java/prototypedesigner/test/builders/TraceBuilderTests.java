package prototypedesigner.test.builders;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import prototypedesigner.PrototypeDesigner.ProtoboardDot;
import prototypedesigner.PrototypeDesigner.ProtoboardTrace;
import prototypedesigner.PrototypeDesigner.TraceBuilder;

import java.util.ArrayList;
import java.util.List;

public class TraceBuilderTests {

    @Test
    public void testTraceBuilder() {
        ProtoboardDot d00 = null;
        ProtoboardDot d30 = null;
        ProtoboardDot d33 = null;
        List<ProtoboardDot> dots = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                ProtoboardDot dot = new ProtoboardDot(i, j);
                dots.add(dot);
                if (i == 0 && j == 0) d00 = dot;
                if (i == 3 && j == 0) d30 = dot;
                if (i == 3 && j == 3) d33 = dot;
            }
        }
        TraceBuilder tb = new TraceBuilder(d00);
        tb.addDot(d30, dots);
        tb.addDot(d33, dots);
        ProtoboardTrace trace = tb.getTrace();
        Assertions.assertEquals(7, trace.getDots().size());
        Assertions.assertSame(d00, trace.getDots().get(0));
        Assertions.assertSame(d30, trace.getDots().get(3));
        Assertions.assertEquals(0, trace.getDots().get(1).getY());
        Assertions.assertEquals(1, trace.getDots().get(1).getX());
        Assertions.assertEquals(0, trace.getDots().get(2).getY());
        Assertions.assertEquals(2, trace.getDots().get(2).getX());
        Assertions.assertSame(d33, trace.getDots().getLast());
    }

    @Test
    public void testTraceCutting() {
        ProtoboardTrace trace = new ProtoboardTrace();
        ProtoboardDot a = null;
        ProtoboardDot b = null;
        for (int i = 0; i < 5; i++) {
            ProtoboardDot dot = new ProtoboardDot(i, 0);
            trace.getDots().add(dot);
            if (i == 2) a = dot;
            if (i == 3) b = dot;
        }
        Assertions.assertEquals(5, trace.getDots().size());
        ProtoboardTrace newTrace = trace.cut(a, b);
        Assertions.assertNotNull(newTrace);
        Assertions.assertEquals(2, newTrace.getDots().size());
        Assertions.assertEquals(3, trace.getDots().size());
        Assertions.assertNotSame(trace, newTrace);
    }
}
