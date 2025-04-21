package prototypedesigner.PrototypeDesigner;

import lombok.Getter;

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
