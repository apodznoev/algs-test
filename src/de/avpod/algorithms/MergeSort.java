package de.avpod.algorithms;

import java.util.Arrays;
import java.util.Random;

public class MergeSort {


    public static void main(String[] args) {
        Random rd = new Random();
        int[] arr1 = new int[rd.nextInt(1000)];
        int[] arr2 = new int[arr1.length];
        int[] original = new int[arr1.length];
        for (int i = 0; i < arr1.length; i++) {
            int number = rd.nextInt(10000);
            arr1[i] = number;
            arr2[i] = number;
            original[i] = number;
        }

        MergeSort sorter = new MergeSort();
        sorter.sort(arr1);
        Arrays.sort(arr2);

        if (!Arrays.equals(arr1, arr2)) {
            System.err.println(Arrays.toString(original) + " - original");
            System.err.println(Arrays.toString(arr1) + " - sorted");
        } else {
            System.out.println("Sort ok:" + Arrays.toString(arr1));

        }
    }

    public void sort(int[] array) {
        if (array.length < 2) {
            return;
        }

        int[] aux = new int[array.length];
        mergeSort(array, aux, 0, array.length - 1);
    }

    private static void mergeSort(int[] array, int[] aux, int start, int end) {
        int mid = (end + start) >> 1;

        if (start == end)
            return;

        mergeSort(array, aux, start, mid);
        mergeSort(array, aux, mid + 1, end);
        merge(array, aux, start, mid, end);
    }

    private static void merge(final int[] array,
                              final int[] aux,
                              final int start,
                              final int mid,
                              final int end) {
        System.arraycopy(array, start, aux, start, end - start + 1);

        int leftPointer = start;
        int rightPointer = mid + 1;
        int writePointer = leftPointer;

        while (leftPointer <= mid && rightPointer <= end) {
            if (aux[leftPointer] <= aux[rightPointer]) {
                array[writePointer] = aux[leftPointer];
                leftPointer++;
            } else {
                array[writePointer] = aux[rightPointer];
                rightPointer++;
            }

            writePointer++;
        }

        while (leftPointer <= mid) {
            array[writePointer] = aux[leftPointer];
            leftPointer++;
            writePointer++;
        }
    }
}
