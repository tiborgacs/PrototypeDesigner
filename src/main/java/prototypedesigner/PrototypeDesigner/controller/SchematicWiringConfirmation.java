package prototypedesigner.PrototypeDesigner.controller;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.layout.VBox;
import javafx.util.Pair;
import javafx.util.StringConverter;
import prototypedesigner.PrototypeDesigner.components.*;

import java.util.List;

public class SchematicWiringConfirmation {

    public static Alert createDialog(WireBuilder.ConnectionContainer container, List<Pair<Wire, Coordinate>> selectedIntersections) {
        Alert confirmConnectionsDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmConnectionsDialog.setTitle("Confirm connections");
        confirmConnectionsDialog.setHeaderText("Select which connections to apply:");
        TableView<Pair<Wire, Coordinate>> intersectionCandidateTable = new TableView<>();
        intersectionCandidateTable.getItems().addAll(container.getIntersectionCandidates());
        intersectionCandidateTable.setEditable(true);
        TableColumn<Pair<Wire, Coordinate>, Integer> intersectionXColumn = new TableColumn<>("X");
        intersectionXColumn.setCellValueFactory(value ->
                new SimpleObjectProperty<>(value.getValue().getValue().getX()/12));
        TableColumn<Pair<Wire, Coordinate>, Integer> intersectionYColumn = new TableColumn<>("Y");
        intersectionYColumn.setCellValueFactory(value ->
                new SimpleObjectProperty<>(value.getValue().getValue().getY()/12));
        TableColumn<Pair<Wire, Coordinate>, Boolean> intersectionSelectColumn = new TableColumn<>("Select");
        intersectionSelectColumn.setEditable(true);
        intersectionSelectColumn.setCellValueFactory(value -> {
            BooleanProperty property = new SimpleBooleanProperty(false);
            property.addListener((observable, oldValue, newValue) -> {
                if (newValue) selectedIntersections.add(value.getValue());
                else selectedIntersections.remove(value.getValue());
            });
            return property;
        });
        intersectionSelectColumn.setCellFactory(CheckBoxTableCell.forTableColumn(intersectionSelectColumn));
        intersectionCandidateTable.getColumns().addAll(
                intersectionSelectColumn,
                intersectionXColumn,
                intersectionYColumn
        );
        ListView<Terminal> components = new ListView<>();
        components.getItems().addAll(container.getComponentTerminals());
        components.setCellFactory(value -> new ListCell<>() {
            @Override
            protected void updateItem(Terminal item, boolean empty) {
                super.updateItem(item, empty);
                if (!empty || item != null) setText(item.getIdentifier() + " of " + item.getComponent().getIdentifier()
                        + " at [x=" + item.getSchX()/12 + ",y=" + item.getSchY()/12 + "]"); // TODO: table display?
                else setText("");
            }
        });
        if (container.getComponentTerminals().size() > 0 && container.getIntersectionCandidates().size() > 0) {
            VBox tableBox = new VBox();
            tableBox.getChildren().addAll(intersectionCandidateTable, components);
            confirmConnectionsDialog.getDialogPane().setContent(tableBox);
        } else if (container.getIntersectionCandidates().size() > 0) {
            confirmConnectionsDialog.getDialogPane().setContent(intersectionCandidateTable);
        } else {
            confirmConnectionsDialog.getDialogPane().setContent(components);
        }
        // TODO: row select -> redraw callback/handle
        return confirmConnectionsDialog;
    }
}
