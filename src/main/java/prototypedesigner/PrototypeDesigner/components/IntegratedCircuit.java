package prototypedesigner.PrototypeDesigner.components;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;

public abstract class IntegratedCircuit extends Component {
	
	private static int idCounter = 0;
	{
		identifier = "IC" + ++idCounter;
	}

	/**
	 * Sets the counter with a starting value for generating identifiers.
	 * @param i starting value (exclusive)
	 */
    public static void setCounter(int i) {
		idCounter = i;
    }

	public void setStrX(int x) {
		strX = x;
		String[] identifiers = pinout.split("_");
		if (stripboardOrientation == ComponentOrientation.UP) {
			for (int i = 0; i < identifiers.length; i++) {
				for (Terminal t: terminals) {
					if (t.getIdentifier().endsWith("_" + identifiers[i])) {
						if (i < identifiers.length / 2) t.setStrX(x);
						else t.setStrX(x + 3);
					}
				}
			}
		}
		if (stripboardOrientation == ComponentOrientation.DOWN) {
			for (int i = 0; i < identifiers.length; i++) {
				for (Terminal t: terminals) {
					if (t.getIdentifier().endsWith("_" + identifiers[i])) {
						if (i < identifiers.length / 2) t.setStrX(x + 3);
						else t.setStrX(x);
					}
				}
			}
		}
		if (stripboardOrientation == ComponentOrientation.LEFT) {
			for (int i = 0; i < identifiers.length; i++) {
				for (Terminal t : terminals) {
					if (t.getIdentifier().endsWith("_" + identifiers[i])) {
						if (i < identifiers.length / 2) t.setStrX(x + i);
						else t.setStrX(x + (terminals.size() - 1 - i));
					}
				}
			}
		}
		if (stripboardOrientation == ComponentOrientation.RIGHT) {
			for (int i = 0; i < identifiers.length; i++) {
				for (Terminal t : terminals) {
					if (t.getIdentifier().endsWith("_" + identifiers[i])) {
						if (i < identifiers.length / 2) t.setStrX(x + (terminals.size() / 2 - 1 - i));
						else t.setStrX(x + i - terminals.size() / 2);
					}
				}
			}
		}
	}

	public void setStrY(int y) {
		strY = y;
		String[] identifiers = pinout.split("_");
		if (stripboardOrientation == ComponentOrientation.UP) {
			for (int i = 0; i < identifiers.length; i++) {
				for (Terminal t : terminals) {
					if (t.getIdentifier().endsWith("_" + identifiers[i])) {
						if (i < identifiers.length / 2) t.setStrY(y + i);
						else t.setStrY(y + (terminals.size() - 1 - i));
					}
				}
			}
		}
		if (stripboardOrientation == ComponentOrientation.DOWN) {
			for (int i = 0; i < identifiers.length; i++) {
				for (Terminal t : terminals) {
					if (t.getIdentifier().endsWith("_" + identifiers[i])) {
						if (i < identifiers.length / 2) t.setStrY(y + (terminals.size() / 2 - 1 - i));
						else t.setStrY(y + i - terminals.size() / 2);
					}
				}
			}
		}
		if (stripboardOrientation == ComponentOrientation.LEFT) {
			for (int i = 0; i < identifiers.length; i++) {
				for (Terminal t : terminals) {
					if (t.getIdentifier().endsWith("_" + identifiers[i])) {
						if (i < identifiers.length / 2) t.setStrY(y + 3);
						else t.setStrY(y);
					}
				}
			}
		}
		if (stripboardOrientation == ComponentOrientation.RIGHT) {
			for (int i = 0; i < identifiers.length; i++) {
				for (Terminal t : terminals) {
					if (t.getIdentifier().endsWith("_" + identifiers[i])) {
						if (i < identifiers.length / 2) t.setStrY(y);
						else t.setStrY(y + 3);
					}
				}
			}
		}
	}

	public void setProX(int x) {
		proX = x;
		String[] identifiers = pinout.split("_");
		if (protoboardOrientation == ComponentOrientation.UP) {
			for (int i = 0; i < identifiers.length; i++) {
				for (Terminal t: terminals) {
					if (t.getIdentifier().endsWith("_" + identifiers[i])) {
						if (i < identifiers.length / 2) t.setProX(x);
						else t.setProX(x + 3);
					}
				}
			}
		}
		if (protoboardOrientation == ComponentOrientation.DOWN) {
			for (int i = 0; i < identifiers.length; i++) {
				for (Terminal t: terminals) {
					if (t.getIdentifier().endsWith("_" + identifiers[i])) {
						if (i < identifiers.length / 2) t.setProX(x + 3);
						else t.setProX(x);
					}
				}
			}
		}
		if (protoboardOrientation == ComponentOrientation.LEFT) {
			for (int i = 0; i < identifiers.length; i++) {
				for (Terminal t : terminals) {
					if (t.getIdentifier().endsWith("_" + identifiers[i])) {
						if (i < identifiers.length / 2) t.setProX(x + i);
						else t.setProX(x + (terminals.size() - 1 - i));
					}
				}
			}
		}
		if (protoboardOrientation == ComponentOrientation.RIGHT) {
			for (int i = 0; i < identifiers.length; i++) {
				for (Terminal t : terminals) {
					if (t.getIdentifier().endsWith("_" + identifiers[i])) {
						if (i < identifiers.length / 2) t.setProX(x + (terminals.size() / 2 - 1 - i));
						else t.setProX(x + i - terminals.size() / 2);
					}
				}
			}
		}
	}

	public void setProY(int y) {
		proY = y;
		String[] identifiers = pinout.split("_");
		if (protoboardOrientation == ComponentOrientation.UP) {
			for (int i = 0; i < identifiers.length; i++) {
				for (Terminal t : terminals) {
					if (t.getIdentifier().endsWith("_" + identifiers[i])) {
						if (i < identifiers.length / 2) t.setProY(y + i);
						else t.setProY(y + (terminals.size() - 1 - i));
					}
				}
			}
		}
		if (protoboardOrientation == ComponentOrientation.DOWN) {
			for (int i = 0; i < identifiers.length; i++) {
				for (Terminal t : terminals) {
					if (t.getIdentifier().endsWith("_" + identifiers[i])) {
						if (i < identifiers.length / 2) t.setProY(y + (terminals.size() / 2 - 1 - i));
						else t.setProY(y + i - terminals.size() / 2);
					}
				}
			}
		}
		if (protoboardOrientation == ComponentOrientation.LEFT) {
			for (int i = 0; i < identifiers.length; i++) {
				for (Terminal t : terminals) {
					if (t.getIdentifier().endsWith("_" + identifiers[i])) {
						if (i < identifiers.length / 2) t.setProY(y + 3);
						else t.setProY(y);
					}
				}
			}
		}
		if (protoboardOrientation == ComponentOrientation.RIGHT) {
			for (int i = 0; i < identifiers.length; i++) {
				for (Terminal t : terminals) {
					if (t.getIdentifier().endsWith("_" + identifiers[i])) {
						if (i < identifiers.length / 2) t.setProY(y);
						else t.setProY(y + 3);
					}
				}
			}
		}
	}

    // TODO: generic drawing?
	@Override
	public void drawOnSchematics(GraphicsContext context) {
		int x = schX;
		int y = schY;
		context.setStroke(Color.DARKBLUE);
		context.setGlobalAlpha(1.0);
		context.setLineWidth(1.0);
		context.strokeRect(x+15, y+3, 24*4-30, 12*terminals.size()-6);
		context.strokeLine(x+9, y+12, x+15, y+12);
		context.strokeLine(x+81, y+12, x+88, y+12);
		context.strokeLine(x+9, y+36, x+20, y+36);
		context.strokeLine(x+48, y+36, x+88, y+36);
		context.strokeLine(x+48, y+36, x+46, y+36);
		context.strokeLine(x+9, y+60, x+20, y+60);
		context.strokeLine(x+9, y+84, x+45, y+84);
		context.strokeLine(x+46, y+84, x+46, y+84);
		context.strokeLine(x+63, y+48, x+67, y+48);
		context.strokeLine(x+68, y+48, x+68, y+60);
		context.strokeLine(x+68, y+60, x+88, y+60);
		context.strokeLine(x+81, y+84, x+88, y+84);
		context.strokeArc(x+36, y-9, 24.0, 24.0, 180, 180, ArcType.CHORD);
	}
}
