package prototypedesigner.PrototypeDesigner.components;

import javafx.scene.canvas.GraphicsContext;

public interface DrawableOnSchematics {
	public void drawOnSchematics(GraphicsContext context);

    void setHighlighted(boolean highlighted);
}
