package prototypedesigner.PrototypeDesigner.controller;

import java.util.*;
import java.util.stream.Collectors;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.CacheHint;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.util.Pair;
import lombok.Getter;
import prototypedesigner.PrototypeDesigner.*;
import prototypedesigner.PrototypeDesigner.components.*;

public class ProtoboardController {
	
	@FXML private ScrollPane scrollPane;

	@Getter @FXML private Canvas protoboardCanvas;
	
	@FXML private ToggleGroup buttonMode;
	
	@FXML private ToggleButton linkToggle;
	
	@FXML private ToggleButton cutToggle;

	@FXML private ToggleButton viaToggle;
	@FXML private ToggleButton upToggle;
	@FXML private ToggleButton downToggle;
	@FXML private ToggleButton leftToggle;
	@FXML private ToggleButton rightToggle;
	@SuppressWarnings("unused") @FXML private ToggleGroup orientationGroup;

	@FXML TableView<Component> schComponentTable;
	@FXML TableColumn<Component, String> schCompIdColumn;
	@FXML TableColumn<Component, String> schCompTypeColumn;
	@FXML TableColumn<Component, String> schCompValueColumn;
	@FXML TableColumn<Component, Component> schCompAddColumn;
	private ToggleGroup schCompAddToggle = new ToggleGroup();

	@FXML private ComboBox<String> pinoutBox;

	@FXML private TableView<Component> protoComponentTable;
	@FXML private TableColumn<Component, String> protoCompIdColumn;
	@FXML private TableColumn<Component, String> protoCompTypeColumn;
	@FXML private TableColumn<Component, String> protoCompValueColumn;
	@FXML private TableColumn<Component, Component> protoCompRemoveColumn;

	@FXML private TextField rowCountField;
	@Getter private int boardHeight;

	@FXML private TextField colCountField;
	@Getter private int boardWidth;

	@FXML private TreeTableView<ProtoLinkingItem> traceDotTable;
	@FXML private TreeTableColumn<ProtoLinkingItem, String> traceTypeColumn;
	@FXML private TreeTableColumn<ProtoLinkingItem, Integer> traceXColumn;
	@FXML private TreeTableColumn<ProtoLinkingItem, Integer> traceYColumn;
	@FXML private  TreeTableColumn<ProtoLinkingItem, ProtoboardTrace> traceDeleteColumn;

	@FXML private TableView<ProtoboardVia> linkTable; // TODO: editable coordinates
	@FXML private TableColumn<ProtoboardVia, Integer> viaFromXColumn;
	@FXML private TableColumn<ProtoboardVia, Integer> viaFromYColumn;
	@FXML private TableColumn<ProtoboardVia, Integer> viaToXColumn;
	@FXML private TableColumn<ProtoboardVia, Integer> viaToYColumn;
	@FXML private TableColumn<ProtoboardVia, ProtoboardVia> viaDeleteColumn;
	
	@FXML private Slider layerSlider;
	
	private List<ProtoboardDot> dots = new ArrayList<>();
	private ProtoboardDot lastLinked = null;
	private ProtoboardDot lastViaDot = null;
	private Coordinate spanStarted;

	private TraceBuilder traceBuilder;
	private CircuitDesign design;

	@FXML
	private void initialize() {
		layerSlider.setValue(100.0);
		protoboardCanvas.heightProperty().bind(scrollPane.heightProperty());
		protoboardCanvas.widthProperty().bind(scrollPane.widthProperty());
		protoboardCanvas.heightProperty().addListener((obs, ov, nv) -> draw());
		protoboardCanvas.widthProperty().addListener((obs, ov, nv) -> draw());
		layerSlider.valueProperty().addListener((obs, ov, nv) -> draw());
		protoboardCanvas.setCache(true);
		protoboardCanvas.setCacheHint(CacheHint.SPEED);
		buttonMode.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null && newValue.isSelected() && schCompAddToggle.getSelectedToggle() != null)
				schCompAddToggle.getSelectedToggle().setSelected(false);
		});
		schComponentTable.setRowFactory(value -> new TableRow<>() {
			@Override protected void updateItem(Component item, boolean empty) {
				super.updateItem(item, empty);
				if (!empty && item != null && protoComponentTable.getItems().contains(item)) setDisable(true);
				else setDisable(false);
			}
		});
		schCompIdColumn.setCellValueFactory(value -> new ReadOnlyStringWrapper(value.getValue().getIdentifier()));
		protoCompIdColumn.setCellValueFactory(value -> new ReadOnlyStringWrapper(value.getValue().getIdentifier()));
		schCompTypeColumn.setCellValueFactory(value -> new ReadOnlyStringWrapper(value.getValue().getType()));
		protoCompTypeColumn.setCellValueFactory(value -> new ReadOnlyStringWrapper(value.getValue().getType()));
		schCompValueColumn.setCellValueFactory(value -> new ReadOnlyStringWrapper(value.getValue().getValue()));
		protoCompValueColumn.setCellValueFactory(value -> new ReadOnlyStringWrapper(value.getValue().getValue()));
		schCompAddColumn.setCellValueFactory(value -> new SimpleObjectProperty<>(value.getValue()));
		protoCompRemoveColumn.setCellValueFactory(value -> new SimpleObjectProperty<>(value.getValue()));
		schCompAddColumn.setCellFactory(value -> new TableCell<>() {
			@Override protected void updateItem(Component item, boolean empty) {
				super.updateItem(item, empty);
				if (!empty && item != null) {
					ToggleButton componentAddToggle = new ToggleButton("+");
					componentAddToggle.setToggleGroup(schCompAddToggle);
					componentAddToggle.setUserData(item);
					setGraphic(componentAddToggle);
				} else setGraphic(null);
			}
		});
		schCompAddToggle.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null && newValue.isSelected()) {
				if (buttonMode.getSelectedToggle() != null)
					buttonMode.getSelectedToggle().setSelected(false);
				if (newValue.getUserData() instanceof BipolarJunctionTransistor
						|| newValue.getUserData() instanceof JunctionFieldEffectTransistor
						|| newValue.getUserData() instanceof MetalOxideSemiconductorFET) {
					pinoutBox.setDisable(false);
					pinoutBox.getItems().setAll(newValue.getUserData() instanceof BipolarJunctionTransistor ?
							TransistorPinouts.getBjtPinouts() : TransistorPinouts.getFetPinouts());
					Component transistor = (Component) newValue.getUserData();
					if (transistor.getPinout() != null && !transistor.getPinout().isEmpty()) {
						if (!pinoutBox.getItems().contains(transistor.getPinout()))
							pinoutBox.getItems().add(transistor.getPinout());
						pinoutBox.getSelectionModel().select(transistor.getPinout());
					}
					pinoutBox.valueProperty().addListener((_observable, _oldValue, _newValue) -> {
						if (_newValue != null && !_newValue.isEmpty()) transistor.setPinout(_newValue); // TODO: remove listener?
					});
				} else {
					pinoutBox.setDisable(true);
					pinoutBox.getSelectionModel().clearSelection();
					pinoutBox.getItems().clear();
				}
			}
		});
		protoCompRemoveColumn.setCellFactory(value -> new TableCell<>() {
			@Override protected void updateItem(Component item, boolean empty) {
				super.updateItem(item, empty);
				if (!empty && item != null) {
					Button button = new Button("-");
					button.setOnAction(e -> {
						protoComponentTable.getItems().remove(item);
						schComponentTable.refresh();
						draw();
					});
					setGraphic(button);
				} else setGraphic(null);
			}
		});
		rowCountField.setText(boardHeight + "");
		colCountField.setText(boardWidth + "");
		traceDotTable.setRoot(new TreeItem<>(new ProtoLinkingItem()));
		traceTypeColumn.setCellValueFactory(value -> new ReadOnlyStringWrapper(value.getValue().getValue().getDot() != null ? "Dot" : "Trace"));
		traceXColumn.setCellValueFactory(value -> new SimpleObjectProperty<>(value.getValue().getValue().getDot() != null ? value.getValue().getValue().getDot().getX() : null));
		traceYColumn.setCellValueFactory(value -> new SimpleObjectProperty<>(value.getValue().getValue().getDot() != null ? value.getValue().getValue().getDot().getY() : null));
		traceDeleteColumn.setCellValueFactory(value -> new SimpleObjectProperty<>(value.getValue().getValue().getTrace()));
		traceDeleteColumn.setCellFactory(value -> new TreeTableCell<>() {
			@Override protected void updateItem(ProtoboardTrace item, boolean empty) {
				super.updateItem(item, empty);
				if (item == null || empty) setGraphic(null);
				else {
					Button deleteButton = new Button("\uD83D\uDDD1");
					deleteButton.setOnAction(event -> {
						traceDotTable.getRoot().getChildren()
								.removeIf(treeItem -> treeItem.getValue().getTrace() == item);
						draw();
					});
					setGraphic(deleteButton);
				}
			}
		});
		viaFromXColumn.setCellValueFactory(value -> new SimpleObjectProperty<>(value.getValue().getStart().getX()));
		viaFromYColumn.setCellValueFactory(value -> new SimpleObjectProperty<>(value.getValue().getStart().getY()));
		viaToXColumn.setCellValueFactory(value -> new SimpleObjectProperty<>(value.getValue().getEnd().getX()));
		viaToYColumn.setCellValueFactory(value -> new SimpleObjectProperty<>(value.getValue().getEnd().getY()));
		viaDeleteColumn.setCellValueFactory(value -> new SimpleObjectProperty<>(value.getValue()));
		viaDeleteColumn.setCellFactory(value -> new TableCell<>() {
			@Override protected void updateItem(ProtoboardVia item, boolean empty) {
				super.updateItem(item, empty);
				if (item == null || empty) setGraphic(null);
				else {
					Button deleteButton = new Button("\uD83D\uDDD1");
					deleteButton.setOnAction(event -> {
						linkTable.getItems().remove(item);
						draw();
					});
					setGraphic(deleteButton);
				}
			}
		});
		protoComponentTable.getItems().addListener((ListChangeListener<? super Component>) lc -> {
			lc.next();
			lc.getAddedSubList().stream().distinct().forEach(component ->
					design.getProtoboardComponents().stream().filter(c -> c.getIdentifier().equals(component.getIdentifier()))
							.findFirst().ifPresentOrElse(
									c -> {
										int idx = design.getProtoboardComponents().indexOf(c);
										design.getProtoboardComponents().set(idx, component);
									}, () -> design.getProtoboardComponents().add(component))
			);
			design.getProtoboardComponents().removeAll(lc.getRemoved());
		});
		traceDotTable.getRoot().getChildren().addListener((ListChangeListener<? super TreeItem<ProtoLinkingItem>>) lc -> {
			lc.next();
			lc.getAddedSubList().stream().distinct().forEach(item ->
					design.getConnectionsOnProtoboard().stream()
							.filter(t -> t.getIdentifier().equals(item.getValue().getTrace().getIdentifier()))
							.findFirst().ifPresentOrElse(
									t -> {
										int idx = design.getConnectionsOnProtoboard().indexOf(t);
										design.getConnectionsOnProtoboard().set(idx, item.getValue().getTrace());
									}, () -> design.getConnectionsOnProtoboard().add(item.getValue().getTrace()))
			);
			design.getConnectionsOnProtoboard().removeAll(
					lc.getRemoved().stream().map(item -> item.getValue().getTrace())
							.collect(Collectors.toList()));
		});
		linkTable.getItems().addListener((ListChangeListener<? super ProtoboardVia>) lc -> {
			lc.next();
			lc.getAddedSubList().stream().distinct().forEach(via ->
					design.getViasOnProtoboard().stream().filter(v -> v.getIdentifier().equals(via.getIdentifier()))
							.findFirst().ifPresentOrElse(
									v -> {
										int idx = design.getViasOnProtoboard().indexOf(v);
										design.getViasOnProtoboard().set(idx, via);
									}, () -> design.getViasOnProtoboard().add(via))
			);
			design.getViasOnProtoboard().removeAll(lc.getRemoved());
		});
		draw();
	}

	public void setDesign(CircuitDesign design) {
		this.design = design;
		schComponentTable.getItems().setAll(
				design.getSchematicsComponents() == null ? Collections.emptyList() : design.getSchematicsComponents());
		protoComponentTable.getItems().setAll(
				design.getProtoboardComponents() == null ? Collections.emptyList() : design.getProtoboardComponents());
		colCountField.setText(design.getProtoboardWidth() + "");
		rowCountField.setText(design.getProtoboardHeight() + "");
		resizeBoard();
		traceDotTable.getRoot().getChildren().clear();
		if (design.getConnectionsOnProtoboard() != null)
			for (ProtoboardTrace trace: design.getConnectionsOnProtoboard()) {
				TreeItem<ProtoLinkingItem> traceItem = new TreeItem<>(new ProtoLinkingItem(trace));
				traceDotTable.getRoot().getChildren().add(traceItem);
				trace.getDots().forEach(dot ->
						traceItem.getChildren().add(new TreeItem<>(new ProtoLinkingItem(dot))));
			}
		linkTable.getItems().setAll(
				design.getViasOnProtoboard() == null ? Collections.emptyList() : design.getViasOnProtoboard());
		draw();
	}

	@FXML
	private void resizeBoard() {
		if (rowCountField.getText().strip().matches("^\\d+$"))
			boardHeight = Integer.parseInt(rowCountField.getText().strip());
		if (colCountField.getText().strip().matches("^\\d+$"))
			boardWidth = Integer.parseInt(colCountField.getText().strip());
		for (int y = 0; y < boardHeight; y++) {
			for (int x = 0; x < boardWidth; x++) {
				int finalX = x;
				int finalY = y;
				if (!dots.stream().anyMatch(d -> d.getX() == finalX && d.getY() == finalY))
					dots.add(new ProtoboardDot(x, y));
			}
		}
		dots.removeIf(d -> d.getX() >= boardWidth || d.getY() >= boardHeight);
		design.setProtoboardHeight(boardHeight);
		design.setProtoboardWidth(boardWidth);
		draw();
	}
	
	private void draw() {
		GraphicsContext context = protoboardCanvas.getGraphicsContext2D();
		context.clearRect(0, 0, protoboardCanvas.getWidth(), protoboardCanvas.getHeight());
		context.setGlobalAlpha(1.0);
		context.setFill(Color.rgb( 	204, 153, 51));
		context.fillRect(0, 0, boardWidth*24, boardHeight*24);
		for(ProtoboardDot dot: dots) dot.drawOnProtoboard(context);
		for (TreeItem<ProtoLinkingItem> traceItem: traceDotTable.getRoot().getChildren())
			traceItem.getValue().getTrace().drawOnProtoboard(context);
		if (traceBuilder != null) traceBuilder.getTrace().drawOnProtoboard(context);
		for(ProtoboardVia link: linkTable.getItems()) link.drawOnProtoboard(context);
		context.setGlobalAlpha(layerSlider.getValue() / 100);
		for(Component drawable: protoComponentTable.getItems()) {
			((DrawableOnProtoboard) drawable).drawOnProtoboard(context);
		}
	}
	
	@FXML
	private void onClick(MouseEvent e) {
		double x = e.getX();
		x = x - (x % 24);
		double y = e.getY();
		y = y - (y % 24);
		final int intX = (int) x / 24;
		final int intY = (int) y / 24;
		ComponentOrientation orientation = null;
		if (leftToggle.isSelected()) orientation = ComponentOrientation.LEFT;
		if (rightToggle.isSelected()) orientation = ComponentOrientation.RIGHT;
		if (upToggle.isSelected()) orientation = ComponentOrientation.UP;
		if (downToggle.isSelected()) orientation = ComponentOrientation.DOWN;
		if (linkToggle.isSelected() || cutToggle.isSelected()) {
			Optional<ProtoboardDot> linkable = dots.stream()
					.filter(dot -> dot.getX() == intX && dot.getY() == intY).findFirst();
			if (linkable.isPresent()) {
				if (lastLinked == null) lastLinked = linkable.get();
				if (linkToggle.isSelected()) {
					if (traceBuilder == null) traceBuilder = new TraceBuilder(linkable.get());
					else traceBuilder.addDot(linkable.get(), dots);
					if (e.getClickCount() == 2) {
						ProtoboardTrace trace = traceBuilder.getTrace();
						TreeItem<ProtoLinkingItem> traceItem = new TreeItem<>(new ProtoLinkingItem(trace));
						traceDotTable.getRoot().getChildren().add(traceItem);
						trace.getDots().forEach(dot ->
								traceItem.getChildren().add(new TreeItem<>(new ProtoLinkingItem(dot))));
						traceBuilder = null;
						lastLinked = null;
					}
				} else if (cutToggle.isSelected() && linkable.get().isNeighbor(lastLinked)) {
					List<ProtoboardTrace> tracesToCut = traceDotTable.getRoot().getChildren().stream()
							.map(TreeItem::getValue).map(ProtoLinkingItem::getTrace)
							.filter(trace -> trace.getDots().contains(linkable.get())
									&& trace.getDots().contains(lastLinked)).collect(Collectors.toList());
					for (ProtoboardTrace trace: tracesToCut) {
						ProtoboardTrace possibleNewTrace = trace.cut(linkable.get(), lastLinked);
						if (possibleNewTrace != null) {
							TreeItem<ProtoLinkingItem> traceItem = new TreeItem<>(new ProtoLinkingItem(possibleNewTrace));
							traceDotTable.getRoot().getChildren().add(traceItem);
							possibleNewTrace.getDots().forEach(dot ->
									traceItem.getChildren().add(new TreeItem<>(new ProtoLinkingItem(dot))));
						}
						if (trace.getDots().size() > 1) {
							traceDotTable.getRoot().getChildren().stream()
									.filter(item -> item.getValue().getTrace() == trace).findFirst()
									.ifPresent(item -> item.getChildren()
											.removeIf(dotItem -> !trace.getDots()
													.contains(dotItem.getValue().getDot())));
						} else {
							traceDotTable.getRoot().getChildren()
									.removeIf(item -> item.getValue().getTrace() == trace);
						}
						lastLinked = null;
					}
				}
			}
		} else lastLinked = null;
		if (viaToggle.isSelected()) {
			Optional<ProtoboardDot> linkable = dots.stream().filter(dot -> dot.getX() == intX && dot.getY() == intY).findFirst();
			if (linkable.isPresent()) {
				if (lastViaDot == null) lastViaDot = linkable.get();
				else {
					ProtoboardVia link;
					if (linkable.get().getX() > lastViaDot.getX() || linkable.get().getY() > lastViaDot.getY())
						link = new ProtoboardVia(lastViaDot, linkable.get());
					else link = new ProtoboardVia(linkable.get(), lastViaDot);
					linkTable.getItems().add(link);
					lastViaDot = null;
				}
			}
		} else lastViaDot = null;
		if (schCompAddToggle.getSelectedToggle() != null) {
			Component c = (Component) schCompAddToggle.getSelectedToggle().getUserData();
			c.setProtoboardOrientation(orientation);
			if (c instanceof Spanning) {
				if (spanStarted == null) spanStarted = new Coordinate(intX, intY);
				else {
					Spanning sc = (Spanning) c;
					sc.setSpanningOnProtoboard(true);
					if (intX > spanStarted.getX() || intY > spanStarted.getY()) {
						sc.setStartOnProtoboard(spanStarted);
						sc.setEndOnProtoboard(new Coordinate(intX, intY));
					} else {
						sc.setStartOnProtoboard(new Coordinate(intX, intY));
						sc.setEndOnProtoboard(spanStarted);
					}
					if (!protoComponentTable.getItems().contains(c))
						protoComponentTable.getItems().add(c);
					schComponentTable.refresh();
					spanStarted = null;
				}
			} else {
				c.setProX(intX);
				c.setProY(intY);
				if (!protoComponentTable.getItems().contains(c))
					protoComponentTable.getItems().add(c);
				schComponentTable.refresh();
			}
		}
		e.consume();
		draw();
	}

	public List<Pair<Set<Terminal>, Set<Terminal>>> checkConnections(List<Set<Terminal>> schNetwork) {
		List<Set<ProtoboardDot>> traversedDots = new ArrayList<>();
		for (ProtoboardTrace trace: design.getConnectionsOnProtoboard()) {
			Optional<Set<ProtoboardDot>> optSet = traversedDots.stream()
					.filter(s -> trace.getDots().stream().anyMatch(s::contains)).findFirst();
			if (optSet.isPresent()) optSet.get().addAll(trace.getDots());
			else traversedDots.add(new HashSet<>(trace.getDots()));
		}
		for (ProtoboardVia via: design.getViasOnProtoboard()) {
			Optional<Set<ProtoboardDot>> start = traversedDots.stream()
					.filter(s -> s.contains(via.getStart())).findFirst();
			Optional<Set<ProtoboardDot>> end = traversedDots.stream()
					.filter(s -> s.contains(via.getEnd())).findFirst();
			if (start.isPresent() && end.isPresent()) {
				start.get().addAll(end.get());
				end.get().clear();
			}
		}
		traversedDots.removeIf(Set::isEmpty);
		List<Set<Terminal>> traversedTerminals = new ArrayList<>();
		for (Set<ProtoboardDot> net: traversedDots) {
			Set<Terminal> terminals = new HashSet<>();
			for (ProtoboardDot dot: net) {
				design.getProtoboardComponents().stream().flatMap(c -> c.getTerminals().stream())
						.filter(t -> t.getProX() == dot.getX() && t.getProY() == dot.getY())
						.findFirst().ifPresent(terminals::add);
			}
			traversedTerminals.add(terminals);
		}
		return DifferenceExtractor.extractDifferences(schNetwork, traversedTerminals);
	}
}
