package de.avpod.algorithms;

import java.util.Arrays;
import java.util.Random;

public class MergeSort {


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

    private static void merge(int[] array, int[] aux, int start, int mid, int end) {
        for (int i = start; i <= end; i++) {
            aux[i] = array[i];
        }

        int startLeft = start;
        int startRight = mid + 1;

        int current = startLeft;
        while (startLeft <= mid && startRight <= end) {
            if (aux[startLeft] <= aux[startRight]) {
                array[current] = aux[startLeft];
                startLeft++;
            } else {
                array[current] = aux[startRight];
                startRight++;
            }
            current++;
        }

        while (startLeft <= mid) {
            array[current] = aux[startLeft];
            current++;
            startLeft++;
        }
    }
}
