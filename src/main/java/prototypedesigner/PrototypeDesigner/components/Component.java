package prototypedesigner.PrototypeDesigner.components;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@JsonIdentityInfo(property = "identifier", generator = ObjectIdGenerators.PropertyGenerator.class)
public abstract class Component implements DrawableOnSchematics {

	@Getter @Setter	protected String identifier;
	@Getter @Setter protected String type;
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
	@Getter @Setter protected List<Terminal> terminals = new ArrayList<>();
	@Setter protected boolean highlighted;
	
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

}
