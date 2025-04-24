package prototypedesigner.PrototypeDesigner;

import lombok.Getter;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Builder class for protoboard traces
 */
public class TraceBuilder {

    @Getter
    private ProtoboardTrace trace;

    public TraceBuilder(ProtoboardDot start) {
        trace = new ProtoboardTrace(start);
    }

    /**
     * Adds a protoboard dot to the trace being build,
     * by also adding all the dots between the latest and current dot vertically or horizontally,
     * based on the coordinates of the latest and current dots.
     * @param dot currently selected dot to add
     * @param between dots to add between the current and last added
     * @return the trace that is being built
     */
    public ProtoboardTrace addDot(ProtoboardDot dot, Collection<ProtoboardDot> between) {
        if (!trace.getDots().contains(dot)) {
            ProtoboardDot last = trace.getDots().getLast();
            if (dot.getX() == last.getX()) {
                int max = dot.getY() > last.getY() ? dot.getY() : last.getY();
                int min = dot.getY() < last.getY() ? dot.getY() : last.getY();
                boolean dec = max == last.getY();
                List<ProtoboardDot> sublist = between.stream().filter(d ->
                        !trace.getDots().contains(d)
                                && d.getX() == dot.getX()
                                && d.getY() > min && d.getY() < max)
                        .collect(Collectors.toList());
                if (dec) Collections.reverse(sublist);
                trace.getDots().addAll(sublist);
                if (!trace.getDots().contains(dot))
                    trace.getDots().add(dot);
            }
            if (dot.getY() == trace.getDots().getLast().getY()) {
                int max = dot.getX() > last.getX() ? dot.getX() : last.getX();
                int min = dot.getX() < last.getX() ? dot.getX() : last.getX();
                boolean dec = max == last.getX();
                List<ProtoboardDot> sublist = between.stream().filter(d ->
                        !trace.getDots().contains(d)
                                && d.getY() == dot.getY()
                                && d.getX() > min && d.getX() < max).collect(Collectors.toList());
                if (dec) Collections.reverse(sublist);
                trace.getDots().addAll(sublist);
                if (!trace.getDots().contains(dot))
                    trace.getDots().add(dot);
            }
        }
        return trace;
    }

}
