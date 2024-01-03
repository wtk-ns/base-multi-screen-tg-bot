package utility;

import java.util.LinkedList;

public class LimitedSizeStack<E> {
    private final int maxSize;
    private final LinkedList<E> stack = new LinkedList<>();

    public LimitedSizeStack(int maxSize) {
        this.maxSize = maxSize;
    }

    public void push(E item) {
        stack.addFirst(item);
        if (stack.size() > maxSize) {
            stack.removeLast();
        }
    }

    public E pop() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack is empty");
        }
        return stack.removeFirst();
    }

    public E peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack is empty");
        }
        return stack.getFirst();
    }

    public boolean isEmpty() {
        return stack.isEmpty();
    }

    public int size() {
        return stack.size();
    }
}
