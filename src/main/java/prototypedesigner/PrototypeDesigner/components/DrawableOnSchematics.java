package prototypedesigner.PrototypeDesigner.components;

import javafx.scene.canvas.GraphicsContext;

public interface DrawableOnSchematics {
	void drawOnSchematics(GraphicsContext context);

    void setHighlighted(boolean highlighted);
}
