package prototypedesigner.PrototypeDesigner;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableRow;

import java.util.List;

public class Utility {

    public static <T> T tail(List<T> list) {
        return list.get(list.size() - 1);
    }

    public static <T> TreeItem<T> getRowParentItem(TreeTableRow<T> row) {
        return row.getTreeItem().getParent();
    }

}
