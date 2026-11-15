package datastructure;
public class Node {
    public Page data;
    public Node prev; // older page
    public Node next; // newer page

    // constructor
    public Node(Page data) {
        this.data = data;
        this.prev = null;
        this.next = null;
    }
}