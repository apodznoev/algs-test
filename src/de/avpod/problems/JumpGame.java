package de.avpod.problems;

import de.avpod.util.ConsoleUtil;

import java.util.Random;

public class JumpGame {
    public static void main(String[] args) {
        JumpGame jumpGame = new JumpGame();
        System.out.println(jumpGame.canJump(ConsoleUtil.firstArgToIntArrayWithBrackets(args)));
    }
    public boolean canJump(int[] nums) {
        return solvePuzzle(nums, new boolean[nums.length], 0);
    }

    private boolean solvePuzzle(final int[] nums, final boolean[] visited, final int index) {
        if (nums.length - 1 == index) {
            return true;
        }

        if (index >= nums.length) {
            return false;
        }

        if (visited[index]) {
            return false;
        }

        int jumps = nums[index];
        for (int i = jumps; i > 0; i--) {
            if (solvePuzzle(nums, visited, index + i)) {
                return true;
            }
        }
        visited[index] = true;

        return false;
    }
}
