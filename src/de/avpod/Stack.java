package de.avpod;

public class Stack<T> {

    private StackNode<T> top;

    public void push(T value) {
        this.top = new StackNode<T>(value, top);
    }

    public T peek() {
        return this.top.value;
    }

    public T pop() {
        if (top == null) {
            throw new RuntimeException("Stack is empty");
        }

        T value = top.value;
        this.top = top.previous;
        return value;
    }

    public boolean isEmpty() {
        return this.top == null;
    }


    private class StackNode<T> {
        private final T value;
        private final StackNode<T> previous;

        private StackNode(T value, StackNode<T> previous) {
            this.value = value;
            this.previous = previous;
        }
    }
}
