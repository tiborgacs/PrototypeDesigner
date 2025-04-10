package prototypedesigner.PrototypeDesigner.components;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Capacitor extends Component implements DrawableOnStripboard, DrawableOnProtoboard, Spanning {

    protected boolean spanningOnStripboard;
    protected boolean spanningOnProtoboard;
    protected Coordinate startOnStripboard;
    protected Coordinate startOnProtoboard;
    protected Coordinate endOnStripboard;
    protected Coordinate endOnProtoboard;

    private static int idCounter = 0;
    {
        identifier = "C" + ++idCounter;
    }

    public static void setCounter(int i) {
        idCounter = i;
    }

}
