package prototypedesigner.PrototypeDesigner.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import prototypedesigner.PrototypeDesigner.CircuitDesign;

import java.io.*;

public class MenuController {

    @FXML
    private void newProject() {
        // TODO: ask to save...
    }

    @FXML
    private void open() {
        // TODO: ask to save
        FileChooser fc = new FileChooser();
        File file = fc.showOpenDialog(MainController.getStage());
        if (file != null) {
            XmlMapper mapper = new XmlMapper();
            try {
                CircuitDesign cd = mapper.readValue(file, CircuitDesign.class);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    private void save() {
        FileChooser fc = new FileChooser();
        File file = fc.showSaveDialog(MainController.getStage());
        if (file != null) {
            XmlMapper mapper = new XmlMapper();
            try (FileWriter fw = new FileWriter(file)) {
                String xml = mapper.writeValueAsString(new CircuitDesign());
                fw.write(xml);
                fw.flush();
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    private void saveAs() {
        //
    }

    @FXML
    private void exportBom() {
        //
    }

    @FXML
    private void exit() {
        // TODO: ask to save etc
        Platform.exit();
    }

    @FXML
    private void about() {
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setTitle("About");
        alert.setHeaderText("Prototype Designer");
        alert.setContentText("Dissertation of Tibor Gacs\n" +
                        "Consultant: Tibor Gregorics\n" +
                "Used technologies: Java 11 (OpenJDK),\n" +
                "OpenJFX,Project Lombok, FasterXML Jackson,\n" +
                "JUnit, Apache Maven");
        alert.getButtonTypes().add(ButtonType.OK);
        alert.initOwner(MainController.getStage());
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.showAndWait();
    }

}
