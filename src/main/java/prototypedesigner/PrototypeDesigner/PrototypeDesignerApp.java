package prototypedesigner.PrototypeDesigner;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import prototypedesigner.PrototypeDesigner.controller.MainController;

import java.io.IOException;

/**
 * Main class. Due to Maven build issues, this is not the entry point for the application, just for the FX thread.
 */
public class PrototypeDesignerApp extends Application {

    private static MainController mainController;

    /**
     * Returns the main controller of the application
     * @return main controller
     */
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

    /**
     * Launches the JavaFX application
     * @param args optional command line arguments coming from the Main class
     */
    public static void main(String[] args) {
        launch(args);
    }

}