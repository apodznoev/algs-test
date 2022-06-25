package de.avpod.problems;

public class MaxGridScoreWithCost {
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
        final int[] row = grid[rowNumber];
        int maxRowPoints = Integer.MIN_VALUE;
        long maxTotalScore = Integer.MIN_VALUE;
        int bestIndex = 0;

        for (int i = 0; i < n; i++) {
            int cellPoints = row[i] - (prevPosition == -1 ? 0 : Math.abs(prevPosition - i));
            if (maxRowPoints  >= Math.abs(bestIndex - i) + cellPoints) {
                continue;
            }

            long rowScore;
            if (rowNumber < m - 1) {
                rowScore = cellPoints + findMaxScore(i, rowNumber + 1);
            } else {
                rowScore = cellPoints;
            }

            if (rowScore > maxTotalScore) {
                maxTotalScore = rowScore;
                bestIndex = i;
                maxRowPoints = cellPoints;
            }
        }
        return maxTotalScore;
    }
}
