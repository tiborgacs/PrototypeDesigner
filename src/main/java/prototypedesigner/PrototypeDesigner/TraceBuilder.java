package prototypedesigner.PrototypeDesigner;

import lombok.Getter;

import java.util.Collection;

public class TraceBuilder {

    @Getter
    private ProtoboardTrace trace;

    public TraceBuilder(ProtoboardDot start) {
        trace = new ProtoboardTrace(start);
    }

    public ProtoboardTrace addDot(ProtoboardDot dot, Collection<ProtoboardDot> between) {
        if (!trace.getDots().contains(dot)) {
            if (dot.getX() == trace.getDots().getLast().getX()) {
                int max = dot.getY() > trace.getDots().getLast().getY() ? dot.getY() : trace.getDots().getLast().getY();
                int min = dot.getY() < trace.getDots().getLast().getY() ? dot.getY() : trace.getDots().getLast().getY();
                for (ProtoboardDot d : between) {
                    if (!trace.getDots().contains(d))
                        if (d.getX() == dot.getX() && d.getY() > min && d.getY() < max)
                            trace.getDots().add(d);
                }
                if (!trace.getDots().contains(dot))
                    trace.getDots().add(dot);
            }
            if (dot.getY() == trace.getDots().getLast().getY()) {
                int max = dot.getX() > trace.getDots().getLast().getX() ? dot.getX() : trace.getDots().getLast().getX();
                int min = dot.getX() < trace.getDots().getLast().getX() ? dot.getX() : trace.getDots().getLast().getX();
                for (ProtoboardDot d : between) {
                    if (!trace.getDots().contains(d))
                        if (d.getY() == dot.getY() && d.getX() > min && d.getX() < max)
                            trace.getDots().add(d);
                }
                if (!trace.getDots().contains(dot))
                    trace.getDots().add(dot);
            }
        }
        return trace;
    }

}
