package prototypedesigner.PrototypeDesigner.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.fxml.FXML;
import javafx.scene.CacheHint;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import prototypedesigner.PrototypeDesigner.MicroChip;
import prototypedesigner.PrototypeDesigner.ProtoboardDot;
import prototypedesigner.PrototypeDesigner.ProtoboardVia;
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
	
	@FXML
	private ScrollPane scrollPane;

	@FXML
	private Canvas protoboardCanvas;
	
	@FXML
	private ToggleGroup buttonMode;
	
	@FXML
	private ToggleButton linkToggle;
	
	@FXML
	private ToggleButton cutToggle;
	
	@FXML
	private ToggleButton microchipToggle;

	@FXML
	private ToggleButton viaToggle;
	
	@FXML
	private ToggleButton resistorToggle;
	
	@FXML
	private ToggleButton ceramicCapToggle;
	
	@FXML
	private ToggleButton filmCapToggle;
	
	@FXML
	private ToggleButton elcoToggle;
	
	@FXML
	private ToggleButton diodeToggle;
	
	@FXML
	private ToggleButton npnToggle;
	
	@FXML
	private ToggleButton pnpToggle;
	
	@FXML
	private ToggleButton njfetToggle;
	
	@FXML
	private ToggleButton pjfetToggle;
	
	@FXML
	private ToggleButton nmosToggle;
	
	@FXML
	private ToggleButton pmosToggle;
	
	@FXML
	private ToggleButton singleAmpToggle;
	
	@FXML
	private ToggleButton dualAmpToggle;
	
	@FXML
	private ToggleButton quadAmpToggle;
	// TODO: transistors are generic, but add LED?	
	
	@FXML
	private ToggleButton upToggle;
	
	@FXML
	private ToggleButton downToggle;
	
	@FXML
	private ToggleButton leftToggle;
	
	@FXML
	private ToggleButton rightToggle;

	@FXML
	private ToggleGroup orientationGroup;
	
	@FXML
	private ToggleButton genericIcToggle;
	
	@FXML
	private ChoiceBox<Integer> genericIcPinsBox;
	
	@FXML
	private Slider layerSlider;
	
	private List<ProtoboardDot> dots = new ArrayList<>();
	private ProtoboardDot lastLinked = null;
	private List<DrawableOnProtoboard> drawQueue = new ArrayList<>();
	private List<ProtoboardVia> links = new ArrayList<>();
	private ProtoboardDot lastLinked2 = null;
	
	@FXML
	private void initialize() {
		layerSlider.setValue(100.0);
		protoboardCanvas.heightProperty().bind(scrollPane.heightProperty());
		protoboardCanvas.widthProperty().bind(scrollPane.widthProperty());
		protoboardCanvas.heightProperty().addListener((obs, ov, nv) -> draw());
		protoboardCanvas.widthProperty().addListener((obs, ov, nv) -> draw());
		layerSlider.valueProperty().addListener((obs, ov, nv) -> draw());
		genericIcPinsBox.disableProperty().bind(genericIcToggle.selectedProperty().not());
		genericIcPinsBox.getItems().setAll(4, 6, 8, 10, 12, 14, 16, 18, 20);
		protoboardCanvas.setCache(true);
		protoboardCanvas.setCacheHint(CacheHint.SPEED);
	}
	
	private void draw() {
		int h = (int) (protoboardCanvas.getHeight() / 24);
		int w = (int) (protoboardCanvas.getWidth() / 24);
		GraphicsContext context = protoboardCanvas.getGraphicsContext2D();
		context.setGlobalAlpha(1.0);
		context.setFill(Color.rgb( 	204, 153, 51));
		context.fillRect(0, 0, w*24, h*24);
		if (dots.isEmpty()) {
			for (int y = 0; y < h; y++) {
				for (int x = 0; x < w; x++) {
					dots.add(new ProtoboardDot(x, y));
				}
			}
		}
		for(ProtoboardDot dot: dots) dot.drawOnProtoboard(context);
		for(ProtoboardVia link: links) link.drawOnProtoboard(context);
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
		if (linkToggle.isSelected() || cutToggle.isSelected()) {
			final int intX = (int) x / 24;
			final int intY = (int) y / 24;
			Optional<ProtoboardDot> linkable = dots.stream().filter(dot -> dot.getX() == intX && dot.getY() == intY).findFirst();
			if (linkable.isPresent()) {
				if (lastLinked == null) lastLinked = linkable.get();
				else {
					if (linkToggle.isSelected())
						lastLinked = linkable.get().link(lastLinked) ? linkable.get() : null;
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
					links.add(link);
					lastLinked2 = null;
				}
			}
		} else lastLinked2 = null;
		draw();
	}
	
}
