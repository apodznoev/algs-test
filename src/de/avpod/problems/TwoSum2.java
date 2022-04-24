package de.avpod.problems;

import de.avpod.algorithms.BinarySearch;
import de.avpod.algorithms.PartitionSort;

import java.util.HashMap;
import java.util.Map;

public class TwoSum2 {

    public static void main(String[] args) {

    }

    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> cache = fillCache(nums);

        for(int i = 0; i < nums.length; i++) {
            int search = target - nums[i];
            Integer foundIdx = cache.get(search);
            if(foundIdx != null && foundIdx != i) {
                return new int[] {i, foundIdx};
            }
        }

        return new int[]{};
    }
    private Map<Integer, Integer> fillCache(int[] nums) {
        Map<Integer, Integer> map = new HashMap<>(nums.length);

        for(int i=0; i < nums.length; i++) {
            map.put(nums[i], i);
        }

        return map;
    }
}
