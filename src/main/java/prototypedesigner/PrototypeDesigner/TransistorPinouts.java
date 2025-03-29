package prototypedesigner.PrototypeDesigner;

import java.util.ArrayList;
import java.util.List;

public class TransistorPinouts {

    private static List<String> bjt = new ArrayList<>();
    static {
        bjt.add("C_B_E"); // BC, PH
        bjt.add("E_B_C"); // 2N
        bjt.add("E_C_B"); // 2SC, BD
        bjt.add("C_E_B"); // BF
    }

    private static List<String> fet = new ArrayList<>();
    static {
        fet.add("S_G_D"); // 2N MOS, 2SK30
        fet.add("D_S_G"); // 2N JFET, BF245, J201, J112, J113
        fet.add("D_G_S"); // 2SK65, 2SK117, 2SK170, BS170, BF244, BF246
        fet.add("G_S_D"); // BF247
    }

    public static List<String> getBjtPinouts() {
        return bjt;
    }

    public static List<String> getFetPinouts() {
        return fet;
    }
}
