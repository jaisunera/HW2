import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.DefaultMutableTreeNode;

public class TreeListener implements TreeModelListener {
    public void treeNodesChanged(TreeModelEvent e) {
        //Retrieve the last modified node in the tree
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) e.getTreePath().getLastPathComponent();

        try {
            int index = e.getChildIndices()[0]; //Try to get index of hte child that was changed
            node = (DefaultMutableTreeNode) node.getChildAt(index); //Update the variable to point to the specific node that changed
        } catch (NullPointerException ignored) {
        }
    }

    //Invoked when nodes in the tree have changed, data to nodes have been modified
    public void treeStructureChanged(TreeModelEvent e) {
    }

    //Invoked when nodes have been added to the tree
    public void treeNodesInserted(TreeModelEvent e) {
    }

    //Invoked when nodes have been removed from the tree
    public void treeNodesRemoved(TreeModelEvent e) {
    }

}
