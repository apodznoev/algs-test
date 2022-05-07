package de.avpod.problems;

import de.avpod.datastructures.ListNode;

public class RemoveNthFromList {

    public static void main(String[] args) {
        ListNode head = ListNode.readFromArray(args[0]);

        RemoveNthFromList nth = new RemoveNthFromList();
        System.out.println(nth.removeNthFromStart(head, 2));
    }

    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode tmp = head;
        int length = 0;
        while (tmp != null) {
            tmp = tmp.next;
            length++;
        }

        return removeNthFromStart(head, length - n);
    }

    public ListNode removeNthFromStart(ListNode head, int n) {
        if(n == 0) {
            return head.next;
        }

        ListNode result = head;
        ListNode nthPrev = null;
        ListNode nth = head;
        ListNode nthNext = head.next;

        int i = 0;
        while (i++ < n) {

            nthPrev = nth;
            nth = nthNext;
            nthNext = nthNext.next;
        }

        nthPrev.next = nthNext;
        nth.next = null;

        return result;
    }
}
