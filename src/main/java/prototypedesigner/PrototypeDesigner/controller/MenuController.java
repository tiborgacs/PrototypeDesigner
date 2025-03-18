package prototypedesigner.PrototypeDesigner.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import prototypedesigner.PrototypeDesigner.CircuitDesign;
import prototypedesigner.PrototypeDesigner.PrototypeDesignerApp;
import prototypedesigner.PrototypeDesigner.components.Component;
import prototypedesigner.PrototypeDesigner.components.ComponentValue;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.*;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class MenuController {

    private File current;

    @FXML
    private void newProject() {
        // TODO: ask to save...
        PrototypeDesignerApp.getMainController().newDesign();
        current = null;
    }

    @FXML
    private void open() {
        // TODO: ask to save
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML files", "*.xml"));
        File file = fc.showOpenDialog(PrototypeDesignerApp.getMainController().getStage());
        if (file != null) {
            XmlMapper mapper = new XmlMapper();
            try {
                CircuitDesign cd = mapper.readValue(file, CircuitDesign.class);
                PrototypeDesignerApp.getMainController().setDesign(cd);
                current = file;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    private void save() {
        File file;
        if (current == null) {
            FileChooser fc = new FileChooser();
            fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML files", "*.xml"));
            file = fc.showSaveDialog(PrototypeDesignerApp.getMainController().getStage());
        } else file = current;
        if (file != null) {
            try {
                saveXmlFile(file);
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
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML files", "*.xml"));
        File file = fc.showSaveDialog(PrototypeDesignerApp.getMainController().getStage());
        if (file != null) {
            try {
                saveXmlFile(file);
                current = file;
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void saveXmlFile(File file) throws IOException {
        XmlMapper mapper = new XmlMapper();
        try (FileWriter fw = new FileWriter(file)) {
            String xml = mapper.writeValueAsString(PrototypeDesignerApp.getMainController().getDesign());
            fw.write(xml);
            fw.flush();
        }
    }

    @FXML
    private void exportBom() {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text files", "*.txt"));
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV files", "*.csv"));
        File file = fc.showSaveDialog(PrototypeDesignerApp.getMainController().getStage());
        if (file != null) {
            boolean csv = fc.getSelectedExtensionFilter().getDescription().startsWith("CSV");
            CircuitDesign design = PrototypeDesignerApp.getMainController().getDesign();
            List<String> bom = design.getSchematicsComponents().stream().sorted(Comparator.comparing(Component::getIdentifier))
                    .map(c -> String.join(
                    csv ? "," : "\t",
                    c.getIdentifier(), c instanceof ComponentValue ? ((ComponentValue) c).getValue() : c.getType()))
                    .collect(Collectors.toList());
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                for (String line: bom) {
                    writer.write(line);
                    writer.newLine();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    @FXML
    private void exportSchematics() {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG images", "*.png"));
        File file = fc.showSaveDialog(PrototypeDesignerApp.getMainController().getStage());
        if (file != null) {
            Canvas canvas = PrototypeDesignerApp.getMainController().getSchematicsViewController().getSchematicsCanvas();
            WritableImage writableImage = new WritableImage((int) canvas.getWidth(), (int) canvas.getHeight());
            canvas.snapshot(null, writableImage);
            RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
            try {
                ImageIO.write(renderedImage, "png", file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
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
        alert.initOwner(PrototypeDesignerApp.getMainController().getStage());
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.showAndWait();
    }

}
