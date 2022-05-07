package de.avpod.datastructures;

import java.util.Arrays;

public class ListNode {
    public int val;
    public ListNode next;

    public ListNode() {
    }

    public ListNode(int val) {
        this.val = val;
    }

    public ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        ListNode tmp = this;
        while (tmp != null) {
            result.append(tmp.val).append("->");
            tmp = tmp.next;
        }
        result.deleteCharAt(result.length() - 1);
        result.deleteCharAt(result.length() - 1);
        return result.toString();
    }

    public static ListNode readFromArray(String str) {
        int[] array1 = Arrays.stream(str
                        .replace("]", "")
                        .replace("[", "")
                        .split(","))
                .mapToInt(Integer::parseInt)
                .toArray();

        ListNode n1 = new ListNode(array1[0], null);
        ListNode first1 = n1;
        for (int i = 1; i < array1.length; i++) {
            n1.next = new ListNode(array1[i], null);
            n1 = n1.next;
        }
        return first1;
    }
}
