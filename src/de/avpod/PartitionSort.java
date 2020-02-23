package de.avpod;

import java.util.Arrays;
import java.util.Random;

public class PartitionSort {


    public static void main(String[] args) {
        Random rd = new Random();
        int[] arr1 = new int[rd.nextInt(1000)];
        int[] arr2 = new int[arr1.length];
        for (int i = 0; i < arr1.length; i++) {
            int number = rd.nextInt();
            arr1[i] = number;
            arr2[i] = number;
        }

        sort(arr1);
        Arrays.sort(arr2);

        if (!Arrays.equals(arr1, arr2)) {
            System.err.println(Arrays.toString(arr1));
        } else {
            System.out.println("Sort ok:" + Arrays.toString(arr1));

        }
    }

    public static void sort(int[] array) {
        quickSort(array, 0, array.length - 1);
    }

    public static void quickSort(int[] array, int start, int end) {
        int partitionIdx = partition(array, start, end);

        if (start < partitionIdx - 1) {
            quickSort(array, start, partitionIdx - 1);
        }

        if (partitionIdx < end) {
            quickSort(array, partitionIdx, end);
        }
    }


    private static int partition(int[] array, int start, int end) {
        int pivot = array[(start + end) >> 1];

        int left = start;
        int right = end;

        while (left <= right) {
            while (array[left] < pivot) left++;
            while (array[right] > pivot) right--;

            if (left <= right) {
                int tmp = array[left];
                array[left] = array[right];
                array[right] = tmp;
                left++;
                right--;
            }
        }

        return left;

    }

}
