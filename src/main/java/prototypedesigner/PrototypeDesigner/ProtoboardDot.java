package prototypedesigner.PrototypeDesigner;

import com.fasterxml.jackson.annotation.JsonInclude;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import prototypedesigner.PrototypeDesigner.components.DrawableOnProtoboard;

/**
 * Models a hole on the prototype board with copper around it.
 */
@Getter
@Setter
@NoArgsConstructor
@JsonInclude
public class ProtoboardDot implements DrawableOnProtoboard {
	
	private int x;
	private int y;

	public ProtoboardDot(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/** Returns if the other dot is directly above this */
	public boolean isNeighborTop(ProtoboardDot other) { return other.x == x && other.y == y - 1; }
	/** Returns if the other dot is directly below this */
	public boolean isNeighborBottom(ProtoboardDot other) { return other.x == x && other.y == y + 1; }
	/** Returns if the other dot is directly left of this */
	public boolean isNeighborLeft(ProtoboardDot other) { return other.x == x - 1 && other.y == y; }
	/** Returns if the other dot is directly right of this */
	public boolean isNeighborRight(ProtoboardDot other) { return other.x == x + 1 && other.y == y; }

	/** Returns if the other dot is next to this horizontally or vertically */
	public boolean isNeighbor(ProtoboardDot other) {
		return isNeighborBottom(other) || isNeighborTop(other) || isNeighborRight(other) || isNeighborLeft(other);
	}

	@Override
	public void drawOnProtoboard(@SuppressWarnings("exports") GraphicsContext context) {
		context.setFill(Color.SILVER);
		context.fillOval(x*24+3, y*24+3, 18, 18);
		context.setFill(Color.WHITE);
		context.fillOval(x*24+9, y*24+9, 6, 6);
	}

	public boolean equals(Object object) {
		if (object == null) return false;
		if (object instanceof ProtoboardDot) {
			ProtoboardDot other = (ProtoboardDot) object;
			return x == other.x && y == other.y;
		} else return false;
	}

}
