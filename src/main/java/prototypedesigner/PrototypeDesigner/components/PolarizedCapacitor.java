package prototypedesigner.PrototypeDesigner.components;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class PolarizedCapacitor extends Capacitor {

	private Terminal anodeLeg;
	private Terminal cathodeLeg;

	{
		anodeLeg = new Terminal(this);
		anodeLeg.setIdentifier(identifier + "_A");
		cathodeLeg = new Terminal(this);
		cathodeLeg.setIdentifier(identifier + "_C");
		type = "PolarizedCapacitor";
	}

	@Override
	public void setSchX(int x) {
		super.setSchX(x);
		if (schematicsOrientation == ComponentOrientation.LEFT) {
			anodeLeg.setSchX(x);
			cathodeLeg.setSchX(x+24);
		} else if (schematicsOrientation == ComponentOrientation.RIGHT) {
			anodeLeg.setSchX(x+24);
			cathodeLeg.setSchX(x);
		} else {
			anodeLeg.setSchX(x+12);
			cathodeLeg.setSchX(x+12);
		}
	}
	
	@Override
	public void setSchY(int y) {
		super.setSchY(y);
		if (schematicsOrientation == ComponentOrientation.UP) {
			anodeLeg.setSchY(y);
			cathodeLeg.setSchY(y+24);
		} else if (schematicsOrientation == ComponentOrientation.DOWN) {
			anodeLeg.setSchY(y+24);
			cathodeLeg.setSchY(y);
		} else {
			anodeLeg.setSchY(y+12);
			cathodeLeg.setSchY(y+12);
		}
	}

	public void setProX(int x) {
		proX = x;
		if (!spanningOnProtoboard) {
			Terminal anodeLeg = terminals.get(0);
			Terminal cathodeLeg = terminals.get(1);
			if (protoboardOrientation == ComponentOrientation.LEFT) {
				cathodeLeg.setProX(x);
				anodeLeg.setProX(x+2);
			} else if (protoboardOrientation == ComponentOrientation.RIGHT) {
				cathodeLeg.setProX(x+2);
				anodeLeg.setProX(x);
			} else {
				cathodeLeg.setProX(x);
				anodeLeg.setProX(x);
			}
		}
	}

	public void setProY(int y) {
		proY = y;
		if (!spanningOnProtoboard) {
			Terminal anodeLeg = terminals.get(0);
			Terminal cathodeLeg = terminals.get(1);
			if (protoboardOrientation == ComponentOrientation.UP) {
				cathodeLeg.setProY(y);
				anodeLeg.setProY(y+2);
			} else if (protoboardOrientation == ComponentOrientation.DOWN) {
				cathodeLeg.setProY(y+2);
				anodeLeg.setProY(y);
			} else {
				cathodeLeg.setProY(y);
				anodeLeg.setProY(y);
			}
		}
	}

	public void setStrX(int x) {
		strX = x;
		if (!spanningOnStripboard) {
			Terminal anodeLeg = terminals.get(0);
			Terminal cathodeLeg = terminals.get(1);
			if (stripboardOrientation == ComponentOrientation.LEFT) {
				cathodeLeg.setStrX(x);
				anodeLeg.setStrX(x+2);
			} else if (stripboardOrientation == ComponentOrientation.RIGHT) {
				cathodeLeg.setStrX(x+2);
				anodeLeg.setStrX(x);
			} else {
				cathodeLeg.setStrX(x);
				anodeLeg.setStrX(x);
			}
		}
	}

	public void setStrY(int y) {
		strY = y;
		if (!spanningOnStripboard) {
			Terminal anodeLeg = terminals.get(0);
			Terminal cathodeLeg = terminals.get(1);
			if (stripboardOrientation == ComponentOrientation.UP) {
				cathodeLeg.setStrY(y);
				anodeLeg.setStrY(y+2);
			} else if (stripboardOrientation == ComponentOrientation.DOWN) {
				cathodeLeg.setStrY(y+2);
				anodeLeg.setStrY(y);
			} else {
				cathodeLeg.setStrY(y);
				anodeLeg.setStrY(y);
			}
		}
	}

	public void setStartOnProtoboard(Coordinate startOnProtoboard) {
		this.startOnProtoboard = startOnProtoboard;
		proX = startOnProtoboard.getX();
		proY = startOnProtoboard.getY();
	}

	public void setEndOnProtoboard(Coordinate endOnProtoboard) {
		this.endOnProtoboard = endOnProtoboard;
		Terminal anodeLeg = terminals.get(0);
		Terminal cathodeLeg = terminals.get(1);
		if (protoboardOrientation == ComponentOrientation.UP || protoboardOrientation == ComponentOrientation.LEFT) {
			cathodeLeg.setProX(startOnProtoboard.getX());
			cathodeLeg.setProY(startOnProtoboard.getY());
			anodeLeg.setProX(endOnProtoboard.getX());
			anodeLeg.setProY(endOnProtoboard.getY());
		} else {
			anodeLeg.setProX(startOnProtoboard.getX());
			anodeLeg.setProY(startOnProtoboard.getY());
			cathodeLeg.setProX(endOnProtoboard.getX());
			cathodeLeg.setProY(endOnProtoboard.getY());
		}
	}

	public void setStartOnStripboard(Coordinate startOnStripboard) {
		this.startOnStripboard = startOnStripboard;
		strX = startOnStripboard.getX();
		strY = startOnStripboard.getY();
	}

	public void setEndOnStripboard(Coordinate endOnStripboard) {
		this.endOnStripboard = endOnStripboard;
		Terminal anodeLeg = terminals.get(0);
		Terminal cathodeLeg = terminals.get(1);
		if (protoboardOrientation == ComponentOrientation.UP || protoboardOrientation == ComponentOrientation.LEFT) {
			cathodeLeg.setStrX(startOnStripboard.getX());
			cathodeLeg.setStrY(startOnStripboard.getY());
			anodeLeg.setStrX(endOnStripboard.getX());
			anodeLeg.setStrY(endOnStripboard.getY());
		} else {
			anodeLeg.setStrX(startOnStripboard.getX());
			anodeLeg.setStrY(startOnStripboard.getY());
			cathodeLeg.setStrX(endOnStripboard.getX());
			cathodeLeg.setStrY(endOnStripboard.getY());
		}
	}

	@Override
	public void drawOnSchematics(GraphicsContext context) {
		int x = schX;
		int y = schY;
		context.setStroke(highlighted ? Color.PURPLE : Color.DARKBLUE);
		context.setFill(highlighted ? Color.PURPLE : Color.DARKBLUE);
		context.setGlobalAlpha(1.0);
		context.setLineWidth(1.0);
		if (schematicsOrientation == ComponentOrientation.UP) {
			context.strokeRect(x+3, y+8, 18, 3);
			context.strokeLine(x+15, y+3, x+21, y+3);
			context.strokeLine(x+18, y, x+18, y+6);
			context.fillRect(x+3, y+14, 18, 3);
			context.strokeLine(x+12, y, x+12, y+8);
			context.strokeLine(x+12, y+18, x+12, y+24);
		}
		if (schematicsOrientation == ComponentOrientation.DOWN) {
			context.fillRect(x+3, y+8, 18, 3);
			context.strokeLine(x+15, y+21, x+21, y+21);
			context.strokeLine(x+18, y+18, x+18, y+24);
			context.strokeRect(x+3, y+14, 18, 3);
			context.strokeLine(x+12, y, x+12, y+8);
			context.strokeLine(x+12, y+18, x+12, y+24);
		}
		if (schematicsOrientation == ComponentOrientation.LEFT) {
			context.strokeRect(x+8, y+3, 3, 18);
			context.strokeLine(x, y+3, x+6, y+3);
			context.strokeLine(x+3, y, x+3, y+6);
			context.fillRect(x+14, y+3, 3, 18);
			context.strokeLine(x, y+12, x+8, y+12);
			context.strokeLine(x+18, y+12, x+24, y+12);
		}
		if (schematicsOrientation == ComponentOrientation.RIGHT) {
			context.fillRect(x+8, y+3, 3, 18);
			context.strokeLine(x+18, y+3, x+24, y+3);
			context.strokeLine(x+21, y, x+21, y+6);
			context.strokeRect(x+14, y+3, 3, 18);
			context.strokeLine(x, y+12, x+8, y+12);
			context.strokeLine(x+18, y+12, x+24, y+12);
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
		if (spanning) {
			if (orientation == ComponentOrientation.UP || orientation == ComponentOrientation.DOWN) {
				int h = Math.abs(spanStart.getY() - spanEnd.getY()) * 24;
				context.setFill(Color.DARKGREY);
				context.fillRoundRect(x + 9, y + 9, 6, h + 6, 6, 6);
				context.setFill(Color.DARKBLUE);
				context.fillOval(x - 12, y + h/2 - 12, 48, 48);
				context.setFill(Color.WHITE);
				if (orientation == ComponentOrientation.DOWN)
					context.fillRect(x + 3, y+ h/2 + 24, 18, 6);
				else context.fillRect(x + 3, y + h/2 - 6, 18, 6);
			} else {
				int w = Math.abs(spanStart.getX() - spanEnd.getX()) * 24;
				context.setFill(Color.DARKGREY);
				context.fillRoundRect(x + 9, y + 9, w + 6, 6, 6, 6);
				context.setFill(Color.DARKBLUE);
				context.fillOval(x + w/2 - 12, y - 12, 48, 48);
				context.setFill(Color.WHITE);
				if (orientation == ComponentOrientation.LEFT)
					context.fillRect(x + w/2 - 6, y + 3, 6, 18);
				else context.fillRect(x + w/2 + 24, y + 3, 6, 18);
			}
		} else {
			if (orientation == ComponentOrientation.UP) {
				context.setFill(Color.DARKBLUE);
				context.fillOval(x - 12, y, 48, 48);
				context.setFill(Color.WHITE);
				context.fillRect(x + 3, y + 36, 18, 6);
			}
			if (orientation == ComponentOrientation.DOWN) {
				context.setFill(Color.DARKBLUE);
				context.fillOval(x - 12, y, 48, 48);
				context.setFill(Color.WHITE);
				context.fillRect(x + 3, y + 6, 18, 6);
			}
			if (orientation == ComponentOrientation.RIGHT) {
				context.setFill(Color.DARKBLUE);
				context.fillOval(x, y - 12, 48, 48);
				context.setFill(Color.WHITE);
				context.fillRect(x + 6, y + 3, 6, 18);
			}
			if (orientation == ComponentOrientation.LEFT) {
				context.setFill(Color.DARKBLUE);
				context.fillOval(x, y - 12, 48, 48);
				context.setFill(Color.WHITE);
				context.fillRect(x + 36, y + 3, 6, 18);
			}
		}
	}

}
