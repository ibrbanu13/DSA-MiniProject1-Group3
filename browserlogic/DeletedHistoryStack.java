package browserlogic;

import java.util.Stack;

public class DeletedHistoryStack {

    private Stack<DeletedEntry> stack;

    // Constructor
    public DeletedHistoryStack() {
        stack = new Stack<>();
    }

    // Push a deleted entry onto the stack
    public void push(DeletedEntry entry) {
        stack.push(entry);
    }

    // Remove and return the most recently deleted entry
    public DeletedEntry pop() {
        if (stack.isEmpty()) {
            return null;
        }

        return stack.pop();
    }

    // Check whether there are deleted pages
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    // Get the number of deleted entries
    public int size() {
        return stack.size();
    }

    // Remove all deleted entries
    public void clear() {
        stack.clear();
    }
}

