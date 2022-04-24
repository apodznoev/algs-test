package de.avpod.datastructures;

import de.avpod.datastructures.SinglyLinkedList.Node;

import java.util.HashMap;
import java.util.Map;

public class CorruptedLinkedList {

    public static void main(String[] args) {
        SinglyLinkedList<String> list = constructList(args);

        Node<String> loopBegin = findLoop(list);
        System.out.println(loopBegin == null ? "No loop" : "Loop at:" + loopBegin.value);
    }


    private static SinglyLinkedList<String> constructList(String[] args) {
        SinglyLinkedList<String> list = new SinglyLinkedList<>();
        Map<String, Node<String>> nodes = new HashMap<>();
        for (String arg : args) {
            nodes.putIfAbsent(arg, new Node<>(arg));
            list.addToEnd(nodes.get(arg));
        }
        return list;
    }

    public static <T extends Comparable<T>> Node<T> findLoop(SinglyLinkedList<T> list) {
        if (list.isEmpty())
            return null;

        Node<T> turtle = list.getHead();
        Node<T> hare = list.getHead();

        while (true) {
            turtle = turtle.next;

            Node<T> tmp = hare.next;
            if (tmp == null || tmp.next == null) {
                return null;
            }

            hare = tmp.next;

            if (hare == turtle) {
                return findLoopStart(list.getHead(), hare);
            }

        }
    }

    private static <T> Node<T> findLoopStart(Node<T> start, Node<T> meetingPoint) {
        while (start != meetingPoint) {
            start = start.next;
            meetingPoint = meetingPoint.next;
        }
        return meetingPoint;
    }
}
