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
		@JsonSubTypes.Type(name="diode", value=Diode.class),
		@JsonSubTypes.Type(name="bipolar-junction-transistor", value=BipolarJunctionTransistor.class),
		@JsonSubTypes.Type(name="junction-fet-transistor", value=JunctionFieldEffectTransistor.class),
		@JsonSubTypes.Type(name="metal-oxide-fet-transistor", value=MetalOxideSemiconductorFET.class),
		@JsonSubTypes.Type(name="single-op-amp", value=SingleOpAmp.class),
		@JsonSubTypes.Type(name="dual-op-amp", value=DualOpAmp.class),
		@JsonSubTypes.Type(name="quad-op-amp", value=QuadOpAmp.class),
})
public abstract class Component implements DrawableOnSchematics {

	@Getter @Setter	protected String identifier;
	@Getter @Setter protected String type;
	@Getter @Setter protected String pinout;
	@Getter @Setter protected String value;
	@Getter @Setter protected ComponentOrientation schematicsOrientation;
	@Getter @Setter protected int schX;
	@Getter @Setter protected int schY;
	@Getter @Setter protected ComponentOrientation stripboardOrientation;
	@Getter protected int strX;
	@Getter protected int strY;
	@Getter @Setter protected ComponentOrientation protoboardOrientation;
	@Getter protected int proX;
	@Getter protected int proY;
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
