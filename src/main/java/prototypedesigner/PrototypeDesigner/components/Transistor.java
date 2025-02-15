package prototypedesigner.PrototypeDesigner.components;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;

public abstract class Transistor extends Component implements DrawableOnStripboard, DrawableOnProtoboard {

	private static int idCounter = 0;

	{
		stripboardOrientation = ComponentOrientation.UP;
		protoboardOrientation = ComponentOrientation.UP;
		identifier = "Q" + ++idCounter;
	}
	
	public void setProtoboardOrientation(ComponentOrientation orientation) {
		protoboardOrientation = orientation;
	}
	
	@Override
	public void drawOnProtoboard(GraphicsContext context) {
		draw(context, proX, proY, protoboardOrientation);
	}
	
	@Override
	public void drawOnStripboard(GraphicsContext context) {
		draw(context, strX, strY, stripboardOrientation);
	}
	
	private void draw(GraphicsContext context, int x, int y, ComponentOrientation orientation) {
		context.setFill(Color.BLACK);
		if (orientation == ComponentOrientation.DOWN) {
			context.fillArc(x, y, 72, 24, 0, 180, ArcType.CHORD);
			context.fillRect(x, y+12, 72, 12);
		}
		if (orientation == ComponentOrientation.LEFT) {
			context.fillArc(x, y, 24, 72, 270, 180, ArcType.CHORD);
			context.fillRect(x, y, 12, 72);
		}
		if (orientation == ComponentOrientation.UP) {
			context.fillArc(x, y, 72, 24, 180, 180, ArcType.CHORD);
			context.fillRect(x, y, 72, 12);
		}
		if (orientation == ComponentOrientation.RIGHT) {
			context.fillArc(x, y, 24, 72, 90, 180, ArcType.CHORD);
			context.fillRect(x+12, y, 12, 72);
		}
	}
}
