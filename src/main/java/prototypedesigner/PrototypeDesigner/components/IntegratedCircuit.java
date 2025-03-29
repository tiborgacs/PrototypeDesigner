package prototypedesigner.PrototypeDesigner.components;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;

public abstract class IntegratedCircuit extends Component {
	
	private static int idCounter = 0;
	{
		identifier = "IC" + ++idCounter;
	}

    public static void setCounter(int i) {
		idCounter = i;
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
		// FIXME x+9 sucks
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
