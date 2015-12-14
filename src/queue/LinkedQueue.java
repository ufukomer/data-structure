package queue;

import javafx.scene.control.TextArea;

import java.util.NoSuchElementException;
import java.util.Random;

/**
 * @author Omer Ufuk Efendioglu
 */
public class LinkedQueue {
    public Node front, rear;
    public int size;

    /* Constructor */
    public LinkedQueue() {
        front = null;
        rear = null;
        size = 0;
    }

    /*  Function to check if queue is empty */
    public boolean isEmpty() {
        return (front == null);
    }

    /*  Function to get the size of the queue */
    public int getSize() {
        return size;
    }

    /*  Function to insert an element to the queue */
    public void insert(int data) {
        Random rnd = new Random();
        Node nptr = new Node(data, null);
        if (rear == null) {
            front = nptr;
            rear = nptr;
            nptr.time = rnd.nextInt(375) + 25;
        } else {
            rear.next = nptr;
            rear = rear.next;
            nptr.time = rnd.nextInt(375) + 25;
        }
        size++;
    }

    /*  Function to get average processing time of the queue */
    public int averageProcessingTime() {
        Node ptr = front;
        int totalTime = 0;

        if (size == 0)
            return 0;
        else {
            while (ptr != rear.next) {
                //System.out.println(ptr.data+":"+ptr.time);
                totalTime = totalTime + ptr.time;
                ptr = ptr.next;
            }
            return totalTime;
        }
    }

    /*  Function to remove front element from the queue */
    public int remove(TextArea textArea) {
        if (isEmpty())
            throw new NoSuchElementException();
        else {
            Node ptr = front;
            front = ptr.next;
            if (front == null)
                rear = null;
            size--;

            display(textArea);
            System.out.println("\nProcessing Time: " + ptr.time + " sec");
            textArea.appendText("\nProcessing Time: " + ptr.time + " sec");
            return ptr.data;
        }
    }

    public void append(Node result) {
        front = result;
    }

    public Node merge_sort(Node front) {
        if (front == null || front.next == null) {
            return front;
        }
        Node middle = getMiddle(front);
        Node sHalf = middle.next;
        middle.next = null;

        Node lol = front;
        Node lol2 = sHalf;
        while (lol != null) {
            System.out.println("Front:" + lol.data);
            lol = lol.next;
        }
        System.out.println("___________________________");
        while (lol2 != null) {
            System.out.println("sHalf: " + lol2.data);
            lol2 = lol2.next;
        }
        System.out.println("___________________________");

        return merge(merge_sort(front), merge_sort(sHalf));
    }

    public Node merge(Node a, Node b) {
        Node dummyHead, curr;
        dummyHead = new Node();
        curr = dummyHead;
        while (a != null && b != null) {
            if (a.data <= b.data) {
                curr.next = a;
                a = a.next;
            } else {
                curr.next = b;
                b = b.next;
            }
            curr = curr.next;
        }
        curr.next = (a == null) ? b : a;
        Node lol = dummyHead.next;
        while (lol != null) {
            System.out.println("Dumyy: " + lol.data);
            lol = lol.next;
        }
        System.out.println("___________________________");
        return dummyHead.next;
    }

    public Node getMiddle(Node front) {
        if (front == null) {
            return front;
        }
        Node slow, fast;
        slow = fast = front;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    /*  Function to check the front element of the queue */
    public int peek() {

        if (isEmpty())
            throw new NoSuchElementException("Underflow Exception");
        return front.data;
    }

    /*  Function to display the status of the queue */
    public void display(TextArea textArea) {
        System.out.println("Queue: ");
        textArea.appendText("Queue: ");
        if (size == 0) {
            System.out.println("             Empty");
            textArea.appendText("\n             Empty");
            return;
        }
        Node ptr = front;
        while (ptr != rear.next) {
            System.out.println("             " + ptr.data + " ");
            textArea.appendText("\n            " + ptr.data + " ");
            ptr = ptr.next;
        }
        //System.out.println();
    }
}