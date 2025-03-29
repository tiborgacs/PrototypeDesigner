package prototypedesigner.PrototypeDesigner.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.CacheHint;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import prototypedesigner.PrototypeDesigner.*;
import prototypedesigner.PrototypeDesigner.components.BipolarJunctionTransistor;
import prototypedesigner.PrototypeDesigner.components.CeramicCapacitor;
import prototypedesigner.PrototypeDesigner.components.ComponentOrientation;
import prototypedesigner.PrototypeDesigner.components.Diode;
import prototypedesigner.PrototypeDesigner.components.DrawableOnProtoboard;
import prototypedesigner.PrototypeDesigner.components.DualOpAmp;
import prototypedesigner.PrototypeDesigner.components.FilmCapacitor;
import prototypedesigner.PrototypeDesigner.components.JunctionFieldEffectTransistor;
import prototypedesigner.PrototypeDesigner.components.MetalOxideSemiconductorFET;
import prototypedesigner.PrototypeDesigner.components.PolarizedCapacitor;
import prototypedesigner.PrototypeDesigner.components.QuadOpAmp;
import prototypedesigner.PrototypeDesigner.components.Resistor;
import prototypedesigner.PrototypeDesigner.components.SingleOpAmp;
import prototypedesigner.PrototypeDesigner.components.Transistor;

public class ProtoboardController {
	
	@FXML private ScrollPane scrollPane;

	@FXML private Canvas protoboardCanvas;
	
	@FXML private ToggleGroup buttonMode;
	
	@FXML private ToggleButton linkToggle;
	
	@FXML private ToggleButton cutToggle;

	@FXML private ToggleButton viaToggle;
	/*
	@FXML private ToggleButton resistorToggle;
	@FXML private ToggleButton ceramicCapToggle;
	@FXML private ToggleButton filmCapToggle;
	@FXML private ToggleButton elcoToggle;
	@FXML private ToggleButton diodeToggle;
	@FXML private ToggleButton npnToggle;
	@FXML private ToggleButton pnpToggle;
	@FXML private ToggleButton njfetToggle;
	@FXML private ToggleButton pjfetToggle;
	@FXML private ToggleButton nmosToggle;
	@FXML private ToggleButton pmosToggle;
	@FXML private ToggleButton singleAmpToggle;
	@FXML private ToggleButton dualAmpToggle;
	@FXML private ToggleButton quadAmpToggle;
	// TODO: transistors are generic, but add LED?
	*/
	@FXML private ToggleButton upToggle;
	@FXML private ToggleButton downToggle;
	@FXML private ToggleButton leftToggle;
	@FXML private ToggleButton rightToggle;
	@FXML private ToggleGroup orientationGroup;
	//@FXML private ToggleButton genericIcToggle;
	//@FXML private ChoiceBox<Integer> genericIcPinsBox;

	@FXML private TextField rowCountField;
	private int boardHeight;

	@FXML private TextField colCountField;
	private int boardWidth;

	@FXML private TreeTableView<ProtoLinkingItem> traceDotTable;
	@FXML private TreeTableColumn<ProtoLinkingItem, String> traceTypeColumn;
	@FXML private TreeTableColumn<ProtoLinkingItem, Integer> traceXColumn;
	@FXML private TreeTableColumn<ProtoLinkingItem, Integer> traceYColumn;

	@FXML private TableView<ProtoboardVia> linkTable;
	@FXML private TableColumn<ProtoboardVia, Integer> viaFromXColumn;
	@FXML private TableColumn<ProtoboardVia, Integer> viaFromYColumn;
	@FXML private TableColumn<ProtoboardVia, Integer> viaToXColumn;
	@FXML private TableColumn<ProtoboardVia, Integer> viaToYColumn;
	// TODO: columns from X, from Y, to X, to Y
	
	@FXML private Slider layerSlider;
	
	private List<ProtoboardDot> dots = new ArrayList<>();
	private ProtoboardDot lastLinked = null;
	private List<DrawableOnProtoboard> drawQueue = new ArrayList<>();
	private ProtoboardDot lastLinked2 = null;

	private ProtoboardTrace currentTrace;
	private TraceBuilder traceBuilder;
	
	@FXML
	private void initialize() {
		layerSlider.setValue(100.0);
		protoboardCanvas.heightProperty().bind(scrollPane.heightProperty());
		protoboardCanvas.widthProperty().bind(scrollPane.widthProperty());
		protoboardCanvas.heightProperty().addListener((obs, ov, nv) -> draw());
		protoboardCanvas.widthProperty().addListener((obs, ov, nv) -> draw());
		layerSlider.valueProperty().addListener((obs, ov, nv) -> draw());
//		genericIcPinsBox.disableProperty().bind(genericIcToggle.selectedProperty().not());
//		genericIcPinsBox.getItems().setAll(4, 6, 8, 10, 12, 14, 16, 18, 20);
		protoboardCanvas.setCache(true);
		protoboardCanvas.setCacheHint(CacheHint.SPEED);
		rowCountField.setText(boardHeight + "");
		colCountField.setText(boardWidth + "");
		traceDotTable.setRoot(new TreeItem<>(new ProtoLinkingItem()));
		traceTypeColumn.setCellValueFactory(value -> new ReadOnlyStringWrapper(value.getValue().getValue().getDot() != null ? "Dot" : "Trace"));
		traceXColumn.setCellValueFactory(value -> new SimpleObjectProperty<>(value.getValue().getValue().getDot() != null ? value.getValue().getValue().getDot().getX() : null));
		traceYColumn.setCellValueFactory(value -> new SimpleObjectProperty<>(value.getValue().getValue().getDot() != null ? value.getValue().getValue().getDot().getY() : null));
		viaFromXColumn.setCellValueFactory(value -> new SimpleObjectProperty<>(value.getValue().getStart().getX()));
		viaFromYColumn.setCellValueFactory(value -> new SimpleObjectProperty<>(value.getValue().getStart().getY()));
		viaToXColumn.setCellValueFactory(value -> new SimpleObjectProperty<>(value.getValue().getEnd().getX()));
		viaToYColumn.setCellValueFactory(value -> new SimpleObjectProperty<>(value.getValue().getEnd().getY()));
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
		draw();
	}
	
	private void draw() {
		GraphicsContext context = protoboardCanvas.getGraphicsContext2D();
		context.clearRect(0, 0, protoboardCanvas.getWidth(), protoboardCanvas.getHeight());
		context.setGlobalAlpha(1.0);
		context.setFill(Color.rgb( 	204, 153, 51));
		context.fillRect(0, 0, boardWidth*24, boardHeight*24);
		for(ProtoboardDot dot: dots) dot.drawOnProtoboard(context);
		if (traceBuilder != null) traceBuilder.getTrace().drawOnProtoboard(context);
		for(ProtoboardVia link: linkTable.getItems()) link.drawOnProtoboard(context);
		context.setGlobalAlpha(layerSlider.getValue() / 100);
		for(DrawableOnProtoboard drawable: drawQueue) {
			drawable.drawOnProtoboard(context);
		}
	}
	
	@FXML
	private void onClick(MouseEvent e) {
		double x = e.getX();
		x = x - (x % 24);
		double y = e.getY();
		y = y - (y % 24);
		ComponentOrientation orientation = null;
		if (leftToggle.isSelected()) orientation = ComponentOrientation.LEFT;
		if (rightToggle.isSelected()) orientation = ComponentOrientation.RIGHT;
		if (upToggle.isSelected()) orientation = ComponentOrientation.UP;
		if (downToggle.isSelected()) orientation = ComponentOrientation.DOWN;
		/*
		if (genericIcToggle.isSelected()) {
			MicroChip mc = new MicroChip(genericIcPinsBox.getValue());
			mc.setX((int) x);
			mc.setY((int) y);
			drawQueue.add(mc);
			lastLinked = null;
		}
		if (resistorToggle.isSelected()) {
			Resistor c = new Resistor();
			c.setProX((int) x);
			c.setProY((int) y);
			c.setProtoboardOrientation(orientation);
			drawQueue.add(c);
		}
		if (ceramicCapToggle.isSelected()) {
			CeramicCapacitor c = new CeramicCapacitor();
			c.setProX((int) x);
			c.setProY((int) y);
			c.setProtoboardOrientation(orientation);
			drawQueue.add(c);
		}
		if (filmCapToggle.isSelected()) {
			FilmCapacitor c = new FilmCapacitor();
			c.setProX((int) x);
			c.setProY((int) y);
			c.setProtoboardOrientation(orientation);
			drawQueue.add(c);
		}
		if (elcoToggle.isSelected()) {
			PolarizedCapacitor c = new PolarizedCapacitor();
			c.setProX((int) x);
			c.setProY((int) y);
			c.setProtoboardOrientation(orientation);
			drawQueue.add(c);
		}
		if (diodeToggle.isSelected()) {
			Diode c = new Diode();
			c.setProX((int) x);
			c.setProY((int) y);
			c.setProtoboardOrientation(orientation);
			drawQueue.add(c);
		}
		if (npnToggle.isSelected()) {
			Transistor c = new BipolarJunctionTransistor();
			c.setProX((int) x);
			c.setProY((int) y);
			c.setProtoboardOrientation(orientation);
			drawQueue.add(c);
		}
		if (pnpToggle.isSelected()) {
			Transistor c = new BipolarJunctionTransistor();
			c.setProX((int) x);
			c.setProY((int) y);
			c.setProtoboardOrientation(orientation);
			drawQueue.add(c);
		}
		if (njfetToggle.isSelected()) {
			Transistor c = new JunctionFieldEffectTransistor();
			c.setProX((int) x);
			c.setProY((int) y);
			c.setProtoboardOrientation(orientation);
			drawQueue.add(c);
		}
		if (pjfetToggle.isSelected()) {
			Transistor c = new JunctionFieldEffectTransistor();
			c.setProX((int) x);
			c.setProY((int) y);
			c.setProtoboardOrientation(orientation);
			drawQueue.add(c);
		}
		if (nmosToggle.isSelected()) {
			Transistor c = new MetalOxideSemiconductorFET();
			c.setProX((int) x);
			c.setProY((int) y);
			c.setProtoboardOrientation(orientation);
			drawQueue.add(c);
		}
		if (pmosToggle.isSelected()) {
			Transistor c = new MetalOxideSemiconductorFET();
			c.setProX((int) x);
			c.setProY((int) y);
			c.setProtoboardOrientation(orientation);
			drawQueue.add(c);
		}
		if (singleAmpToggle.isSelected()) {
			SingleOpAmp c = new SingleOpAmp();
			c.setProX((int) x);
			c.setProY((int) y);
			c.setProtoboardOrientation(orientation);
			drawQueue.add(c);
		}
		if (dualAmpToggle.isSelected()) {
			DualOpAmp c = new DualOpAmp();
			c.setProX((int) x);
			c.setProY((int) y);
			c.setProtoboardOrientation(orientation);
			drawQueue.add(c);
		}
		if (quadAmpToggle.isSelected()) {
			QuadOpAmp c = new QuadOpAmp();
			c.setProX((int) x);
			c.setProY((int) y);
			c.setProtoboardOrientation(orientation);
			drawQueue.add(c);
		}
		*/
		if (linkToggle.isSelected() || cutToggle.isSelected()) {
			final int intX = (int) x / 24;
			final int intY = (int) y / 24;
			Optional<ProtoboardDot> linkable = dots.stream().filter(dot -> dot.getX() == intX && dot.getY() == intY).findFirst();
			if (linkable.isPresent()) {
				/*
				if (linkToggle.isSelected()) {
					if (currentTrace == null) currentTrace = new ProtoboardTrace(linkable.get());
					else {
						Optional<ProtoboardDot> newLink = currentTrace.getDots().stream().filter(dot -> dot.link(linkable.get())).findFirst();
						if (newLink.isPresent()) {
							currentTrace.getDots().add(linkable.get());
							Optional<TreeItem<ProtoLinkingItem>> optTrace = traceDotTable.getRoot().getChildren().stream()
									.filter(item -> item.getValue().getTrace() != null)
									.filter(item -> item.getValue().getTrace().getDots().contains(linkable.get()))
									.findFirst();
							if (!optTrace.isPresent()) {
								TreeItem<ProtoLinkingItem> newTraceItem = new TreeItem<>(new ProtoLinkingItem(currentTrace));
								traceDotTable.getRoot().getChildren().add(newTraceItem);
								for (ProtoboardDot dot: currentTrace.getDots()) {
									if (newTraceItem.getChildren().stream().noneMatch(item -> item.getValue().getDot() == dot)) {
										TreeItem<ProtoLinkingItem> newDotItem = new TreeItem<>(new ProtoLinkingItem(dot));
										newTraceItem.getChildren().add(newDotItem);
									}
								}
							} else {
								if (optTrace.get().getChildren().stream().noneMatch(item -> item.getValue().getDot() == linkable.get()))
									optTrace.get().getChildren().add(new TreeItem<>(new ProtoLinkingItem(linkable.get())));
							}
						}
					}
				}
				*/
				if (traceBuilder == null) traceBuilder = new TraceBuilder(linkable.get());
				else traceBuilder.addDot(linkable.get(), dots);
				if (e.getClickCount() == 2) {
					ProtoboardTrace trace = traceBuilder.getTrace();
					TreeItem<ProtoLinkingItem> traceItem = new TreeItem<>(new ProtoLinkingItem(trace));
					traceDotTable.getRoot().getChildren().add(traceItem);
					trace.getDots().forEach(dot -> traceItem.getChildren().add(new TreeItem<>(new ProtoLinkingItem(dot))));

				}
				if (lastLinked == null) lastLinked = linkable.get();
				else {
					if (linkToggle.isSelected()) {
						lastLinked = linkable.get().link(lastLinked) ? linkable.get() : null;
						/*if (lastLinked != null) {
							Optional<TreeItem<ProtoLinkingItem>> optTrace = traceDotTable.getRoot().getChildren().stream()
									.filter(item -> item.getValue().getTrace() != null)
									.filter(item -> item.getValue().getTrace().getDots().contains(lastLinked))
									.findFirst();
							TreeItem<ProtoLinkingItem> trace = optTrace.orElse(new TreeItem<>(new ProtoLinkingItem(new ProtoboardTrace(lastLinked))));
							trace.getValue().getTrace().getDots().add(linkable.get());
							if (!optTrace.isPresent()) {
								traceDotTable.getRoot().getChildren().add(trace);
								trace.getChildren().add(new TreeItem<>(new ProtoLinkingItem(lastLinked)));
							}
							trace.getChildren().add(new TreeItem<>(new ProtoLinkingItem(linkable.get())));
						}*/
					}
					if (cutToggle.isSelected())
						lastLinked = linkable.get().unlink(lastLinked) ? linkable.get() : null;
				}
			}
		} else lastLinked = null;
		if (viaToggle.isSelected()) {
			final int intX = (int) x / 24;
			final int intY = (int) y / 24;
			Optional<ProtoboardDot> linkable = dots.stream().filter(dot -> dot.getX() == intX && dot.getY() == intY).findFirst();
			if (linkable.isPresent()) {
				if (lastLinked2 == null) lastLinked2 = linkable.get();
				else {
					ProtoboardVia link = new ProtoboardVia(lastLinked2, linkable.get());
					linkTable.getItems().add(link);
					lastLinked2 = null;
				}
			}
		} else lastLinked2 = null;
		draw();
	}
	
}
