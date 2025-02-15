package prototypedesigner.PrototypeDesigner;

import java.util.List;

import prototypedesigner.PrototypeDesigner.components.Component;
import prototypedesigner.PrototypeDesigner.components.Wire;

public class CircuitDesign {

	private String projectName;
	private String author;
	private boolean isSchematics;
	private boolean isStripboard;
	private boolean isProtoboard;
	private List<Component> billOfMaterials;
	private List<Component> schematicsComponents;
	private List<Component> stripboardComponents;
	private List<Component> protoboardComponents;
	private List<Wire> connectionsOnSchematics;
	private List<Wire> connectionsOnStripboard;
	private List<Wire> connectionsOnProtoboard;
	
}
