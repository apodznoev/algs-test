package de.avpod.problems;

import de.avpod.util.ConsoleUtil;

import java.util.Comparator;
import java.util.PriorityQueue;

public class MaxGridScoreWithCost {

    public static void main(String[] args) {
        MaxGridScoreWithCost task = new MaxGridScoreWithCost();
        int[][] grid = ConsoleUtil.firstArgToIntMatrixWithBrackets(args);
        System.out.println(task.maxPoints(grid));
    }

    int[][] grid;
    int m;
    int n;


    public long maxPoints(int[][] points) {
        this.grid = points;
        this.m = points.length;
        this.n = points[0].length;

        return findMaxScore(-1, 0);
    }

    private long findMaxScore(final int prevPosition, final int rowNumber) {
        PriorityQueue<Pair> queue = new PriorityQueue<>((o1, o2) -> Integer.compare(o2.left, o1.left));

        final int[] row = grid[rowNumber];
        for (int i = 0; i < n; i++) {
            queue.offer(new Pair(row[i], i));
        }

        int maxRowPoints = 0;
        long maxTotalScore = Integer.MIN_VALUE;
        int bestPosition = 0;

        while (!queue.isEmpty()) {
            Pair next = queue.poll();
            int position = next.right;
            int cellPoints = next.left - (prevPosition == -1 ? 0 : Math.abs(prevPosition - position));
            if (cellPoints - maxRowPoints <= Math.abs(bestPosition - position)) {
                continue;
            }

            long rowScore;
            if (rowNumber < m - 1) {
                rowScore = cellPoints + findMaxScore(position, rowNumber + 1);
            } else {
                rowScore = cellPoints;
            }

            if (rowScore > maxTotalScore) {
                maxTotalScore = rowScore;
                bestPosition = position;
                maxRowPoints = cellPoints;
            }
        }

        return maxTotalScore;
    }

    private record Pair(int left, int right) {
    }

}
