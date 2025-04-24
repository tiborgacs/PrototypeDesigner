package prototypedesigner.PrototypeDesigner.converter;

import javafx.util.StringConverter;

/** No operation conversion between string and string */
public class NoOpStringConverter extends StringConverter<String> {
    @Override public String toString(String object) { return object; }
    @Override public String fromString(String string) { return string; }
}
