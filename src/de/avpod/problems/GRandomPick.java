package de.avpod.problems;

import java.util.Random;

public class GRandomPick {
    int[] w;
    double[] probabilities;

    public static void main(String[] args) {
        GRandomPick randomPick = new GRandomPick(new int[]{3, 14, 1, 7});
        System.out.println(randomPick.pickIndex());
        System.out.println(randomPick.pickIndex());
        System.out.println(randomPick.pickIndex());
        System.out.println(randomPick.pickIndex());
    }

    public GRandomPick(int[] w) {
        this.w = w;
        this.probabilities = new double[w.length + 1];
        int sum = 0;
        for (final int j : w) {
            sum += j;
        }

        if (sum == 0) {
            sum = 1;
        }

        probabilities[0] = 0;
        for (int i = 0; i < w.length; i++) {
            double probability = (double) w[i] / (double) sum;
            if (i == 0) {
                probabilities[i + 1] = probability;
                continue;
            }

            probabilities[i +1 ] = probabilities[i] + probability;
        }
    }

    public int pickIndex() {
        double rand = Math.random() / Math.nextDown(1.0);
        return search(probabilities, rand);
    }

    public int search(double[] array, double searchTarget) {
        return search(array, searchTarget, 0, array.length - 1);
    }

    public int search(double[] array, double searchTarget, int startIdx, int endIdx) {
        int mid = (startIdx + endIdx) >> 1;

        if (array[mid] <= searchTarget && searchTarget <= array[mid + 1]) {
            return mid;
        }

        if (array[mid] < searchTarget) {
            return search(array, searchTarget, mid + 1, endIdx);
        }

        return search(array, searchTarget, startIdx, mid - 1);
    }
}
