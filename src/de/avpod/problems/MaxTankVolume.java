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

        int volume = calcVolume(height, left, right);
        int leftH = height[left];
        int rightH = height[right];

        boolean moveLeftBar = height[left] <= height[right];
        if (moveLeftBar) {
            ++left;
        } else {
            --right;
        }

        while (left < right) {
            if (moveLeftBar) {
                if (height[left] <= leftH) {
                    ++left;
                    continue;
                }

                int newVolume = calcVolume(height, left, right);
                if (newVolume > volume) {
                    volume = newVolume;
                    leftH = height[left];
                }
            } else {
                if (height[right] <= rightH) {
                    --right;
                    continue;
                }

                int newVolume = calcVolume(height, left, right);
                if (newVolume > volume) {
                    volume = newVolume;
                    rightH = height[right];
                }
            }

            moveLeftBar = height[left] <= height[right];
            if (moveLeftBar) {
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
