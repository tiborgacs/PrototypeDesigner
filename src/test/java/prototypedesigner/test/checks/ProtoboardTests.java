package prototypedesigner.test.checks;

import org.junit.jupiter.api.Test;
import prototypedesigner.PrototypeDesigner.CircuitDesign;
import prototypedesigner.PrototypeDesigner.controller.ProtoboardController;

import java.lang.reflect.Field;

public class ProtoboardTests {

    @Test
    public void testProtoboardCollecting() throws NoSuchFieldException, IllegalAccessException {
        ProtoboardController controller = new ProtoboardController();
        CircuitDesign design = new CircuitDesign();
        Field designField = controller.getClass().getDeclaredField("design");
        designField.setAccessible(true);
        designField.set(controller, design);
    }
}
