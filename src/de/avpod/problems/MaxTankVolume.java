package de.avpod.problems;

import de.avpod.util.ConsoleUtil;

public class MaxTankVolume {

    public static void main(String[] args) {
        MaxTankVolume mtv = new MaxTankVolume();
        System.out.println(mtv.maxArea(ConsoleUtil.firstArgToIntArrayWithBrackets(args)));
    }

    public int maxArea(int[] height) {
        if (height.length < 2) {
            return 0;
        }

        int left = 0;
        int right = height.length - 1;

        while (height[left] == 0) {++left;}
        while (height[right] == 0) {--right;}

        int volume = 0;

        while (left < right) {
            int newVolume = calcVolume(height, left, right);
            if (newVolume > volume) {
                volume = newVolume;
            }

            if (height[left] <= height[right]) {
                ++left;
            } else {
                --right;
            }
        }

        return volume;
    }

    private int calcVolume(final int[] heights, final int left, int right) {
        return Math.min(heights[left], heights[right]) * (right - left);
    }
}
