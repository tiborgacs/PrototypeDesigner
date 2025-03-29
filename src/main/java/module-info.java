module prototypedesigner.PrototypeDesigner {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.dataformat.xml;
    requires com.fasterxml.jackson.core;
    requires com.google.common;
    requires javafx.swing;
    requires org.checkerframework.checker.qual;

    opens prototypedesigner.PrototypeDesigner to javafx.fxml;
    exports prototypedesigner.PrototypeDesigner;
    exports prototypedesigner.PrototypeDesigner.controller;
    opens prototypedesigner.PrototypeDesigner.controller to javafx.fxml;
    exports prototypedesigner.PrototypeDesigner.converter;
    opens prototypedesigner.PrototypeDesigner.converter to javafx.fxml;
    exports prototypedesigner.PrototypeDesigner.components;
}
