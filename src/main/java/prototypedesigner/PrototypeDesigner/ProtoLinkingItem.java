package prototypedesigner.PrototypeDesigner;

import lombok.Getter;

/**
 * Tree item to list wiring traces and the dots they cover in the same table.
 */
@Getter
public class ProtoLinkingItem {

    private ProtoboardDot dot;
    private ProtoboardTrace trace;

    public ProtoLinkingItem() { /* root */ }

    public ProtoLinkingItem(ProtoboardDot dot) {
        this.dot = dot;
    }

    public ProtoLinkingItem(ProtoboardTrace trace) {
        this.trace = trace;
    }

}
