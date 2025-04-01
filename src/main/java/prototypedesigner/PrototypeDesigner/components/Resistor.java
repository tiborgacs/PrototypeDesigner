package prototypedesigner.PrototypeDesigner.components;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class Resistor extends Component implements DrawableOnStripboard, DrawableOnProtoboard, Spanning {

	private static int idCounter = 0;

	private boolean spanningOnStripboard;
	private boolean spanningOnProtoboard;
	private Coordinate startOnStripboard;
	private Coordinate startOnProtoboard;
	private Coordinate endOnStripboard;
	private Coordinate endOnProtoboard;

	{
		identifier = "R" + ++idCounter;
		type = "Resistor";
		Terminal leg1 = new Terminal(this);
		leg1.setIdentifier(identifier + "_1");
		Terminal leg2 = new Terminal(this);
		leg2.setIdentifier(identifier + "_2");
	}

    public static void setCounter(int i) {
		idCounter = i;
    }

	public void setSchX(int x) {
		schX = x;
		if (schematicsOrientation == ComponentOrientation.UP || schematicsOrientation == ComponentOrientation.DOWN) {
			terminals.get(0).setSchX(x+12);
			terminals.get(1).setSchX(x+12);
		} else { // TODO: flip?
			terminals.get(0).setSchX(x);
			terminals.get(1).setSchX(x+48);
		}
	}

	public void setSchY(int y) {
		schY = y;
		if (schematicsOrientation == ComponentOrientation.UP || schematicsOrientation == ComponentOrientation.DOWN) {
			terminals.get(0).setSchY(y);
			terminals.get(1).setSchY(y+48);
			// TODO: flip?
		} else {
			terminals.get(0).setSchY(y+12);
			terminals.get(1).setSchY(y+12);
		}
	}

	@Override
	public void drawOnSchematics(GraphicsContext context) {
		int x = schX;
		int y = schY;
		context.setStroke(highlighted ? Color.PURPLE : Color.DARKBLUE);
		context.setGlobalAlpha(1.0);
		context.setLineWidth(1.0);
		if (schematicsOrientation == ComponentOrientation.UP || schematicsOrientation == ComponentOrientation.DOWN) {
			context.strokeRect(x + 6, y + 6, 12, 36);
			context.strokeLine(x + 12, y, x + 12, y + 6);
			context.strokeLine(x + 12, y + 42, x + 12, y + 48);
			// TODO: identifier - KiCAD has it on the right
		} else {
			context.strokeRect(x + 6, y + 6, 36, 12);
			context.strokeLine(x, y + 12, x + 6, y + 12);
			context.strokeLine(x + 42, y + 12, x + 48, y + 12);
			context.strokeText(identifier, x+8, y+17);
		}
	}

	@Override
	public void drawOnStripboard(GraphicsContext context) {
		draw(context, strX, strY, stripboardOrientation, spanningOnStripboard, startOnStripboard, endOnStripboard);
	}

	@Override
	public void drawOnProtoboard(GraphicsContext context) {
		draw(context, proX, proY, protoboardOrientation, spanningOnProtoboard, startOnProtoboard, endOnProtoboard);
	}
	
	private void draw(GraphicsContext context, int x, int y, ComponentOrientation orientation,
					  boolean spanning, Coordinate spanStart, Coordinate spanEnd) {
		context.setFill(Color.DODGERBLUE);
		if (spanning) {
			// line - drawing in the middle - line
			if (orientation == ComponentOrientation.UP || orientation == ComponentOrientation.DOWN) {
				int h = Math.abs(spanStart.getY() - spanEnd.getY());
				context.fillRoundRect(x + 3, y + 12 + h / 2 - 32, 18, 64, 12, 12);
				context.setFill(Color.DARKGREY);
				context.fillRoundRect(x + 9, y + 12, 6, h / 2 - 32, 6, 6);
				context.fillRoundRect(x + 9, y + 12 + h / 2 + 32, 6, h / 2 - 32, 6, 6);
			}
		} else {
			if (orientation == ComponentOrientation.UP || orientation == ComponentOrientation.DOWN) {
				context.fillRoundRect(x + 3, y + 16, 18, 64, 12, 12);
				// FIXME: leg span, standing placement
			} else {
				context.fillRoundRect(x + 16, y + 3, 64, 18, 12, 12);
			}
		}
	}

}
