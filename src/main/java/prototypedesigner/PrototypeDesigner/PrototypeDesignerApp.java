package prototypedesigner.PrototypeDesigner;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import prototypedesigner.PrototypeDesigner.controller.MainController;

import java.io.IOException;

public class PrototypeDesignerApp extends Application {

    private static MainController mainController;

    public static MainController getMainController() {
        return mainController;
    }

    @Override
    public void start(@SuppressWarnings("exports") Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(PrototypeDesignerApp.class.getResource("MainView" + ".fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 640, 480);
        stage.setMaximized(true);
        stage.setScene(scene);
        mainController = fxmlLoader.getController();
        mainController.setStage(stage);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}