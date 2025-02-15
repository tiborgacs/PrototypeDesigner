package prototypedesigner.PrototypeDesigner.components;

import java.util.ArrayList;
import java.util.List;

public abstract class Component implements DrawableOnSchematics {

	protected String identifier;
	protected String type;
	protected ComponentOrientation schematicsOrientation;
	protected int schX;
	protected int schY;
	protected ComponentOrientation stripboardOrientation;
	protected int strX;
	protected int strY;
	protected ComponentOrientation protoboardOrientation;
	protected int proX;
	protected int proY;
	protected Packaging packaging;
	protected List<Terminal> terminals = new ArrayList<>();
	protected boolean highlighted;
	
	public String getIdentifier() {
		return identifier;
	}
	
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public void setSchX(int x) {
		this.schX = x;
	}
	
	public void setSchY(int y) {
		this.schY = y;
	}
	
	public void setStrX(int x) {
		this.strX = x;
	}
	
	public void setStrY(int y) {
		this.strY = y;
	}
	
	public void setProX(int x) {
		this.proX = x;
	}
	
	public void setProY(int y) {
		this.proY = y;
	}

	public List<Terminal> getTerminals() {
		return terminals;
	}

	@Override
	public void setHighlighted(boolean highlighted) {
		this.highlighted = highlighted;
	}
}
