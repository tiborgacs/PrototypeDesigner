package prototypedesigner.PrototypeDesigner.components;

public interface Spanning {
    default boolean isSpanningOnStripboard() { return false; }
    default boolean isSpanningOnProtoboard() { return false; }
    void setSpanningOnStripboard(boolean spanning);
    void setSpanningOnProtoboard(boolean spanning);
    Coordinate getStartOnStripboard();
    Coordinate getStartOnProtoboard();
    void setStartOnStripboard(Coordinate start);
    void setStartOnProtoboard(Coordinate start);
    Coordinate getEndOnStripboard();
    Coordinate getEndOnProtoboard();
    void setEndOnStripboard(Coordinate end);
    void setEndOnProtoboard(Coordinate end);
}
