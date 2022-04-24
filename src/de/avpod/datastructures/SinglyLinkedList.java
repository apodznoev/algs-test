package de.avpod.datastructures;

public class SinglyLinkedList<T extends Comparable<T>> {
    private int size;
    private Node<T> head;
    private Node<T> tail;
    private Node<T> preTail;

    public boolean isEmpty() {
        return size == 0;
    }

    public void addToEnd(Node<T> newNode) {
        if (size == 0) {
            head = newNode;
            size++;
            return;
        }

        if (size == 1) {
            preTail = newNode;
            head.next = preTail;
            size++;
            return;
        }

        if (size == 2) {
            tail = newNode;
            preTail.next = tail;
            size++;
            return;
        }

        if (tail.next != null) {
            throw new IllegalStateException("Not allowed to add in the middle of the list");
        }

        preTail = tail;
        tail = newNode;
        size++;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        if (isEmpty())
            return "empty";

        Node<T> next = this.head;

        while (true) {
            stringBuilder.append(next.value);

            next = next.next;

            if (next != null) {
                stringBuilder.append(" -> ");
            } else {
                break;
            }
        }

        return stringBuilder.toString();
    }



    public int getSize() {
        return size;
    }

    public Node<T> getHead() {
        return head;
    }

    public Node<T> getTail() {
        return tail;
    }

    public void setHead(Node<T> newHead) {
        newHead.next = this.head.next;
        this.head = newHead;
    }

    public void setTail(Node<T> newTail) {
        if(this.head.next == this.tail) {
            this.head.next = newTail;
        }

        this.tail = newTail;
        this.preTail.next = this.tail;
    }

    public SinglyLinkedList<T> mirrowedSublist(int startInclusive, int endExclusive) {
        int k = 0;
        Node<T> start = this.head;
        while (k != startInclusive) {
            start = start.next;
            k++;
        }
        SinglyLinkedList<T> sublist = new SinglyLinkedList<>();
        sublist.addToEnd(start);
        k++;
        while (k < endExclusive) {
            start = start.next;
            sublist.addToEnd(start);
            k++;
        }
        return sublist;
    }

    public static class Node<T> {
        final T value;
        Node<T> next;

        Node(T value) {
            this.value = value;
            this.next = null;
        }
    }
}
