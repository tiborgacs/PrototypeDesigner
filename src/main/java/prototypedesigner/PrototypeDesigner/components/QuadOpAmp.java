package prototypedesigner.PrototypeDesigner.components;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;

public class QuadOpAmp extends IntegratedCircuit implements DrawableOnStripboard, DrawableOnProtoboard {
	
	// TL074
	{
		packaging = Packaging.DIL14;
		schematicsOrientation = ComponentOrientation.UP; // this one can be constant
		stripboardOrientation = ComponentOrientation.LEFT;
		protoboardOrientation = ComponentOrientation.UP;
	}
	
	private Terminal output1 = new Terminal(this);
	private Terminal invertingInput1 = new Terminal(this);
	private Terminal nonInvertingInput1 = new Terminal(this);
	private Terminal positiveSupply = new Terminal(this);
	private Terminal nonInvertingInput2 = new Terminal(this);
	private Terminal invertingInput2 = new Terminal(this);
	private Terminal output2 = new Terminal(this);
	private Terminal output3 = new Terminal(this);
	private Terminal invertingInput3 = new Terminal(this);
	private Terminal nonInvertingInput3 = new Terminal(this);
	private Terminal negativeSuppy = new Terminal(this);
	private Terminal nonInvertingInput4 = new Terminal(this);
	private Terminal invertingInput4 = new Terminal(this);
	private Terminal output4 = new Terminal(this);
	
	public void setProtoboardOrientation(ComponentOrientation orientation) {
		protoboardOrientation = orientation;
	}
	
	@Override
	public void setSchX(int x) {
		super.setSchX(x);
		output1.setSchX(x+12);
		invertingInput1.setSchX(x+12);
		nonInvertingInput1.setSchX(x+12);
		positiveSupply.setSchX(x+12);
		nonInvertingInput2.setSchX(x+12);
		invertingInput2.setSchX(x+12);
		output2.setSchX(x+12);
		output3.setSchX(x+84);
		invertingInput3.setSchX(x+84);
		nonInvertingInput3.setSchX(x+84);
		negativeSuppy.setSchX(x+84);
		nonInvertingInput4.setSchX(x+84);
		invertingInput4.setSchX(x+84);
		output4.setSchX(x+84);
	}
	
	@Override
	public void setSchY(int y) {
		super.setSchY(y);
		output1.setSchY(y+12);
		invertingInput1.setSchY(y+36);
		nonInvertingInput1.setSchY(y+60);
		positiveSupply.setSchY(y+84);
		nonInvertingInput2.setSchY(y+108);
		invertingInput2.setSchY(y+132);
		output2.setSchY(y+156);
		output3.setSchY(y+156);
		invertingInput3.setSchY(y+132);
		nonInvertingInput3.setSchY(y+108);
		negativeSuppy.setSchY(y+84);
		nonInvertingInput4.setSchY(y+60);
		invertingInput4.setSchY(y+36);
		output4.setSchY(y+12);
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
		if (orientation == ComponentOrientation.UP || orientation == ComponentOrientation.DOWN) {
			context.fillRect(x+15, y+3, 24*4-30, 24*7-6);
			context.setFill(Color.DARKGRAY);
			for (int i = 0; i < 7; i++) {
				context.fillRect(x+9, y+8+i*24, 6, 10);
				context.fillRect(x+72+9, y+8+i*24, 6, 10);
			}
			if (orientation == ComponentOrientation.UP) {
				context.fillArc(x+36, y-9, 24.0, 24.0, 180, 180, ArcType.CHORD);
				context.fillOval(x+21, y+9, 6, 6);
			} else {
				context.fillArc(x+36, y-15+7*24, 24.0, 24.0, 0, 180, ArcType.CHORD);
				context.fillOval(x+48+21, y+6*24+9, 6, 6);
			}
		} else if (orientation == ComponentOrientation.LEFT || orientation == ComponentOrientation.RIGHT) {
			context.fillRect(x+3, y+15, 24*7-6, 24*4-30);
			context.setFill(Color.DARKGRAY);
			for (int i = 0; i < 7; i++) {
				context.fillRect(x+7+i*24,y+9, 10, 6);
				context.fillRect(x+7+i*24, y+72+9, 10, 6);
			}
			if (orientation == ComponentOrientation.LEFT) {
				context.fillArc(x-9, y+36, 24.0, 24.0, 270, 180, ArcType.CHORD);
				context.fillOval(x+9, y+48+21, 6, 6);
			} else {
				context.fillArc(x-15+7*24, y+36, 24.0, 24.0, 90, 180, ArcType.CHORD);
				context.fillOval(x+6*24+9, y+21, 6, 6);
			}
		}
	}
	
	@Override
	public void drawOnSchematics(GraphicsContext context) {
		int x = schX;
		int y = schY;
		context.setStroke(highlighted ? Color.PURPLE : Color.DARKBLUE);
		context.setGlobalAlpha(1.0);
		context.setLineWidth(1.0);
		context.strokeRect(x+15, y+3, 24*4-30, 24*7-6);
		context.strokePolygon(new double[] {x+20, x+20, x+42}, new double[] {y+29, y+67, y+48}, 3);
		context.strokeLine(x+42, y+48, x+44, y+48);
		context.strokeLine(x+44, y+48, x+44, y+12);
		context.strokeText("-", x+22, y+40);
		context.strokeText("+", x+20, y+62);
		context.strokePolygon(new double[] {x+76, x+76, x+58}, new double[] {y+29, y+67, y+48}, 3);
		context.strokeLine(x+58, y+48, x+52, y+48);
		context.strokeLine(x+52, y+48, x+52, y+12);
		context.strokeText("-", x+70, y+40);
		context.strokeText("+", x+68, y+62);
		context.strokeLine(x+9, y+12, x+44, y+12);
		context.strokeLine(x+52, y+12, x+88, y+12);
		context.strokeLine(x+9, y+36, x+20, y+36);
		context.strokeLine(x+77, y+36, x+88, y+36);
		context.strokeLine(x+9, y+60, x+20, y+60);
		context.strokeLine(x+9, y+84, x+15, y+84);
		context.strokeLine(x+77, y+60, x+88, y+60);
		context.strokeLine(x+81, y+84, x+88, y+84);
		context.strokePolygon(new double[] {x+20, x+20, x+42}, new double[] {y+29+72, y+67+72, y+48+72}, 3);
		context.strokeLine(x+42, y+48+72, x+44, y+48+72);
		context.strokeText("-", x+22, y+62+72);
		context.strokeText("+", x+20, y+40+72);
		context.strokePolygon(new double[] {x+76, x+76, x+58}, new double[] {y+29+72, y+67+72, y+48+72}, 3);
		context.strokeText("-", x+70, y+62+72);
		context.strokeText("+", x+68, y+40+72);
		context.strokeLine(x+9, y+36+72, x+20, y+36+72);
		context.strokeLine(x+77, y+36+72, x+88, y+36+72);
		context.strokeLine(x+9, y+60+72, x+20, y+60+72);
		context.strokeLine(x+77, y+60+72, x+88, y+60+72);
		context.strokeLine(x+58, y+48+72, x+52, y+48+72);
		context.strokeLine(x+9, y+12+144, x+44, y+12+144);
		context.strokeLine(x+52, y+12+144, x+88, y+12+144);
		context.strokeLine(x+52, y+48+108, x+52, y+12+108);
		context.strokeLine(x+44, y+48+108, x+44, y+12+108);
		context.strokeArc(x+36, y-9, 24.0, 24.0, 180, 180, ArcType.CHORD);
	}

}
