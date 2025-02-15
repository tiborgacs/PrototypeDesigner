package prototypedesigner.PrototypeDesigner.components;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;

public class Wire implements DrawableOnSchematics {
	
	private List<Terminal> connectedComponents = new ArrayList<>();
	private List<Wire> connectedWires = new ArrayList<>();
	private LinkedList<Coordinate> schPoints = new LinkedList<>();
	private List<Coordinate> intersections = new ArrayList<>();
	private boolean highlighted;
	
	public List<Terminal> getConnectedComponents() {
		return connectedComponents;
	}
	
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

	public List<Coordinate> getSchPoints() {
		return schPoints;
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

	public static boolean intersects(Coordinate a, Coordinate b, Coordinate c, Coordinate d) {
		double a1 = b.getY() - a.getY();
		double b1 = a.getX() - b.getX();
		double a2 = d.getY() - c.getY();
		double b2 = c.getX() - d.getX();

		double determinant = a1*b2 - a2*b1;
		System.out.println(determinant);
		return determinant != 0;
	}

	public static Coordinate intersects2(Coordinate a, Coordinate b, Coordinate c, Coordinate d) {
		double a1 = b.getY() - a.getY();
		double b1 = a.getX() - b.getX();
		double c1 = a1*a.getX() + b1*a.getY();
		double a2 = d.getY() - c.getY();
		double b2 = c.getX() - d.getX();
		double c2 = a2*c.getX()+ b2*c.getY();
		double determinant = a1*b2 - a2*b1;
		if (determinant != 0) {
			if (a.getX() == b.getX() && (c.getY() > Math.max(a.getY(), b.getY()) || c.getY() < Math.min(a.getY(), b.getY()))) {
				return null;
			}
			if (a.getY() == b.getY() && (c.getX() > Math.max(a.getX(), b.getX()) || c.getX() < Math.min(a.getX(), b.getX()))) {
				return null;
			}
			return new Coordinate(
					(int) ((b2*c1 - b1*c2)/determinant),
					(int) ((a1*c2 - a2*c1)/determinant)
			);
		} else return null;
	}

    public boolean isHighlighted() {
        return highlighted;
    }

	@Override
	public void setHighlighted(boolean highlighted) {
        this.highlighted = highlighted;
    }
}
