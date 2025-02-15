package prototypedesigner.PrototypeDesigner;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import prototypedesigner.PrototypeDesigner.components.DrawableOnStripboard;

public class StripboardLink implements DrawableOnStripboard {

    private int x;
    private int y;
    private int span; // 0 is neighboring

    public StripboardLink(int x, int y, int span) {
        this.x = x;
        this.y = y;
        this.span = span;
    }

    @Override
    public void drawOnStripboard(GraphicsContext context) {
        context.setFill(Color.BLACK);
        context.fillRoundRect(x*24+9, y*24+9, 6, span*24+6, 6, 6);
    }
}
