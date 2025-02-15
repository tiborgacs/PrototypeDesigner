package prototypedesigner.PrototypeDesigner;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import prototypedesigner.PrototypeDesigner.components.DrawableOnProtoboard;
import prototypedesigner.PrototypeDesigner.components.DrawableOnStripboard;

public class MicroChip implements DrawableOnStripboard, DrawableOnProtoboard {

	private int legs;
	private int x;
	private int y;
	
	public MicroChip(int legs) {
		this.legs = legs;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}

	public void drawOnStripboard(@SuppressWarnings("exports") GraphicsContext context) {
		drawOnProtoboard(context);
	}
	
	public void drawOnProtoboard(@SuppressWarnings("exports") GraphicsContext context) {
		context.setFill(Color.BLACK);
		context.fillRect(x+15, y+3, 24*4-30, 24*(legs/2)-6);
		context.setFill(Color.DARKGRAY);
		for (int i = 0; i < legs/2; i++) {
			context.fillRect(x+9, y+8+i*24, 6, 10);
			context.fillRect(x+72+9, y+8+i*24, 6, 10);
		}
		context.fillArc(x+36, y-9, 24.0, 24.0, 180, 180, ArcType.CHORD);
		context.fillOval(x+21, y+9, 6, 6); // FIXME are the coordinates correct?
	}

	
}
