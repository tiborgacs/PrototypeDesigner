package prototypedesigner.PrototypeDesigner.components;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import lombok.Getter;
import lombok.Setter;

public class Diode extends Component implements DrawableOnStripboard, DrawableOnProtoboard, Spanning {

	private static int idCounter = 0;
	
	{
		identifier = "D" + ++idCounter;
		type = "Diode";
		Terminal anodeLeg = new Terminal(this);
		anodeLeg.setIdentifier(identifier + "_A");
		Terminal cathodeLeg = new Terminal(this);
		cathodeLeg.setIdentifier(identifier + "_C");
	}

	@Getter
	@Setter
	private boolean spanning;
	private Coordinate spanStart;
	private Coordinate spanEnd;

    public static void setCounter(int i) {
		idCounter = i;
    }

    @Override
	public void setSchX(int x) {
		super.setSchX(x);
		Terminal anodeLeg = terminals.get(0);
		Terminal cathodeLeg = terminals.get(1);
		if (schematicsOrientation == ComponentOrientation.LEFT) {
			cathodeLeg.setSchX(x);
			anodeLeg.setSchX(x+24);
		} else if (schematicsOrientation == ComponentOrientation.RIGHT) {
			cathodeLeg.setSchX(x+24);
			anodeLeg.setSchX(x);
		} else {
			anodeLeg.setSchX(x+12);
			cathodeLeg.setSchX(x+12);
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
	
	@Override
	public void setSchY(int y) {
		super.setSchY(y);
		Terminal anodeLeg = terminals.get(0);
		Terminal cathodeLeg = terminals.get(1);
		if (schematicsOrientation == ComponentOrientation.UP) {
			cathodeLeg.setSchY(y);
			anodeLeg.setSchY(y+24);
		} else if (schematicsOrientation == ComponentOrientation.DOWN) {
			cathodeLeg.setSchY(y+24);
			anodeLeg.setSchY(y);
		} else {
			anodeLeg.setSchY(y+12);
			cathodeLeg.setSchY(y+12);
		}
	}
	
	@Override
	public void drawOnSchematics(GraphicsContext context) {
		int x = schX;
		int y = schY;
		context.setStroke(highlighted ? Color.PURPLE : Color.DARKBLUE);
		context.setGlobalAlpha(1.0);
		context.setLineWidth(1.0);
		if (schematicsOrientation == ComponentOrientation.UP) {
			context.strokeLine(x+12, y, x+12, y+6);
			context.strokeLine(x+6, y+6, x+18, y+6);
			context.strokePolygon(new double[] {x+6, x+12, x+18}, new double[] {y+18, y+6, y+18}, 3);
			context.strokeLine(x+12, y+18, x+12, y+24);
		}
		if (schematicsOrientation == ComponentOrientation.DOWN) {
			context.strokeLine(x+12, y, x+12, y+6);
			context.strokePolygon(new double[] {x+6, x+12, x+18}, new double[] {y+6, y+18, y+6}, 3);
			context.strokeLine(x+6, y+18, x+18, y+18);
			context.strokeLine(x+12, y+18, x+12, y+24);
		}
		if (schematicsOrientation == ComponentOrientation.RIGHT) {
			context.strokeLine(x, y+12, x+6, y+12);
			context.strokePolygon(new double[] {x+6, x+18, x+6},new double[] {y+6, y+12, y+18},  3);
			context.strokeLine(x+18, y+6, x+18, y+18);
			context.strokeLine(x+18, y+12, x+24, y+12);
		}
		if (schematicsOrientation == ComponentOrientation.LEFT) {
			context.strokeLine(x, y+12, x+6, y+12);
			context.strokeLine(x+6, y+6, x+6, y+18);
			context.strokePolygon(new double[] {x+18, x+6, x+18},new double[] {y+6, y+12, y+18},  3);
			context.strokeLine(x+18, y+12, x+24, y+12);
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
		if (orientation == ComponentOrientation.UP) {
			context.setFill(Color.BLACK);
			context.fillRect(x+3, y+16, 18, 8);
			context.setFill(Color.WHITE);
			context.fillRect(x+3, y+24, 18, 8);
			context.setFill(Color.BLACK);
			context.fillRect(x+3, y+32, 18, 24);
		}
		if (orientation == ComponentOrientation.DOWN) {
			context.setFill(Color.BLACK);
			context.fillRect(x+3, y+16, 18, 24);
			context.setFill(Color.WHITE);
			context.fillRect(x+3, y+40, 18, 8);
			context.setFill(Color.BLACK);
			context.fillRect(x+3, y+48, 18, 8);
		}
		if (orientation == ComponentOrientation.RIGHT) {
			context.setFill(Color.BLACK);
			context.fillRect(x+16, y+3, 24, 18);
			context.setFill(Color.WHITE);
			context.fillRect(x+40, y+3, 8, 18);
			context.setFill(Color.BLACK);
			context.fillRect(x+48, y+3, 8, 18);
		}
		if (orientation == ComponentOrientation.LEFT) {
			context.setFill(Color.BLACK);
			context.fillRect(x+16, y+3, 8, 18);
			context.setFill(Color.WHITE);
			context.fillRect(x+24, y+3, 8, 18);
			context.setFill(Color.BLACK);
			context.fillRect(x+32, y+3, 24, 18);
		}
	}
	
}
