package prototypedesigner.PrototypeDesigner;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableRow;

import java.util.List;

public class Utility {

    /**
     * Gets the last element of a list
     * @param list a list of elements
     * @return the last element
     * @param <T>
     */
    public static <T> T tail(List<T> list) {
        return list.get(list.size() - 1);
    }

    /**
     * Gets the parent TreeItem of a table row
     * @param row table row
     * @return parent element of the table row
     * @param <T>
     */
    public static <T> TreeItem<T> getRowParentItem(TreeTableRow<T> row) {
        return row.getTreeItem().getParent();
    }

}
