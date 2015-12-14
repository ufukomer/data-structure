package queue;

/**
 * @author Omer Ufuk Efendioglu
 */
public class Node {
    protected int data;
    protected Node next;
    public int time;

    /*  Constructor  */
    public Node() {
        next = null;
        data = 0;
    }

    /*  Constructor  */
    public Node(int d, Node n) {
        data = d;
        next = n;
    }
}
