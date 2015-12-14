package tree;

import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.Stack;

/**
 * @author Omer Ufuk Efendioglu
 */
public class Tree {

    public TreeNode root;

    public Tree() {
        root = null;
    }

    public void drawLine(AnchorPane anchorPane, int startX, int startY,
                         int endX, int endY) {
        Line line = new Line();
        line.setStartX(startX);
        line.setStartY(startY);
        line.setEndX(endX);
        line.setEndY(endY);
        line.setStrokeWidth(2.0);
        line.setSmooth(true);
        anchorPane.getChildren().add(line);
    }

    public void drawNode(AnchorPane anchorPane, int x, int y, int data) {

        Text text = null;
        if (String.valueOf(data).length() == 2)
            text = new Text(x - 8, y + 5, String.valueOf(data));

        else if (String.valueOf(data).length() == 1)
            text = new Text(x - 5, y + 5, String.valueOf(data));

        else if (String.valueOf(data).length() == 3)
            text = new Text(x - 11, y + 5, String.valueOf(data));

        text.setFont(new Font(15));
        text.setFill(Color.rgb(255, 255, 255, 1.0));

        Circle circle = new Circle(x, y, 20);
        circle.getStyleClass().add("circle");
        circle.setFill(Color.rgb(255, 0, 0, 0.5));

        circle.setSmooth(true);
        anchorPane.getChildren().addAll(circle, text);
    }

    public void insert(AnchorPane anchorPane, int data) {
        //Provides to add node to middle of the root
        int x = (int) (anchorPane.getWidth() / 2);

        //Y distance between parent and child
        int y = 50;

        //Start points for line
        int startX;
        int startY;

        //End points for line
        int endX;
        int endY;

        //X distance between parent and child
        int z = 400;

        TreeNode newNode = new TreeNode();
        newNode.data = data;
        if (root == null) {
            root = newNode;
            drawNode(anchorPane, x, y, data);
        } else {
            TreeNode current = root;
            TreeNode parent;

            while (true) {
                parent = current;
                if (data < current.data) {
                    current = current.leftChild;
                    startX = x;
                    startY = y;
                    x = x - z;
                    y = y + 100;
                    z = (int) (z / 1.65);
                    endX = x;
                    endY = y;

                    if (current == null) {
                        parent.leftChild = newNode;
                        drawLine(anchorPane, startX, startY + 20, endX, endY - 19);
                        drawNode(anchorPane, x, y, data);
                        return;
                    }
                } else {
                    current = current.rightChild;
                    startX = x;
                    startY = y;
                    x = x + z;
                    y = y + 100;
                    z = z / 2;
                    endX = x;
                    endY = y;

                    if (current == null) {
                        parent.rightChild = newNode;
                        drawLine(anchorPane, startX, startY + 20, endX, endY - 19);
                        drawNode(anchorPane, x, y, data);
                        return;
                    }
                }
            }
        }
    }

    public void deleteAll(TreeNode localRoot) {
        if (localRoot != null) {
            deleteAll(localRoot.leftChild);
            delete(localRoot.data);
            deleteAll(localRoot.rightChild);
        }
    }

    public void inOrder(TreeNode localRoot, TextArea textArea) {
        if (localRoot != null) {
            inOrder(localRoot.leftChild, textArea);
            System.out.print(localRoot.data + "      ");
            textArea.appendText("      " + localRoot.data);
            inOrder(localRoot.rightChild, textArea);
        }
    }

    public void preOrder(TreeNode localRoot, TextArea textArea) {
        if (localRoot != null) {
            System.out.print(localRoot.data + "      ");
            textArea.appendText("      " + localRoot.data);
            preOrder(localRoot.leftChild, textArea);
            preOrder(localRoot.rightChild, textArea);
        }
    }

    public void postOrder(TreeNode localRoot, TextArea textArea) {
        if (localRoot != null) {
            postOrder(localRoot.leftChild, textArea);
            postOrder(localRoot.rightChild, textArea);
            System.out.print(localRoot.data + " ");
            textArea.appendText("      " + localRoot.data);
        }
    }

    public void levelOrder(TextArea textArea) {
        Stack<TreeNode> globalStack = new Stack<TreeNode>();
        globalStack.push(root);
        boolean isRowEmpty = false;
        while (!isRowEmpty) {
            Stack<TreeNode> localStack = new Stack<TreeNode>();
            isRowEmpty = true;
            while (!globalStack.isEmpty()) {
                TreeNode temp = (TreeNode) globalStack.pop();
                if (temp != null) {
                    System.out.print(temp.data);
                    textArea.appendText("      " + temp.data);
                    localStack.push(temp.leftChild);
                    localStack.push(temp.rightChild);
                    if (temp.leftChild != null || temp.rightChild != null)
                        isRowEmpty = false;
                } else {
                    localStack.push(null);
                    localStack.push(null);
                }
            }
            System.out.println();
            while (!localStack.isEmpty())
                globalStack.push(localStack.pop());
        }
    }

    public boolean delete(int key) {
        TreeNode current = root;
        TreeNode parent = root;
        boolean isLeftChild = true;
        while (current.data != key) {

            parent = current;
            if (key < current.data) {
                isLeftChild = true;
                current = current.leftChild;
            } else {
                isLeftChild = false;
                current = current.rightChild;
            }
            if (current == null)
                return false;
        }
        if (current.leftChild == null && current.rightChild == null) {
            if (current == root)
                root = null;

            else if (isLeftChild)
                parent.leftChild = null;
            else
                parent.rightChild = null;
        } else if (current.rightChild == null)
            if (current == root)
                root = current.leftChild;

            else if (isLeftChild)
                parent.leftChild = current.leftChild;

            else
                parent.rightChild = current.leftChild;

        else if (current.leftChild == null)
            if (current == root)
                root = current.rightChild;

            else if (isLeftChild)
                parent.leftChild = current.rightChild;

            else
                parent.rightChild = current.rightChild;

        else {
            TreeNode successor = getSuccessor(current);
            if (current == root)
                root = successor;
            else if (isLeftChild)
                parent.leftChild = successor;
            else
                parent.rightChild = successor;

            successor.leftChild = current.leftChild;
        }
        return true;
    }

    private TreeNode getSuccessor(TreeNode delNode) {
        TreeNode successorParent = delNode;
        TreeNode successor = delNode;
        TreeNode current = delNode.rightChild;

        while (current != null) {
            successorParent = successor;
            successor = current;
            current = current.leftChild;
        }

        if (successor != delNode.rightChild) {
            successorParent.leftChild = successor.rightChild;
            successor.rightChild = delNode.rightChild;
        }
        return successor;
    }
}