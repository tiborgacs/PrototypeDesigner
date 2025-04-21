package prototypedesigner.test.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import prototypedesigner.PrototypeDesigner.TransistorPinouts;

public class TransistorPinoutsTests {

    @Test
    public void testCommonBjtPinouts() {
        Assertions.assertTrue(TransistorPinouts.getBjtPinouts().stream().allMatch(pinout -> pinout.contains("C")));
        Assertions.assertTrue(TransistorPinouts.getBjtPinouts().stream().allMatch(pinout -> pinout.contains("B")));
        Assertions.assertTrue(TransistorPinouts.getBjtPinouts().stream().allMatch(pinout -> pinout.contains("E")));
    }

    @Test
    public void testCommonFetPinouts() {
        Assertions.assertTrue(TransistorPinouts.getFetPinouts().stream().allMatch(pinout -> pinout.contains("D")));
        Assertions.assertTrue(TransistorPinouts.getFetPinouts().stream().allMatch(pinout -> pinout.contains("S")));
        Assertions.assertTrue(TransistorPinouts.getFetPinouts().stream().allMatch(pinout -> pinout.contains("G")));
    }

}
