package prototypedesigner.PrototypeDesigner.components;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Capacitor extends Component implements ComponentValue {

	private static int idCounter = 0;

	private Terminal leg1 = new Terminal(this);
	private Terminal leg2 = new Terminal(this);
	// TODO can be flipped
	private String value;
	
	{
		schematicsOrientation = ComponentOrientation.LEFT;
		identifier = "C" + ++idCounter;
		leg1.setIdentifier(identifier + "A");
		leg2.setIdentifier(identifier + "B");
	}

	public void setSchematicsOrientation(ComponentOrientation orientation) {
		schematicsOrientation = orientation;
	}
	
	@Override
	public void setSchX(int x) {
		super.setSchX(x);
		if (schematicsOrientation == ComponentOrientation.UP || schematicsOrientation == ComponentOrientation.DOWN) {
			leg1.setSchX(x+12);
			leg2.setSchX(x+12);
		} else {
			leg1.setSchX(x);
			leg2.setSchY(x+24);
		}
	}
	
	@Override
	public void setSchY(int y) {
		super.setSchY(y);
		if (schematicsOrientation == ComponentOrientation.UP || schematicsOrientation == ComponentOrientation.DOWN) {
			leg1.setSchY(y);
			leg2.setSchY(y+24);
		} else {
			leg1.setSchY(y+12);
			leg2.setSchY(y+12);
		}
	}
	
	@Override
	public String getValue() {
		return value;
	}

	@Override
	public void drawOnSchematics(GraphicsContext context) {
		int x = schX;
		int y = schY;
		context.setStroke(highlighted ? Color.PURPLE : Color.DARKBLUE);
		context.setGlobalAlpha(1.0);
		context.setLineWidth(1.0);
		if (schematicsOrientation == ComponentOrientation.UP || schematicsOrientation == ComponentOrientation.DOWN) {
			context.strokeLine(x+3, y+10, x+21, y+10);
			context.strokeLine(x+3, y+14, x+21, y+14);
			context.strokeLine(x+12, y, x+12, y+10);
			context.strokeLine(x+12, y+14, x+12, y+24);
		} else {
			context.strokeLine(x+10, y+3, x+10, y+21);
			context.strokeLine(x+14, y+3, x+14, y+21);
			context.strokeLine(x, y+12, x+10, y+12);
			context.strokeLine(x+14, y+12, x+24, y+12);
		}
		context.strokeText(identifier, schX, schY);
	}

	
}
