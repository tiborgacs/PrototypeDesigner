package prototypedesigner.PrototypeDesigner;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import prototypedesigner.PrototypeDesigner.components.Coordinate;
import prototypedesigner.PrototypeDesigner.components.DrawableOnProtoboard;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude
//@JsonIdentityInfo(property = "identifier", generator = ObjectIdGenerators.PropertyGenerator.class)
public class ProtoboardDot implements DrawableOnProtoboard {
	
	private int x;
	private int y;
	//private String identifier;

	public ProtoboardDot(int x, int y) {
		this.x = x;
		this.y = y;
		//identifier = x + ":" + y;
	}

	public boolean isNeighborTop(ProtoboardDot other) { return other.x == x && other.y == y - 1; }
	public boolean isNeighborBottom(ProtoboardDot other) { return other.x == x && other.y == y + 1; }
	public boolean isNeighborLeft(ProtoboardDot other) { return other.x == x - 1 && other.y == y; }
	public boolean isNeighborRight(ProtoboardDot other) { return other.x == x + 1 && other.y == y; }

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
