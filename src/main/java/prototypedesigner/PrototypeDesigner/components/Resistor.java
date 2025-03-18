package prototypedesigner.PrototypeDesigner.components;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class Resistor extends Component implements ComponentValue, DrawableOnStripboard, DrawableOnProtoboard, Spanning {

	private static int idCounter = 0;

	private Terminal leg1 = new Terminal(this);
	private Terminal leg2 = new Terminal(this);
	@Getter @Setter	private String value;
	@Getter @Setter private boolean spanning;
	private Coordinate spanStart;
	private Coordinate spanEnd;

	{
		schematicsOrientation = ComponentOrientation.LEFT;
		stripboardOrientation = ComponentOrientation.UP;
		protoboardOrientation = ComponentOrientation.UP;
		identifier = "R" + ++idCounter;
		leg1.setIdentifier(identifier + "A");
		leg2.setIdentifier(identifier + "B");
	}

	public void setProtoboardOrientation(ComponentOrientation orientation) {
		protoboardOrientation = orientation;
	}
	
	//@Override
	public void setSchX(int x) {
		//super.setSchX(x);
		schX = x;
		if (schematicsOrientation == ComponentOrientation.UP || schematicsOrientation == ComponentOrientation.DOWN) {
			leg1.setSchX(x+12);
			leg2.setSchX(x+12);
		} else {
			leg1.setSchX(x);
			leg2.setSchY(x+48);
		}
	}
	
	//@Override
	public void setSchY(int y) {
		//super.setSchY(y);
		schY = y;
		if (schematicsOrientation == ComponentOrientation.UP || schematicsOrientation == ComponentOrientation.DOWN) {
			leg1.setSchY(y);
			leg2.setSchY(y+48);
		} else {
			leg1.setSchY(y+12);
			leg2.setSchY(y+12);
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
		} else {
			context.strokeRect(x + 6, y + 6, 36, 12);
			context.strokeLine(x, y + 12, x + 6, y + 12);
			context.strokeLine(x + 42, y + 12, x + 48, y + 12);
			context.strokeText(identifier, x+8, y+17);
		}
	}

	@Override
	public void drawOnStripboard(GraphicsContext context) {
		draw(context, strX, strY, stripboardOrientation);
	}

	@Override
	public void drawOnProtoboard(GraphicsContext context) {
		draw(context, proX, proY, protoboardOrientation);
	}
	
	private void draw(GraphicsContext context, int x, int y, ComponentOrientation orientation) {
		context.setFill(Color.DODGERBLUE);
		if (spanning) {
			// line - drawing in the middle - line
			int h = Math.abs(spanStart.getY()-spanEnd.getY());
			context.fillRoundRect(x + 3, y + 12+ h/2 - 32, 18, 64, 12, 12);
			context.setFill(Color.DARKGREY);
			context.fillRoundRect(x+9, y+12, 6, h/2-32, 6, 6);
			context.fillRoundRect(x+9, y+12 + h/2 + 32, 6, h/2-32, 6, 6);
		} else {
			if (orientation == ComponentOrientation.UP || orientation == ComponentOrientation.DOWN) {
				context.fillRoundRect(x + 3, y + 16, 18, 64, 12, 12);
				// FIXME: leg span, standing placement
			} else {
				context.fillRoundRect(x + 16, y + 3, 64, 18, 12, 12);
			}
		}
	}

	@Override
	public Coordinate getStart() {
		return spanStart;
	}

	@Override
	public void setStart(Coordinate start) {
		spanStart = start;
	}

	@Override
	public Coordinate getEnd() {
		return spanEnd;
	}

	@Override
	public void setEnd(Coordinate end) {
		spanEnd = end;
	}
}
