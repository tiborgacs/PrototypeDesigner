package prototypedesigner.PrototypeDesigner.controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.CacheHint;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import prototypedesigner.PrototypeDesigner.StripboardLink;
import prototypedesigner.PrototypeDesigner.StripboardTrace;
import prototypedesigner.PrototypeDesigner.components.*;

import static prototypedesigner.PrototypeDesigner.Utility.tail;

public class StripboardController {

	@FXML
	private ScrollPane scrollPane;

	@FXML private TextField rowCountField;
	private int boardHeight;

	@FXML private TextField colCountField;
	private int boardWidth;
	
	@FXML
	private Canvas stripboardCanvas;
	
	@FXML
	private ToggleGroup buttonMode;
	
	@FXML
	private ToggleButton cutToggle;
	
	@FXML
	private ToggleButton chipToggle;

	@FXML
	private ToggleButton linkToggle;

	@FXML
	private Slider layerSlider;

	private Coordinate linkStarted = null;
	
	List<StripboardTrace> stripes = new ArrayList<>();
	List<DrawableOnStripboard> drawQueue = new ArrayList<>();
	List<StripboardLink> links = new ArrayList<>();

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
		rowCountField.textProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null && newValue.strip().matches("^\\d+$")) boardHeight = Integer.parseInt(newValue.strip());
		});
		colCountField.setText(boardWidth + "");
		colCountField.textProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null && newValue.strip().matches("^\\d+$")) boardWidth = Integer.parseInt(newValue.strip());
		});
		draw();
	}

	@FXML
	private void resizeBoard() {
		recalculateStrips(); // FIXME rename instead of calling
	}

	private void recalculateStrips() {
		// FIXME limit by outmost component or link if shrinking
		if (stripes.isEmpty()) {
			for (int y = 0; y < boardHeight; y++) {
				stripes.add(new StripboardTrace(0, y, boardWidth));
			}
		} else {
			int lastRow = stripes.stream().max(Comparator.comparingInt(StripboardTrace::getY))
					.map(StripboardTrace::getY).orElse(0);
			for (int i = (lastRow + 1); i < boardHeight; i++) {
				stripes.add(new StripboardTrace(0, i, boardWidth));
			}
			for (int i = 0; i < stripes.size() - 1; i++) {
				StripboardTrace strip = stripes.get(i);
				StripboardTrace next = stripes.get(i+1);
				if (strip.getY() < next.getY()) {
					if (strip.getX() + strip.getW() != boardWidth) {
						int increaseWith = boardWidth - strip.getX() - strip.getW();
						strip.setW(strip.getW() + increaseWith);
					}
				}
			}
			StripboardTrace last = tail(stripes);
			int increaseWith = boardWidth - last.getX() - last.getW();
			last.setW(last.getW() + increaseWith);
			stripes.removeIf(s -> s.getW() == 0);
		}
		draw();
	}

	private void draw() {
		int h = (int) (stripboardCanvas.getHeight() / 24);
		int w = (int) (stripboardCanvas.getWidth() / 24);
		GraphicsContext context = stripboardCanvas.getGraphicsContext2D();
		context.setGlobalAlpha(1.0);
		context.setFill(Color.WHITE);
		context.fillRect(0, 0, w*24, h*24);
		for (DrawableOnStripboard drawable: stripes) drawable.drawOnStripboard(context);
		context.setGlobalAlpha(layerSlider.getValue() / 100.0);
		for (DrawableOnStripboard drawable: links) drawable.drawOnStripboard(context);
		for (DrawableOnStripboard drawable: drawQueue) drawable.drawOnStripboard(context);
	}
	
	@FXML
	private void onClick(MouseEvent e) {
		double x = e.getX();
		x = x - (x % 24);
		double y = e.getY();
		y = y - (y % 24);
		if (chipToggle.isSelected()) {
			/*
			BipolarJunctionTransistor bjt = new BipolarJunctionTransistor();
			bjt.setStrX((int) x);
			bjt.setStrY((int) y);
			drawQueue.add(bjt);
			*/
			if (spanStarted == null) {
				spanStarted = new Coordinate((int) x, (int) y);
			} else {
				Resistor r = new Resistor();
				r.setSpanning(true);
				r.setStart(spanStarted);
				r.setStrX(spanStarted.getX());
				r.setStrY(spanStarted.getY());
				r.setEnd(new Coordinate((int) x, (int) y));
				spanStarted = null;
				/*
				r.setStrX((int) x);
				r.setStrY((int) y);
				*/
				drawQueue.add(r);
			}
		}
		if (cutToggle.isSelected()) drawStripeCut(x/24, y/24);
		if (linkToggle.isSelected()) {
			if (linkStarted == null) {
				linkStarted = new Coordinate((int) x/24, (int) y/24);
			} else { // TODO limit column x
				int linkY = Math.min((int) y/24, linkStarted.getY());
				int span = Math.abs(linkY - Math.max((int) y/24, linkStarted.getY()));
				StripboardLink link = new StripboardLink(linkStarted.getX(), linkY, span);
				links.add(link);
				linkStarted = null;
			}
		}
		e.consume();
		for (DrawableOnStripboard drawable: drawQueue) {
			if (drawable instanceof Component) {
				Component component = (Component) drawable;
				for (Terminal terminal: component.getTerminals()) {
					for (StripboardTrace trace: stripes) {
						if (terminal.getStrY()/24 == trace.getY() && terminal.getStrX()/24 >= trace.getX() && terminal.getStrX()/24 < (trace.getX() + trace.getW())) {
							// TODO: connect
						}
					}
				}
				System.out.println();
			}
		}
		draw();
	}
	
	private void drawStripeCut(double x, double y) {
		StripboardTrace remainder = null;
		int index = -1;
		for (int i = 0; i < stripes.size(); i++) {
			remainder = stripes.get(i).cutAt((int) x, (int) y);
			if (remainder != null) {
				index = i;
				break;
			}
		}
		if (remainder != null) stripes.add(index, remainder);
		// TODO recheck connections
	}
}
