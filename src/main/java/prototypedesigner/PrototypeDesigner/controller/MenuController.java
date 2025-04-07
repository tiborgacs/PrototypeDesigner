package prototypedesigner.PrototypeDesigner.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import prototypedesigner.PrototypeDesigner.CircuitDesign;
import prototypedesigner.PrototypeDesigner.PrototypeDesignerApp;
import prototypedesigner.PrototypeDesigner.components.Component;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
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
                if (cd.getSchematicsComponents() == null) cd.setSchematicsComponents(new ArrayList<>());
                if (cd.getProtoboardComponents() == null) cd.setProtoboardComponents(new ArrayList<>());
                if (cd.getStripboardComponents() == null) cd.setStripboardComponents(new ArrayList<>());
                if (cd.getConnectionsOnSchematics() == null) cd.setConnectionsOnSchematics(new ArrayList<>());
                if (cd.getConnectionsOnProtoboard() == null) cd.setConnectionsOnProtoboard(new ArrayList<>());
                if (cd.getConnectionsOnStripboard() == null) cd.setConnectionsOnStripboard(new ArrayList<>());
                if (cd.getLinksOnStripboard() == null) cd.setLinksOnStripboard(new ArrayList<>());
                if (cd.getViasOnProtoboard() == null) cd.setViasOnProtoboard(new ArrayList<>());
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
                    c.getIdentifier(), c.getType()))
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
            // TODO maybe clear grid
            Canvas canvas = PrototypeDesignerApp.getMainController().getSchematicsViewController().getSchematicsCanvas();
            WritableImage writableImage = new WritableImage(
                    PrototypeDesignerApp.getMainController().getSchematicsViewController().getCropWidth() + 24,
                    PrototypeDesignerApp.getMainController().getSchematicsViewController().getCropHeight() + 24);
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
    private void exportStripboard() {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG images", "*.png"));
        File file = fc.showSaveDialog(PrototypeDesignerApp.getMainController().getStage());
        if (file != null) {
            Canvas canvas = PrototypeDesignerApp.getMainController().getStripboardViewController().getStripboardCanvas();
            int w = PrototypeDesignerApp.getMainController().getStripboardViewController().getBoardWidth() * 24;
            if (w == 0) w = (int) canvas.getWidth();
            int h = PrototypeDesignerApp.getMainController().getStripboardViewController().getBoardHeight() * 24;
            if (h == 0) h = (int) canvas.getHeight();
            WritableImage writableImage = new WritableImage(w, h);
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
    private void exportProtoboard() {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG images", "*.png"));
        File file = fc.showSaveDialog(PrototypeDesignerApp.getMainController().getStage());
        if (file != null) {
            Canvas canvas = PrototypeDesignerApp.getMainController().getProtoboardViewController().getProtoboardCanvas();
            int w = PrototypeDesignerApp.getMainController().getProtoboardViewController().getBoardWidth() * 24;
            if (w == 0) w = (int) canvas.getWidth();
            int h = PrototypeDesignerApp.getMainController().getProtoboardViewController().getBoardHeight() * 24;
            if (h == 0) h = (int) canvas.getHeight();
            WritableImage writableImage = new WritableImage(w, h);
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
    private void projectInfo() {
        CircuitDesign design = PrototypeDesignerApp.getMainController().getDesign();
        StringProperty designNameProperty = new SimpleStringProperty(design.getProjectName());
        StringProperty designAuthorProperty = new SimpleStringProperty(design.getAuthor());
        BooleanProperty hasSchematicProperty = new SimpleBooleanProperty(design.isSchematics());
        BooleanProperty hasStripboardProperty = new SimpleBooleanProperty(design.isStripboard());
        BooleanProperty hasProtoboardProperty = new SimpleBooleanProperty(design.isProtoboard());
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setTitle("Project info");
        alert.setHeaderText(current == null ? "Unsaved project" : current.getName());
        VBox container = new VBox();
        container.getChildren().add(new Label("Project name:"));
        TextField  nameField = new TextField(design.getProjectName());
        designNameProperty.bind(nameField.textProperty());
        container.getChildren().add(nameField);
        container.getChildren().add(new Label("Project author:"));
        TextField  authorField = new TextField(design.getAuthor());
        designAuthorProperty.bind(authorField.textProperty());
        container.getChildren().add(authorField);
        CheckBox schematicsCheckbox = new CheckBox("Based on schematics");
        schematicsCheckbox.setSelected(design.isSchematics());
        hasSchematicProperty.bind(schematicsCheckbox.selectedProperty());
        container.getChildren().add(schematicsCheckbox);
        CheckBox stripboardCheckbox = new CheckBox("Stripboard design");
        stripboardCheckbox.setSelected(design.isStripboard());
        hasStripboardProperty.bind(stripboardCheckbox.selectedProperty());
        container.getChildren().add(stripboardCheckbox);
        CheckBox protoboardCheckbox = new CheckBox("Protoboard design");
        protoboardCheckbox.setSelected(design.isProtoboard());
        hasProtoboardProperty.bind(protoboardCheckbox.selectedProperty());
        container.getChildren().add(protoboardCheckbox);
        container.setPadding(new Insets(6.0));
        container.setSpacing(6.0);
        alert.getDialogPane().setContent(container);
        alert.getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initOwner(PrototypeDesignerApp.getMainController().getStage());
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            design.setProjectName(designNameProperty.get());
            design.setAuthor(designAuthorProperty.get());
            design.setSchematics(hasSchematicProperty.get());
            design.setStripboard(hasStripboardProperty.get());
            design.setProtoboard(hasProtoboardProperty.get());
        }
    }

    @FXML
    private void about() {
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setTitle("About");
        alert.setHeaderText("Prototype Designer");
        alert.setContentText("Dissertation of Tibor Gacs\n" +
                        "Consultant: Tibor Gregorics Dr.\n" +
                "Used technologies: Java 11 (OpenJDK),\n" +
                "OpenJFX, Project Lombok, FasterXML Jackson,\n" +
                "JUnit, Apache Maven");
        alert.getButtonTypes().add(ButtonType.OK);
        alert.initOwner(PrototypeDesignerApp.getMainController().getStage());
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.showAndWait();
    }

}
