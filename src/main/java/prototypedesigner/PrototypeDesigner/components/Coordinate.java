package prototypedesigner.PrototypeDesigner.components;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonInclude
@NoArgsConstructor
@Getter
@Setter
public class Coordinate {

	private int x;
	private int y;

	public Coordinate(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public boolean equals(Object object) {
		if (object == null) return false;
		if (object instanceof Coordinate) {
			Coordinate other = (Coordinate) object;
			return x == other.x && y == other.y;
		} else return false;
	}
}
