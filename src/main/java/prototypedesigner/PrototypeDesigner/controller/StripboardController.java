package prototypedesigner.PrototypeDesigner.controller;

import java.util.Comparator;

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
import prototypedesigner.PrototypeDesigner.components.*;

import static prototypedesigner.PrototypeDesigner.Utility.tail;

public class StripboardController {

	@FXML private ScrollPane scrollPane;

	@FXML private TextField rowCountField;
	private int boardHeight;

	@FXML private TextField colCountField;
	private int boardWidth;
	
	@FXML private Canvas stripboardCanvas;
	
	@FXML private ToggleGroup buttonMode;
	
	@FXML private ToggleButton cutToggle;

	@FXML private ToggleButton linkToggle;

	@FXML private Slider layerSlider;

	@FXML TableView<Component> schComponentTable;
	@FXML TableColumn<Component, String> schCompIdColumn;
	@FXML TableColumn<Component, String> schCompTypeColumn;
	@FXML TableColumn<Component, String> schCompValueColumn;
	@FXML TableColumn<Component, Component> schCompAddColumn;
	private ToggleGroup schCompAddToggle = new ToggleGroup();

	@SuppressWarnings("unused") @FXML private ToggleGroup orientationToggle;
	@FXML private ToggleButton upToggle;
	@FXML private ToggleButton downToggle;
	@FXML private ToggleButton leftToggle;
	@FXML private ToggleButton rightToggle;

	@FXML private ComboBox<String> pinoutBox;

	@FXML private TableView<Component> stripComponentTable;
	@FXML private TableColumn<Component, String> stripCompIdColumn;
	@FXML private TableColumn<Component, String> stripCompTypeColumn;
	@FXML private TableColumn<Component, String> stripCompValueColumn;
	@FXML private TableColumn<Component, Component> stripCompRemoveColumn;

	@FXML private TableView<StripboardTrace> stripTable;
	@FXML private TableColumn<StripboardTrace, Integer> stripRowColumn;
	@FXML private TableColumn<StripboardTrace, Integer> stripColColumn;
	@FXML private TableColumn<StripboardTrace, Integer> stripWidthColumn;

	@FXML private TableView<StripboardLink> linksTable;
	@FXML private TableColumn<StripboardLink, Integer> linkRowColumn;
	@FXML private TableColumn<StripboardLink, Integer> linkColColumn;
	@FXML private TableColumn<StripboardLink, Integer> linkSpanColumn;

	private CircuitDesign design;

	private Coordinate linkStarted = null;

	private Coordinate spanStarted = null;
	
	@FXML
	private void initialize() {
		layerSlider.setValue(80.0);
		stripboardCanvas.heightProperty().bind(scrollPane.heightProperty());
		stripboardCanvas.widthProperty().bind(scrollPane.widthProperty());
		layerSlider.valueProperty().addListener((obs, ov, nv) -> draw());
		stripboardCanvas.setCache(true);
		stripboardCanvas.setCacheHint(CacheHint.SPEED);
		rowCountField.setText(boardHeight + "");
		colCountField.setText(boardWidth + "");
		schCompIdColumn.setCellValueFactory(value -> new ReadOnlyStringWrapper(value.getValue().getIdentifier()));
		stripCompIdColumn.setCellValueFactory(value -> new ReadOnlyStringWrapper(value.getValue().getIdentifier()));
		schCompTypeColumn.setCellValueFactory(value -> new ReadOnlyStringWrapper(value.getValue().getType()));
		stripCompTypeColumn.setCellValueFactory(value -> new ReadOnlyStringWrapper(value.getValue().getType()));
		schCompValueColumn.setCellValueFactory(value -> new ReadOnlyStringWrapper(value.getValue().getValue()));
		stripCompValueColumn.setCellValueFactory(value -> new ReadOnlyStringWrapper(value.getValue().getValue()));
		schCompAddColumn.setCellValueFactory(value -> new SimpleObjectProperty<>(value.getValue()));
		stripCompRemoveColumn.setCellValueFactory(value -> new SimpleObjectProperty<>(value.getValue()));
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
		stripCompRemoveColumn.setCellFactory(value -> new TableCell<>() {
			@Override protected void updateItem(Component item, boolean empty) {
				super.updateItem(item, empty);
				if (!empty && item != null) {
					Button button = new Button("-");
					button.setOnAction(e -> {
						stripComponentTable.getItems().remove(item);
						schComponentTable.refresh();
						draw();
					});
					setGraphic(button);
				} else setGraphic(null);
			}
		});
		schComponentTable.setRowFactory(value -> new TableRow<>() {
			@Override protected void updateItem(Component item, boolean empty) {
				super.updateItem(item, empty);
				if (!empty && item != null && stripComponentTable.getItems().contains(item)) setDisable(true);
				else setDisable(false);
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
					pinoutBox.getItems().setAll(TransistorPinouts.getBjtPinouts());
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
		buttonMode.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null && newValue.isSelected() && schCompAddToggle.getSelectedToggle() != null)
				schCompAddToggle.getSelectedToggle().setSelected(false);
		});
		stripRowColumn.setCellValueFactory(value -> new SimpleObjectProperty<>(value.getValue().getY()));
		stripColColumn.setCellValueFactory(value -> new SimpleObjectProperty<>(value.getValue().getX()));
		stripWidthColumn.setCellValueFactory(value -> new SimpleObjectProperty<>(value.getValue().getW()));
		linkRowColumn.setCellValueFactory(value -> new SimpleObjectProperty<>(value.getValue().getY()));
		linkColColumn.setCellValueFactory(value -> new SimpleObjectProperty<>(value.getValue().getX()));
		linkSpanColumn.setCellValueFactory(value -> new SimpleObjectProperty<>(value.getValue().getSpan()));
		draw();
	}

	@FXML
	private void resizeBoard() {
		recalculateStrips(); // FIXME rename instead of calling
	}

	private void recalculateStrips() {
		// FIXME limit by outmost component or link if shrinking
		if (rowCountField.getText().strip().matches("^\\d+$"))
			boardHeight = Integer.parseInt(rowCountField.getText().strip());
		if (colCountField.getText().strip().matches("^\\d+$"))
			boardWidth = Integer.parseInt(colCountField.getText().strip());
		if (stripTable.getItems().isEmpty()) {
			for (int y = 0; y < boardHeight; y++) {
				stripTable.getItems().add(new StripboardTrace(0, y, boardWidth));
			}
		} else {
			int lastRow = stripTable.getItems().stream().max(Comparator.comparingInt(StripboardTrace::getY))
					.map(StripboardTrace::getY).orElse(0);
			for (int i = (lastRow + 1); i < boardHeight; i++) {
				stripTable.getItems().add(new StripboardTrace(0, i, boardWidth));
			}
			for (int i = 0; i < stripTable.getItems().size() - 1; i++) {
				StripboardTrace strip = stripTable.getItems().get(i);
				StripboardTrace next = stripTable.getItems().get(i+1);
				if (strip.getY() < next.getY()) {
					if (strip.getX() + strip.getW() != boardWidth) {
						int increaseWith = boardWidth - strip.getX() - strip.getW();
						strip.setW(strip.getW() + increaseWith);
					}
				}
			}
			StripboardTrace last = tail(stripTable.getItems());
			int increaseWith = boardWidth - last.getX() - last.getW();
			last.setW(last.getW() + increaseWith);
			stripTable.getItems().removeIf(s -> s.getW() == 0);
			stripTable.getItems().removeIf(s -> s.getY() >= boardHeight);
		}
		draw();
	}

	public void setDesign(CircuitDesign design) {
		this.design = design;
		// TODO: fill lists and tables
		schComponentTable.getItems().setAll(design.getSchematicsComponents());
		draw();
	}

	private void draw() {
		int h = (int) (stripboardCanvas.getHeight() / 24);
		int w = (int) (stripboardCanvas.getWidth() / 24);
		GraphicsContext context = stripboardCanvas.getGraphicsContext2D();
		context.setGlobalAlpha(1.0);
		context.setFill(Color.WHITE);
		context.fillRect(0, 0, w*24, h*24);
		for (DrawableOnStripboard drawable: stripTable.getItems()) drawable.drawOnStripboard(context);
		context.setGlobalAlpha(layerSlider.getValue() / 100.0);
		for (DrawableOnStripboard drawable: linksTable.getItems()) drawable.drawOnStripboard(context);
		for (Component drawable: stripComponentTable.getItems()) {
			if (drawable instanceof DrawableOnStripboard)
				((DrawableOnStripboard) drawable).drawOnStripboard(context);
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
		if (cutToggle.isSelected()) drawStripeCut(intX, intY);
		if (linkToggle.isSelected()) {
			if (linkStarted == null) {
				linkStarted = new Coordinate(intX, intY);
			} else {
				int linkY = Math.min(intY, linkStarted.getY());
				int span = Math.abs(linkY - Math.max(intY, linkStarted.getY()));
				StripboardLink link = new StripboardLink(linkStarted.getX(), linkY, span);
				linksTable.getItems().add(link);
				linkStarted = null;
			}
		}
		if (schCompAddToggle.getSelectedToggle() != null) {
			Component c = (Component) schCompAddToggle.getSelectedToggle().getUserData();
			c.setStripboardOrientation(getOrientation());
			if (c instanceof Spanning) {
				if (spanStarted == null) spanStarted = new Coordinate(intX, intY);
				else {
					Spanning sc = (Spanning) c;
					sc.setSpanningOnStripboard(true);
					if (intX > spanStarted.getX() || intY > spanStarted.getY()) {
						sc.setStartOnStripboard(spanStarted);
						sc.setEndOnStripboard(new Coordinate(intX, intY));
					} else {
						sc.setStartOnStripboard(new Coordinate(intX, intY));
						sc.setEndOnStripboard(spanStarted);
					}
					if (!stripComponentTable.getItems().contains(c))
						stripComponentTable.getItems().add(c);
					schComponentTable.refresh();
					spanStarted = null;
				}
			} else {
				c.setStrX((int) x / 24);
				c.setStrY((int) y / 24);
				if (!stripComponentTable.getItems().contains(c))
					stripComponentTable.getItems().add(c);
				schComponentTable.refresh();
			}
		}
		e.consume();
//		for (DrawableOnStripboard drawable: drawQueue) {
//			if (drawable instanceof Component) {
//				Component component = (Component) drawable;
//				for (Terminal terminal: component.getTerminals()) {
//					for (StripboardTrace trace: stripes) {
//						if (terminal.getStrY()/24 == trace.getY() && terminal.getStrX()/24 >= trace.getX() && terminal.getStrX()/24 < (trace.getX() + trace.getW())) {
//							// TODO: connect
//						}
//					}
//				}
//				System.out.println();
//			}
//		}
		draw();
	}

	private ComponentOrientation getOrientation() {
		if (upToggle.isSelected()) return ComponentOrientation.UP;
		if (downToggle.isSelected()) return ComponentOrientation.DOWN;
		if (rightToggle.isSelected()) return ComponentOrientation.RIGHT;
		if (leftToggle.isSelected()) return ComponentOrientation.LEFT;
		return null;
	}

	private void drawStripeCut(double x, double y) {
		StripboardTrace remainder = null;
		int index = -1;
		for (int i = 0; i < stripTable.getItems().size(); i++) {
			remainder = stripTable.getItems().get(i).cutAt((int) x, (int) y);
			if (remainder != null) {
				index = i;
				break;
			}
		}
		if (remainder != null) stripTable.getItems().add(index, remainder);
		// TODO recheck connections
	}
}
