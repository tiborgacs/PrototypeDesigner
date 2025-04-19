package prototypedesigner.test.counter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import prototypedesigner.PrototypeDesigner.ProtoboardDot;
import prototypedesigner.PrototypeDesigner.ProtoboardTrace;
import prototypedesigner.PrototypeDesigner.ProtoboardVia;
import prototypedesigner.PrototypeDesigner.TraceBuilder;
import prototypedesigner.PrototypeDesigner.components.*;

public class CounterTests {

    @Test
    public void testCapacitorCounter() {
        Capacitor.setCounter(0);
        NonPolarizedCapacitor c1 = new NonPolarizedCapacitor();
        Assertions.assertEquals("C1", c1.getIdentifier());
        PolarizedCapacitor c2 = new PolarizedCapacitor();
        Assertions.assertEquals("C2", c2.getIdentifier());
        Capacitor.setCounter(0);
        PolarizedCapacitor cNew = new PolarizedCapacitor();
        Assertions.assertEquals("C1", cNew.getIdentifier());
    }

    @Test
    public void testResistorCounter() {
        Resistor.setCounter(0);
        Resistor r1 = new Resistor();
        Assertions.assertEquals("R1", r1.getIdentifier());
        Resistor r2 = new Resistor();
        Assertions.assertEquals("R2", r2.getIdentifier());
        Resistor.setCounter(0);
        Resistor rNew = new Resistor();
        Assertions.assertEquals("R1", rNew.getIdentifier());
    }

    @Test
    public void testDiodeCounter() {
        Diode.setCounter(0);
        Diode d1 = new Diode();
        Assertions.assertEquals("D1", d1.getIdentifier());
        Diode d2 = new Diode();
        Assertions.assertEquals("D2", d2.getIdentifier());
        Diode.setCounter(0);
        Diode dNew = new Diode();
        Assertions.assertEquals("D1", dNew.getIdentifier());
    }

    @Test
    public void testTransistorCounter() {
        Transistor.setCounter(0);
        BipolarJunctionTransistor bjt = new BipolarJunctionTransistor();
        Assertions.assertEquals("Q1", bjt.getIdentifier());
        JunctionFieldEffectTransistor jfet = new JunctionFieldEffectTransistor();
        Assertions.assertEquals("Q2", jfet.getIdentifier());
        MetalOxideSemiconductorFET mosfet = new MetalOxideSemiconductorFET();
        Assertions.assertEquals("Q3", mosfet.getIdentifier());
        Transistor.setCounter(0);
        Transistor t = new BipolarJunctionTransistor();
        Assertions.assertEquals("Q1", t.getIdentifier());
    }

    @Test
    public void testIntegratedCircuitCounter() {
        IntegratedCircuit.setCounter(0);
        SingleOpAmp ic1 = new SingleOpAmp();
        Assertions.assertEquals("IC1", ic1.getIdentifier());
        DualOpAmp ic2 = new DualOpAmp();
        Assertions.assertEquals("IC2", ic2.getIdentifier());
        QuadOpAmp ic3 = new QuadOpAmp();
        Assertions.assertEquals("IC3", ic3.getIdentifier());
        IntegratedCircuit.setCounter(0);
        DualOpAmp icNew = new DualOpAmp();
        Assertions.assertEquals("IC1", icNew.getIdentifier());
    }

    @Test
    public void testWireCounter() {
        Wire.setCounter(0);
        Wire w1 = new Wire();
        Assertions.assertEquals("#1", w1.getIdentifier());
        Wire w2 = new Wire();
        Assertions.assertEquals("#2", w2.getIdentifier());
        Wire w3 = new WireBuilder(0, 0).getWire();
        Assertions.assertEquals("#3", w3.getIdentifier());
        Wire.setCounter(0);
        Wire wNew = new Wire();
        Assertions.assertEquals("#1", wNew.getIdentifier());
    }

    @Test
    public void testProtoboardTraceCounter() {
        ProtoboardTrace.setCounter(0);
        ProtoboardTrace t1 = new TraceBuilder(new ProtoboardDot(0, 0)).getTrace();
        Assertions.assertEquals("trace#1", t1.getIdentifier());
        ProtoboardTrace t2 = new ProtoboardTrace();
        Assertions.assertEquals("trace#2", t2.getIdentifier());
        ProtoboardTrace.setCounter(0);
        ProtoboardTrace tNew = new ProtoboardTrace();
        Assertions.assertEquals("trace#1", tNew.getIdentifier());
    }

    @Test
    public void testProtoboardVia() {
        ProtoboardVia.setCounter(0);
        ProtoboardVia v1 = new ProtoboardVia(null, null);
        Assertions.assertEquals("via#1", v1.getIdentifier());
        ProtoboardVia v2 = new ProtoboardVia(null, null);
        Assertions.assertEquals("via#2", v2.getIdentifier());
        ProtoboardVia.setCounter(0);
        ProtoboardVia vNew = new ProtoboardVia(null, null);
        Assertions.assertEquals("via#1", vNew.getIdentifier());
    }
}
