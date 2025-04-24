package prototypedesigner.PrototypeDesigner;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import prototypedesigner.PrototypeDesigner.components.DrawableOnStripboard;

/**
 * Models a vertical wire link between horizontal strips.
 */
@Getter
@Setter
@NoArgsConstructor
@JsonIdentityInfo(property = "identifier", generator = ObjectIdGenerators.PropertyGenerator.class)
public class StripboardLink implements DrawableOnStripboard {

    private int x;
    private int y;
    private int span; // 0 is neighboring
    private String identifier;

    public StripboardLink(int x, int y, int span) {
        this.x = x;
        this.y = y;
        this.span = span;
        identifier = "link:" + x + ":" + y;
    }

    @Override
    public void drawOnStripboard(GraphicsContext context) {
        context.setFill(Color.BLACK);
        context.fillRoundRect(x*24+9, y*24+9, 6, span*24+6, 6, 6);
    }
}
