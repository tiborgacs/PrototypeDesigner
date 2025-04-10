package prototypedesigner.PrototypeDesigner.controller;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
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
import javafx.scene.shape.ArcType;
import javafx.util.Pair;
import lombok.Getter;
import prototypedesigner.PrototypeDesigner.CircuitDesign;
import prototypedesigner.PrototypeDesigner.converter.IntegerStringConverter;
import prototypedesigner.PrototypeDesigner.SchematicsWiringItem;
import prototypedesigner.PrototypeDesigner.components.Component;
import prototypedesigner.PrototypeDesigner.components.*;
import prototypedesigner.PrototypeDesigner.converter.NoOpStringConverter;

import java.util.*;
import java.util.stream.Collectors;

import static prototypedesigner.PrototypeDesigner.Utility.getRowParentItem;

public class SchematicsController {

	@FXML private ScrollPane scrollPane;
	@Getter @FXML private Canvas schematicsCanvas;
	@FXML private ToggleGroup buttonMode;
	@SuppressWarnings("unused") @FXML private ToggleGroup orientationGroup;
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
	private WireBuilder wireBuilder;
	@FXML private Label coordinateLabel;
	
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
	private CircuitDesign design;
	@Getter private int cropWidth;
	@Getter private int cropHeight;

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
		componentTypeColumn.setCellFactory(TextFieldTableCell.forTableColumn()); // TODO: probably doesn't need editing
		componentValueColumn.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getValue()));
		componentValueColumn.setCellFactory(value -> new TextFieldTableCell<>(new NoOpStringConverter()) {
			@Override public void commitEdit(String newValue) {
				super.commitEdit(newValue);
				getTableRow().getItem().setValue(newValue);
			}
		});
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
			if (item.getWire() != null) return new ReadOnlyStringWrapper("Wire " + item.getWire().getIdentifier());
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
				setText(newValue.toString());
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
						setText(newValue.toString());
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
							getRowParentItem(getTreeTableRow()).getChildren().remove(this.getTreeTableRow().getTreeItem());
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
					drawGrid();
					if (newValue != null && newValue.getValue() != null) {
						if (newValue.getValue().getWire() != null) {
							newValue.getValue().getWire().setHighlighted(true);
							drawGrid();
						}
						if (newValue.getValue().getCoordinate() != null) {
							newValue.getParent().getValue().getWire().setHighlighted(true);
							Coordinate c = newValue.getValue().getCoordinate();
							drawGrid();
							GraphicsContext ctx = schematicsCanvas.getGraphicsContext2D();
							ctx.setStroke(Color.RED);
							ctx.strokeArc(c.getX() - 2, c.getY() - 2,4, 4, 0, 360, ArcType.CHORD);
						}
					}
					//drawGrid();
				});
		schematicsCanvas.setOnMouseMoved(event -> {
			coordinateLabel.setText(String.format("X: %d, Y: %d", snap((int) event.getX())/12, snap((int) event.getY())/12));
		});
		wires.addListener((ListChangeListener<? super Wire>) lc -> {
			// FIXME: list change listener, elements must be distinct
			lc.next();
			lc.getAddedSubList().stream().distinct().forEach(wire ->
				design.getConnectionsOnSchematics().stream().filter(w -> w.getIdentifier().equals(wire.getIdentifier()))
						.findFirst().ifPresentOrElse(
								w -> {
									int idx = design.getConnectionsOnSchematics().indexOf(w);
									design.getConnectionsOnSchematics().set(idx, wire);
								}, () -> design.getConnectionsOnSchematics().add(wire))
			);
			design.getConnectionsOnSchematics().removeAll(lc.getRemoved());
		});
		components.addListener((ListChangeListener<? super Component>) lc -> {
			lc.next();
			lc.getAddedSubList().stream().distinct().forEach(component ->
				design.getSchematicsComponents().stream().filter(c -> c.getIdentifier().equals(component.getIdentifier()))
						.findFirst().ifPresentOrElse(
								c -> {
									int idx = design.getSchematicsComponents().indexOf(c);
									design.getSchematicsComponents().set(idx, component);
								}, () -> design.getSchematicsComponents().add(component))
			);
			design.getSchematicsComponents().removeAll(lc.getRemoved());
		});
	}
	
	private void drawGrid() {
		GraphicsContext context = getGraphicsContextWithGrids();
		int maxX = 0;
		int maxY = 0;
		for (Component c: components) {
			c.drawOnSchematics(context);
			OptionalInt optX = c.getTerminals().stream().mapToInt(t -> t.getSchX()).max();
			OptionalInt optY = c.getTerminals().stream().mapToInt(t -> t.getSchY()).max();
			if (optX.isPresent() && optX.getAsInt() > maxX) maxX = optX.getAsInt();
			if (optY.isPresent() && optY.getAsInt() > maxY) maxY = optY.getAsInt();
		}
		for (Wire w: wires) {
			w.drawOnSchematics(context);
			for (Coordinate c: w.getSchPoints()) {
				if (c.getX() > maxX) maxX = c.getX();
				if (c.getY() > maxY) maxY = c.getY();
			}
		}
		if (maxX == 0) maxX = (int) schematicsCanvas.getWidth();
		if (maxY == 0) maxY = (int) schematicsCanvas.getHeight();
		cropWidth = maxX;
		cropHeight = maxY;
        if (wireBuilder != null) wireBuilder.getWire().drawOnSchematics(context);
	}

	private GraphicsContext getGraphicsContextWithGrids() {
		int h = (int) (schematicsCanvas.getHeight() / 24);
		int w = (int) (schematicsCanvas.getWidth() / 24);
		GraphicsContext context = schematicsCanvas.getGraphicsContext2D();
		context.setGlobalAlpha(1.0);
		context.setFill(Color.WHITE);
		context.fillRect(0, 0, w*24, h*24);
		context.setStroke(Color.LIGHTBLUE);
		for (int x = 0; x < w; x++) {
			if (x > 0) context.strokeText((x * 2) % 100 + "", x * 24 + 1, 12);
			for (int y = 0; y < h; y++) {
				if (x == 0)context.strokeText((y * 2) % 100 + "", 0, y * 24 - 1);
				context.strokeRect(x*24, y*24, 24, 24);
			}
		}
		return context;
	}

	@FXML
	private void onClick(MouseEvent e) {
		int x = (int) e.getX();
		x = snap(x);
		System.out.println(x);
		int y = (int) e.getY();
		y = snap(y);
		ComponentOrientation orientation = null;
		if (upToggle.isSelected()) orientation = ComponentOrientation.UP;
		else if (downToggle.isSelected()) orientation = ComponentOrientation.DOWN;
		else if (leftToggle.isSelected()) orientation = ComponentOrientation.LEFT;
		else if (rightToggle.isSelected()) orientation = ComponentOrientation.RIGHT;
		else if (buttonMode.getSelectedToggle() == null) return;
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
			NonPolarizedCapacitor c = new NonPolarizedCapacitor();
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
			c.setPolarity(Polarity.P);
			c.setSchematicsOrientation(orientation);
			c.setSchX(x);
			c.setSchY(y);
			components.add(c);
		}
		if (npnToggle.isSelected()) {
			BipolarJunctionTransistor c = new BipolarJunctionTransistor();
			c.setPolarity(Polarity.N);
			c.setSchematicsOrientation(orientation);
			c.setSchX(x);
			c.setSchY(y);
			components.add(c);
		}
		if (pjfetToggle.isSelected()) {
			JunctionFieldEffectTransistor c = new JunctionFieldEffectTransistor();
			c.setPolarity(Polarity.P);
			c.setSchX(x);
			c.setSchY(y);
			components.add(c);
		}
		if (njfetToggle.isSelected()) {
			JunctionFieldEffectTransistor c = new JunctionFieldEffectTransistor();
			c.setPolarity(Polarity.N);
			c.setSchX(x);
			c.setSchY(y);
			components.add(c);
		}
		if (pmosToggle.isSelected()) {
			MetalOxideSemiconductorFET c = new MetalOxideSemiconductorFET();
			c.setPolarity(Polarity.N);
			c.setSchX(x);
			c.setSchY(y);
			components.add(c);
		}
		if (nmosToggle.isSelected()) {
			MetalOxideSemiconductorFET c = new MetalOxideSemiconductorFET();
			c.setPolarity(Polarity.N);
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
			if (wireBuilder == null) wireBuilder = new WireBuilder(new Coordinate(x, y));
			wireBuilder.addCoordinates(x, y);
			if (e.getClickCount() == 2) {
				WireBuilder.ConnectionContainer container = wireBuilder.checkForConnections(wires, components);
				if (container != null && !container.isEmpty()) {
					List<Pair<Wire, Coordinate>> selectedIntersections = new ArrayList<>();
					Alert confirmConnectionsDialog = SchematicWiringConfirmation.createDialog(container, selectedIntersections);
					Optional<ButtonType> result = confirmConnectionsDialog.showAndWait();
					if (result.isPresent() && result.get() == ButtonType.OK) {
						for (Pair<Wire, Coordinate> pair: selectedIntersections)
							wireBuilder.getWire().connectToWire(pair.getKey(), pair.getValue());
						//container.getComponentTerminals().forEach(t -> t.connectToWire(wireBuilder.getWire()));
						container.getComponentTerminals().stream().distinct().
								forEach(t -> wireBuilder.getWire().getConnectedComponents().add(t));
						wireBuilder.getWire().setHighlighted(false);
					} else {
						// TODO: cancel connections if any
						drawGrid();
						return;
					}
				}
				wires.add(wireBuilder.getWire());
				TreeItem<SchematicsWiringItem> treeItem = new TreeItem<>(new SchematicsWiringItem(wireBuilder.getWire()));
				for (Coordinate coordinate: wireBuilder.getWire().getSchPoints()) {
					TreeItem<SchematicsWiringItem> pointItem = new TreeItem<>(new SchematicsWiringItem(coordinate));
					treeItem.getChildren().add(pointItem);
				}
				wireTable.getRoot().getChildren().add(treeItem);
				wireTable.getSelectionModel().select(treeItem);
				wireBuilder = null;
			}
		}
		drawGrid();
	}

	private int snap(int coordinate) {
		int hard = coordinate - (coordinate % 12);
		int soft = coordinate - (coordinate % 6);
		return hard == soft ? hard : (soft + 6);
	}

	public void setDesign(CircuitDesign design) {
		this.design = design;
		components.setAll(design.getSchematicsComponents());
		wires.setAll(design.getConnectionsOnSchematics());
		wireTable.getRoot().getChildren().clear();
		wires.forEach(wire -> {
			TreeItem<SchematicsWiringItem> treeItem = new TreeItem<>(new SchematicsWiringItem(wire));
			for (Coordinate coordinate: wire.getSchPoints()) {
				TreeItem<SchematicsWiringItem> pointItem = new TreeItem<>(new SchematicsWiringItem(coordinate));
				treeItem.getChildren().add(pointItem);
			}
			wireTable.getRoot().getChildren().add(treeItem);
		});
		drawGrid();
	}

    public List<Set<Terminal>> getConnections() {
		List<Set<Wire>> traversed = new ArrayList<>();
		for (Wire wire: design.getConnectionsOnSchematics()) {
			if (traversed.stream().noneMatch(s -> s.contains(wire)
					|| wire.getConnectedWires().stream().anyMatch(s::contains))) {
				Set<Wire> net = new HashSet<>();
				net.add(wire);
				net.addAll(wire.getConnectedWires());
				traversed.add(net);
			} else {
				traversed.stream().filter(s -> s.contains(wire)
						|| wire.getConnectedWires().stream().anyMatch(s::contains))
						.findFirst().ifPresent(s -> {
							s.add(wire);
							s.addAll(wire.getConnectedWires());
						});
			}
		}
		// FIXME probably unnecessary to run again O(n*n)
		for (Set<Wire> s1: traversed) {
			for (Set<Wire> s2: traversed) {
				if (s1 != s2 && !s1.isEmpty() && !s2.isEmpty()) {
					Set<Wire> temp = new HashSet<>(s1);
					temp.retainAll(new HashSet<>(s2));
					if (!temp.isEmpty()) {
						s1.addAll(s2);
						s2.clear();
					}
				}
			}
		}
		traversed.removeIf(Set::isEmpty);
		return traversed.stream()
				.map(s -> s.stream()
						.flatMap(w -> w.getConnectedComponents().stream())
						.collect(Collectors.toSet()))
				.collect(Collectors.toList());
	}

}
