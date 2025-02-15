package prototypedesigner.PrototypeDesigner.converter;

import javafx.util.StringConverter;

public class IntegerStringConverter extends StringConverter<Integer> {

    @Override
    public String toString(Integer object) {
        return object == null ? "" : object.toString();
    }

    @Override
    public Integer fromString(String string) {
        return (string == null || string.matches("\\D+")) ? null : Integer.parseInt(string) ;
    }

}