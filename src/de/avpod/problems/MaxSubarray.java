package de.avpod.problems;

import java.util.Arrays;

public class MaxSubarray {

    public static void main(String[] args) {
        int[] a = toIntArray(args);
        int startIdx = 0;
        int endIdx = 0;
        int maxSum = Integer.MIN_VALUE;

        int curStartIdx = 0;
        int curEndIdx = 0;
        int curSum = 0;

        for (int i = 0; i < a.length; i++) {
            curEndIdx = i;
            curSum += a[i];

            if (curSum < 0) {
                if (curSum > maxSum) {
                    startIdx = curStartIdx;
                    endIdx = curEndIdx;
                    maxSum = curSum;
                }
                curSum = 0;
                curStartIdx = i + 1;
                continue;
            }

            if (curSum >= maxSum) {
                startIdx = curStartIdx;
                endIdx = curEndIdx;
                maxSum = curSum;
            }
        }

        System.out.println(maxSum);
        System.out.println(Arrays.toString(Arrays.copyOfRange(a, startIdx, endIdx + 1)));
    }

    public static int[] toIntArray(String[] args) {
        int[] result = new int[args.length];
        for (int i = 0; i < args.length; i++) {
            result[i] = Integer.parseInt(args[i]);
        }
        return result;
    }


}
