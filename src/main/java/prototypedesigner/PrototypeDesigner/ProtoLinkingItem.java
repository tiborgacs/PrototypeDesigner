package prototypedesigner.PrototypeDesigner;

import lombok.Getter;

//import java.util.HashSet;
//import java.util.Set;

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

    /*
    public void populateSet() {
        ProtoboardDot _dot = dot;
        if (traceDots == null) traceDots = new HashSet<>();
        if (!traceDots.contains(_dot)) traverse(_dot);
    }

    private void traverse(ProtoboardDot _dot) {
        traceDots.add(_dot);
        for (ProtoboardDot dot: _dot.getLinkedWith()) {
            if (!traceDots.contains(dot)) traverse(dot);
        }
    }
     */

}
