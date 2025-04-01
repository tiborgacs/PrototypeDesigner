package prototypedesigner.PrototypeDesigner.components;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class FilmCapacitor extends Capacitor implements DrawableOnStripboard, DrawableOnProtoboard {
	
	{
		schematicsOrientation = ComponentOrientation.UP;
		stripboardOrientation = ComponentOrientation.UP;
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
			context.fillRoundRect(x+3, y+3, 18, 66, 12, 12);
		} else {
			context.fillRoundRect(x+3, y+3, 66, 18, 12, 12);
		}
	}
}
