package prototypedesigner.PrototypeDesigner.components;

import java.util.ArrayList;
import java.util.List;

public class Terminal {
	
	private int schX;
	private int schY;
	private int strX;
	private int strY;
	private int proX;
	private int proY;

	private Component component;
	private List<Wire> connectedWires = new ArrayList<>();
	
	public Terminal(Component component) {
		this.component = component;
		component.getTerminals().add(this);
	}
	
	public Component getComponent() {
		return component;
	}
	
	public void connectToWire(Wire wire) {
		connectedWires.add(wire);
		wire.getConnectedComponents().add(this);
	}
	
	public void cutConnection(Wire wire) {
		connectedWires.remove(wire);
		wire.getConnectedComponents().remove(this);
	}

	public int getSchX() {
		return schX;
	}

	public void setSchX(int schX) {
		this.schX = schX;
	}

	public int getSchY() {
		return schY;
	}

	public void setSchY(int schY) {
		this.schY = schY;
	}

	public int getStrX() {
		return strX;
	}

	public void setStrX(int strX) {
		this.strX = strX;
	}

	public int getStrY() {
		return strY;
	}

	public void setStrY(int strY) {
		this.strY = strY;
	}

	public int getProX() {
		return proX;
	}

	public void setProX(int proX) {
		this.proX = proX;
	}

	public int getProY() {
		return proY;
	}

	public void setProY(int proY) {
		this.proY = proY;
	}
	
}
