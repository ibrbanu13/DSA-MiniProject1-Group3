package datastructure;
public class DoublyLinkedList {
    private Node head; // new page
    private Node tail; // old page
    private int size;
    private final int capacity = 50;

    // constructor
    public DoublyLinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    // getters
    public Node getHead() {
        return head;
    }
    public Node getTail() {
        return tail;
    }
    public int getSize() {
        return size;
    }


    // node addition:

    /* in BrowserHistory class visit(url):
            - add the page to the list with add(Node current, Page data) method
            - if the size exceeds the capacity, remove the tail node (oldest page) with removeTail() method
    */
    public Node add(Node current, Page data){ // allegory of visit(url) method in BrowserHistory class
        // clear forward history from the current pointer forward
        while (current != null && current.next != null) {
            removeNode(current.next); // removeNode method already decreases the size
        }

        // add the new page to the list as the next page
        Node newNode = new Node(data);
        if (current == null){
            head = newNode;
            tail = newNode;
        } else {
            newNode.prev = current;
            current.next = newNode;
            head = newNode; // update head to the new node
        }
        size++;

        return newNode; // shifting the current pointer to the new node
    }

    public Node insertBetween(Node older, Node newer, Page data) {
        
        Node newNode = new Node(data);
        
        // Connect the new node to its neighbors
        newNode.prev = older;
        newNode.next = newer;
        
        // Update the older neighbor
        if (older != null) {
            older.next = newNode;
        } else {
            // No older neighbor means the new node is the oldest
            tail = newNode;
        }
    
        // Update the newer neighbor
        if (newer != null) {
            newer.prev = newNode;
        } else {
            // No newer neighbor means the new node is the newest
            head = newNode;
        }
    
        size++;

        return newNode;
    }
   
    // node removal:
    public void removeNode(Node node) {

        if (node == null) {
            return;
        }

        // Update the older neighbor
        if (node.prev != null) {
            node.prev.next = node.next;
        } else {
            tail = node.next;
        }

        // Update the newer neighbor
        if (node.next != null) {
            node.next.prev = node.prev;
        } else {
            head = node.prev;
        }

        // Disconnect the removed node
        node.prev = null;
        node.next = null;

        size--;
    }

    // we already have removeNode(Node node) method, so we can use it to remove the tail node (oldest page) whenever the capacity is exceeded
    
    // i am keeping this method just in case nevertheless
    // removeTail() == removeNode(tail)
    public void removeTail(){ // removes the tail node (oldest page) whenever the capacity is exceeded
        if (tail != null) {
            removeNode(tail);
        }
    }

    public void clear(){
        head = null;
        tail = null;
        size = 0;
    }


    // node traversal:
    public Node moveBackward(Node current){ // go to prev
        if(current != null && current.prev != null){
            return current.prev;
        }

        return current; // do not change the page if there is no previous page (oldest page)
                        // basically, prev page = current since there is no any page to go to
    }

    public Node moveForward(Node current){ // go to next
        if(current != null && current.next != null){
            return current.next;
        }

        return current; // do not change the page if there is no next page (newest page)
                        // basically, next page = current since there is no any page to go to
    }


    // node search:
    public boolean contains(String url){
        Node current = head; // from newest
        while (current != null) {
            if (current.data.getUrl().equals(url)) {
                return true;
            }
            current = current.prev; // to the oldest page (tail)
        }
        return false;
    }

    public Node find(String url){
        Node current = head; // from newest
        while (current != null) {
            if (current.data.getUrl().equals(url)) {
                return current;
            }
            current = current.prev; // to the oldest page (tail)
        }
        return null;
    }
}
