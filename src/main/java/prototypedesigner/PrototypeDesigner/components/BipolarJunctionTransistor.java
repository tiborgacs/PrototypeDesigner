package prototypedesigner.PrototypeDesigner.components;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;

public class BipolarJunctionTransistor extends Transistor {

	private Terminal emitterLeg;
	private Terminal collectorLeg;
	private Terminal baseLeg;

	{
		type = "BipolarTransistor";
		emitterLeg = new Terminal(this);
		emitterLeg.setIdentifier(identifier + "_E");
		collectorLeg = new Terminal(this);
		collectorLeg.setIdentifier(identifier + "_C");
		baseLeg = new Terminal(this);
		baseLeg.setIdentifier(identifier + "_B");
	}

	@Override
	public void setSchX(int x) {
		super.setSchX(x);
		emitterLeg.setSchX(x+36);
		baseLeg.setSchX(x);
		collectorLeg.setSchX(x+36);
	}
	
	@Override
	public void setSchY(int y) {
		super.setSchY(y);
		emitterLeg.setSchY(y+48);
		baseLeg.setSchY(y+24);
		collectorLeg.setSchY(y);
	}

	@Override
	public void setStrX(int x) {
		super.setStrX(x);
		// TODO: orientation, pinout
		emitterLeg.setStrX(x);
		baseLeg.setStrX(x+24);
		collectorLeg.setStrX(x+48);
	}

	@Override
	public void setStrY(int y) {
		super.setStrY(y);
		emitterLeg.setStrY(y);
		baseLeg.setStrY(y);
		collectorLeg.setStrY(y);
	}

	@Override
	public void drawOnSchematics(GraphicsContext context) {
		int x = schX;
		int y = schY;
		context.setStroke(highlighted ? Color.PURPLE : Color.DARKBLUE);
		context.setGlobalAlpha(1.0);
		context.setLineWidth(1.0);
		context.strokeArc(x+6, y+6, 36, 36, 0, 360, ArcType.CHORD);
		context.strokeLine(x+18, y+15, x+18, y+33);
		context.strokeLine(x+0, y+24, x+18, y+24); // B
		context.strokeLine(x+18, y+18, x+36, y+9);
		context.strokeLine(x+36, y+9, x+36, y);
		context.setFill(highlighted ? Color.PURPLE : Color.DARKBLUE);
		if (polarity == Polarity.P)
			context.fillPolygon(new double[]{x+18, x+24, x+26}, new double[]{y+18, y+12, y+18}, 3);
		context.strokeLine(x+18, y+30, x+36, y+39);
		context.strokeLine(x+36, y+39, x+36, y+48);
		if (polarity == Polarity.N)
			context.fillPolygon(new double[]{x+36, x+29, x+27}, new double[]{y+39, y+32, y+38}, 3);
	}

}
