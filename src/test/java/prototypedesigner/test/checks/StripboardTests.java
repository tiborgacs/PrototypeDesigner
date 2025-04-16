package prototypedesigner.test.checks;

import org.junit.jupiter.api.Test;
import prototypedesigner.PrototypeDesigner.CircuitDesign;
import prototypedesigner.PrototypeDesigner.controller.StripboardController;

import java.lang.reflect.Field;

public class StripboardTests {

    @Test
    public void testStripboardCollecting() throws NoSuchFieldException, IllegalAccessException {
        StripboardController controller = new StripboardController();
        CircuitDesign design = new CircuitDesign();
        Field designField = controller.getClass().getDeclaredField("design");
        designField.setAccessible(true);
        designField.set(controller, design);
    }
}
