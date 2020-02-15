package de.avpod;

public class StackQueue<T> {

    public static void main(String[] args) {
        StackQueue<String> queue = new StackQueue<>();
        queue.add("1");
        queue.add("2");
        queue.add("3");
        queue.add("4");
        queue.add("5");
        System.out.println(queue.remove());
        System.out.println(queue.peek());
        System.out.println(queue.peek());
        System.out.println(queue.remove());
        System.out.println(queue.remove());
        queue.add("6");
        queue.add("7");
        queue.add("8");
        System.out.println(queue.remove());
        System.out.println(queue.remove());
        queue.add("9");
        queue.add("10");
        System.out.println(queue.remove());
        System.out.println(queue.remove());
        System.out.println(queue.remove());
        System.out.println(queue.remove());
        System.out.println(queue.peek());
        System.out.println(queue.peek());
        System.out.println(queue.remove());
        System.out.println(queue.isEmpty());
    }

    private final Stack<T> toAdd = new Stack<>();
    private final Stack<T> toRemove = new Stack<>();

    public void add(T value) {
        toAdd.push(value);
    }


    public T remove() {
        if (toRemove.isEmpty()) {
            stackup();
        }

        if (toRemove.isEmpty()) {
            throw new RuntimeException("Queue is empty");
        }

        return toRemove.pop();
    }

    public T peek() {
        if (toRemove.isEmpty()) {
            stackup();
        }

        return toRemove.peek();
    }

    private void stackup() {
        while (!toAdd.isEmpty()) {
            toRemove.push(toAdd.pop());
        }
    }

    public boolean isEmpty() {
        return toAdd.isEmpty() && toRemove.isEmpty();
    }
}
