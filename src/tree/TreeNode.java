package tree;

/**
 * @author Omer Ufuk Efendioglu
 */
public class TreeNode {

    public int data;
    public TreeNode leftChild;
    public TreeNode rightChild;

    @Override
    public String toString() {
        return  "{ " + data + " }";
    }
}