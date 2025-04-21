package prototypedesigner.test.stringconverter;

import javafx.util.StringConverter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import prototypedesigner.PrototypeDesigner.converter.IntegerStringConverter;
import prototypedesigner.PrototypeDesigner.converter.NoOpStringConverter;

public class StringConverterTests {

    @Test
    public void testNoOpConverter() {
        StringConverter<String> noOpConverter = new NoOpStringConverter();
        Assertions.assertEquals("abcd", noOpConverter.fromString("abcd"));
        Assertions.assertEquals("abcd", noOpConverter.toString("abcd"));
        Assertions.assertNull(noOpConverter.fromString(null));
        Assertions.assertNull(noOpConverter.toString(null));
    }

    @Test
    public void testIntegerConverter() {
        StringConverter<Integer> integerStringConverter = new IntegerStringConverter();
        Assertions.assertEquals(0, integerStringConverter.fromString("0"));
        Assertions.assertEquals(1, integerStringConverter.fromString("1"));
        Assertions.assertEquals(2, integerStringConverter.fromString("2"));
        Assertions.assertNull(integerStringConverter.fromString("egy"));
        Assertions.assertEquals("0", integerStringConverter.toString(0));
        Assertions.assertEquals("1", integerStringConverter.toString(1));
        Assertions.assertEquals("2", integerStringConverter.toString(2));
        Assertions.assertEquals("", integerStringConverter.toString(null));
    }
}
