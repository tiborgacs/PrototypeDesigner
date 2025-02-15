package prototypedesigner.PrototypeDesigner.converter;

import javafx.util.StringConverter;

public class NoOpStringConverter extends StringConverter<String> {
    @Override public String toString(String object) { return object; }
    @Override public String fromString(String string) { return string; }
}
