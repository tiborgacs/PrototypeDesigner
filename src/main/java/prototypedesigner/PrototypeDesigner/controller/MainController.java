package prototypedesigner.PrototypeDesigner.controller;

import javafx.stage.Stage;

public class MainController {

    private static Stage stage;

    public static Stage getStage() {
        return stage;
    }

    public static void setStage(Stage stage) {
        MainController.stage = stage;
    }
}
