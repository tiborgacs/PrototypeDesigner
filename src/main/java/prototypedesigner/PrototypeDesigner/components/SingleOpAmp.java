package prototypedesigner.PrototypeDesigner.components;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;

public class SingleOpAmp extends IntegratedCircuit implements DrawableOnStripboard, DrawableOnProtoboard {

	// LM741, TL071, LM386
	{
		packaging = Packaging.DIL08;
		schematicsOrientation = ComponentOrientation.UP; // this one can be constant
		stripboardOrientation = ComponentOrientation.RIGHT;
		protoboardOrientation = ComponentOrientation.UP;
	}
	
	public void setProtoboardOrientation(ComponentOrientation orientation) {
		protoboardOrientation = orientation;
	}
	
	private Terminal offset1 = new Terminal(this);
	private Terminal invertingInput = new Terminal(this);
	private Terminal nonInvertingInput = new Terminal(this);
	private Terminal negativeSupply = new Terminal(this);
	private Terminal offset2 = new Terminal(this);
	private Terminal output = new Terminal(this);
	private Terminal positiveSupply = new Terminal(this);
	private Terminal notConnected = new Terminal(this);
	
	
	@Override
	public void setSchX(int x) {
		super.setSchX(x);
		offset1.setSchX(x+12);
		invertingInput.setSchX(x+12);
		nonInvertingInput.setSchX(x+12);
		negativeSupply.setSchX(x+12);
		offset2.setSchX(x+84);
		output.setSchX(x+84);
		positiveSupply.setSchX(x+84);
		notConnected.setSchX(x+84);
	}
	
	@Override
	public void setSchY(int y) {
		super.setSchY(y);
		offset1.setSchY(y+12);
		invertingInput.setSchY(y+36);
		nonInvertingInput.setSchY(y+60);
		negativeSupply.setSchY(y+84);
		offset2.setSchY(y+84);
		output.setSchY(y+60);
		positiveSupply.setSchY(y+36);
		notConnected.setSchY(y+12);
	}
	
	@Override
	public void drawOnSchematics(GraphicsContext context) {
		int x = schX;
		int y = schY;
		context.setStroke(highlighted ? Color.PURPLE : Color.DARKBLUE);
		context.setGlobalAlpha(1.0);
		context.setLineWidth(1.0);
		context.strokeRect(x+15, y+3, 24*4-30, 24*4-6);
		context.strokePolygon(new double[] {x+20, x+20, x+63}, new double[] {y+29, y+67, y+48}, 3);
		context.strokeText("-", x+23, y+40);
		context.strokeText("+", x+20, y+62);
		// FIXME x+9 sucks
		context.strokeLine(x+9, y+12, x+15, y+12);
		context.strokeLine(x+81, y+12, x+88, y+12);
		context.strokeLine(x+9, y+36, x+20, y+36);
		context.strokeLine(x+48, y+36, x+88, y+36);
		context.strokeLine(x+48, y+36, x+46, y+39);
		context.strokeLine(x+9, y+60, x+20, y+60);
		context.strokeLine(x+9, y+84, x+45, y+84);
		context.strokeLine(x+46, y+84, x+46, y+55);
		context.strokeLine(x+63, y+48, x+67, y+48);
		context.strokeLine(x+68, y+48, x+68, y+60);
		context.strokeLine(x+68, y+60, x+88, y+60);
		context.strokeLine(x+81, y+84, x+88, y+84);
		context.strokeArc(x+36, y-9, 24.0, 24.0, 180, 180, ArcType.CHORD);
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
		context.setFill(Color.BLACK);
		if (orientation == ComponentOrientation.UP || orientation == ComponentOrientation.DOWN) {
			context.fillRect(x+15, y+3, 24*4-30, 24*4-6);
			context.setFill(Color.DARKGRAY);
			for (int i = 0; i < 4; i++) {
				context.fillRect(x+9, y+8+i*24, 6, 10);
				context.fillRect(x+72+9, y+8+i*24, 6, 10);
			}
			if (orientation == ComponentOrientation.UP) {
				context.fillArc(x+36, y-9, 24.0, 24.0, 180, 180, ArcType.CHORD);
				context.fillOval(x+21, y+9, 6, 6);
			} else {
				context.fillArc(x+36, y-15+96, 24.0, 24.0, 0, 180, ArcType.CHORD);
				context.fillOval(x+48+21, y+72+9, 6, 6);
			}
		} else if (orientation == ComponentOrientation.LEFT || orientation == ComponentOrientation.RIGHT) {
			context.fillRect(x+3, y+15, 24*4-6, 24*4-30);
			context.setFill(Color.DARKGRAY);
			for (int i = 0; i < 4; i++) {
				context.fillRect(x+7+i*24,y+9, 10, 6);
				context.fillRect(x+7+i*24, y+72+9, 10, 6);
			}
			if (orientation == ComponentOrientation.LEFT) {
				context.fillArc(x-9, y+36, 24.0, 24.0, 270, 180, ArcType.CHORD);
				context.fillOval(x+9, y+48+21, 6, 6);
			} else {
				context.fillArc(x-15+96, y+36, 24.0, 24.0, 90, 180, ArcType.CHORD);
				context.fillOval(x+72+9, y+21, 6, 6);
			}
		}
	}
	
}
