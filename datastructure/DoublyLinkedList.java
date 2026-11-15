package datastructure;
public class DoublyLinkedList {
    private Node head;
    private Node tail;
    private int size;
    private final int capacity = 50;

    // constructor
    public DoublyLinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    public Node getHead() {
        return head;
    }
    public Node getTail() {
        return tail;
    }
    public int getSize() {
        return size;
    }



    public Node add(Node current, Page data){
        while (current != null && current.next != null) {
            removeNode(current.next);
        }

        Node newNode = new Node(data);
        if (current == null){
            head = newNode;
            tail = newNode;
        } else {
            newNode.prev = current;
            current.next = newNode;
            head = newNode;
        }
        size++;

        return newNode;
    }

    public Node insertBetween(Node older, Node newer, Page data) {
        
        Node newNode = new Node(data);
        
        newNode.prev = older;
        newNode.next = newer;
        
        if (older != null) {
            older.next = newNode;
        } else {
            tail = newNode;
        }
    
        if (newer != null) {
            newer.prev = newNode;
        } else {
            head = newNode;
        }
    
        size++;

        return newNode;
    }
   
    public void removeNode(Node node) {

        if (node == null) {
            return;
        }

        if (node.prev != null) {
            node.prev.next = node.next;
        } else {
            tail = node.next;
        }

        if (node.next != null) {
            node.next.prev = node.prev;
        } else {
            head = node.prev;
        }

        node.prev = null;
        node.next = null;

        size--;
    }

    public void removeTail(){
        if (tail != null) {
            removeNode(tail);
        }
    }

    public void clear(){
        head = null;
        tail = null;
        size = 0;
    }


    public Node moveBackward(Node current){ // go to prev
        if(current != null && current.prev != null){
            return current.prev;
        }

        return current; 
    }

    public Node moveForward(Node current){
        if(current != null && current.next != null){
            return current.next;
        }

        return current;
    }


    // node search:
    public boolean contains(String url){
        Node current = head;
        while (current != null) {
            if (current.data.getUrl().equals(url)) {
                return true;
            }
            current = current.prev;
        }
        return false;
    }

    public Node find(String url){
        Node current = head;
        while (current != null) {
            if (current.data.getUrl().equals(url)) {
                return current;
            }
            current = current.prev;
        }
        return null;
    }
}
