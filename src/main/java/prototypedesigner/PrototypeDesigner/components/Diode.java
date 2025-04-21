package prototypedesigner.PrototypeDesigner.components;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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

	private boolean spanningOnStripboard;
	private boolean spanningOnProtoboard;
	private Coordinate startOnStripboard;
	private Coordinate startOnProtoboard;
	private Coordinate endOnStripboard;
	private Coordinate endOnProtoboard;

    public static void setCounter(int i) {
		idCounter = i;
    }

    @Override
	public void setSchX(int x) {
		super.setSchX(x);
		Terminal anodeLeg = terminals.get(0).getIdentifier().endsWith("A") ? terminals.get(0) : terminals.get(1);
		Terminal cathodeLeg = terminals.get(0).getIdentifier().endsWith("C") ? terminals.get(0) : terminals.get(1);
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

	public void setProX(int x) {
		proX = x;
		if (!spanningOnProtoboard) {
			Terminal anodeLeg = terminals.get(0).getIdentifier().endsWith("A") ? terminals.get(0) : terminals.get(1);
			Terminal cathodeLeg = terminals.get(0).getIdentifier().endsWith("C") ? terminals.get(0) : terminals.get(1);
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
			Terminal anodeLeg = terminals.get(0).getIdentifier().endsWith("A") ? terminals.get(0) : terminals.get(1);
			Terminal cathodeLeg = terminals.get(0).getIdentifier().endsWith("C") ? terminals.get(0) : terminals.get(1);
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
			Terminal anodeLeg = terminals.get(0).getIdentifier().endsWith("A") ? terminals.get(0) : terminals.get(1);
			Terminal cathodeLeg = terminals.get(0).getIdentifier().endsWith("C") ? terminals.get(0) : terminals.get(1);
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
			Terminal anodeLeg = terminals.get(0).getIdentifier().endsWith("A") ? terminals.get(0) : terminals.get(1);
			Terminal cathodeLeg = terminals.get(0).getIdentifier().endsWith("C") ? terminals.get(0) : terminals.get(1);
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
		Terminal anodeLeg = terminals.get(0).getIdentifier().endsWith("A") ? terminals.get(0) : terminals.get(1);
		Terminal cathodeLeg = terminals.get(0).getIdentifier().endsWith("C") ? terminals.get(0) : terminals.get(1);
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
		Terminal anodeLeg = terminals.get(0).getIdentifier().endsWith("A") ? terminals.get(0) : terminals.get(1);
		Terminal cathodeLeg = terminals.get(0).getIdentifier().endsWith("C") ? terminals.get(0) : terminals.get(1);
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
	public void setSchY(int y) {
		super.setSchY(y);
		Terminal anodeLeg = terminals.get(0).getIdentifier().endsWith("A") ? terminals.get(0) : terminals.get(1);
		Terminal cathodeLeg = terminals.get(0).getIdentifier().endsWith("C") ? terminals.get(0) : terminals.get(1);
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
		context.strokeText(identifier, x + 16, y + 4);
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
			context.setFill(Color.BLACK);
			context.setStroke(Color.LIGHTGRAY); // white cathode strip, gray label
			if (orientation == ComponentOrientation.UP || orientation == ComponentOrientation.DOWN) {
				int h = Math.abs(spanStart.getY() - spanEnd.getY()) * 24;
				if (h <= 24) {
					context.fillRect(x + 3, y + 6, 18, 36);
					context.setFill(Color.WHITE);
					if (orientation == ComponentOrientation.UP)
						context.fillRect(x + 3, y + 12, 18, 4);
					else context.fillRect(x + 3, y + 32, 18, 4);
					context.strokeText(
							identifier.length() == 2 ? identifier : identifier.replace("D", ""),
							x + 4, y + 30);
				} else if (h <= 48) {
					if (orientation == ComponentOrientation.UP) {
						context.setFill(Color.BLACK);
						context.fillRect(x + 3, y + 16, 18, 8);
						context.setFill(Color.WHITE);
						context.fillRect(x + 3, y + 24, 18, 8);
						context.setFill(Color.BLACK);
						context.fillRect(x + 3, y + 32, 18, 24);
					}
					if (orientation == ComponentOrientation.DOWN) {
						context.setFill(Color.BLACK);
						context.fillRect(x + 3, y + 16, 18, 24);
						context.setFill(Color.WHITE);
						context.fillRect(x + 3, y + 40, 18, 8);
						context.setFill(Color.BLACK);
						context.fillRect(x + 3, y + 48, 18, 8);
					}
					context.strokeText(identifier.replace("D", "D\n"), x + 4, y + 30);
				} else {
					context.fillRect(x + 3, y + 12 + h / 2 - 32, 18, 64);
					context.setFill(Color.WHITE);
					if (orientation == ComponentOrientation.UP)
						context.fillRect(x + 3, y + 16 + h / 2 - 32, 18, 4);
					else context.fillRect(x + 3, y + 64 + h / 2 - 32, 18, 4);
					context.setFill(Color.DARKGREY);
					context.fillRoundRect(x + 9, y + 12, 6, h / 2 - 32, 6, 6);
					context.fillRoundRect(x + 9, y + 12 + h / 2 + 32, 6, h / 2 - 32, 6, 6);
					context.strokeText(identifier.replace("D", "D\n"), x + 4, y + h / 2);
				}
			} else {
				int w = Math.abs(spanStart.getX() - spanEnd.getX()) * 24;
				if (w <= 24) {
					context.fillRect(x + 6, y + 3, 36, 18);
					context.setFill(Color.WHITE);
					if (orientation == ComponentOrientation.LEFT)
						context.fillRect(x + 12, y + 3, 4, 18);
					else context.fillRect(x + 32, y + 3, 4, 18);
					context.strokeText(
							identifier.length() == 2 ? identifier : identifier.replace("D", ""),
							x + 16, y + 18);
				} else if (w <= 48) {
					if (orientation == ComponentOrientation.RIGHT) {
						context.setFill(Color.BLACK);
						context.fillRect(x + 16, y + 3, 24, 18);
						context.setFill(Color.WHITE);
						context.fillRect(x + 40, y + 3, 8, 18);
						context.setFill(Color.BLACK);
						context.fillRect(x + 48, y + 3, 8, 18);
					}
					if (orientation == ComponentOrientation.LEFT) {
						context.setFill(Color.BLACK);
						context.fillRect(x + 16, y + 3, 8, 18);
						context.setFill(Color.WHITE);
						context.fillRect(x + 24, y + 3, 8, 18);
						context.setFill(Color.BLACK);
						context.fillRect(x + 32, y + 3, 24, 18);
					}
					context.strokeText(identifier, x + 16, y + 18);
				} else {
					context.fillRect(x + 12 + w / 2 - 32, y + 3, 64, 18);
					context.setFill(Color.WHITE);
					if (orientation == ComponentOrientation.LEFT)
						context.fillRect(x + 16 + w / 2 - 32, y + 3, 4, 18);
					else context.fillRect(x  + 64 + w / 2 - 32, y + 3, 4, 18);
					context.setFill(Color.DARKGREY);
					context.fillRoundRect(x + 12, y + 9, w / 2 - 32, 6, 6, 6);
					context.fillRoundRect(x  + 12 + w / 2 + 32, y + 9,w / 2 - 32, 6, 6, 6);
					context.strokeText(identifier, x + w / 2, y + 18);
				}
			}
		} else {
			if (orientation == ComponentOrientation.UP) {
				context.setFill(Color.BLACK);
				context.fillRect(x + 3, y + 16, 18, 8);
				context.setFill(Color.WHITE);
				context.fillRect(x + 3, y + 24, 18, 8);
				context.setFill(Color.BLACK);
				context.fillRect(x + 3, y + 32, 18, 24);
			}
			if (orientation == ComponentOrientation.DOWN) {
				context.setFill(Color.BLACK);
				context.fillRect(x + 3, y + 16, 18, 24);
				context.setFill(Color.WHITE);
				context.fillRect(x + 3, y + 40, 18, 8);
				context.setFill(Color.BLACK);
				context.fillRect(x + 3, y + 48, 18, 8);
			}
			if (orientation == ComponentOrientation.RIGHT) {
				context.setFill(Color.BLACK);
				context.fillRect(x + 16, y + 3, 24, 18);
				context.setFill(Color.WHITE);
				context.fillRect(x + 40, y + 3, 8, 18);
				context.setFill(Color.BLACK);
				context.fillRect(x + 48, y + 3, 8, 18);
			}
			if (orientation == ComponentOrientation.LEFT) {
				context.setFill(Color.BLACK);
				context.fillRect(x + 16, y + 3, 8, 18);
				context.setFill(Color.WHITE);
				context.fillRect(x + 24, y + 3, 8, 18);
				context.setFill(Color.BLACK);
				context.fillRect(x + 32, y + 3, 24, 18);
			}
		}
	}
	
}
