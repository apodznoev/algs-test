package de.avpod.problems;

public class MaximumSubarray {

    public int maxSubArray(int[] nums) {
        int maxSum = nums[0];
        int curSum = nums[0];

        for(int i = 1; i < nums.length; i++) {
            curSum += nums[i];

            if(nums[i] >= curSum) {
                curSum = nums[i];
            }

            if(curSum >= maxSum) {
                maxSum = curSum;
            }
        }

        return maxSum;
    }
}
