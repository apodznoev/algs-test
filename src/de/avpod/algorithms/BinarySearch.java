package de.avpod.algorithms;

public class BinarySearch {

    public static void main(String[] args) {
    }

    public int search(int[] array, int searchTarget) {
        return search(array, searchTarget, 0, array.length -1 );
    }

    public int search(int[] array, int searchTarget, int startIdx, int endIdx) {
        if(endIdx < 0 || startIdx >= array.length || startIdx > endIdx) {
            return -1;
        }

        int mid = (startIdx + endIdx) >> 1;

        if(array[mid] == searchTarget){
            return mid;
        }

        if(array[mid] > searchTarget) {
            return search(array, searchTarget, startIdx, mid -1);
        }

        return search(array, searchTarget, mid + 1, endIdx);
    }

}
