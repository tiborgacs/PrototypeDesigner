package prototypedesigner.PrototypeDesigner;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import prototypedesigner.PrototypeDesigner.components.Component;
import prototypedesigner.PrototypeDesigner.components.Wire;

/**
 * Represents a design by aggregating the components and wiring of schematics
 * and the corresponding prototype equivalents.
 */
@Getter
@Setter
public class CircuitDesign {

	private String projectName;
	private String author;
	private boolean isSchematics;
	private boolean isStripboard;
	private boolean isProtoboard;
	private List<Component> schematicsComponents;
	private List<Component> stripboardComponents;
	private List<Component> protoboardComponents;
	private int protoboardWidth;
	private int protoboardHeight;
	private List<Wire> connectionsOnSchematics;
	private List<StripboardTrace> connectionsOnStripboard;
	private List<StripboardLink> linksOnStripboard;
	private List<ProtoboardTrace> connectionsOnProtoboard;
	private List<ProtoboardVia> viasOnProtoboard;
	
}
