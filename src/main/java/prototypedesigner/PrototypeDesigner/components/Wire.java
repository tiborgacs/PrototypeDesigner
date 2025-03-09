package prototypedesigner.PrototypeDesigner.components;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import lombok.Getter;
import lombok.Setter;

@JsonInclude
public class Wire implements DrawableOnSchematics {
	
	@Getter @Setter private List<Terminal> connectedComponents = new ArrayList<>();
	private List<Wire> connectedWires = new ArrayList<>();
	@Getter @Setter private LinkedList<Coordinate> schPoints = new LinkedList<>();
	private List<Coordinate> intersections = new ArrayList<>();
	@Setter @Getter private boolean highlighted;

	public void connectToWire(Wire other) {
		this.connectedWires.add(other);
		other.connectedWires.add(this);
	}

	public void connectToWire(Wire other, Coordinate intersection) {
		this.connectedWires.add(other);
		other.connectedWires.add(this);
		intersections.add(intersection);
	}
	
	public void cutConnection(Wire other) {
		this.connectedWires.remove(other);
		other.connectedWires.remove(this);
	}

	public void drawSch(int x, int y) {
		if (schPoints.isEmpty()) schPoints.add(new Coordinate(x, y));
		else {
			Coordinate last = schPoints.getLast();
			if (Math.abs(last.getX() - x) > Math.abs(last.getY() - y)) {
				schPoints.add(new Coordinate(x, last.getY()));
			} else schPoints.add(new Coordinate(last.getX(), y));
 		}
		System.out.println(schPoints);
	}

	@Override
	public void drawOnSchematics(GraphicsContext context) {
		context.setStroke(highlighted ? Color.PURPLE : Color.DARKBLUE);
		context.setGlobalAlpha(1.0);
		context.setLineWidth(1.0);
		double[] xs = new double[schPoints.size()];
		double[] ys = new double[schPoints.size()];
		for (int i = 0; i < schPoints.size(); i++) {
			xs[i] = schPoints.get(i).getX();
			ys[i] = schPoints.get(i).getY();
		}
		context.strokePolyline(xs, ys, schPoints.size());
		for (Coordinate intersection: intersections) {
			System.out.println(intersection);
			context.strokeArc(intersection.getX()-2, intersection.getY()-2, 4, 4, 0, 360, ArcType.ROUND);
		}
	}

	public static Coordinate intersects(Coordinate a, Coordinate b, Coordinate c, Coordinate d) {
		double a1 = b.getY() - a.getY();
		double b1 = a.getX() - b.getX();
		double c1 = a1*a.getX() + b1*a.getY();
		double a2 = d.getY() - c.getY();
		double b2 = c.getX() - d.getX();
		double c2 = a2*c.getX()+ b2*c.getY();
		double determinant = a1*b2 - a2*b1;
		if (a.getX() == b.getX() && c.getY() == d.getY()) {
			if (between(c.getY(), a.getY(), b.getY()) && between(a.getX(), c.getX(), d.getX()))
				return new Coordinate(
						(int) ((b2*c1 - b1*c2)/determinant),
						(int) ((a1*c2 - a2*c1)/determinant));
		}
		if (a.getY() == b.getY() && c.getX() == d.getX()) {
			if (between(c.getX(), a.getX(), b.getX()) && between(a.getY(), c.getY(), d.getY()))
				return new Coordinate(
						(int) ((b2*c1 - b1*c2)/determinant),
						(int) ((a1*c2 - a2*c1)/determinant));
		}
		return null;
	}

	private static boolean between(int value, int start, int end) {
		if (start >= end) return value <= start && value >= end;
		else if (start <= end) return value >= start && value <= end;
		else return false;
	}

}
