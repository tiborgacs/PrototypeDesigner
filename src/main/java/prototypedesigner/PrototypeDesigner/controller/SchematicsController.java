package prototypedesigner.PrototypeDesigner.controller;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.CacheHint;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.control.cell.TextFieldTreeTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import prototypedesigner.PrototypeDesigner.converter.IntegerStringConverter;
import prototypedesigner.PrototypeDesigner.SchematicsWiringItem;
import prototypedesigner.PrototypeDesigner.components.Component;
import prototypedesigner.PrototypeDesigner.components.*;
import prototypedesigner.PrototypeDesigner.converter.NoOpStringConverter;

import static prototypedesigner.PrototypeDesigner.Utility.getRowParentItem;

public class SchematicsController {

	@FXML private ScrollPane scrollPane;
	@FXML private Canvas schematicsCanvas;
	@FXML private ToggleGroup buttonMode;
	@FXML private ToggleGroup orientationGroup;
	@FXML private ToggleButton singleAmpToggle;
	@FXML private ToggleButton dualAmpToggle;
	@FXML private ToggleButton quadAmpToggle;
	@FXML private ToggleButton wireToggle;
	@FXML private ToggleButton labelToggle;
	@FXML private ToggleButton resistorToggle;
	@FXML private ToggleButton capacitorToggle;
	@FXML private ToggleButton elcoToggle;
	@FXML private ToggleButton diodeToggle;
	@FXML private ToggleButton npnToggle;
	@FXML private ToggleButton pnpToggle;
	@FXML private ToggleButton njfetToggle;
	@FXML private ToggleButton pjfetToggle;
	@FXML private ToggleButton nmosToggle;
	@FXML private ToggleButton pmosToggle;
	@FXML private ToggleButton genericIcToggle;
	@FXML private ChoiceBox<Integer> genericIcPinsBox;
	@FXML private ToggleButton upToggle;
	@FXML private ToggleButton downToggle;
	@FXML private ToggleButton leftToggle;
	@FXML private ToggleButton rightToggle;
	
	private ObservableList<Component> components = FXCollections.observableArrayList();
	private ObservableList<Wire> wires = FXCollections.observableArrayList();
	private Wire lastWire = null;
	
	@FXML private TreeTableView<SchematicsWiringItem> wireTable;
	@FXML private TreeTableColumn<SchematicsWiringItem, String> wiringTypeColumn;
	@FXML private TreeTableColumn<SchematicsWiringItem, Integer> wireXColumn;
	@FXML private TreeTableColumn<SchematicsWiringItem, Integer> wireYColumn;
	@FXML private TreeTableColumn<SchematicsWiringItem, SchematicsWiringItem> deleteWireColumn;
	@FXML private TableView<Component> componentTable;
	@FXML private TableColumn<Component, String> componentIdColumn;
	@FXML private TableColumn<Component, String> componentTypeColumn;
	@FXML private TableColumn<Component, String> componentValueColumn;
	@FXML private TableColumn<Component, Component> deleteComponentColumn;

	@FXML
	private void initialize() {
		schematicsCanvas.heightProperty().bind(scrollPane.heightProperty());
		schematicsCanvas.widthProperty().bind(scrollPane.widthProperty());
		schematicsCanvas.heightProperty().addListener((ob, ov, nv) -> drawGrid());
		schematicsCanvas.widthProperty().addListener((ob, ov, nv) -> drawGrid());
		genericIcPinsBox.disableProperty().bind(genericIcToggle.selectedProperty().not());
		genericIcPinsBox.getItems().setAll(4, 6, 8, 10, 12, 14, 16, 18, 20);
		schematicsCanvas.setCache(true);
		schematicsCanvas.setCacheHint(CacheHint.SPEED);
		componentTable.setItems(components);
		componentIdColumn.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getIdentifier()));
		componentIdColumn.setCellFactory(value -> new TextFieldTableCell<>(new NoOpStringConverter()) {
            @Override public void commitEdit(String newValue) {
                super.commitEdit(newValue);
                getTableRow().getItem().setIdentifier(newValue);
            }
        });
		componentTypeColumn.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getType()));
		componentTypeColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		componentValueColumn.setCellValueFactory(value -> new SimpleStringProperty(
				value.getValue() instanceof ComponentValue ? ((ComponentValue) value.getValue()).getValue() : ""
		));
		componentValueColumn.setEditable(false); /*.setCellFactory(value -> new TextFieldTableCell<>() {
			@Override public void commitEdit(String newValue) {
				super.commitEdit(newValue);
				Component item = getTableRow().getItem();
				if (item != null && item instanceof ComponentValue) ; // FIXME edit value
			}
		});*/
		// TODO: value in separate interface, merge with component superclass
		deleteComponentColumn.setCellValueFactory(value -> new SimpleObjectProperty<>(value.getValue()));
		deleteComponentColumn.setCellFactory(value -> new TableCell<>() {
			@Override protected void updateItem(Component item, boolean empty) {
				super.updateItem(item, empty);
				if (item == null || empty) setGraphic(null);
				else {
					Button deleteButton = new Button("\uD83D\uDDD1");
					deleteButton.setOnAction(event -> {
						components.remove(item);
						drawGrid();
					});
					setGraphic(deleteButton);
				}
			}
		});
		wireTable.setRoot(new TreeItem<>(new SchematicsWiringItem()));
		wireTable.setShowRoot(false);
		wiringTypeColumn.setCellValueFactory(value -> {
			SchematicsWiringItem item = value.getValue().getValue();
			if (item.getWire() != null) return new ReadOnlyStringWrapper("Wire");
			if (item.getCoordinate() != null) return new ReadOnlyStringWrapper("Point");
			return null;
		});
		wireXColumn.setCellValueFactory(value -> {
			SchematicsWiringItem item = value.getValue().getValue();
			if (item.getCoordinate() != null) return new SimpleObjectProperty<>(item.getCoordinate().getX()/12);
			return null;
		});
		wireXColumn.setCellFactory(value ->
				new TextFieldTreeTableCell<>(new IntegerStringConverter()) {
			@Override public void commitEdit(Integer newValue) {
                super.commitEdit(newValue);
                getTreeTableRow().getItem().getCoordinate().setX(newValue * 12);
                drawGrid();
            }
        });
		wireYColumn.setCellValueFactory(value -> {
			SchematicsWiringItem item = value.getValue().getValue();
			if (item.getCoordinate() != null) return new SimpleObjectProperty<>(item.getCoordinate().getY()/12);
			return null;
		});
		wireYColumn.setCellFactory(value ->
				new TextFieldTreeTableCell<>(new IntegerStringConverter()) {
					@Override
					public void commitEdit(Integer newValue) {
						super.commitEdit(newValue);
						getTreeTableRow().getItem().getCoordinate().setY(newValue * 12);
						drawGrid();
					}
				});
		deleteWireColumn.setCellValueFactory(value -> value.getValue().valueProperty());
		deleteWireColumn.setCellFactory(value -> new TreeTableCell<>() {
			@Override protected void updateItem(SchematicsWiringItem item, boolean empty) {
				super.updateItem(item, empty);
				if (item == null || empty) setGraphic(null);
				else {
					Button deleteButton = new Button("\uD83D\uDDD1");
					deleteButton.setOnAction(event -> {
						if (item.getCoordinate() != null) {
							getRowParentItem(getTreeTableRow()).getValue().getWire().getSchPoints().remove(item.getCoordinate());
							getRowParentItem(getTreeTableRow()).getParent().getChildren().remove(this.getTreeTableRow().getTreeItem());
						}
						if (item.getWire() != null) {
							wires.remove(item.getWire());
							wireTable.getRoot().getChildren().remove(getTreeTableRow().getTreeItem());
						}
						drawGrid();
					});
					setGraphic(deleteButton);
				}
			}
		});
		wireTable.setRowFactory(value -> new TreeTableRow<>() {
			@Override protected void updateItem(SchematicsWiringItem item, boolean empty) {
				super.updateItem(item, empty);
				if (item == null || empty) setGraphic(null);
			}
		});
		componentTable.setRowFactory(value -> new TableRow<>() {
			@Override protected void updateItem(Component item, boolean empty) {
				super.updateItem(item, empty);
				if (item == null || empty) setGraphic(null);
			}
		});
		componentTable.getSelectionModel().selectedItemProperty().addListener(
				(observableValue, oldValue, newValue) -> {
					if (oldValue != null) oldValue.setHighlighted(false);
					if (newValue != null) newValue.setHighlighted(true);
					drawGrid();
				});
		wireTable.getSelectionModel().selectedItemProperty()
				.addListener(
				(observableValue, oldValue, newValue) -> {
					if (oldValue != null && oldValue.getValue() != null) {
						if (oldValue.getValue().getCoordinate() != null)
							oldValue.getParent().getValue().getWire().setHighlighted(false);
						if (oldValue.getValue().getWire() != null)
							oldValue.getValue().getWire().setHighlighted(false);
					}
					if (newValue != null && newValue.getValue() != null) {
						if (newValue.getValue().getWire() != null)
							newValue.getValue().getWire().setHighlighted(true);
						if (newValue.getValue().getCoordinate() != null)
							newValue.getParent().getValue().getWire().setHighlighted(true);
					}
					drawGrid();
				});
	}
	
	private void drawGrid() {
		int h = (int) (schematicsCanvas.getHeight() / 24);
		int w = (int) (schematicsCanvas.getWidth() / 24);
		GraphicsContext context = schematicsCanvas.getGraphicsContext2D();
		context.setGlobalAlpha(1.0);
		context.setFill(Color.WHITE);
		context.fillRect(0, 0, w*24, h*24);
		context.setStroke(Color.LIGHTBLUE);
		for (int x = 0; x < w; x++) {
			for (int y = 0; y < h; y++) {
				context.strokeRect(x*24, y*24, 24, 24);
			}
		}
        components.forEach(c -> c.drawOnSchematics(context));
        wires.forEach(wire -> wire.drawOnSchematics(context));
        if (lastWire != null) lastWire.drawOnSchematics(context);
	}
	
	@FXML
	private void onClick(MouseEvent e) {
		int x = (int) e.getX();
		x = x - (x % 12);
		int y = (int) e.getY();
		y = y - (y % 12);
		ComponentOrientation orientation = null;
		if (upToggle.isSelected()) orientation = ComponentOrientation.UP;
		else if (downToggle.isSelected()) orientation = ComponentOrientation.DOWN;
		else if (leftToggle.isSelected()) orientation = ComponentOrientation.LEFT;
		else if (rightToggle.isSelected()) orientation = ComponentOrientation.RIGHT;
		else return;
		if (singleAmpToggle.isSelected()) {
			Component c = new SingleOpAmp();
			c.setSchX(x);
			c.setSchY(y);
			components.add(c);
		}
		if (dualAmpToggle.isSelected()) {
			Component c = new DualOpAmp();
			c.setSchX(x);
			c.setSchY(y);
			components.add(c);
		}
		if (quadAmpToggle.isSelected()) {
			Component c = new QuadOpAmp();
			c.setSchX(x);
			c.setSchY(y);
			components.add(c);
		}
		if (resistorToggle.isSelected()) {
			Resistor c = new Resistor();
			c.setSchematicsOrientation(orientation);
			c.setSchX(x);
			c.setSchY(y);
			components.add(c);
		}
		if (capacitorToggle.isSelected()) {
			Capacitor c = new Capacitor();
			c.setSchematicsOrientation(orientation);
			c.setSchX(x);
			c.setSchY(y);
			components.add(c);
		}
		if (elcoToggle.isSelected()) {
			PolarizedCapacitor c = new PolarizedCapacitor();
			c.setSchematicsOrientation(orientation);
			c.setSchX(x);
			c.setSchY(y);
			components.add(c);
		}
		if (diodeToggle.isSelected()) {
			Diode c = new Diode();
			c.setSchematicsOrientation(orientation);
			c.setSchX(x);
			c.setSchY(y);
			components.add(c);
		}
		if (pnpToggle.isSelected()) {
			BipolarJunctionTransistor c = new BipolarJunctionTransistor();
			c.setPolarity("PNP");
			c.setSchematicsOrientation(orientation);
			c.setSchX(x);
			c.setSchY(y);
			components.add(c);
		}
		if (npnToggle.isSelected()) {
			BipolarJunctionTransistor c = new BipolarJunctionTransistor();
			c.setPolarity("NPN");
			c.setSchematicsOrientation(orientation);
			c.setSchX(x);
			c.setSchY(y);
			components.add(c);
		}
		if (pjfetToggle.isSelected()) {
			JunctionFieldEffectTransistor c = new JunctionFieldEffectTransistor();
			c.setChannel("P");
			c.setSchX(x);
			c.setSchY(y);
			components.add(c);
		}
		if (njfetToggle.isSelected()) {
			JunctionFieldEffectTransistor c = new JunctionFieldEffectTransistor();
			c.setChannel("N");
			c.setSchX(x);
			c.setSchY(y);
			components.add(c);
		}
		if (pmosToggle.isSelected()) {
			MetalOxideSemiconductorFET c = new MetalOxideSemiconductorFET();
			c.setChannel("P");
			c.setSchX(x);
			c.setSchY(y);
			components.add(c);
		}
		if (nmosToggle.isSelected()) {
			MetalOxideSemiconductorFET c = new MetalOxideSemiconductorFET();
			c.setChannel("N");
			c.setSchX(x);
			c.setSchY(y);
			components.add(c);
		}
		if (genericIcToggle.isSelected()) {
			//
		}
		if (labelToggle.isSelected()) {
			// TODO: add labels or tags
		}
		if (wireToggle.isSelected()) {
			if (lastWire == null) lastWire = new Wire();
			lastWire.drawSch(x, y);
			if (lastWire.getSchPoints().size() > 1) {
				for (Wire wire : wires) {
					for (int i = 0; i < wire.getSchPoints().size() - 1; i++) {
						for (int j = 0; j < lastWire.getSchPoints().size() - 1; j++) {
							Coordinate intersection = Wire.intersects2(
									lastWire.getSchPoints().get(j),
									lastWire.getSchPoints().get(j+1),
									wire.getSchPoints().get(i),
									wire.getSchPoints().get(i+1));
							if (intersection != null) {
								Tooltip tp = new Tooltip();
								Button bt = new Button("hallo");
								// FIXME replace with state machine, finish drawing -> prompt intersection, connections etc
								bt.setOnAction(be -> {
									wire.connectToWire(lastWire, intersection);
									tp.hide();
									scrollPane.setTooltip(null);
								});
								tp.setGraphic(bt);
								scrollPane.setTooltip(tp);
								tp.show(
										MainController.getStage(), e.getSceneX(), e.getSceneY()
										// FIXME: state machine, finish drawing -> show dialog, list all possible connections, select what to apply
								);
							}
						}
					}
				}
			}
			for (Component component: components) {
				for (Terminal terminal: component.getTerminals()) {
					if (terminal.getSchX() == x && terminal.getSchY() == y) {
						terminal.connectToWire(lastWire);
						System.out.println("connected " + terminal + " of " + terminal.getComponent() + " with " + lastWire);
					}
				}
			}
		} else {
			if (lastWire != null) {
				wires.add(lastWire);
				TreeItem<SchematicsWiringItem> treeItem = new TreeItem<>(new SchematicsWiringItem(lastWire));
				for (Coordinate coordinate: lastWire.getSchPoints()) {
					TreeItem<SchematicsWiringItem> pointItem = new TreeItem<>(new SchematicsWiringItem(coordinate));
					treeItem.getChildren().add(pointItem);
				}
				wireTable.getRoot().getChildren().add(treeItem);
			}
			lastWire = null;
			scrollPane.setTooltip(null);
		}
		drawGrid();
	}

}
