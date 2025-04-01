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
import prototypedesigner.PrototypeDesigner.components.DrawableOnProtoboard;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude
@JsonIdentityInfo(property = "identifier", generator = ObjectIdGenerators.PropertyGenerator.class)
public class ProtoboardDot implements DrawableOnProtoboard {
	
	private int x;
	private int y;
	private String identifier;
	private Set<ProtoboardDot> linkedWith = new HashSet<>();

	public ProtoboardDot(int x, int y) {
		this.x = x;
		this.y = y;
		identifier = x + ":" + y;
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
		for (ProtoboardDot linked: linkedWith) {
			context.setFill(Color.SILVER);
			if (isNeighborTop(linked)) context.fillRect(x*24+9, linked.y*24+15, 6, 18);
			if (isNeighborBottom(linked)) context.fillRect(x*24+9, y*24+15, 6, 18);
			if (isNeighborLeft(linked)) context.fillRect(linked.x*24+15, y*24+9, 18, 6);
			if (isNeighborRight(linked)) context.fillRect(x*24+15, y*24+9, 18, 6);
		}
	}

}
