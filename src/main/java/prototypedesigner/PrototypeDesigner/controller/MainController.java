package prototypedesigner.PrototypeDesigner.controller;

import com.google.common.eventbus.EventBus;
import javafx.fxml.FXML;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import prototypedesigner.PrototypeDesigner.CircuitDesign;

import java.util.ArrayList;

public class MainController {

    @Getter @Setter
    private Stage stage;
    private CircuitDesign design = new CircuitDesign();
    private boolean hasChanges;

    @FXML private MenuBar menuBar;
    @FXML private MenuController menuBarController;
    @FXML private BorderPane schematicsView;
    @Getter @FXML private SchematicsController schematicsViewController;
    @FXML private BorderPane stripboardView;
    @FXML private StripboardController stripboardViewController;
    @FXML private BorderPane protoboardView;
    @FXML private ProtoboardController protoboardViewController;

    private EventBus eventbus = new EventBus(); // TODO: register listeners

    @FXML public void initialize() {
        eventbus.register(schematicsViewController);
        eventbus.register(stripboardViewController);
        eventbus.register(protoboardViewController);
        newDesign();
    }

    public void newDesign() {
        design = new CircuitDesign();
        design.setSchematicsComponents(new ArrayList<>());
        design.setConnectionsOnSchematics(new ArrayList<>());
        schematicsViewController.setDesign(design);
    }

    public void setDesign(CircuitDesign design) {
        this.design = design;
        schematicsViewController.setDesign(design);
    }

    public CircuitDesign getDesign() {
        return design;
    }
}
