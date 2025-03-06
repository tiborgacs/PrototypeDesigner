package prototypedesigner.PrototypeDesigner.controller;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.util.Pair;
import prototypedesigner.PrototypeDesigner.components.Coordinate;
import prototypedesigner.PrototypeDesigner.components.Wire;
import prototypedesigner.PrototypeDesigner.components.WireBuilder;

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
        // TODO: row select -> redraw callback/handle
        confirmConnectionsDialog.getDialogPane().setContent(intersectionCandidateTable);
        return confirmConnectionsDialog;
    }
}
