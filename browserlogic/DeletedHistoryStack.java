package browserlogic;

import java.util.Stack;

public class DeletedHistoryStack {

    private Stack<DeletedEntry> stack;

    public DeletedHistoryStack() {
        stack = new Stack<>();
    }

    public void push(DeletedEntry entry) {
        stack.push(entry);
    }

    public DeletedEntry pop() {
        if (stack.isEmpty()) {
            return null;
        }

        return stack.pop();
    }

    public boolean isEmpty() {
        return stack.isEmpty();
    }

    public int size() {
        return stack.size();
    }

    public void clear() {
        stack.clear();
    }
}

