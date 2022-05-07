package de.avpod.problems;

import de.avpod.datastructures.ListNode;

import java.util.Arrays;

public class MergeSortedLists {

    public static void main(String[] args) {
        ListNode first1 = ListNode.readFromArray(args[0]);
        ListNode first2 = ListNode.readFromArray(args[1]);

        MergeSortedLists msl = new MergeSortedLists();

        System.out.println(msl.mergeTwoLists(first1, first2));
    }

    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        if (list1 == null) {
            return list2;
        }
        if (list2 == null) {
            return list1;
        }

        ListNode result;
        ListNode tmp;
        ListNode candidate1;
        ListNode candidate2;
        if (list1.val <= list2.val) {
            tmp = new ListNode(list1.val, null);
            candidate1 = list1.next;
            candidate2 = list2;
        } else {
            tmp = new ListNode(list2.val, null);
            candidate1 = list1;
            candidate2 = list2.next;
        }
        result = tmp;

        while (true) {
            if (candidate1 == null) {
                tmp.next = candidate2;
                break;
            }

            if (candidate2 == null) {
                tmp.next = candidate1;
                break;
            }

            if (candidate1.val <= candidate2.val) {
                tmp.next = new ListNode(candidate1.val);
                candidate1 = candidate1.next;
            } else {
                tmp.next = new ListNode(candidate2.val);
                candidate2 = candidate2.next;
            }
            tmp = tmp.next;
        }

        return result;
    }
}
