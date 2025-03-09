package prototypedesigner.PrototypeDesigner.components;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@JsonInclude
@Getter
@Setter
@NoArgsConstructor
@JsonIdentityInfo(property = "identifier", generator = ObjectIdGenerators.PropertyGenerator.class)
public class Terminal {
	
	private int schX;
	private int schY;
	private int strX;
	private int strY;
	private int proX;
	private int proY;
	private String identifier;

	private Component component;
	private List<Wire> connectedWires = new ArrayList<>();
	
	public Terminal(Component component) {
		this.component = component;
		component.getTerminals().add(this);
	}

	public void connectToWire(Wire wire) {
		connectedWires.add(wire);
		wire.getConnectedComponents().add(this);
	}
	
	public void cutConnection(Wire wire) {
		connectedWires.remove(wire);
		wire.getConnectedComponents().remove(this);
	}

}
