package prototypedesigner.test.serialization;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.junit.jupiter.api.Test;
import prototypedesigner.PrototypeDesigner.components.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SerializationTest {
    @Test
    public void testSerialization() {
        WireBuilder wb = new WireBuilder(new Coordinate(0, 0));
        wb.addCoordinates(0, 1);
        wb.addCoordinates(1, 1);
        Wire wire = wb.getWire();
        XmlMapper mapper = new XmlMapper();
        try {
            String xml = mapper.writeValueAsString(wire);
            assertTrue(xml.startsWith("<Wire>"));
            assertTrue(xml.endsWith("</Wire>"));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testDeserialization() {
        String xml = "<Wire><connectedComponents/><schPoints><schPoints><x>0</x><y>0</y></schPoints><schPoints><x>0</x><y>1</y></schPoints><schPoints><x>1</x><y>1</y></schPoints></schPoints><highlighted>true</highlighted></Wire>";
        XmlMapper mapper = new XmlMapper();
        try {
            Wire wire = mapper.readValue(xml, Wire.class);
            assertEquals(3, wire.getSchPoints().size());
            assertEquals(0, wire.getSchPoints().get(0).getX());
            assertEquals(0, wire.getSchPoints().get(0).getY());
            assertEquals(1, wire.getSchPoints().get(2).getX());
            assertEquals(1, wire.getSchPoints().get(2).getY());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void serializeSmallCircuit() {
        Resistor r1 = new Resistor();
        Resistor r2 = new Resistor();
        Wire w1 = new Wire();
        Wire w2 = new Wire();
        r1.getTerminals().get(0).connectToWire(w1);
        r1.getTerminals().get(1).connectToWire(w2);
        r2.getTerminals().get(0).connectToWire(w1);
        r2.getTerminals().get(1).connectToWire(w2);
        XmlMapper mapper = new XmlMapper();
        try {
            String xml = mapper.writeValueAsString(r1);
            Component c = mapper.readValue(xml, Resistor.class);
            assertTrue(c instanceof Resistor);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
