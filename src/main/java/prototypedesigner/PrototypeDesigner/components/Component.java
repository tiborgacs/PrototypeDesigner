package prototypedesigner.PrototypeDesigner.components;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@JsonIdentityInfo(property = "identifier", generator = ObjectIdGenerators.PropertyGenerator.class)
@JsonTypeInfo(
		use = JsonTypeInfo.Id.NAME,
		include = JsonTypeInfo.As.PROPERTY,
		property = "type")
@JsonSubTypes({
		@JsonSubTypes.Type(name="resistor", value=Resistor.class),
		@JsonSubTypes.Type(name="capacitor", value=Capacitor.class),
		@JsonSubTypes.Type(name="polarized-capacitor", value=PolarizedCapacitor.class),
})
public abstract class Component implements DrawableOnSchematics {

	@Getter @Setter	protected String identifier;
	@Getter @Setter protected String type;
	@Getter @Setter protected ComponentOrientation schematicsOrientation;
	@Getter @Setter protected int schX;
	@Getter @Setter protected int schY;
	protected ComponentOrientation stripboardOrientation;
	protected int strX;
	protected int strY;
	protected ComponentOrientation protoboardOrientation;
	protected int proX;
	protected int proY;
	protected Packaging packaging;
	@Getter @Setter protected List<Terminal> terminals = new ArrayList<>();
	@Setter protected boolean highlighted;

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
