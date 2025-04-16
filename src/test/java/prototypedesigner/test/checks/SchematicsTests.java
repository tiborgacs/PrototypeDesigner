package prototypedesigner.test.checks;

import org.junit.jupiter.api.Test;
import prototypedesigner.PrototypeDesigner.CircuitDesign;
import prototypedesigner.PrototypeDesigner.controller.SchematicsController;

import java.lang.reflect.Field;

public class SchematicsTests {

    @Test
    public void testSchematicCollecting() throws NoSuchFieldException, IllegalAccessException {
        SchematicsController controller = new SchematicsController();
        CircuitDesign design = new CircuitDesign();
        Field designField = controller.getClass().getDeclaredField("design");
        designField.setAccessible(true);
        designField.set(controller, design);
    }
}
