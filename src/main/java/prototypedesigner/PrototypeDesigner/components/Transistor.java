package prototypedesigner.PrototypeDesigner.components;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import lombok.Getter;
import lombok.Setter;

public abstract class Transistor extends Component implements DrawableOnStripboard, DrawableOnProtoboard {

	private static int idCounter = 0;
	@Getter @Setter protected Polarity polarity;
	{
		stripboardOrientation = ComponentOrientation.UP;
		protoboardOrientation = ComponentOrientation.UP;
		identifier = "Q" + ++idCounter;
	}

	public static void setCounter(int i) {
		idCounter = i;
	}

	public void setStrX(int x) {
		strX = x;
		if (stripboardOrientation == ComponentOrientation.LEFT || stripboardOrientation == ComponentOrientation.RIGHT) {
			for(Terminal t: terminals) t.setStrX(x);
		} else {
			String[] identifiers = pinout.split("_");
			if (stripboardOrientation == ComponentOrientation.DOWN) {
				String temp = identifiers[2];
				identifiers[2] = identifiers[0];
				identifiers[0] = temp;
			}
			for (int i = 0; i < identifiers.length; i++) {
				for (Terminal t: terminals) {
					if (t.getIdentifier().endsWith("_" + identifiers[i])) t.setStrX(x + i);
				}
			}
		}
	}

	public void setStrY(int y) {
		strY = y;
		if (stripboardOrientation == ComponentOrientation.UP || stripboardOrientation == ComponentOrientation.DOWN) {
			for(Terminal t: terminals) t.setStrY(y);
		} else {
			String[] identifiers = pinout.split("_");
			if (stripboardOrientation == ComponentOrientation.RIGHT) {
				String temp = identifiers[2];
				identifiers[2] = identifiers[0];
				identifiers[0] = temp;
			}
			for (int i = 0; i < identifiers.length; i++) {
				for (Terminal t: terminals) {
					if (t.getIdentifier().endsWith("_" + identifiers[i])) t.setStrY(y + i);
				}
			}
		}
	}

	public void setProX(int x) {
		proX = x;
		if (protoboardOrientation == ComponentOrientation.LEFT || protoboardOrientation == ComponentOrientation.RIGHT) {
			for(Terminal t: terminals) t.setProX(x);
		} else {
			String[] identifiers = pinout.split("_");
			if (protoboardOrientation == ComponentOrientation.DOWN) {
				String temp = identifiers[2];
				identifiers[2] = identifiers[0];
				identifiers[0] = temp;
			}
			for (int i = 0; i < identifiers.length; i++) {
				for (Terminal t: terminals) {
					if (t.getIdentifier().endsWith("_" + identifiers[i])) t.setProX(x + i);
				}
			}
		}
	}

	public void setProY(int y) {
		proY = y;
		if (protoboardOrientation == ComponentOrientation.UP || protoboardOrientation == ComponentOrientation.DOWN) {
			for(Terminal t: terminals) t.setProY(y);
		} else {
			String[] identifiers = pinout.split("_");
			if (protoboardOrientation == ComponentOrientation.RIGHT) {
				String temp = identifiers[2];
				identifiers[2] = identifiers[0];
				identifiers[0] = temp;
			}
			for (int i = 0; i < identifiers.length; i++) {
				for (Terminal t: terminals) {
					if (t.getIdentifier().endsWith("_" + identifiers[i])) t.setProY(y + i);
				}
			}
		}
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
		x = x * 24;
		y = y * 24;
		context.setFill(Color.BLACK);
		context.setStroke(Color.WHITE);
		String[] identifiers = pinout.split("_");
		if (orientation == ComponentOrientation.DOWN) {
			context.fillArc(x, y, 72, 24, 0, 180, ArcType.CHORD);
			context.fillRect(x, y+12, 72, 12);
			context.strokeText(
					identifiers[0] + "  " + identifier + (identifier.length() == 2 ? " " : "")
							+ identifiers[1] + "  " + identifiers[2], x + 4, y + 16);
		}
		if (orientation == ComponentOrientation.LEFT) {
			context.fillArc(x, y, 24, 72, 270, 180, ArcType.CHORD);
			context.fillRect(x, y, 12, 72);
			context.strokeText(
					identifiers[0] + "\n" + identifier + "\n" + identifiers[1] + "\n" + identifiers[2], x + 4, y + 16);
		}
		if (orientation == ComponentOrientation.UP) {
			context.fillArc(x, y, 72, 24, 180, 180, ArcType.CHORD);
			context.fillRect(x, y, 72, 12);
			context.strokeText(
					identifiers[2] + "  " + identifier + (identifier.length() == 2 ? " " : "")
							+ identifiers[1] + "  " + identifiers[0], x + 4, y + 16);
		}
		if (orientation == ComponentOrientation.RIGHT) {
			context.fillArc(x, y, 24, 72, 90, 180, ArcType.CHORD);
			context.fillRect(x+12, y, 12, 72);
			context.strokeText(
					identifiers[2] + "\n" + identifier + "\n" + identifiers[1] + "\n" + identifiers[0], x + 4, y + 16);
		}
	}
}
