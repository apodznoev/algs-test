package de.avpod.problems;

import de.avpod.algorithms.BinarySearch;
import de.avpod.algorithms.PartitionSort;

public class TwoSum {

    public static void main(String[] args) {

    }

    public int[] twoSum(int[] array, int target) {
        PartitionSort partitionSort = new PartitionSort();
        final int[] sortedArray = copy(array);
        partitionSort.sort(sortedArray);

        for (int i = 0; i < sortedArray.length; i++) {
            int searchGoal = target - sortedArray[i];
            BinarySearch binarySearch = new BinarySearch();
            int foundIdx = binarySearch.search(sortedArray, searchGoal, i + 1, sortedArray.length - 1);
            if (foundIdx != -1) {
                return restoreIndexes(array, sortedArray[i], searchGoal);
            }
        }

        return new int[]{};
    }

    private int[] copy(final int[] array) {
        int[] copy = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            copy[i] = array[i];
        }
        return copy;
    }

    private int[] restoreIndexes(final int[] array, final int element1, int element2) {
        int[] result = new int[2];
        boolean element1Found = false;
        boolean element2Found = false;
        for(int i =0; i< array.length; i++) {
            if(!element1Found && array[i] == element1) {
                result[0] = i;
                element1Found = true;
            } else if(!element2Found && array[i] == element2) {
                result[1] = i;
                element2Found = true;
            }

            if(element1Found && element2Found) {
                return result;
            }
        }

        return result;
    }
}
