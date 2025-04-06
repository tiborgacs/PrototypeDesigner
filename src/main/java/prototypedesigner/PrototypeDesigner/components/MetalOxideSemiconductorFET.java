package prototypedesigner.PrototypeDesigner.components;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;

public class MetalOxideSemiconductorFET extends Transistor {

	private Terminal drainLeg;
	private Terminal gateLeg;
	private Terminal sourceLeg;

	{
		drainLeg = new Terminal(this);
		drainLeg.setIdentifier(identifier + "_D");
		gateLeg = new Terminal(this);
		gateLeg.setIdentifier(identifier + "_G");
		sourceLeg = new Terminal(this);
		sourceLeg.setIdentifier(identifier + "_S");
		type = "MOSFET";
	}

	@Override
	public void setSchX(int x) {
		super.setSchX(x);
		drainLeg.setSchX(x+36);
		gateLeg.setSchX(x);
		sourceLeg.setSchX(x+36);
	}
	
	@Override
	public void setSchY(int y) {
		super.setSchY(y);
		drainLeg.setSchY(y);
		gateLeg.setSchY(y+24);
		sourceLeg.setSchY(y+48);
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
		context.strokeLine(x+21, y+15, x+21, y+20);
		context.strokeLine(x+21, y+22, x+21, y+27);
		context.strokeLine(x+21, y+29, x+21, y+33);
		context.strokeLine(x+0, y+24, x+18, y+24); // G
		context.setFill(highlighted ? Color.PURPLE : Color.DARKBLUE);
		context.strokeLine(x+21, y+24, x+36, y+24);
		if (polarity == Polarity.P)
			context.fillPolygon(new double[] {x+24, x+24, x+36}, new double[] {y+27, y+21, y+24}, 3);
		else if (polarity == Polarity.N)
			context.fillPolygon(new double[] {x+21, x+33, x+33}, new double[] {y+24, y+21, y+27}, 3);
		context.strokeLine(x+21, y+18, x+36, y+18); // D
		context.strokeLine(x+36, y+18, x+36, y);
		context.strokeLine(x+21, y+30, x+36, y+30); // S
		context.strokeLine(x+36, y+24, x+36, y+48);
	}

}
