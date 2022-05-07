package de.avpod.problems;

import de.avpod.datastructures.ListNode;

public class RemoveNthFromList {

    public static void main(String[] args) {
        ListNode head = ListNode.readFromArray(args[0]);

        RemoveNthFromList nth = new RemoveNthFromList();
        System.out.println(nth.removeNthFromStart(head, 2));
    }

    public ListNode removeNthFromStart(ListNode head, int n) {
        ListNode result = head;
        ListNode nthPrev = null;
        ListNode nth = head;
        ListNode nthNext = head.next;

        ListNode tmp = null;

        int i = 0;
        while (i++ < n) {
            tmp = nthNext;

            nthPrev = nth;
            nth = nthNext;
            nthNext = tmp.next;
        }

        nthPrev.next = nthNext;
        nth.next = null;

        return result;
    }
}
