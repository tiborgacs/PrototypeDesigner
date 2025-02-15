package prototypedesigner.PrototypeDesigner.components;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class CeramicCapacitor extends Capacitor implements DrawableOnStripboard, DrawableOnProtoboard {
	{
		stripboardOrientation = ComponentOrientation.LEFT;
		protoboardOrientation = ComponentOrientation.UP;
	}
	
	public void setProtoboardOrientation(ComponentOrientation orientation) {
		protoboardOrientation = orientation;
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
		context.setFill(Color.GOLD);
		if (orientation == ComponentOrientation.UP || orientation == ComponentOrientation.DOWN) {
			context.fillOval(x+3, y+3, 18, 42);
		} else {
			context.fillOval(x+3, y+3, 42, 18);
		}
	}
}
