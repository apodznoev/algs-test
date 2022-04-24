package de.avpod.algorithms;

import java.util.Arrays;
import java.util.Random;

public class PartitionSort {


    public static void main(String[] args) {
        int[] original;
        int[] mySort;
        int[] testSort;

        if(args.length == 0) {
            Random rd = new Random();
            mySort = new int[rd.nextInt(100)];
            testSort = new int[mySort.length];
            original = new int[mySort.length];
            for (int i = 0; i < mySort.length; i++) {
                int number = rd.nextInt(1000);
                mySort[i] = number;
                testSort[i] = number;
                original[i] = number;
            }
        } else {
            mySort = new int[args.length];
            testSort = new int[mySort.length];
            original = new int[mySort.length];
            for (int i = 0; i < mySort.length; i++) {
                int number = Integer.valueOf(args[i]);
                mySort[i] = number;
                testSort[i] = number;
                original[i] = number;
            }
        }

        PartitionSort partitionSort = new PartitionSort();
        partitionSort.sort(mySort);
        Arrays.sort(testSort);

        if (!Arrays.equals(mySort, testSort)) {
            System.err.println("Sort wrong: " + Arrays.toString(mySort));
            System.err.println("Original array: " + Arrays.toString(original));
        } else {
            System.out.println("Sort ok:" + Arrays.toString(mySort));
        }
    }

    public void sort(int[] array) {
        quickSort(array, 0, array.length - 1);
    }

    private void quickSort(int[] array, int start, int end) {
        int partitionIdx = partition(array, start, end);

        if (partitionIdx > start) {
            quickSort(array, start, partitionIdx);
        }

        if (partitionIdx < end) {
            quickSort(array, partitionIdx + 1, end);
        }
    }


    private static int partition(int[] array, int start, int end) {
        int pivot = array[(start + end) >> 1];

        int left = start;
        int right = end;

        while (true) {
            while (array[left] < pivot) left++;
            while (array[right] > pivot) right--;

            if(left >= right){
                return right;
            }

            swap(array, left, right);
            left++;
            right--;
        }
    }

    private static void swap(final int[] array, final int left, final int right) {
        if (left < right) {
            int tmp = array[left];
            array[left] = array[right];
            array[right] = tmp;
        }
    }

}
