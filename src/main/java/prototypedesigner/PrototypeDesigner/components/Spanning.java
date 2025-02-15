package prototypedesigner.PrototypeDesigner.components;

public interface Spanning {
    default boolean isSpanning() { return false; }
    void setSpanning(boolean spanning);
    Coordinate getStart();
    void setStart(Coordinate start);
    Coordinate getEnd();
    void setEnd(Coordinate end);
}
