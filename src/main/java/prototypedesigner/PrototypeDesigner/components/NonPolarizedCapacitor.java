package prototypedesigner.PrototypeDesigner.components;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class NonPolarizedCapacitor extends Capacitor {

	{
		type = "Capacitor";
		Terminal leg1 = new Terminal(this);
		leg1.setIdentifier(identifier + "_1");
		Terminal leg2 = new Terminal(this);
		leg2.setIdentifier(identifier + "_2");
	}

    @Override
	public void setSchX(int x) {
		super.setSchX(x);
		if (schematicsOrientation == ComponentOrientation.UP || schematicsOrientation == ComponentOrientation.DOWN) {
			terminals.get(0).setSchX(x+12);
			terminals.get(1).setSchX(x+12);
		} else {
			terminals.get(0).setSchX(x);
			terminals.get(1).setSchX(x+24);
		}
	}
	
	@Override
	public void setSchY(int y) {
		super.setSchY(y);
		if (schematicsOrientation == ComponentOrientation.UP || schematicsOrientation == ComponentOrientation.DOWN) {
			terminals.get(0).setSchY(y);
			terminals.get(1).setSchY(y+24);
		} else {
			terminals.get(0).setSchY(y+12);
			terminals.get(1).setSchY(y+12);
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
				terminals.get(1).setProY(y);
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
	public void drawOnSchematics(GraphicsContext context) {
		int x = schX;
		int y = schY;
		context.setStroke(highlighted ? Color.PURPLE : Color.DARKBLUE);
		context.setGlobalAlpha(1.0);
		context.setLineWidth(1.0);
		if (schematicsOrientation == ComponentOrientation.UP || schematicsOrientation == ComponentOrientation.DOWN) {
			context.strokeLine(x+3, y+10, x+21, y+10);
			context.strokeLine(x+3, y+14, x+21, y+14);
			context.strokeLine(x+12, y, x+12, y+10);
			context.strokeLine(x+12, y+14, x+12, y+24);
		} else {
			context.strokeLine(x+10, y+3, x+10, y+21);
			context.strokeLine(x+14, y+3, x+14, y+21);
			context.strokeLine(x, y+12, x+10, y+12);
			context.strokeLine(x+14, y+12, x+24, y+12);
		}
		context.strokeText(identifier, schX, schY);
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
		context.setFill(Color.GOLD);
		x = x * 24;
		y = y * 24;
		if (spanning) {
			if (orientation == ComponentOrientation.UP || orientation == ComponentOrientation.DOWN) {
				int h = Math.abs(spanStart.getY() - spanEnd.getY()) * 24;
				if (h <= 24) context.fillOval(x + 3, y + 3, 18, 42);
				else {
					context.fillRoundRect(x + 3, y + 12 + h / 2 - 32, 18, 64, 12, 12);
					context.setFill(Color.DARKGREY);
					context.fillRoundRect(x + 9, y + 12, 6, h / 2 - 32, 6, 6);
					context.fillRoundRect(x + 9, y + 12 + h / 2 + 32, 6, h / 2 - 32, 6, 6);
				}
			} else {
				int w = Math.abs(spanStart.getX() - spanEnd.getX()) * 24;
				if (w <= 24) context.fillOval(x + 3, y + 3, 42, 18);
				else {
					context.fillRoundRect(x + 12 + w / 2 - 32, y + 3, 64, 18, 12, 12);
					context.setFill(Color.DARKGREY);
					context.fillRoundRect(x + 12, y + 9, w / 2 - 32, 6, 6, 6);
					context.fillRoundRect(x  + 12 + w / 2 + 32, y + 9,w / 2 - 32, 6, 6, 6);
				}
			}
		} else {
			if (orientation == ComponentOrientation.UP || orientation == ComponentOrientation.DOWN) {
				context.fillOval(x + 3, y + 3, 18, 42);
			} else {
				context.fillOval(x + 3, y + 3, 42, 18);
			}
		}
	}

}
