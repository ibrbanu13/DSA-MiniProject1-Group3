package datastructure;
public class Node {
    Page data;
    Node prev; // older page
    Node next; // newer page

    // constructor
    public Node(Page data) {
        this.data = data;
        this.prev = null;
        this.next = null;
    }
}