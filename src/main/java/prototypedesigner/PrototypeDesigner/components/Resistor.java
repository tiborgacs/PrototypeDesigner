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
	private boolean spanningOnProtoboard = true;
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
			context.strokeText(identifier.replace("R", "R\n"), x + 6, y + 18);
		} else {
			context.strokeRect(x + 6, y + 6, 36, 12);
			context.strokeLine(x, y + 12, x + 6, y + 12);
			context.strokeLine(x + 42, y + 12, x + 48, y + 12);
			context.strokeText(identifier, x+8, y+17);
		}
	}

	public void setStartOnProtoboard(Coordinate startOnProtoboard) {
		this.startOnProtoboard = startOnProtoboard;
		proX = startOnProtoboard.getX();
		proY = startOnProtoboard.getY();
		terminals.get(0).setProX(startOnProtoboard.getX());
		terminals.get(0).setProY(startOnProtoboard.getY());
	}

	public void setEndOnProtoboard(Coordinate endOnProtoboard) {
		this.endOnProtoboard = endOnProtoboard;
		terminals.get(1).setProX(endOnProtoboard.getX());
		terminals.get(1).setProY(endOnProtoboard.getY());
	}

	public void setStartOnStripboard(Coordinate startOnStripboard) {
		this.startOnStripboard = startOnStripboard;
		strX = startOnStripboard.getX();
		strY = startOnStripboard.getY();
		terminals.get(0).setStrX(startOnStripboard.getX());
		terminals.get(0).setStrY(startOnStripboard.getY());
	}

	public void setEndOnStripboard(Coordinate endOnStripboard) {
		this.endOnStripboard = endOnStripboard;
		terminals.get(1).setStrX(endOnStripboard.getX());
		terminals.get(1).setStrY(endOnStripboard.getY());
	}

	public void setProX(int x) {
		proX = x;
		if (!spanningOnProtoboard) {
			if (protoboardOrientation == ComponentOrientation.UP || protoboardOrientation == ComponentOrientation.DOWN) {
				terminals.get(0).setProX(x);
				terminals.get(1).setProX(x);
			} else {
				terminals.get(0).setProX(x);
				terminals.get(1).setProX(x+3);
			}
		}
	}

	public void setProY(int y) {
		proY = y;
		if (!spanningOnProtoboard) {
			if (protoboardOrientation == ComponentOrientation.UP || protoboardOrientation == ComponentOrientation.DOWN) {
				terminals.get(0).setProY(y);
				terminals.get(1).setProY(y+3);
			} else {
				terminals.get(0).setProY(y);
				terminals.get(1).setProX(y);
			}
		}
	}

	public void setStrX(int x) {
		strX = x;
		if (!spanningOnStripboard) {
			if (stripboardOrientation == ComponentOrientation.UP || stripboardOrientation == ComponentOrientation.DOWN) {
				terminals.get(0).setStrX(x);
				terminals.get(1).setStrX(x);
			} else {
				terminals.get(0).setStrX(x);
				terminals.get(1).setStrX(x+3);
			}
		}
	}

	public void setStrY(int y) {
		strY = y;
		if (!spanningOnStripboard) {
			if (stripboardOrientation == ComponentOrientation.UP || stripboardOrientation == ComponentOrientation.DOWN) {
				terminals.get(0).setStrY(y);
				terminals.get(1).setStrY(y+3);
			} else {
				terminals.get(0).setStrY(y);
				terminals.get(1).setStrY(y);
			}
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
		x = x * 24;
		y = y * 24;
		context.setFill(Color.DODGERBLUE);
		context.setStroke(Color.WHITE);
		if (spanning) {
			if (orientation == ComponentOrientation.UP || orientation == ComponentOrientation.DOWN) {
				int h = Math.abs(spanStart.getY() - spanEnd.getY()) * 24;
				if (h <= 24 && h >= -24) {
					context.fillRoundRect(x + 3, y + 15, 18, 18, 12, 12);
					context.strokeText(
							identifier.length() == 2 ? identifier : identifier.replace("R", ""),
							x + 4, y + 30);
				} else if (h <= 48 && h >= -48) {
					context.fillRoundRect(x + 3, y + 15, 18, 42, 12, 12);
					context.strokeText(identifier.replace("R", "R\n"), x + 4, y + 30);
				}  else if (h <= 72 && h >= -72) {
					context.fillRoundRect(x + 3, y + 15, 18, 66, 12, 12);
					context.strokeText(identifier.replace("R", "R\n"), x + 4, y + 30);
				} else {
					context.fillRoundRect(x + 3, y + 12 + h / 2 - 32, 18, 64, 12, 12);
					context.setFill(Color.DARKGREY);
					context.fillRoundRect(x + 9, y + 12, 6, h / 2 - 32, 6, 6);
					context.fillRoundRect(x + 9, y + 12 + h / 2 + 32, 6, h / 2 - 32, 6, 6);
					context.strokeText(identifier.replace("R", "R\n"), x + 4, y + h / 2);
				}
			} else {
				int w = Math.abs(spanStart.getX() - spanEnd.getX()) * 24;
				if (w <= 24) {
					context.fillRoundRect(x + 15, y + 3, 18, 18, 12, 12);
					context.strokeText(
							identifier.length() == 2 ? identifier : identifier.replace("R", ""),
							x + 16, y + 18);
				} else if (w <= 48) {
					context.fillRoundRect(x + 15, y + 3, 42, 18, 12, 12);
					context.strokeText(identifier, x + 16, y + 18);
				} else if (w <= 72) {
					context.fillRoundRect(x + 15, y + 3, 66, 18, 12, 12);
					context.strokeText(identifier, x + 16, y + 18);
				} else {
					context.fillRoundRect(x + 12 + w / 2 - 32, y + 3, 64, 18, 12, 12);
					context.setFill(Color.DARKGREY);
					context.fillRoundRect(x + 12, y + 9, w / 2 - 32, 6, 6, 6);
					context.fillRoundRect(x  + 12 + w / 2 + 32, y + 9,w / 2 - 32, 6, 6, 6);
					context.strokeText(identifier, x + w / 2, y + 18);
				}
			}
		} else {
			if (orientation == ComponentOrientation.UP || orientation == ComponentOrientation.DOWN) {
				context.fillRoundRect(x + 3, y + 16, 18, 64, 12, 12);
			} else {
				context.fillRoundRect(x + 16, y + 3, 64, 18, 12, 12);
			}
		}
	}

}
