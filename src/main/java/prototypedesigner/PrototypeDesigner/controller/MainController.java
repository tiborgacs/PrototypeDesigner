package prototypedesigner.PrototypeDesigner.controller;

import com.google.common.eventbus.EventBus;
import javafx.fxml.FXML;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import prototypedesigner.PrototypeDesigner.CircuitDesign;
import prototypedesigner.PrototypeDesigner.ProtoboardTrace;
import prototypedesigner.PrototypeDesigner.ProtoboardVia;
import prototypedesigner.PrototypeDesigner.components.*;

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
    @Getter @FXML private StripboardController stripboardViewController;
    @FXML private BorderPane protoboardView;
    @Getter @FXML private ProtoboardController protoboardViewController;

    @FXML public void initialize() {
        newDesign();
    }

    public void newDesign() {
        design = new CircuitDesign();
        design.setSchematicsComponents(new ArrayList<>());
        design.setProtoboardComponents(new ArrayList<>());
        design.setStripboardComponents(new ArrayList<>());
        design.setConnectionsOnSchematics(new ArrayList<>());
        design.setConnectionsOnProtoboard(new ArrayList<>());
        design.setConnectionsOnStripboard(new ArrayList<>());
        design.setLinksOnStripboard(new ArrayList<>());
        design.setViasOnProtoboard(new ArrayList<>());
        schematicsViewController.setDesign(design);
        stripboardViewController.setDesign(design);
        protoboardViewController.setDesign(design);
        Wire.setCounter(0);
        Transistor.setCounter(0);
        Resistor.setCounter(0);
        Capacitor.setCounter(0);
        Diode.setCounter(0);
        IntegratedCircuit.setCounter(0);
        ProtoboardTrace.setCounter(0);
        ProtoboardVia.setCounter(0);
    }

    public void setDesign(CircuitDesign design) {
        this.design = design;
        schematicsViewController.setDesign(design);
        stripboardViewController.setDesign(design);
        protoboardViewController.setDesign(design);
        Wire.setCounter(design.getConnectionsOnSchematics().size());
        int transistorCount = 0;
        int resistorCount = 0;
        int capacitorCount = 0;
        int diodeCount = 0;
        int icCount = 0;
        for (Component c: design.getSchematicsComponents()) {
            if (c instanceof Transistor) {
                int idNr = Integer.parseInt(c.getIdentifier().replace("Q", ""));
                if (idNr > transistorCount) transistorCount = idNr;
            }
            if (c instanceof Resistor) {
                int idNr = Integer.parseInt(c.getIdentifier().replace("R", ""));
                if (idNr > resistorCount) resistorCount = idNr;
            }
            if (c instanceof Capacitor) {
                int idNr = Integer.parseInt(c.getIdentifier().replace("C", ""));
                if (idNr > capacitorCount) capacitorCount = idNr;
            }
            if (c instanceof Diode) {
                int idNr = Integer.parseInt(c.getIdentifier().replace("D", ""));
                if (idNr > diodeCount) diodeCount = idNr;
            }
            if (c instanceof IntegratedCircuit) {
                int idNr = Integer.parseInt(c.getIdentifier().replace("IC", ""));
                if (idNr > icCount) icCount = idNr;
            }
        }
        Transistor.setCounter(transistorCount);
        Resistor.setCounter(resistorCount);
        Capacitor.setCounter(capacitorCount);
        Diode.setCounter(diodeCount);
        IntegratedCircuit.setCounter(icCount);
        ProtoboardTrace.setCounter(design.getConnectionsOnProtoboard() == null ? 0 :
                design.getConnectionsOnProtoboard().stream()
                        .mapToInt(trace -> Integer.parseInt(trace.getIdentifier().replace("trace#", "")))
                        .max().orElse(0));
        ProtoboardVia.setCounter(design.getViasOnProtoboard() == null ? 0 :
                design.getViasOnProtoboard().stream()
                        .mapToInt(trace -> Integer.parseInt(trace.getIdentifier().replace("via#", "")))
                        .max().orElse(0));
    }

    public CircuitDesign getDesign() {
        return design;
    }
}
