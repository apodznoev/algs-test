package de.avpod.datastructures;

import de.avpod.datastructures.SinglyLinkedList.Node;

public class SortedLinkedList {

    public static void main(String[] args) {
        SinglyLinkedList<Integer> list = constructList(args);

        sort(list);

        System.out.println(list);
    }

    private static void sort(SinglyLinkedList<Integer> list) {
        if (list.getSize() <= 1) {
            return;
        }

        if (list.getSize() == 2) {
            Node<Integer> first = list.getHead();
            Node<Integer> second = list.getTail();
            if (first.value <= second.value) {
                return;
            }
            list.setHead(second);
            list.setTail(first);
            return;
        }

        SinglyLinkedList<Integer> sublist1 = list.mirrowedSublist(0, list.getSize() / 2);
        SinglyLinkedList<Integer> sublist2 = list.mirrowedSublist(list.getSize() / 2, list.getSize());
    }


    private static SinglyLinkedList<Integer> constructList(String[] args) {
        SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
        for (String arg : args) {
            list.addToEnd(new Node<>(Integer.parseInt(arg)));
        }
        return list;
    }


}
